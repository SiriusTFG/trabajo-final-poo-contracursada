package Controlador;

import Vista.Ventana;

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