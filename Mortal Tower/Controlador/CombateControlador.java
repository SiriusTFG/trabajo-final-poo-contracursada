package Controlador;

import Modelo.CombateModelo;
import GameState.CombateState;
import Vista.CombateVista;

public class CombateControlador {
    
    private CombateModelo model;
    private Teclado teclado;
    private Game game;

    public CombateControlador(CombateModelo model, Teclado teclado, Game game) {
        this.model = model;
        this.teclado = teclado;
        this.game = game;

        game.playLoop(3);
    }

    public void update(){}

    public void ejecutar(){}
    
}
