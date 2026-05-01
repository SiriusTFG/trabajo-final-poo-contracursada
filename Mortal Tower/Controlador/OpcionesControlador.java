package Controlador;

import GameState.OpcioneState;
import Modelo.OpcionesModelo;

public class OpcionesControlador {

    private OpcionesModelo opcionesModelo;
    private Teclado teclado;
    private Game game;

    public OpcionesControlador(OpcionesModelo opcionesModelo, Teclado teclado, Game game ){

        this.opcionesModelo = opcionesModelo;
        this.game = game;
        this.teclado = teclado;
    }

    public void update(){

        

        if (teclado.back) {

            System.out.println("esc");
            opcionesModelo.back();
            teclado.back = false;
            game.setOverlay(null);
        }

        

    }



    
    
}
