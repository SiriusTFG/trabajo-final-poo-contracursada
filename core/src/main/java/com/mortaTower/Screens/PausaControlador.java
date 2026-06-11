package com.mortaTower.Screens;

import java.sql.SQLException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mortaTower.Main;
import com.mortaTower.DAO.PartidaDao;
import com.mortaTower.Modelo.Partida;
import com.mortaTower.Vista.PausaVista;

public class PausaControlador extends Screens{

    private PausaVista vistaPausa;
    private OpcionesControlador opcionesControlador;
    private boolean pausa;
    private Stage menuStage;

    private int nivel;

    public PausaControlador(Main game, Stage menuStage, int nivel){

        super(game);
        this.nivel = nivel;
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
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                
                game.audio.play(12);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.audio.play(3);
                
                pausa = false;
                Gdx.input.setInputProcessor(menuStage);
            }
        });

        vistaPausa.getBtnReintentar().addListener(new ClickListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                
                game.audio.play(12);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.audio.play(3);
                game.audio.stop(nivel);

                Partida partidaActual = game.getPartidaActual();

                try {

                    partidaActual.setPisoActual(1);
                    PartidaDao partidaDao = new PartidaDao();
                    partidaDao.guardarProgreso(partidaActual);
                } catch (SQLException e) { 
                    e.printStackTrace();
                }

                String nombreHeroe = partidaActual.getNombrePartida();

                // Cambiar pantalla con transición
                game.setScreen(new TransicionScreen(game, PausaControlador.this, new CombateScreen(game, nombreHeroe, 1)));

                // Limpiar input antiguo
                Gdx.input.setInputProcessor(null);
            
            }
        });

        vistaPausa.getBtnOpciones().addListener(new ClickListener() {
            
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                
                game.audio.play(12);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                
                game.audio.play(0);
                opcionesControlador.setOpciones(true);
                Gdx.input.setInputProcessor(opcionesControlador.getVista().getStage());
            }
        });

        vistaPausa.getBtnSalir().addListener(new ClickListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                
                game.audio.play(12);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.audio.stop(nivel);
                game.audio.play(1);
                game.setScreen(new TransicionScreen(game, PausaControlador.this, new MenuScreen(game)));
            }
        });
    }
    
    // GETTERS
    public PausaVista getVista() {return vistaPausa;}
    public boolean opciones(){ return pausa;}
    public void setOpciones(boolean pausa) {this.pausa = pausa;}
    
}
