package com.mortaTower.Modelo;

public class CombateModelo {
    
    public enum Opciones {LUCHAR, HABILIDADES, OPCIONES};
    private Opciones seleccion = Opciones.LUCHAR;
    public static final Opciones[] valores = Opciones.values();

    public void izquierda(){int index = (seleccion.ordinal() - 1 + valores.length) % valores.length;
        seleccion = valores[index];}

    public void derecha(){int index = (seleccion.ordinal() + 1) % valores.length;
        seleccion = valores[index];}

    public Opciones getOpcionActual() {return seleccion;}
    public Opciones[] getOpciones() {return valores;}
}  