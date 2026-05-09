package com.mortaTower.Modelo;

public class SeleccionModelo {

    public enum Heroe {CABALLERO, MAGO};
    public static final Heroe[] valores = Heroe.values();

    private Heroe seleccion = Heroe.CABALLERO;

    public void izquierda(){
        
        int index = (seleccion.ordinal() - 1 + valores.length) % valores.length;
        seleccion = valores[index];
    }

    public void derecha(){
        
        int index = (seleccion.ordinal() + 1) % valores.length;
        seleccion = valores[index];
    }


    // GETTERS
    public Heroe[] getOpciones() {return valores;}
    public Heroe getOpcionActual() {return seleccion;}
    
}
