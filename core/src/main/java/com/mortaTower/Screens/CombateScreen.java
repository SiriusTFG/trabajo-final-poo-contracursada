package com.mortaTower.Screens;

import java.sql.SQLException;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mortaTower.DAO.PartidaDao;
import com.mortaTower.DAO.SpriteDao;
import com.mortaTower.Main;
import com.mortaTower.Modelo.CombateModelo;
import com.mortaTower.Modelo.CombateModelo.Resultado;
import com.mortaTower.Modelo.DatosSprite;
import com.mortaTower.Modelo.Entidad;
import com.mortaTower.Modelo.Habilidad;
import com.mortaTower.Modelo.Heroe;
import com.mortaTower.Modelo.Partida;
import com.mortaTower.Vista.CombateVista;

public class CombateScreen extends Screens {

    // GAMEPLAY
    private final int nivelesTotal = 5;
    private CombateModelo modelo;
    private int nivel;
    private String nombreHeroe;

    // ESTADO UI
    public enum FlujoCombate { COMBATE, PAUSA, RESULTADO, RECOMPENSA }
    private FlujoCombate flujoActual = FlujoCombate.COMBATE;

    // ESTADO DE FLUJO
    private String resultado;
    private boolean esperandoRecompensa = false;
    private boolean derrotaProcesada = false;

    private float contador = 0f;
    private float tiempoPausa;
    private boolean proximoTurnoJugador;
    private boolean danioAplicado;

    // ESTADO HEROE
    private float tempRecuperacionHeroe = 0f;
    private boolean heroeAturdido = false;

    // TRANCICION
    private float tiempoResultado = 0f;

    // UI + CONTROLADRORES
    private CombateVista vista;
    private PausaControlador pausaControlador;
    private RecompensaScreen recompensaControlador;
    private GoblinControlador goblinControlador;

    public CombateScreen(Main game, String nombreHeroe, int nivel) {
        super(game);

        this.nombreHeroe = nombreHeroe;
        this.nivel = nivel;

        Heroe heroe = game.getPartidaActual().getHeroe();
        modelo = new CombateModelo(heroe, nivel);
        recompensaControlador = new RecompensaScreen(game, this.nivel);

        System.out.println("COMBATE CREADO");
        game.audio.loop(nivel);
    }

