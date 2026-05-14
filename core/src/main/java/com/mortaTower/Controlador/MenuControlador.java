package com.mortaTower.Controlador;

import com.mortaTower.Modelo.MenuModelo;

public class MenuControlador {

    public enum Action {NONE, START_GAME, OPTIONS, CREDITS,EXIT}

    private final MenuModelo menuModelo;
    private final Teclado teclado;
    private final Audio audio;

    public MenuControlador(MenuModelo model, Teclado teclado, Audio audio) {
        this.menuModelo = model;
        this.teclado = teclado;
        this.audio = audio;

        //audio.loop(0);
    }

    public Action update() {

        teclado.update();

        // mover arriba
        if (teclado.upPressed) {
            menuModelo.arriba();
            
            audio.play(0); // hover menu
        }

        // mover abajo
        if (teclado.downPressed) {
            menuModelo.abajo();
            
            audio.play(0);
        }

        // seleccionar opción
        if (teclado.selectPressed) {

            switch (menuModelo.getOpcionActual()) {

                case JUGAR -> {
                    audio.play(3); // confirm
                    audio.stop(0);
                    return Action.START_GAME;
                    
                }

                case OPCIONES -> {
                    
                    audio.play(5);
                    return Action.OPTIONS;
                }

                case CARGAR -> {
                    return Action.CREDITS;
                }

                case SALIR -> {
                    return Action.EXIT;
                }

                
            }
        }

        return Action.NONE;
    }
}