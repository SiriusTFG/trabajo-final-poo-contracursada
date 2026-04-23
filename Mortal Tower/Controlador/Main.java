package Controlador;

import java.awt.Menu;

import Vista.MenuPrincipal;
import Vista.Ventana;

public class Main {

    public static void main(String[] args) {
        //JuegoControlador juego = new JuegoControlador();
        //juego.iniciarJuego();

        Ventana ventana = new Ventana();
        MenuPrincipal menuPrincipal = new MenuPrincipal();

        ventana.add(menuPrincipal);
        //ventana.pack();
        ventana.setVisible(true);

        menuPrincipal.setFocusable(true);
        menuPrincipal.requestFocusInWindow();
    }
}