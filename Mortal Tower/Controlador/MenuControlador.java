package Controlador;

import GameState.OpcioneState;
import GameState.SeleccionState;
import GameState.TransicionState;
import Modelo.MenuModelo;

public class MenuControlador {

    private long ultimoInput = 0;
    private final long cooldown = 120;

    private boolean bloqueado = false;
    private long inicioBloqueo;
    private final long duracionBloqueo = 3000; // 1 segundo

    private MenuModelo menuModelo;
    private Teclado teclado;
    private Game game;

    public MenuControlador(MenuModelo menuModelo, Teclado teclado, Game game) {
        this.menuModelo = menuModelo;
        this.teclado = teclado;
        this.game = game;

        game.playLoop(0); // música de fondo
    }

    public void update() {

        long now = System.currentTimeMillis();

        // congelar menú temporalmente
        if (bloqueado) {

            if (now - inicioBloqueo >= duracionBloqueo) {

                bloqueado = false;

                game.setOverlay(new TransicionState(game,() -> game.setState(new SeleccionState(teclado, game))));
            }

            return;
        }

        if (teclado.upPressed && now - ultimoInput > cooldown) {
            //game.playSound(1);
            System.out.println("arriba");
            menuModelo.arriba();
            teclado.upPressed = false;

            ultimoInput = now;
        }

        if (teclado.downPressed && now - ultimoInput > cooldown) {
            //game.playSound(1);
            System.out.println("abajo");
            menuModelo.abajo();
            teclado.downPressed = false;

            ultimoInput = now;
        }

        if (teclado.select) {
            System.out.println("enter");

            switch (menuModelo.getSeleccion()) {

                case 0 -> {
                
                    System.out.println("Nueva partida");

                    game.playSound(2);
                    game.stopLoop(0);
                    // Activa congelamiento
                    bloqueado = true;
                    inicioBloqueo = now;

                }

                case 1 -> {System.out.println("Opciones");
                    game.setOverlay(new OpcioneState(teclado, game));
                }

                case 2 -> System.exit(0);
            }

            //ejecutar();
            teclado.select = false;
        }
    }

    /*private void ejecutar() {

        switch (menuModelo.getSeleccion()) {

            case 0 -> {
                
                System.out.println("Nueva partida");


                game.setOverlay(new TransicionState(game,() -> game.setState(new SeleccionState(teclado, game))));

            }

            case 1 -> {System.out.println("Opciones");
                game.setOverlay(new OpcioneState(teclado, game));
            }

            case 2 -> System.exit(0);
        }
    }*/
}