    @Override
    public void show() {

        iniciarVista();
        iniciarHabilidades();

        pausaControlador = new PausaControlador(game, vista.getStage(), nivel);
        
        vista.getBtnPausa().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.audio.play(0);
                flujoActual = FlujoCombate.PAUSA;
                pausaControlador.setOpciones(true);
                setInput(pausaControlador.getVista().getStage());
            }
        });

        //manejo del hilo en el combate
        goblinControlador = new GoblinControlador();
        goblinControlador.iniciar(game, modelo, pausaControlador, viewport);
    }

    // INICIA VISTA DEL COMBATE
    private void iniciarVista(){

        SpriteDao spriteDao = new SpriteDao();

        List<DatosSprite> spritesHeroe = null;
        List<DatosSprite> spritesEnemigo = null;

        try {
            spritesHeroe = spriteDao.obtenerSpritesPorHeroe(modelo.getHeroe().getId());
            spritesEnemigo = spriteDao.obtenerSpritesPorEnemigo(modelo.getEnemigo().getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        vista = new CombateVista(viewport,game,nivel,spritesHeroe, spritesEnemigo);
        setInput(vista.getStage());
    }

    // INICIA HABILIDADES DEL COMBATE
    private void iniciarHabilidades(){

        String[] nombresHabilidades = modelo.getNombreHabilidadesActuales();
        String[] tiposHabilidades = modelo.getTipoHabilidadesActuales();
        String[] descripciones = modelo.getDescripcionesHabilidadesActuales();
        int[] danio = modelo.getDaniosActuales();
        int[] mana = modelo.getConsumosActuales();

        vista.cargarInventarioHabilidades(nombresHabilidades, tiposHabilidades);

        int cantidad = vista.getCantidadHabilidades();

        for (int i = 0; i < cantidad; i++) {

            final int index = i;

            vista.getBotonHabilidad(i).addListener(new ClickListener() {
               
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                    vista.setTextoDescripcion(descripciones[index]);
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

    private void setInput(Stage stageActivo) {
        Gdx.input.setInputProcessor(stageActivo);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        vista.getStage().act(delta);
        vista.getStage().draw();


        if(modelo.getResultado() == Resultado.NINGUNO){
        
            vista.getStsCombate().setVisible(true);
            vista.dibujarInterfazHeroe(spriteBatch, nombreHeroe, modelo.getHeroe().getVidaActual(), modelo.getHeroe().getVidaMax(), modelo.getHeroe().getManaActual(), modelo.getHeroe().getManaMax());
            vista.dibujarInterfazEnemigo(spriteBatch, modelo.getEnemigo().getNombre(), modelo.getEnemigo().getVidaActual(), modelo.getEnemigo().getVidaMax(), modelo.getEnemigo().getManaActual(), modelo.getEnemigo().getManaMax());
        } else {

            vista.getStsCombate().setVisible(false);

        }

        spriteBatch.begin();
        
        String mensaje = modelo.getMensajeCombate();

        vista.comentarista(spriteBatch, mensaje);

        //if()
        vista.resultado(spriteBatch, resultado); 

        // Logica y Dibujado del Goblin
        goblinControlador.update(delta, modelo, pausaControlador, vista, game, WORLD_WIDTH, WORLD_HEIGHT);
        goblinControlador.render(spriteBatch, vista);

        vista.dibujarSprite(spriteBatch, modelo.getHeroe().getEstadoActual(), modelo.getHeroe().getStatetime(), modelo.getEnemigo().getEstadoActual(), modelo.getEnemigo().getStatetime(), 
            modelo.getEnemigo().getNombre(),
            modelo.getEnemigo().getFaseVisual()
            ); 


        if (heroeAturdido) {
            tempRecuperacionHeroe -= delta;
            if(tempRecuperacionHeroe <= 0) {
                modelo.getHeroe().setEstadoActual(Entidad.Estado.PARADO);
                heroeAturdido = false;
            }
        }

        spriteBatch.end();

        modelo.getHeroe().actualizarAnimacion(delta);

        if (modelo.getEnemigo() != null) {
            modelo.getEnemigo().actualizarAnimacion(delta);
        }
        
        manejarFlujosDePantalla(delta);
        procesarTurnos(delta);
    
    }

    // PROCESA LOS TURNOS DEL COMBATE
    private void procesarTurnos(float delta) {

        if (modelo.getResultado() != CombateModelo.Resultado.NINGUNO) {return;}

        switch (modelo.getTurnoActual()) {

            case JUGADOR -> procesarTurnoJugador();

            case ENEMIGO -> procesarTurnoEnemigo(delta);

            case PROCESANDO -> procesarTurnoProcesando(delta);
        }
    }

    // JUGADOR
    private void procesarTurnoJugador() {
        vista.mostrarInventario();
    }

    // ENEMIGO
    private void procesarTurnoEnemigo(float delta) {

        contador += delta;

        if (contador >= tiempoPausa) {

            ejecutarTurnoEnemigo();

            modelo.setTurnoActual(CombateModelo.Turno.PROCESANDO);

            proximoTurnoJugador = true;

            contador = 0;
        }
    }

    // PROCESAMIENTO
    private void procesarTurnoProcesando(float delta) {

        contador += delta;

        if (contador >= tiempoPausa / 2.0f && !danioAplicado) {

            if (!proximoTurnoJugador) {
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

                modelo.setTurnoActual(proximoTurnoJugador ? CombateModelo.Turno.JUGADOR : CombateModelo.Turno.ENEMIGO);
            }

            contador = 0;

            modelo.mostrarMensaje("");
        }
    }

    // ESTADOS DEL COMBATE
    private void manejarFlujosDePantalla(float delta) {

        if (modelo.getResultado() == Resultado.VICTORIA && flujoActual == FlujoCombate.COMBATE) {

            flujoActual = FlujoCombate.RESULTADO;
            game.audio.stop(nivel); 
            game.audio.play(4); 
            resultado = "victoria"; 
            esperandoRecompensa = true; 
        } 
        
        if (modelo.getResultado() == Resultado.DERROTA && !derrotaProcesada && !esperandoRecompensa) { 

            flujoActual = FlujoCombate.RESULTADO;
            game.audio.stop(nivel); 
            game.audio.play(5); 
            resultado = "derrota"; 
            esperandoRecompensa = true; 
        }

        switch (flujoActual) {

            case RECOMPENSA -> recompensaControlador.render(delta);

            case PAUSA -> {pausaControlador.render(delta);

                if(pausaControlador.opciones() == false){
                    flujoActual = FlujoCombate.COMBATE;
                }
            }

            case RESULTADO -> manejarTransicionResultado(delta);

            case COMBATE -> { /* sigue el flujo del combate */ }

        }
    }

    // MUESTRA VISTAS SEGUN EL RESULTADO DEL COMBATE
    private void manejarTransicionResultado(float delta) {

        goblinControlador.detener();
        tiempoResultado += delta;

        if (tiempoResultado < 3f) return;

        if (modelo.getResultado() == Resultado.VICTORIA) {

            tiempoResultado = 0f;

            if (nivel < nivelesTotal){
                
                
                flujoActual = FlujoCombate.RECOMPENSA;
                game.audio.play(6);
                recompensaControlador.mostrar();
                setInput(recompensaControlador.getVista().getStage());

                return;

            } else {

                nivel = 0;

                Partida partidaActual = game.getPartidaActual();

                try {
                    partidaActual.setPisoActual(nivel + 1);
                    new PartidaDao().guardarProgreso(partidaActual);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                game.setScreen(new TransicionScreen(game, this, new CreditosScreen(game, spriteBatch)));
            }

        }else{

            tiempoResultado = 0f;
            
            flujoActual = FlujoCombate.PAUSA;
            pausaControlador.setOpciones(true);
            setInput(pausaControlador.getVista().getStage());
            derrotaProcesada = true; 

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
        Entidad.Estado animacionHeroe = habilidad.getEstadoEjecucion();
        Entidad.Estado animacionEnemigo = habilidad.getEstadoReaccion();

        modelo.getHeroe().setEstadoActual(animacionHeroe);
        modelo.getEnemigo().setEstadoActual(animacionEnemigo);
        
        this.tiempoPausa = vista.getTiempoAnimacionHeroe(animacionHeroe);
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

            modelo.mostrarMensaje( modelo.getEnemigo().getNombre() + " usó " + modelo.getEnemigo().getUltimaHabilidadUsada() + "!");
        }
    }
    
    @Override
    public void dispose() {
        super.dispose();
        vista.cerrar();

        if (goblinControlador != null) {
            goblinControlador.detener();
        }

    }
}