package Controlador;

import GameState.OpcioneState;
import Modelo.MenuModelo;

public class MenuControlador {

    private long ultimoInput = 0;
    private final long cooldown = 120;

    private MenuModelo menuModelo;
    private Teclado teclado;
    private Game game;

    public MenuControlador(MenuModelo menuModelo, Teclado teclado, Game game) {
        this.menuModelo = menuModelo;
        this.teclado = teclado;
        this.game = game;

        //game.playLoop(0); // música de fondo
    }

    public void update() {

        long now = System.currentTimeMillis();

        if (teclado.upPressed && now - ultimoInput > cooldown) {
            game.playSound(1);
            System.out.println("arriba");
            menuModelo.arriba();
            teclado.upPressed = false;

            ultimoInput = now;
        }

        if (teclado.downPressed && now - ultimoInput > cooldown) {
            game.playSound(1);
            System.out.println("abajo");
            menuModelo.abajo();
            teclado.downPressed = false;

            ultimoInput = now;
        }

        if (teclado.select && now - ultimoInput > cooldown) {
            System.out.println("enter");
            ejecutar();
            teclado.select = false;

            ultimoInput = now;
        }
    }

    private void ejecutar() {

        switch (menuModelo.getSeleccion()) {

            case 0 -> {
                game.playSound(2);;
                game.stopLoop(0);
                System.out.println("Nueva partida");
                //game.setState(new PlayState(game, teclado)); // 👈 cambio de estado
            }

            case 1 -> {System.out.println("Opciones");
                game.setOverlay(new OpcioneState(teclado, game));
            }

            case 2 -> System.exit(0);
        }
    }
}