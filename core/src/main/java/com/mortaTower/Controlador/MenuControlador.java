package com.mortaTower.Controlador;

import com.mortaTower.Modelo.MenuModelo;

public class MenuControlador {

    public enum Action {NONE, START_GAME, OPTIONS, LOAD_GAME,CREDITS,EXIT}

    private final MenuModelo menuModelo;
    private final Teclado teclado;
    private final Audio audio;

    public MenuControlador(MenuModelo model, Teclado teclado, Audio audio) {
        this.menuModelo = model;
        this.teclado = teclado;
        this.audio = audio;

        //audio.loop(3);
    }

    public Action update() {

        teclado.update();

        // mover arriba
        if (teclado.upPressed) {
            menuModelo.arriba();
            
            audio.play(1); // hover menu
        }

        // mover abajo
        if (teclado.downPressed) {
            menuModelo.abajo();
            
            audio.play(1);
        }

        // seleccionar opción
        if (teclado.selectPressed) {

            switch (menuModelo.getOpcionActual()) {

                case JUGAR -> {
                    audio.play(2); // confirm
                    return Action.START_GAME;
                    
                }

                case OPCIONES -> {
                    return Action.OPTIONS;
                }

                case CARGAR -> {
                    audio.play(2);
                    return Action.LOAD_GAME;
                }

                case SALIR -> {
                    return Action.EXIT;
                }

                
            }
        }

        return Action.NONE;
    }
}