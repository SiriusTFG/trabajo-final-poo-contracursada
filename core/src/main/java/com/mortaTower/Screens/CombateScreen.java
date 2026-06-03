package com.mortaTower.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mortaTower.Controlador.PausaControlador;
import com.mortaTower.Controlador.RecompensaControlador;
import com.mortaTower.Hilo.GoblinAtacante;
import com.mortaTower.Main;
import com.mortaTower.Modelo.CombateModelo;
import com.mortaTower.Modelo.CombateModelo.Resultado;
import com.mortaTower.Modelo.Entidad;
import com.mortaTower.Modelo.Goblin;
import com.mortaTower.Modelo.Habilidad;
import com.mortaTower.Modelo.Heroe;
import com.mortaTower.Vista.CombateVista;


public class CombateScreen extends Screens {

    private CombateModelo modelo;
    private PausaControlador pausaControlador;
    private boolean derrotaProcesada = false;
    private float tiempoResultado = 0f;
    private boolean esperandoRecompensa = false;

    private RecompensaControlador recompensaControlador;

    private CombateVista vista;     

    private boolean victoriaProcesada = false;
    private boolean danioAplicado;
    private int nivel;

    private String nombreHeroe;
    private float contador = 0;
    private float tiempoPausa;
    private boolean proximoTurnoJugador;

    private boolean pausa = false;

    private String resultado;

    //Goblin
    private Goblin goblin;
    private GoblinAtacante hiloGoblin;
    private float tempRecuperacionHeroe = 0f;
    private boolean heroeAturdido = false;

    //constructor
    public CombateScreen(Main game, String nombreHeroe, int nivel) {
        super(game);
        this.nombreHeroe = nombreHeroe;
        this.nivel = nivel;
        Heroe heroe = game.getPartidaActual().getHeroe();
        modelo = new CombateModelo(heroe, nivel);
        recompensaControlador = new RecompensaControlador(game, this.nivel);
    }

