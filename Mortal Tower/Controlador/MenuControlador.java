package Controlador;

import DAO.PartidaDao;
import GameState.OpcioneState;
import GameState.SeleccionState;
import GameState.TransicionState;
import Modelo.MenuModelo;
import java.sql.SQLException;
import java.util.List;

public class MenuControlador {

    private boolean bloqueado = false;
    private long inicioBloqueo;
    private final long duracionBloqueo = 1500; // 1,5 segundos

    private MenuModelo menuModelo;
    private Teclado teclado;
    private Game game;

    public MenuControlador(MenuModelo menuModelo, Teclado teclado, Game game) {
        this.menuModelo = menuModelo;
        this.teclado = teclado;
        this.game = game;

        game.playLoop(3); // música de fondo
    }

    public void update() {

        long now = System.currentTimeMillis(); // el tiempo actual del sistema en milisegundos.

        // congelar menú temporalmente
        if (bloqueado) {

            if (now - inicioBloqueo >= duracionBloqueo) {

                bloqueado = false;

                game.setOverlay(new TransicionState(game,() -> game.setState(new SeleccionState(teclado, game))));
            }

            return;
        }

        if (teclado.upPressed) {

            //game.playSound(1);
            System.out.println("arriba");
            menuModelo.arriba();
            teclado.upPressed = false;
        }

        if (teclado.downPressed) {

            //game.playSound(1);
            System.out.println("abajo");
            menuModelo.abajo();
            teclado.downPressed = false;
        }

        if (teclado.select) {
            System.out.println("enter");

            switch (menuModelo.getSeleccion()) {

                case 0 -> {
                
                    System.out.println("Nueva partida");

                    game.playSound(2);
                    //game.stopLoop(0);
                    // Activa congelamiento
                    bloqueado = true;
                    inicioBloqueo = now;

                }

                case 1 -> {System.out.println("Opciones");
                    game.setOverlay(new OpcioneState(teclado, game));
                }

                case 2 -> {System.out.println("Cargar Partida");
                    System.out.println("\n===== REVISANDO MEMORIA DE LA TORRE =====");
                    try {
                        PartidaDao pDao = new PartidaDao();
                        List<String> resumenes = pDao.obtenerResumenPartidas();

                        if (resumenes.isEmpty()) {
                            System.out.println("No se encontraron partidas en la base de datos.");
                        } else {
                            System.out.println("Partidas encontradas:");
                            for (String info : resumenes) {
                                System.out.println("    > " + info);
                            }
                        }
                    } catch (SQLException e) {
                        System.err.println("[X] Error al acceder a la base de datos: " + e.getMessage());
                    }
                    System.out.println("==========================================\n");
                }

                case 3 -> System.exit(0);
            }

            //ejecutar();
            teclado.select = false;
        }
    }
}