package Controlador;

import DAO.GestorDeConexion;
import Vista.Ventana;

public class Main {

    public static void main(String[] args) {

        GestorDeConexion.getInstancia();

        Teclado input = new Teclado();
        Ventana ventana = new Ventana();
        Game game = new Game(input);

        ventana.add(game);
        ventana.setVisible(true);

        game.startGame();
    }
}