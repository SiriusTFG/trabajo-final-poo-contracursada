package com.mortaTower.Controlador;

import com.mortaTower.Modelo.OpcionesModelo;
import com.mortaTower.Modelo.OpcionesModelo.EstadoEnum;

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

        if (modelo.getEstadoActual() != EstadoEnum.CONTROLES){

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
            actualizarVolumen();
            teclado.leftPressed = false;
        }

        if(teclado.rightPressed){

            modelo.derecha();
            actualizarVolumen();
            teclado.rightPressed = false;
        }

        if (teclado.selectPressed){

            modelo.aceptar();
            teclado.selectPressed = false;
        }
        }


        if (teclado.backPressed){

            modelo.atras();
            teclado.backPressed = false;
        }


    }
    
    private void actualizarVolumen() {
        audio.setVolumenMusica(modelo.getVolMusica() / 10f);
        audio.setVolumenFx(modelo.getVolFx() / 10f);
    }
}
