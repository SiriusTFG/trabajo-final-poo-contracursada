package com.mortaTower.Modelo;


public class MenuModelo {

    public enum OpcionesEnum {JUGAR, CARGAR, OPCIONES, SALIR}
    public enum Estado {MENU, OPCIONES}

    private static final OpcionesEnum[] valores = OpcionesEnum.values();

    private OpcionesEnum seleccion = OpcionesEnum.JUGAR;
    private Estado estado = Estado.MENU;

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

    // GETTERS / SETTERS
    public OpcionesEnum getOpcionActual() {return seleccion;}
    public Estado getEstadoActual() {return estado;}
}