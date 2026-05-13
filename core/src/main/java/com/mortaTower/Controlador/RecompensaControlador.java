package com.mortaTower.Controlador;

import com.mortaTower.Modelo.RecompensasModelo;

public class RecompensaControlador {
    
    private RecompensasModelo modelo;
    private Teclado teclado;

    public RecompensaControlador(RecompensasModelo modelo, Teclado teclado){

        this.modelo = modelo;
        this.teclado = teclado;

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
    }
}
