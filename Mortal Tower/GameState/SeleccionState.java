package GameState;

import Modelo.SeleccionModelo;
import Vista.SeleccionVista;

import java.awt.Graphics2D;

import Controlador.Game;
import Controlador.SeleccionControlador;
import Controlador.Teclado;

public class SeleccionState implements GameState {

    private SeleccionModelo model;
    private SeleccionVista view;
    private SeleccionControlador controller;

    public SeleccionState(Teclado teclado, Game game){

        model = new SeleccionModelo();
        view = new SeleccionVista(model);
        controller = new SeleccionControlador(model, teclado, game);
        
    }

    @Override
    public void update() {
        controller.update();
    }

    @Override
    public void draw(Graphics2D g2) {
        view.draw(g2);
    }
    
}
