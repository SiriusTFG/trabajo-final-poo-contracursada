package com.mortaTower.Screens;

import com.mortaTower.Main;
import com.mortaTower.Controlador.SeleccionControlador.Action;
import com.mortaTower.Controlador.SeleccionControlador;
import com.mortaTower.Modelo.SeleccionModelo;
import com.mortaTower.Vista.SeleccionVista;

public class SeleccionScreen extends Screens {

    private final SeleccionModelo modelo;
    private final SeleccionVista vista;
    private final SeleccionControlador controlador;

    public SeleccionScreen(Main game){

        super(game);

        this.modelo = new SeleccionModelo();
        this.controlador = new SeleccionControlador(modelo, game.teclado, game.audio);
        this.vista = new SeleccionVista(modelo, stage);
        
    }

    @Override
    public void update(float delta) {


        Action action = controlador.update();

        switch (action) {

            case ATRAS -> {game.setScreen(new MenuScreen(game)); dispose();}

            case CABALLERO -> System.out.println("Comienza");

            case MAGO -> System.out.println("Comienza");

            case NONE -> {
                // no hacer nada
            }
        }
    }

    @Override
    public void draw(float delta) {
       // menu principal
        vista.draw(spriteBatch);

    }

    
    
}
