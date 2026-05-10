package com.mortaTower.Controlador;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mortaTower.DAO.PartidaDao;
import com.mortaTower.Modelo.Partida;
import com.mortaTower.Modelo.SeleccionModelo;
import com.mortaTower.Modelo.SeleccionModelo.Heroe;

public class SeleccionControlador {

    public enum Action {NONE,ATRAS, CABALLERO, MAGO}

    private SeleccionModelo modelo;
    private Teclado teclado;
    private Audio audio;
    private PartidaDao partidaDao;

    public SeleccionControlador(SeleccionModelo modelo, Teclado teclado, Audio audio) {
        this.modelo = modelo;
        this.teclado = teclado;
        this.audio = audio;
        
        partidaDao = new PartidaDao();
    }

    public Action update(){

        teclado.update();

        if (teclado.rightPressed){

            modelo.derecha();
            teclado.rightPressed = false;

            //return Action.CABALLERO;
        }

        if (teclado.leftPressed){

            modelo.izquierda();
            teclado.leftPressed = false;

            //return Action.MAGO;
        }

        if (teclado.select) {
            
            modelo.aceptar();
            //confirmarSeleccion();
            teclado.select = false;
        }

        if (teclado.backPressed){
            
            audio.stop(3);
            
            //game.setOverlay(new TransicionState(game,() -> game.setState(new MenuState(teclado, game)))); // salir del menú opciones
            teclado.backPressed = false;
            return Action.ATRAS;
        }
       return Action.NONE;
    }

    private void confirmarSeleccion() {
        String nombrePartida = JOptionPane.showInputDialog(null, "Nombre de tu aventura: ", "Nueva Partida", JOptionPane.QUESTION_MESSAGE);
        if (nombrePartida != null && !nombrePartida.trim().isEmpty()) {
            try {
                int idHeroe = (modelo.getOpcionActual() == Heroe.CABALLERO) ? 1 : 2;

                int idGenerado = partidaDao.nuevaPartida(nombrePartida, idHeroe);

                Partida nuevPartida = partidaDao.cargarPartida(idGenerado);
                //game.setPartida(nuevPartida);
                //game.setOverlay(new TransicionState(game, () -> game.setState(new CombateState(teclado, game))));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cargar partida: " + e.getMessage());
            }
        }
    }
}
