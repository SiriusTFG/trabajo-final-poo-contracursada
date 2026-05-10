package com.mortaTower;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mortaTower.Controlador.Audio;
import com.mortaTower.Controlador.Teclado;
import com.mortaTower.DAO.GestorDeConexion;
import com.mortaTower.Modelo.Partida;
import com.mortaTower.Screens.MenuScreen;


public class Main extends Game {

    public SpriteBatch batch;
    public Teclado teclado;
    public Audio audio;

    private Partida partidaActual;

    @Override
    public void create() {

        GestorDeConexion.getInstancia();
        
        batch = new SpriteBatch();
        teclado = new Teclado();
        audio = new Audio();
        setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose() {

        if (getScreen() != null) {
            getScreen().dispose();
        }

        batch.dispose();
    }

    public void setPartida(Partida partida) {partidaActual = partida;}
}