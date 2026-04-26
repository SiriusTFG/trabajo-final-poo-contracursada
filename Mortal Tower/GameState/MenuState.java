package GameState;

import java.awt.Graphics2D;

import Controlador.Game;
import Controlador.MenuControlador;
import Controlador.Teclado;
import Modelo.MenuModelo;
import Vista.MenuPrincipal;

import java.awt.Graphics2D;

public class MenuState implements GameState {

    private MenuModelo model;
    private MenuControlador controller;
    private MenuPrincipal view;

    public MenuState(Teclado teclado, Game game) {

        model = new MenuModelo();

        controller = new MenuControlador(model, teclado, game);

        view = new MenuPrincipal(model);
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
