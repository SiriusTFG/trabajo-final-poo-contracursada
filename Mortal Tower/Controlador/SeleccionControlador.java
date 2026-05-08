package Controlador;

import DAO.PartidaDao;
import GameState.CombateState;
import GameState.MenuState;
import GameState.TransicionState;
import Modelo.Partida;
import Modelo.SeleccionModelo;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class SeleccionControlador {

    private SeleccionModelo modelo;
    private Teclado teclado;
    private Game game;
    private PartidaDao partidaDao;

    public SeleccionControlador(SeleccionModelo modelo, Teclado teclado, Game game) {
        this.modelo = modelo;
        this.teclado = teclado;
        this.game = game;
        partidaDao = new PartidaDao();
    }

    public void update(){

        if (teclado.right){

            modelo.derecha();
            teclado.right = false;
        }

        if (teclado.left){

            modelo.izquierda();
            teclado.left = false;
        }

        if (teclado.select) {
            confirmarSeleccion();
            teclado.select = false;
        }

        if (teclado.back){
            
            game.stopLoop(3);
            game.setOverlay(new TransicionState(game,() -> game.setState(new MenuState(teclado, game)))); // salir del menú opciones
            teclado.back = false;
        }
    }

    private void confirmarSeleccion() {
        String nombrePartida = JOptionPane.showInputDialog(null, "Nombre de tu aventura: ", "Nueva Partida", JOptionPane.QUESTION_MESSAGE);
        if (nombrePartida != null && !nombrePartida.trim().isEmpty()) {
            try {
                int idHeroe = (modelo.getSeleccion() == 0) ? 1 : 2;

                int idGenerado = partidaDao.nuevaPartida(nombrePartida, idHeroe);

                Partida nuevPartida = partidaDao.cargarPartida(idGenerado);
                game.setPartida(nuevPartida);
                game.setOverlay(new TransicionState(game, () -> game.setState(new CombateState(teclado, game))));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cargar partida: " + e.getMessage());
            }
        }
    }
}