package com.mortaTower.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mortaTower.Main;
import com.mortaTower.Modelo.CombateModelo;
import com.mortaTower.Modelo.Heroe;
import com.mortaTower.Modelo.RecompensasModelo;
import com.mortaTower.Modelo.CombateModelo.Resultado;
import com.mortaTower.Modelo.Entidad;
import com.mortaTower.Modelo.Habilidad;
import com.mortaTower.Modelo.HabilidadAtaque;
import com.mortaTower.Modelo.HabilidadCuracion;
import com.mortaTower.Modelo.HabilidadDefensa;
import com.mortaTower.Modelo.HabilidadMana;
import com.mortaTower.Strategy.ComportamientoAgresivo;
import com.mortaTower.Vista.CombateVista;

import com.mortaTower.Vista.PausaVista;
import com.mortaTower.Vista.RecompensasVista;

public class CombateScreen extends Screens {

    private CombateModelo modelo;
    private RecompensasModelo modeloRec;
    
    private CombateVista vista;     
    private RecompensasVista vistaRecompensa;
    private PausaVista vistaPausa;

    private boolean victoriaProcesada = false;
    private boolean danioAplicado;
    private int nivel;

    private Habilidad[] habilidadesMostradas;

    private float contador = 0;
    private float tiempoPausa;
    private boolean proximoTurnoJugador;

    private boolean pausa = false;
    private boolean seleccionandoReemplazo = false;
    private boolean inventarioVisible = false;
    private Habilidad recompensaSeleccionada;

    //constructor
    public CombateScreen(Main game, int nivel) {

        super(game);

        this.nivel = nivel;

        Heroe heroe = game.getPartidaActual().getHeroe();
        
        modelo = new CombateModelo(heroe, nivel);
        modeloRec = new RecompensasModelo();

        if (modelo.getEnemigo() != null) {
            modelo.getEnemigo().cambiarComportamiento(new ComportamientoAgresivo());
        }
        
        
    }

