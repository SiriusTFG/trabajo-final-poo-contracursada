package com.mortaTower.Controlador;

import com.mortaTower.Modelo.SeleccionModelo;

public class SeleccionControlador {

    public enum Action {NONE, CABALLERO, MAGO}

    private SeleccionModelo modelo;
    private Teclado teclado;
    private Audio audio;
    //private PartidaDao partidaDao;

    public SeleccionControlador(SeleccionModelo modelo, Teclado teclado, Audio audio) {
        this.modelo = modelo;
        this.teclado = teclado;
        this.audio = audio;
        
        //partidaDao = new PartidaDao();
    }

    public Action update(){

        teclado.update();

        if (teclado.right){

            modelo.derecha();
            teclado.right = false;

            return Action.CABALLERO;
        }

        if (teclado.left){

            modelo.izquierda();
            teclado.left = false;

            return Action.MAGO;
        }

        /*if (teclado.select) {
            confirmarSeleccion();
            teclado.select = false;
        }*/

        /*if (teclado.back){
            
            game.stopLoop(3);
            game.setOverlay(new TransicionState(game,() -> game.setState(new MenuState(teclado, game)))); // salir del menú opciones
            teclado.back = false;
        }*/
       return Action.NONE;
    }

    /*private void confirmarSeleccion() {
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
    }*/
}
