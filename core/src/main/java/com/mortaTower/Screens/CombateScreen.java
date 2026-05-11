package com.mortaTower.Screens;

import com.mortaTower.Main;
import com.mortaTower.Controlador.CombateControlador;
import com.mortaTower.Modelo.CombateModelo;
import com.mortaTower.Vista.CombateVista;

public class CombateScreen extends Screens {

    private CombateModelo modelo;
    private CombateVista vista;
    private CombateControlador controlador;

    //constructor
    public CombateScreen(Main game) {

        super(game);
        
        modelo = new CombateModelo();
        vista = new CombateVista(modelo);
        controlador = new CombateControlador(modelo, game.teclado, game.audio);
    }

    @Override
    public void update(float delta) {
        
        controlador.update();
    }

    @Override
    public void draw(float delta) {
        vista.draw(spriteBatch);
    }
}
