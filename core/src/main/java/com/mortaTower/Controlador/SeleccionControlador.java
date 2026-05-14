package com.mortaTower.Controlador;


import com.mortaTower.Modelo.SeleccionModelo;
import com.mortaTower.Modelo.SeleccionModelo.Estado;

public class SeleccionControlador {

    public enum Action {NONE, IR_MENU, INICIAR_PARTIDA}

    private final SeleccionModelo modelo;
    private final Teclado teclado;
    private final Audio audio;

    public SeleccionControlador(SeleccionModelo modelo, Teclado teclado, Audio audio) {

        this.modelo = modelo;
        this.teclado = teclado;
        this.audio = audio;
    }

    public Action update() {

        teclado.update();

        if (teclado.leftPressed) {

            audio.play(0);
            modelo.izquierda();
            teclado.leftPressed = false;
        }

        if (teclado.rightPressed) {

            audio.play(0);
            modelo.derecha();
            teclado.rightPressed = false;
        }

        if (teclado.selectPressed) {
            
            audio.play(5);

            if (modelo.getEstadoActual() == Estado.SELECCION){
            modelo.confirmarHeroe();
            }else{
                return Action.INICIAR_PARTIDA;
            }
            teclado.selectPressed = false;
        }

        if (teclado.backPressed) {

            if (modelo.getEstadoActual() == Estado.SELECCION){
            return Action.IR_MENU;
            }else{
                audio.play(4);
                modelo.volverSeleccion();  
            }

            teclado.backPressed = false;
        }

        return Action.NONE;
    }
}