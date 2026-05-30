package com.mortaTower.Controlador;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mortaTower.Main;
import com.mortaTower.Modelo.Habilidad;
import com.mortaTower.Modelo.RecompensasModelo;
import com.mortaTower.Screens.CombateScreen;
import com.mortaTower.Screens.Screens;
import com.mortaTower.Screens.TransicionScreen;
import com.mortaTower.Vista.RecompensasVista;

public class RecompensaControlador  extends Screens{

    //private Main game;
    private RecompensasModelo modeloRecompensa;
    
    private RecompensasVista vistaRecompensa;
    private int nivel;
    private Habilidad[] habilidad;
    private int[] tipo;

    private enum EstadoRecompensa {LISTA_HABILIDADES, REEMPLAZO}
    private EstadoRecompensa estado = EstadoRecompensa.LISTA_HABILIDADES;

    public RecompensaControlador(Main game, int nivel) {

        super(game);
        this.nivel = nivel;

        modeloRecompensa= new RecompensasModelo();

        habilidad = modeloRecompensa.getHabilidad();
        tipo = modeloRecompensa.getipo();

        cargarAssets();

        vistaRecompensa = new RecompensasVista(game);
    }

    public void mostrar() {

        vistaRecompensa.listaHabilidades(habilidad, tipo);

        Gdx.input.setInputProcessor(vistaRecompensa.getStage());

        listenersRecompensas();   
    }

    public void render(float delta) {

        vistaRecompensa.getStage().act(delta);
        vistaRecompensa.getStage().draw();
    }

    private void listenersRecompensas() {

        int cantidad = vistaRecompensa.getCantidadHabilidades();

        for (int i = 0; i < cantidad; i++) {

            vistaRecompensa.getBoton(i).addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {

                    cambiarEstado(EstadoRecompensa.REEMPLAZO);
                }
            });
        }

       vistaRecompensa.getBtnAtras().addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {

                    cambiarEstado(EstadoRecompensa.LISTA_HABILIDADES);
                }
        });

        int cant = vistaRecompensa.getCantidadRemplazo();

        for (int i = 0; i < cant; i++) {

            vistaRecompensa.getBtnRemplazo(i).addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {

                    game.setScreen(new TransicionScreen(game, RecompensaControlador.this, new CombateScreen(game, nivel + 1)));
                }
                
            });
        }

    }

    private void cambiarEstado(EstadoRecompensa nuevoEstado) {

        estado = nuevoEstado;

        switch (estado) {

            case LISTA_HABILIDADES:
                vistaRecompensa.limpiar();
                vistaRecompensa.listaHabilidades(habilidad, tipo);
                listenersRecompensas();
                break;

            case REEMPLAZO:
                vistaRecompensa.limpiar();
                vistaRecompensa.cuadroRemplazo();
                listenersRecompensas();
                break;
        }
    }

    private void cargarAssets() {

        game.assets.load("Imagenes/black.png", Texture.class);
        game.assets.load("Imagenes/SeccionRecompensa/cuadroHabilidad.png", Texture.class);
        game.assets.load("Imagenes/SeccionRecompensa/tipoHabilidad.png", Texture.class);
        game.assets.load("Imagenes/SeccionRecompensa/cuadroRemplazo.png", Texture.class);
        game.assets.load("Imagenes/SeccionRecompensa/barraRemplazo.png", Texture.class);

        game.assets.finishLoading();
    }

    public RecompensasVista getVista() {
        return vistaRecompensa;
    }

    public void cerrar() {
        vistaRecompensa.cerrar();
    }
}