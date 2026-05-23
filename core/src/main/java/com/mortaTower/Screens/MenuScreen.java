package com.mortaTower.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mortaTower.Main;
import com.mortaTower.Vista.MenuVista;


public class MenuScreen extends Screens {

    private MenuVista vista;

    private int volMusica = 11;
    private int volFx = 11;

    public MenuScreen(Main game) {
        super(game);
        game.audio.loop(0);
    }

   @Override
    public void show() {

        game.assets.load("Imagenes/MenuInicio/Fondo.png", Texture.class);
        game.assets.load("Imagenes/MenuInicio/nueva.png", Texture.class);
        game.assets.load("Imagenes/MenuInicio/cargar.png", Texture.class);
        game.assets.load("Imagenes/MenuInicio/opciones.png", Texture.class);
        game.assets.load("Imagenes/MenuInicio/salir.png", Texture.class);
        game.assets.finishLoading(); //obliga al juego a cargar todo antes de seguir

        vista = new MenuVista(viewport, game); // el viewport es del screen
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
                vista.getCapaPrincipal().setVisible(false);
                vista.getCapaOpciones().setVisible(true);
            }
        });

        vista.getBtnMusicaMas().addListener(new ClickListener() {
            
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                if (volMusica < 11) {
                    volMusica++;
                    game.audio.setVolumenMusica(volMusica / 11f);
                    vista.actualizarBarraMusica(volMusica);
                }  
            }
        });

        vista.getBtnMusicaMenos().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                
                if (volMusica > 0) {
                    volMusica--;    
                    game.audio.setVolumenMusica(volMusica / 11f);
                    vista.actualizarBarraMusica(volMusica);
                } 
            }
        });

        vista.getBtnFxMas().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                if (volFx < 11) {
                    volFx++;
                    game.audio.setVolumenFx(volFx / 11f);
                    game.audio.play(0);
                    vista.actualizarBarraEfectos(volFx);
                }
            }
        });

        vista.getBtnFxMenos().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                if (volFx > 0) {
                    volFx--;
                    game.audio.setVolumenFx(volFx / 11f);
                    game.audio.play(0);
                    vista.actualizarBarraEfectos(volFx);
                }
            }
        });

        vista.getCapaControles().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                game.audio.play(4);
                vista.getCapaControles().setVisible(false);
            }
        });

        vista.getBtnSalir().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        vista.getBtnAtras().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                game.audio.play(4);
                vista.getCapaOpciones().setVisible(false);
                vista.getCapaPrincipal().setVisible(true);
            }
        });

        vista.getBtnControles().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                vista.getCapaControles().setVisible(true);
            }
        });

    }

    @Override
    public void render(float delta) {
        super.render(delta); //limpia la pantalla
        vista.getStage().act(delta);
        vista.getStage().draw();
        //vista.render(delta); //dibuja la vista
    }

    @Override
    public void dispose() {
        super.dispose();
        vista.cerrar();
    }

    @Override
    public void update(float delta) {}
    public void draw(float delta) {}
}
