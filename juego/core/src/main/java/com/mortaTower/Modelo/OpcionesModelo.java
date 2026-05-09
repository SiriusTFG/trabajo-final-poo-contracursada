package com.mortaTower.Modelo;

public class OpcionesModelo {

    public enum OpcionesEnum {MUSICA, EFECTOS, CONTROLES}
    private static final OpcionesEnum[] valores = OpcionesEnum.values();

    private OpcionesEnum seleccion = OpcionesEnum.MUSICA;


    public void arriba(){
        int index = (seleccion.ordinal() - 1 + valores.length) % valores.length;
        seleccion = valores[index];
    }
    
    public void abajo(){
        int index = (seleccion.ordinal() + 1) % valores.length;
        seleccion = valores[index];
    }

    public void izquierda(){

        if (seleccion == OpcionesEnum.MUSICA) {
            
        } else if (seleccion == OpcionesEnum.EFECTOS) {
           
        }
    }

    public void derecha(){

        if (seleccion == OpcionesEnum.MUSICA) {
            
        } else if (seleccion == OpcionesEnum.EFECTOS) {
           
        }
    }

    // GETTERS
    public OpcionesEnum[] getOpciones() {return valores;}
    public OpcionesEnum getOpcionActual() {return seleccion;}
}
