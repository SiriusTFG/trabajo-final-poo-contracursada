package com.mortaTower.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mortaTower.Main;
import com.mortaTower.Vista.MenuVista;


public class MenuScreen extends Screens {

    private MenuVista vista;
    private OpcionesControlador opcionesControlador;

    public MenuScreen(Main game) {
        super(game);

        //game.audio.loop(0);
        
        vista = new MenuVista(viewport, game); 
        opcionesControlador = new OpcionesControlador(game, vista.getStage());
    }

   @Override
    public void show() {

        Gdx.input.setInputProcessor(vista.getStage());

        vista.getBtnJugar().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.audio.play(3);
                game.audio.stop(0);
                game.setScreen(new TransicionScreen(game, MenuScreen.this, new SeleccionScreen(game)));
                //game.setScreen(new TransicionScreen(game, MenuScreen.this, new CreditosScreen(game, game.batch)));
                
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
                
                opcionesControlador.setOpciones(true);
                Gdx.input.setInputProcessor(opcionesControlador.getVista().getStage());
            }
        });

        vista.getBtnSalir().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                System.exit(0);
            }
        });

    }

    @Override
    public void render(float delta) {
        super.render(delta); //limpia la pantalla
        
        vista.getStage().act(delta);
        vista.getStage().draw();

        if (opcionesControlador.opciones()) {
            
            opcionesControlador.render(delta);
            return;
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        vista.cerrar();
    }
}
