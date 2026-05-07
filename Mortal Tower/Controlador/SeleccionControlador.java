package Controlador;

import GameState.MenuState;
import GameState.SeleccionState;
import GameState.TransicionState;
import Modelo.MenuModelo;
import Modelo.OpcionesModelo;
import Modelo.SeleccionModelo;

public class SeleccionControlador {

    private SeleccionModelo modelo;
    private Teclado teclado;
    private Game game;

    public SeleccionControlador(SeleccionModelo modelo, Teclado teclado, Game game) {
        this.modelo = modelo;
        this.teclado = teclado;
        this.game = game;

        game.playLoop(3); // música de fondo
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

        if (teclado.back){
            
            game.stopLoop(3);
            game.setOverlay(new TransicionState(game,() -> game.setState(new MenuState(teclado, game)))); // salir del menú opciones
            teclado.back = false;
        }
    }
}