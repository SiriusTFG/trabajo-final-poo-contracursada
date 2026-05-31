package com.mortaTower.Controlador;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mortaTower.Main;
import com.mortaTower.Screens.MenuScreen;
import com.mortaTower.Screens.Screens;
import com.mortaTower.Screens.TransicionScreen;
import com.mortaTower.Vista.PausaVista;

public class PausaControlador extends Screens{

    private PausaVista vistaPausa;
    private OpcionesControlador opcionesControlador;
    private boolean pausa;
    private Stage menuStage;

    public PausaControlador(Main game, Stage menuStage){

        super(game);
        this.menuStage = menuStage;
        vistaPausa = new PausaVista(game);
        listenerPausa();

        opcionesControlador = new OpcionesControlador(game, vistaPausa.getStage());
    }

    public void render(float delta) {

        vistaPausa.getStage().act();
        vistaPausa.getStage().draw();

        if (opcionesControlador.opciones()) {
            
            opcionesControlador.render(delta);
            return;
        }
    }

    public void listenerPausa(){

        vistaPausa.getBtnRenudar().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pausa = false;
                Gdx.input.setInputProcessor(menuStage);
            }
        });

        vistaPausa.getBtnOpciones().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                
                opcionesControlador.setOpciones(true);
                Gdx.input.setInputProcessor(opcionesControlador.getVista().getStage());
            }
        });

        vistaPausa.getBtnSalir().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               game.setScreen(new TransicionScreen(game, PausaControlador.this, new MenuScreen(game)));
            }
        });
    }

    // GETTERS
    public PausaVista getVista() {return vistaPausa;}
    public boolean opciones(){ return pausa;}
    public void setOpciones(boolean pausa) {this.pausa = pausa;}
    
}
