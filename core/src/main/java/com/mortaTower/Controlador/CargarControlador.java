package com.mortaTower.Controlador;

import com.mortaTower.Modelo.CargarModelo;


public class CargarControlador {

    public enum Action {NONE, CONFIRM_LOAD, CANCEL}
    
    private final CargarModelo cargarModelo;
    private final Teclado teclado;
    private final Audio audio;

    public CargarControlador(CargarModelo cargarModelo,Teclado teclado, Audio audio) {
        this.cargarModelo = cargarModelo;
        this.teclado = teclado;
        this.audio = audio;
    }

    public Action update () {
        teclado.update();     

        if (teclado.upPressed) {
            cargarModelo.arriba();
            audio.play(1);
            }
            
        if (teclado.downPressed) {
            cargarModelo.abajo();
            audio.play(1);
    
        }
        if (teclado.selectPressed){
            audio.play(2);
            return Action.CONFIRM_LOAD;
        }
        if (teclado.backPressed){
            audio.play(1);
            return Action.CANCEL;
        }
        return Action.NONE;
    }
}