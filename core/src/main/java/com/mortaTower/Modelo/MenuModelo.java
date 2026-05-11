package com.mortaTower.Modelo;


public class MenuModelo {

    public enum OpcionesEnum {JUGAR, CARGAR, OPCIONES, SALIR}
    public enum Action {NONE, START_GAME, CARGAR, OPTIONS ,EXIT}
    private static final OpcionesEnum[] valores = OpcionesEnum.values();

    private OpcionesEnum seleccion = OpcionesEnum.JUGAR;

    // mover hacia arriba
    public void arriba() {
        int index = (seleccion.ordinal() - 1 + valores.length) % valores.length;
        seleccion = valores[index];
    }

    // mover hacia abajo
    public void abajo() {
        int index = (seleccion.ordinal() + 1) % valores.length;
        seleccion = valores[index];
    }

    // GETTERS
    public OpcionesEnum[] getOpciones() {return valores;}
    public OpcionesEnum getOpcionActual() {return seleccion;}
}