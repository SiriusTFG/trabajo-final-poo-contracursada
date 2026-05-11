package com.mortaTower.Controlador;

import com.mortaTower.Modelo.CombateModelo;

public class CombateControlador {
    
    private CombateModelo modelo;
    private Teclado teclado;
    private Audio audio;

    public CombateControlador(CombateModelo model, Teclado teclado, Audio audio) {
        this.modelo = model;
        this.teclado = teclado;

        audio.loop(3);
    }

    public void update(){

        if (teclado.leftPressed) {
            modelo.izquierda();
            teclado.leftPressed = false;
        }
        
        if (teclado.rightPressed) {
            modelo.derecha();
            teclado.rightPressed = false;
        }
            

        if(teclado.select){
            System.out.println("Enter");

        }
    }

}
