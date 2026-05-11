package com.mortaTower.Modelo;

import com.mortaTower.Modelo.OpcionesModelo.EstadoEnum;

public class SeleccionModelo {

    public enum Heroe {CABALLERO, MAGO};
    public enum Estado {MENU, SELECCION, NOMBRE}
    public static final Heroe[] valores = Heroe.values();

    private Heroe seleccion = Heroe.CABALLERO;
    private Estado estado = Estado.SELECCION;

    public void izquierda(){
        
        int index = (seleccion.ordinal() - 1 + valores.length) % valores.length;
        seleccion = valores[index];
    }

    public void derecha(){
        
        int index = (seleccion.ordinal() + 1) % valores.length;
        seleccion = valores[index];
    }

    public void aceptar(){

        if(estado == Estado.SELECCION){

            estado = Estado.NOMBRE;
        }

    }

    public void atras(){

        if(estado == Estado.NOMBRE){

            estado = Estado.SELECCION;
        }else{

            estado = Estado.MENU;
        }
    }


    // GETTERS
    public Heroe[] getOpciones() {return valores;}
    public Heroe getOpcionActual() {return seleccion;}

    public Estado getEstadoActual() {return estado;}
    
}
