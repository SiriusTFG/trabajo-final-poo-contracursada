package GameState;

import java.awt.Graphics2D;

import Controlador.Game;
import Controlador.OpcionesControlador;
import Controlador.Teclado;
import Modelo.OpcionesModelo;

import Vista.OpcionesVista;

public class OpcioneState implements GameState {

    private OpcionesModelo model;
    private OpcionesControlador controller;
    private OpcionesVista view;


    public OpcioneState(Teclado teclado, Game game) {

        model = new OpcionesModelo();
        controller = new OpcionesControlador(model, teclado, game);
        view = new OpcionesVista(model);
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
