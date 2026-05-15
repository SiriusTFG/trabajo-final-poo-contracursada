package com.mortaTower.Screens;

import com.badlogic.gdx.Gdx;
import com.mortaTower.Controlador.CombateControlador;
import com.mortaTower.Main;
import com.mortaTower.Modelo.CombateModelo;
import com.mortaTower.Modelo.Heroe;
import com.mortaTower.Strategy.ComportamientoAgresivo;
import com.mortaTower.Vista.CombateVista;

public class CombateScreen extends Screens {

    private CombateModelo modelo;
    private CombateVista vista;
    private CombateControlador controlador;

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
        vista = new CombateVista(modelo);
        controlador = new CombateControlador(modelo, game.teclado, game.audio);
        
    }

    @Override
    public void update(float delta) {
        controlador.update(delta);
    }

    @Override
    public void draw(float delta) {
        vista.draw(spriteBatch);
        vista.dibujarInterfaz(spriteBatch);
        vista.dibujarSprite(spriteBatch);
    }
}
