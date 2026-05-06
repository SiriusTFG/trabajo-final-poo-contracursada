package Controlador;

import GameState.MenuState;
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

        if (teclado.back){
            
            game.stopLoop(3);
            game.setState(new MenuState(teclado, game)); // salir del menú opciones
            teclado.back = false;
        }
    }
}