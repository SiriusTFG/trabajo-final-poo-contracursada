package Controlador;

import GameState.OpcioneState;
import Modelo.MenuModelo;

public class MenuControlador {

    private SonidoControlador musica = new SonidoControlador();
    private SonidoControlador efectos = new SonidoControlador();

    private long ultimoInput = 0;
    private final long cooldown = 120;

    private MenuModelo menuModelo;
    private Teclado teclado;
    private Game game;

    public MenuControlador(MenuModelo menuModelo, Teclado teclado, Game game) {
        this.menuModelo = menuModelo;
        this.teclado = teclado;
        this.game = game;

        musica.loop(0); // música de fondo
    }

    public void update() {

        long now = System.currentTimeMillis();

        if (teclado.up && now - ultimoInput > cooldown) {
            efectos.play(1);
            System.out.println("arriba");
            menuModelo.arriba();
            teclado.up = false;

            ultimoInput = now;
        }

        if (teclado.down && now - ultimoInput > cooldown) {
            efectos.play(1);
            System.out.println("abajo");
            menuModelo.abajo();
            teclado.down = false;

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
                efectos.play(2);
                musica.stop(0);
                System.out.println("Nueva partida");
                //game.setState(new PlayState(game, teclado)); // 👈 cambio de estado
            }

            case 1 -> {System.out.println("Opciones");
                game.setState(new OpcioneState(teclado, game));
            }

            case 2 -> System.exit(0);
        }
    }
}