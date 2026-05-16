package com.mortaTower.Controlador;

import com.mortaTower.Modelo.InventarioModelo;

public class InventarioControlador {
    
    private InventarioModelo modelo;
    private Teclado teclado;
    private Audio audio;

    public InventarioControlador(InventarioModelo modelo, Teclado teclado, Audio audio){

        this.modelo = modelo;
        this.teclado = teclado;
        this.audio = audio;

    }

    public void update(){

        if (teclado.upPressed){

            modelo.arriba();
            teclado.upPressed = false;
        }

        if (teclado.downPressed){

            modelo.abajo();
            teclado.downPressed = false;
        }

        if (teclado. leftPressed){

            modelo.izquierda();
            teclado.leftPressed = false;
        }

        if (teclado.rightPressed){

            modelo.derecha();
            teclado.rightPressed = false;
        }
    }

}
