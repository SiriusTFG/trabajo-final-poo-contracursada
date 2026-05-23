package com.mortaTower.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mortaTower.Controlador.CombateControlador;
import com.mortaTower.Main;
import com.mortaTower.Modelo.CombateModelo;
import com.mortaTower.Modelo.Heroe;

import com.mortaTower.Modelo.CombateModelo.Opciones;
import com.mortaTower.Modelo.Entidad;
import com.mortaTower.Strategy.ComportamientoAgresivo;
import com.mortaTower.Vista.CombateVista;
import com.mortaTower.Vista.InventarioVista;
import com.mortaTower.Vista.PausaVista;
import com.mortaTower.Vista.RecompensasVista;

public class CombateScreen extends Screens {

    private CombateModelo modelo;
    private CombateVista vista;
    private CombateControlador controlador;

    private RecompensasVista vistaRecompensa;
    private InventarioVista vistaInventario;
    private PausaVista vistaPausa;

    private float contador = 0;
    private final float tiempoPausa = 1.2f;
    private boolean proximoTurnoJugador;

    //constructor
    public CombateScreen(Main game) {

        super(game);
        Gdx.input.setInputProcessor(null);
        game.teclado.resetPresiones();
        Heroe heroe = game.getPartidaActual().getHeroe();
        
        modelo = new CombateModelo(heroe, 1);
        if (modelo.getEnemigo() != null) {
            modelo.getEnemigo().cambiarComportamiento(new ComportamientoAgresivo());
        }
        
    }

    @Override
    public void show() {

        // INVENTARIO
        game.assets.load("Imagenes/Combate/categorias.png", Texture.class);
        game.assets.load("Imagenes/Combate/inventario.png", Texture.class);

        // PANEL STATUS
        game.assets.load("Imagenes/Combate/vidaHeroe.png", Texture.class);
        game.assets.load("Imagenes/Combate/vidaEnemigo.png", Texture.class);

        // PANEL VICTORIA/DERROTA
        game.assets.load("Imagenes/Combate/victoria.png", Texture.class);
        game.assets.load("Imagenes/Combate/derrota.png", Texture.class);

        // NIVELES TORRES
        game.assets.load("Imagenes/Combate/nivel1.png", Texture.class);
        game.assets.load("Imagenes/Combate/nivel2.png", Texture.class);
        game.assets.load("Imagenes/Combate/nivel3.png", Texture.class);
        game.assets.load("Imagenes/Combate/nivel4.png", Texture.class);
        game.assets.load("Imagenes/Combate/nivel5.png", Texture.class);

        // RECOMPENSA
        game.assets.load("Imagenes/black.png", Texture.class);
        game.assets.load("Imagenes/SeccionRecompensa/cuadroHabilidad.png", Texture.class);
        game.assets.load("Imagenes/SeccionRecompensa/tipoHabilidad.png", Texture.class);

        //obliga al juego a cargar todo antes de seguir
        game.assets.finishLoading(); 

        vista = new CombateVista(viewport, modelo, game);
        Gdx.input.setInputProcessor(vista.getStage());

        /*vista4 = new RecompensasVista(game);
        Gdx.input.setInputProcessor(vista4.getStage());*/
        
        vistaInventario = new InventarioVista(viewport, game);
        Gdx.input.setInputProcessor(vistaInventario.getStage());

        //vista3 = new PausaVista(game);
        //Gdx.input.setInputProcessor(vista3.getStage());
    }

    @Override

    public void render(float delta) {

        super.render(delta);
        
        game.teclado.update();
        modelo.actualizarMensaje(delta);
        //controlador.update(delta);

        vista.getStage().act(delta);
        vista.getStage().draw();

        spriteBatch.begin();

        vista.dibujarSprite(spriteBatch);
        vista.comentarista(spriteBatch);
        vista.resultado(spriteBatch);

        spriteBatch.end();

        vista.dibujarInterfaz(spriteBatch);

        switch (modelo.getTurnoActual()) {
            case JUGADOR -> {

                vistaInventario.render(delta);
                manejarEntradaJugador();
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
                if (contador >= tiempoPausa) {
                    modelo.getHeroe().setEstadoActual(Entidad.Estado.PARADO);
                    modelo.getEnemigo().setEstadoActual(Entidad.Estado.PARADO);
                    if (modelo.getEnemigo().getVidaActual() <= 0) {
                        System.out.println("Victoria");
                        modelo.setResultado(CombateModelo.Resultado.VICTORIA);
                        modelo.getEnemigo().setEstadoActual(Entidad.Estado.MUERTE);
                    } else if (modelo.getHeroe().getVidaActual() <= 0) {
                        System.out.println("Game Over");
                        modelo.setResultado(CombateModelo.Resultado.DERROTA);
                        modelo.getHeroe().setEstadoActual(Entidad.Estado.MUERTE);
                    } else {
                        modelo.setTurnoActual(proximoTurnoJugador ? CombateModelo.Turno.JUGADOR : CombateModelo.Turno.ENEMIGO);  
                    }
                    contador = 0;
                }
            }
        }

        
    }

    private void manejarEntradaJugador() {

        

        if (game.teclado.selectPressed) {
            Opciones opt = modelo.getOpcionActual();

            if (opt == Opciones.PAUSA) {
                System.out.println("Menu opciones");
            } else {
                int indiceHabilidad = opt.ordinal();
                Heroe heroe = modelo.getHeroe();

                if (heroe.getHabilidades()[indiceHabilidad] != null && heroe.getHabilidades()[indiceHabilidad].puedeUsarse(heroe)) {
                    System.out.println("Turno del " + modelo.getHeroe().getNombre());
                    //probar sprites y estados.
                    heroe.setEstadoActual(Entidad.Estado.ATAQUE);
                    modelo.getEnemigo().setEstadoActual(Entidad.Estado.DANIO);
                    //
                    heroe.seleccionarHabilidad(indiceHabilidad);
                    String nombreHabilidad = heroe.getHabilidades()[indiceHabilidad].getNombre();
                    modelo.mostrarMensaje( heroe.getNombre() + " uso " + nombreHabilidad + "!");
                    heroe.realizarTurno(modelo.getEnemigo());
                    proximoTurnoJugador = false;
                    modelo.setTurnoActual(CombateModelo.Turno.PROCESANDO);
                    contador = 0;
                }
            }
            game.teclado.resetPresiones();
        }
    }

    private void ejecutarTurnoEnemigo() {
        if (modelo.getEnemigo().getVidaActual() > 0) {
            System.out.println("Turno del " + modelo.getEnemigo().getNombre());

            modelo.getEnemigo().setEstadoActual(Entidad.Estado.ATAQUE);
            modelo.getHeroe().setEstadoActual(Entidad.Estado.DANIO);

            modelo.getEnemigo().realizarTurno(modelo.getHeroe());

            modelo.mostrarMensaje( modelo.getEnemigo().getNombre() + " usó " + modelo.getEnemigo().getUltimaHabilidadUsada() + "!");
            //probar sprites y estados.
            //modelo.getEnemigo().setEstadoActual(Entidad.Estado.ATAQUE);
            //modelo.getHeroe().setEstadoActual(Entidad.Estado.DANIO);
           // modelo.mostrarMensaje (modelo.getEnemigo().getNombre() + " ataca");
           // modelo.getEnemigo().realizarTurno(modelo.getHeroe());
            game.teclado.resetPresiones();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        vista.cerrar();
    }
    
    @Override
    public void update(float delta) {}
    @Override
    public void draw(float delta) {}
}