    @Override
    public void show() {

        cargarAssets();

        vista = new CombateVista(viewport, modelo, game, nivel);
        vistaRecompensa = new RecompensasVista(game);
        vistaPausa = new PausaVista(game);

        // un input a la vez
        setInput(vista.getStage());

        // Iputs Categoria
        agregarListenerCategoria(vista.getBtnAtaque(),HabilidadAtaque.class);
        agregarListenerCategoria(vista.getBtnDefensa(),HabilidadDefensa.class);
        agregarListenerCategoria(vista.getBtnCuracion(),HabilidadCuracion.class);
        agregarListenerCategoria(vista.getBtnMana(),HabilidadMana.class);        

        vistaPausa.getBtnRenudar().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pausa = false;
            }
        });

        vistaPausa.getBtnSalir().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TransicionScreen(game, CombateScreen.this, new MenuScreen(game)));
            }
        });

        // listeners para los botones de recompensas
        listenersRecompensas();
    }

    private void setInput(Stage stageActivo) {
        Gdx.input.setInputProcessor(stageActivo);
    }

    private void agregarListenerCategoria(ImageButton boton, Class<? extends Habilidad> tipo)   {

        boton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                habilidadesMostradas = modelo.getHabilidadesPorTipo(tipo);
                vista.listaHabilidades(habilidadesMostradas);
                
                // Iputs Habilidades
                listenersHabilidades();
            }
        });
    }

    private void listenersHabilidades() {

        int cantidad = vista.getCantidadHabilidades();

        for (int i = 0; i < cantidad; i++) {

            final int index = i;

            vista.getBotonHabilidad(i).addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if (seleccionandoReemplazo){
                        
                        // aca tendria que ir metodo o intruccion para remplazar la recompenza seleccionada por una actual
                        game.setScreen(new TransicionScreen(game, CombateScreen.this, new CombateScreen(game, nivel + 1)));
                        
                    }else{

                       manejarEntradaJugador(index); 
                    }
                }
            });
        }
    }

    private void listenersRecompensas(){

        int cantidad = vistaRecompensa.getCantidadHabilidades();

        for (int i = 0; i < cantidad; i++) {

            vistaRecompensa.getBoton(i).addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    seleccionandoReemplazo = true;

                      
                    vistaRecompensa.cerrar();
                    vista.mostrarInventario();
                    setInput(vista.getStage());
                    
                }
            });
        }
    }

    @Override
    public void render(float delta) {

        super.render(delta);

        // Toggle pausa
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {

            pausa = !pausa;

            if (pausa) {
                setInput(vistaPausa.getStage());
            } else {
                setInput(vista.getStage());
            }
        }

        // PAUSA
        if (pausa) {

            vistaPausa.getStage().act(delta);
            vistaPausa.getStage().draw();

            return;
        }

        vista.getStage().act(delta);
        vista.getStage().draw();

        spriteBatch.begin();

        vista.dibujarSprite(spriteBatch);
        vista.comentarista(spriteBatch);
        vista.resultado(spriteBatch);

        spriteBatch.end();

        vista.dibujarInterfaz(spriteBatch);

        // LÓGICA SOLO SI NO PAUSADO
        if (!pausa) {

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
                    }
                }
            }

            if (modelo.getResultado() == Resultado.VICTORIA && !seleccionandoReemplazo) {
                
                setInput(vistaRecompensa.getStage());
                vistaRecompensa.getStage().act(delta);
                vistaRecompensa.getStage().draw();
                
                return;
                
            }
        }
    }

    private void manejarEntradaJugador(int indiceHabilidad) {

        Habilidad habilidad = habilidadesMostradas[indiceHabilidad];
        Heroe heroe = modelo.getHeroe();

        if (habilidad == null || !habilidad.puedeUsarse(heroe)) {
            return;
        }

        System.out.println("Turno del " + heroe.getNombre());

        this.danioAplicado = false; //para reinicar Flag

        heroe.setEstadoActual(Entidad.Estado.ATAQUE);
        modelo.getEnemigo().setEstadoActual(Entidad.Estado.DANIO);
        this.tiempoPausa = vista.getTiempoAnimacionHeroe(Entidad.Estado.ATAQUE);

        heroe.seleccionarHabilidad(habilidad);

        modelo.mostrarMensaje(
            heroe.getNombre() + " usó " + habilidad.getNombre() + "!"
        );

        // heroe.realizarTurno(modelo.getEnemigo());

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
            //probar sprites y estados.
            //modelo.getEnemigo().setEstadoActual(Entidad.Estado.ATAQUE);
            //modelo.getHeroe().setEstadoActual(Entidad.Estado.DANIO);
           // modelo.mostrarMensaje (modelo.getEnemigo().getNombre() + " ataca");
           // modelo.getEnemigo().realizarTurno(modelo.getHeroe());
           // game.teclado.resetPresiones();
        }
    }

    private void cargarAssets() {

        game.assets.load("Imagenes/Combate/categorias.png", Texture.class);
        game.assets.load("Imagenes/Combate/inventario.png", Texture.class);
        game.assets.load("Imagenes/Combate/cuadroHabilidad.png", Texture.class);

        game.assets.load("Imagenes/Combate/vidaHeroe.png", Texture.class);
        game.assets.load("Imagenes/Combate/vidaEnemigo.png", Texture.class);

        game.assets.load("Imagenes/Combate/victoria.png", Texture.class);
        game.assets.load("Imagenes/Combate/derrota.png", Texture.class);

        game.assets.load("Imagenes/Combate/nivel1.png", Texture.class);
        game.assets.load("Imagenes/Combate/nivel2.png", Texture.class);
        game.assets.load("Imagenes/Combate/nivel3.png", Texture.class);
        game.assets.load("Imagenes/Combate/nivel4.png", Texture.class);
        game.assets.load("Imagenes/Combate/nivel5.png", Texture.class);

        // RECOMPENSA
        game.assets.load("Imagenes/black.png", Texture.class);
        game.assets.load("Imagenes/SeccionRecompensa/cuadroHabilidad.png", Texture.class);
        game.assets.load("Imagenes/SeccionRecompensa/tipoHabilidad.png", Texture.class);

        // PAUSA
        game.assets.load("Imagenes/MenuInicio/renudar.png", Texture.class);

        game.assets.finishLoading();
    }

    @Override
    public void dispose() {
        super.dispose();
        vista.cerrar();
    }
}
