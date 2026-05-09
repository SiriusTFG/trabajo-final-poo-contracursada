package com.mortaTower.Modelo;

public class OpcionesModelo {

    public enum OpcionesEnum {MUSICA, EFECTOS, CONTROLES}
    private static final OpcionesEnum[] valores = OpcionesEnum.values();

    private OpcionesEnum seleccion = OpcionesEnum.MUSICA;

    private int seleccionMusica;
    private int seleccionFx;

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
              seleccionMusica = Math.min(seleccionMusica + 1, 11);
        } else if (seleccion == OpcionesEnum.EFECTOS) {
           seleccionFx = Math.min(seleccionFx + 1, 11);
        }
    }

    public void derecha(){

        if (seleccion == OpcionesEnum.MUSICA) {
            seleccionMusica = Math.max(seleccionMusica - 1, 0);
        } else if (seleccion == OpcionesEnum.EFECTOS) {
           seleccionFx = Math.max(seleccionFx - 1, 0);
        }
    }

    // GETTERS
    public OpcionesEnum[] getOpciones() {return valores;}
    public OpcionesEnum getOpcionActual() {return seleccion;}

    public int getseleccionMusica() {return seleccionMusica;}
    public int getseleccionFx() {return seleccionFx;}
}
