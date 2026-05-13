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
            audio.play(0);
            modelo.arriba();
            teclado.upPressed = false;
        }

        if(teclado.downPressed){
            audio.play(0);
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
            audio.play(5);
            modelo.aceptar();
            teclado.selectPressed = false;
        }
        }


        if (teclado.backPressed){
            audio.play(4);
            modelo.atras();
            teclado.backPressed = false;
        }


    }
    
    private void actualizarVolumen() {
        audio.setVolumenMusica(modelo.getVolMusica() / 10f);
        audio.setVolumenFx(modelo.getVolFx() / 10f);
    }
}