    @Override
    public void show() {
        vista = new CombateVista(viewport,game,nivel);
        pausaControlador = new PausaControlador(game, vista.getStage(), nivel);

        // un input a la vez
        setInput(vista.getStage());
        
        String[] nombresHabilidades = modelo.getNombreHabilidadesActuales();
        String[] tiposHabilidades = modelo.getTipoHabilidadesActuales();
        String[] descripciones = modelo.getDescripcionesHabilidadesActuales();
        int[] danio = modelo.getDaniosActuales();
        int[] mana = modelo.getConsumosActuales();

        vista.cargarInventarioHabilidades(nombresHabilidades, tiposHabilidades);

        vista.getBtnPausa().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                
                pausaControlador.setOpciones(true);
                setInput(pausaControlador.getVista().getStage());
            }
        });

        //manejo del hilo en el combate
        goblin = new Goblin();
        hiloGoblin = new GoblinAtacante();

        //se inicia el hilo secundario
        hiloGoblin.iniciar(10, 15, () -> {
            //metodo de libgdx para dejar que el hilo secundario modifique el hilo principal
            Gdx.app.postRunnable(() -> {
                if (!pausa && modelo.getResultado() == CombateModelo.Resultado.NINGUNO) {
                    float posicionX = Gdx.graphics.getWidth(); //arranca por la derecha
                    float posicionY = WORLD_HEIGHT * 0.40f;
                    goblin.prepararCorrida(posicionX, posicionY);
                    System.out.println("el goblin entra al combate");
                }
            });
        });

        listenersHabilidades(descripciones, danio, mana); 
    }

    private void setInput(Stage stageActivo) {
        Gdx.input.setInputProcessor(stageActivo);
    }

    private void listenersHabilidades(String[] descripcion, int[] danio, int[] mana) {
        int cantidad = vista.getCantidadHabilidades();

        for (int i = 0; i < cantidad; i++) {

            final int index = i;
            //final String desc = descripciones[i];
            vista.getBotonHabilidad(i).addListener(new ClickListener() {
               
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                    vista.setTextoDescripcion(descripcion[index]);
                    vista.setTextoDanio(Integer.toString(danio[index]));
                    vista.setTextoMana(Integer.toString(mana[index]));
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                    vista.setTextoDescripcion(""); // Limpiamos el texto
                    vista.setTextoDanio("");
                    vista.setTextoMana("");
                }
                
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    vista.setTextoDescripcion("");
                    manejarEntradaJugador(index); 
                }
            });
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        vista.getStage().act(delta);
        vista.getStage().draw();

        spriteBatch.begin();
        
        String mensaje = modelo.getMensajeCombate();

        vista.dibujarSprite(spriteBatch, 
            modelo.getHeroe().getEstadoActual(), 
            modelo.getHeroe().getStatetime(), 
            nombreHeroe,
            modelo.getEnemigo().getEstadoActual(), 
            modelo.getEnemigo().getStatetime(), 
            modelo.getEnemigo().getNombre());
        vista.comentarista(spriteBatch, mensaje);

        //temporizador?
        vista.resultado(spriteBatch, resultado); 

        if (heroeAturdido) {
            tempRecuperacionHeroe -= delta;
            if(tempRecuperacionHeroe <= 0) {
                modelo.getHeroe().setEstadoActual(Entidad.Estado.PARADO);
                heroeAturdido = false;
            }
        }

        //LOGICA Y DIBUJADO DEL GOBLIN
       if (goblin != null && goblin.getActivo() && !pausa) {
            
            float duracionAtaque = vista.getTiempoAnimacionGoblin(Goblin.EstadoGoblin.ATACANDO);
            boolean huboImpacto = goblin.actualizar(delta, modelo.getHeroe(), WORLD_WIDTH * 0.20f, WORLD_WIDTH + 100, WORLD_HEIGHT * 0.40f, duracionAtaque);

            if (huboImpacto) {
                modelo.getHeroe().setEstadoActual(Entidad.Estado.DANIO);
                heroeAturdido = true;
                tempRecuperacionHeroe = vista.getTiempoAnimacionHeroe(Entidad.Estado.DANIO);
                modelo.mostrarMensaje("¡Un duende te robó " + goblin.getDanio() + " de vida!");
            }
            vista.dibujarGoblin(spriteBatch, goblin.getX(), goblin.getY(), goblin.getStateTime(), goblin.getEstadoActual());
        }

        spriteBatch.end();

        vista.dibujarInterfazHeroe(spriteBatch, modelo.getHeroe().getVidaActual(), modelo.getHeroe().getVidaMax(), modelo.getHeroe().getManaActual(), modelo.getHeroe().getManaMax());
        vista.dibujarInterfazEnemigo(spriteBatch, modelo.getEnemigo().getVidaActual(), modelo.getEnemigo().getVidaMax(), modelo.getEnemigo().getManaActual(), modelo.getEnemigo().getManaMax());

        if (victoriaProcesada) {

            recompensaControlador.render(delta);

            return;
        }

        if (pausaControlador.opciones()) {
    
            pausaControlador.render(delta);
            return;
        }


        modelo.getHeroe().actualizarAnimacion(delta);
        if (modelo.getEnemigo() != null) {
            modelo.getEnemigo().actualizarAnimacion(delta);
        }

        switch (modelo.getTurnoActual()) {

            case JUGADOR -> {
                vista.mostrarInventario();

            }

            case ENEMIGO -> {
                contador += delta;
                if (contador >= tiempoPausa) {
                    ejecutarTurnoEnemigo();
                    modelo.setTurnoActual(CombateModelo.Turno.PROCESANDO);
                    proximoTurnoJugador = true;
                    contador = 0;
                }
            }

            case PROCESANDO -> {
                contador += delta;
                if (contador >= tiempoPausa / 2.0f && !danioAplicado) {
                    if (proximoTurnoJugador == false) {
                    modelo.getHeroe().realizarTurno(modelo.getEnemigo());
                } else {
                    modelo.getEnemigo().realizarTurno(modelo.getHeroe());
                }                                     
                    danioAplicado = true;
                }
                if (contador >= tiempoPausa) {
                    modelo.getHeroe().setEstadoActual(Entidad.Estado.PARADO);
                    modelo.getEnemigo().setEstadoActual(Entidad.Estado.PARADO);

                    if (modelo.getEnemigo().getVidaActual() <= 0) {
                        modelo.setResultado(CombateModelo.Resultado.VICTORIA);
                        modelo.getEnemigo().setEstadoActual(Entidad.Estado.MUERTE);

                    } else if (modelo.getHeroe().getVidaActual() <= 0) {
                        modelo.setResultado(CombateModelo.Resultado.DERROTA);
                        modelo.getHeroe().setEstadoActual(Entidad.Estado.MUERTE);

                    } else {
                        modelo.setTurnoActual(
                            proximoTurnoJugador
                                ? CombateModelo.Turno.JUGADOR
                                : CombateModelo.Turno.ENEMIGO
                        );
                    }

                    contador = 0;
                    modelo.mostrarMensaje("");
                }
            }
        }

        if (modelo.getResultado() == Resultado.VICTORIA   && !esperandoRecompensa) {
            game.audio.play(6);
            resultado = "victoria";
            esperandoRecompensa = true;
            tiempoResultado = 0f;
        }

        if (modelo.getResultado() == Resultado.DERROTA && !derrotaProcesada && !esperandoRecompensa) {
            game.audio.play(7);
            resultado = "derrota";
            esperandoRecompensa = true;
            tiempoResultado = 0f;
        }

        if (esperandoRecompensa) {
            tiempoResultado += delta;

            if (tiempoResultado >= 3f) {

                resultado = null;

                if (modelo.getResultado() == Resultado.VICTORIA) {

                    victoriaProcesada = true;
                    recompensaControlador.mostrar();
                    setInput(recompensaControlador.getVista().getStage());

                } else if (modelo.getResultado() == Resultado.DERROTA) {

                    pausaControlador.setOpciones(true);
                    setInput(pausaControlador.getVista().getStage());
                    derrotaProcesada = true;

                }

                esperandoRecompensa = false;
            }

            return;
        }
    }

    private void manejarEntradaJugador(int indiceHabilidad) {
        Habilidad habilidad = modelo.getHeroe().getHabilidades()[indiceHabilidad];

        if (habilidad == null || !habilidad.puedeUsarse(modelo.getHeroe())) {
            return;
        }

        System.out.println("Turno del " + modelo.getHeroe().getNombre());
        this.danioAplicado = false; //para reinicar Flag

        modelo.getHeroe().setEstadoActual(Entidad.Estado.ATAQUE);
        modelo.getEnemigo().setEstadoActual(Entidad.Estado.DANIO);
        this.tiempoPausa = vista.getTiempoAnimacionHeroe(Entidad.Estado.ATAQUE);

        modelo.getHeroe().seleccionarHabilidad(habilidad);

        modelo.mostrarMensaje(modelo.getHeroe().getNombre() + " usó " + habilidad.getNombre() + "!");

        proximoTurnoJugador = false;
        vista.ocultarInventario();
        modelo.setTurnoActual(CombateModelo.Turno.PROCESANDO);
        contador = 0;
    }
    

    private void ejecutarTurnoEnemigo() {
        if (modelo.getEnemigo().getVidaActual() > 0) {
            System.out.println("Turno del " + modelo.getEnemigo().getNombre());
            this.danioAplicado = false;
            modelo.getEnemigo().setEstadoActual(Entidad.Estado.ATAQUE);
            modelo.getHeroe().setEstadoActual(Entidad.Estado.DANIO);
            this.tiempoPausa = vista.getTiempoAnimacionEnemigo(Entidad.Estado.ATAQUE);
           // modelo.getEnemigo().realizarTurno(modelo.getHeroe());

            modelo.mostrarMensaje( modelo.getEnemigo().getNombre() + " usó " + modelo.getEnemigo().getUltimaHabilidadUsada() + "!");
        }
    }
    
    @Override
    public void dispose() {
        super.dispose();
        vista.cerrar();
        if (hiloGoblin != null) {
            hiloGoblin.pararHilo();
        }
    }
}