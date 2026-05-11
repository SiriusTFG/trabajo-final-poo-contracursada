package com.mortaTower.Controlador;


import com.mortaTower.Modelo.SeleccionModelo;
import com.mortaTower.Modelo.SeleccionModelo.Estado;

public class SeleccionControlador {

    public enum Action {NONE, IR_MENU, INICIAR_PARTIDA}

    private final SeleccionModelo modelo;
    private final Teclado teclado;

    public SeleccionControlador(SeleccionModelo modelo, Teclado teclado) {

        this.modelo = modelo;
        this.teclado = teclado;
    }

    public Action update() {

        teclado.update();

        if (teclado.leftPressed) {

            modelo.izquierda();
            teclado.leftPressed = false;
        }

        if (teclado.rightPressed) {

            modelo.derecha();
            teclado.rightPressed = false;
        }

        if (teclado.select) {
            
            if (modelo.getEstadoActual() == Estado.SELECCION){
            modelo.confirmarHeroe();
            }else{
                return Action.INICIAR_PARTIDA;
            }
            teclado.select = false;
        }

        if (teclado.backPressed) {

            if (modelo.getEstadoActual() == Estado.SELECCION){
            return Action.IR_MENU;
            }else{
                modelo.volverSeleccion();  
            }

            teclado.backPressed = false;
        }

        return Action.NONE;
    }
}