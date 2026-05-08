package Controlador;

import DAO.PartidaDao;
import GameState.CombateState;
import GameState.MenuState;
import GameState.TransicionState;
import Modelo.CargarModelo;
import Modelo.Partida;

public class CargarControlador {

    private CargarModelo modelo;
    private Teclado teclado;
    private Game game;
    private PartidaDao partidaDao = new PartidaDao();


    public CargarControlador(CargarModelo modelo, Teclado teclado, Game game) {
        this.modelo = modelo;
        this.teclado = teclado;
        this.game = game;
    }

    public void update() {
        if (teclado.upPressed) {
            modelo.arriba();
            teclado.upPressed = false;
        }

        if (teclado.downPressed) {
            modelo.abajo();
            teclado.downPressed = false;
        }

        if (teclado.back) {
            game.setOverlay(new TransicionState(game, () -> game.setState(new MenuState(teclado, game))));
            teclado.back = false;
        }

        if (teclado.select) {
            seleccionarPartida();
            teclado.select = false;
        }
    }

    private void seleccionarPartida() {
        if (modelo.getPartidas().isEmpty()) return;

        try {
            String linea = modelo.getPartidas().get(modelo.getSeleccion());
            int idPartida = Integer.parseInt(linea.split(" - ")[0]);

            Partida partida = partidaDao.cargarPartida(idPartida);
            game.setPartida(partida);

            System.out.println("Partida cargada con exito: " + partida.getNombrePartida());
            game.setOverlay(new TransicionState(game, () -> game.setState(new CombateState(teclado, game))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
