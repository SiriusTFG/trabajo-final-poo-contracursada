package Controlador;

import java.awt.Menu;
import javax.swing.*;

import Modelo.MenuModelo;
import Vista.MenuPrincipal;
import Vista.Ventana;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {

        Teclado input = new Teclado();
        Ventana ventana = new Ventana();
        Game game = new Game(input);

        ventana.add(game);
        ventana.setVisible(true);

        game.startGame();
    }
}