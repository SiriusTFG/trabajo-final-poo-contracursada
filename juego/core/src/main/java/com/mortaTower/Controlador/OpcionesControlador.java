package com.mortaTower.Controlador;

import com.mortaTower.Modelo.OpcionesModelo;

public class OpcionesControlador {

    public enum Action {NONE, MENU}

    private final OpcionesModelo modelo;
    private final Teclado teclado;
    private final Audio audio;

    public OpcionesControlador(OpcionesModelo modelo, Teclado teclado, Audio audio){

        this.modelo = modelo;
        this.teclado = teclado;
        this.audio = audio;
    }

    public void update(){

        teclado.update();

        if(teclado.upPressed){

            modelo.arriba();
            teclado.upPressed = false;
        }

        if(teclado.downPressed){

            modelo.abajo();
            teclado.downPressed = false;
        }

        if(teclado.leftPressed){

            modelo.izquierda();
            teclado.leftPressed = false;
        }

        if(teclado.rightPressed){

            modelo.derecha();
            teclado.rightPressed = false;
        }


    }
    
}
