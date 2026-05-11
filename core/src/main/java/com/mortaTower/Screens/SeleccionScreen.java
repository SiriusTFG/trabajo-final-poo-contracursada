package com.mortaTower.Screens;

import com.mortaTower.Main;
import com.mortaTower.Controlador.SeleccionControlador.Action;
import com.mortaTower.Controlador.SeleccionControlador;
import com.mortaTower.Modelo.SeleccionModelo;
import com.mortaTower.Vista.NombreVista;
import com.mortaTower.Vista.SeleccionVista;

public class SeleccionScreen extends Screens {

    private final SeleccionModelo modelo;
    private final SeleccionControlador controlador;

    private final SeleccionVista seleccionVista;
    private final NombreVista nombreVista;

    public SeleccionScreen(Main game) {

        super(game);

        modelo = new SeleccionModelo();
        controlador = new SeleccionControlador(modelo, game.teclado);
        seleccionVista = new SeleccionVista(modelo, stage);
        nombreVista = new NombreVista(modelo, stage);
    }

    @Override
    public void update(float delta) {

        Action action = controlador.update();

        switch (action) {

            case IR_MENU -> { game.setScreen(new TransicionScreen(game,this,new MenuScreen(game))); }

            case INICIAR_PARTIDA -> { System.out.println("Comenzar partida"); }

            case NONE -> {}
        }
    }

    @Override
    public void draw(float delta) {

        switch (modelo.getEstadoActual()) {

            case SELECCION -> { seleccionVista.draw(spriteBatch); }

            case NOMBRE -> {

                seleccionVista.draw(spriteBatch);

                nombreVista.draw(spriteBatch);
            }

        }
    }
}
