package com.mortaTower.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mortaTower.Main;
import com.mortaTower.Vista.MenuVista;
import com.mortaTower.Vista.OpcionesVista;


public class MenuScreen extends Screens {

    private MenuVista vista;
    private OpcionesVista opcionesVista;

    private int volMusica = 9;
    private int volFx = 9;

    private boolean mostrarOpciones = false;

    public MenuScreen(Main game) {
        super(game);
        //game.audio.loop(0);
    }

   @Override
    public void show() {

        game.assets.load("Imagenes/MenuInicio/Fondo.png", Texture.class);
        game.assets.load("Imagenes/MenuInicio/nueva.png", Texture.class);
        game.assets.load("Imagenes/MenuInicio/cargar.png", Texture.class);
        game.assets.load("Imagenes/MenuInicio/opciones.png", Texture.class);
        game.assets.load("Imagenes/MenuInicio/salir.png", Texture.class);

        game.assets.load("Imagenes/Opciones/menuOpciones.png", Texture.class);
        game.assets.finishLoading(); //obliga al juego a cargar todo antes de seguir

        vista = new MenuVista(viewport, game); // el viewport es del screen
        opcionesVista = new OpcionesVista(viewport, game);
        Gdx.input.setInputProcessor(vista.getStage());

        vista.getBtnJugar().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.audio.play(3);
                game.audio.stop(0);
                game.setScreen(new TransicionScreen(game, MenuScreen.this, new SeleccionScreen(game)));
            }
        });

        vista.getBtnCargar().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.audio.play(3);
                game.audio.stop(0);
                game.setScreen(new TransicionScreen(game, MenuScreen.this, new CargarScreen(game, MenuScreen.this)));
            }
        });

        vista.getBtnOpciones().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.audio.play(5);
                mostrarOpciones = true;
                Gdx.input.setInputProcessor(opcionesVista.getStage());
            }
        });

        vista.getBtnSalir().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                System.exit(0);
            }
        });

        configurarListenersOpcines();

    }

    private void configurarListenersOpcines() {
        opcionesVista.getBtnMusicaMas().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                if (volMusica < 9) {
                    volMusica++;
                    game.audio.setVolumenMusica(volMusica / 9f);
                    opcionesVista.actualizarBarraMusica(volMusica);
                }
            }
        });

        opcionesVista.getBtnMusicaMenos().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                if (volMusica > 0) {
                    volMusica--;
                    game.audio.setVolumenMusica(volMusica / 9f);
                    opcionesVista.actualizarBarraMusica(volMusica);
                }
            }
        });

        opcionesVista.getBtnFxMas().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                if (volFx < 9) {
                    volFx++;
                    game.audio.setVolumenFx(volFx / 9f);
                    game.audio.play(0);
                    opcionesVista.actualizarBarraEfectos(volFx);
                }
            }
        });

        opcionesVista.getBtnFxMenos().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                if (volFx > 0) {
                    volFx--;
                    game.audio.setVolumenFx(volFx / 9f);
                    game.audio.play(0);
                    opcionesVista.actualizarBarraEfectos(volFx);
                }
            }
        });

        opcionesVista.getBtnAtras().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                game.audio.play(4);
                mostrarOpciones = false;
                Gdx.input.setInputProcessor(vista.getStage());
            }
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta); //limpia la pantalla
        vista.getStage().act(delta);
        vista.getStage().draw();
        //vista.render(delta); //dibuja la vista
        if (mostrarOpciones) {
            opcionesVista.render(delta);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        vista.cerrar();
        opcionesVista.cerrar();
    }
}
