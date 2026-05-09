package com.mortaTower.Modelo;

public class CombateModelo {
    
    private String[] opciones = {"Luchar", "Habilidad", "Opciones"};
    private int seleccion = 0;

    public void izquierda(){seleccion = (seleccion - 1 + opciones.length) % opciones.length;}
    public void derecha(){seleccion = (seleccion + 1) % opciones.length;}

    public String[] getOpciones() {return opciones;}
    public int getSeleccion() {return seleccion;}
}  