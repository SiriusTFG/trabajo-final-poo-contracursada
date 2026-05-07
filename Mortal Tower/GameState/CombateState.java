package GameState;

import java.awt.Graphics2D;

import Controlador.CombateControlador;
import Controlador.Game;
import Controlador.Teclado;
import Vista.CombateVista;
import Modelo.CombateModelo;

public class CombateState implements GameState {

    private CombateModelo model;
    private CombateVista view;
    private CombateControlador controller;

    //constructor
    public CombateState(Teclado teclado, Game game) {
        model = new CombateModelo();
        view = new CombateVista();
        controller = new CombateControlador(model, teclado, game);
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