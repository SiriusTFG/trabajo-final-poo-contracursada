package com.mortaTower.Modelo;

public class OpcionesModelo {

    public enum OpcionesEnum {MUSICA, EFECTOS, CONTROLES}
    public enum EstadoEnum {MENU, OPCIONES, CONTROLES}
    private static final OpcionesEnum[] valores = OpcionesEnum.values();

    private OpcionesEnum seleccion = OpcionesEnum.MUSICA;
    private EstadoEnum estado = EstadoEnum.OPCIONES;

    private int seleccionMusica;
    private int seleccionFx;

    private int volMusica = 11;
    private int volFx = 11;

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
            volMusica = Math.max(volMusica - 1, 0);
        } else if (seleccion == OpcionesEnum.EFECTOS) {
           seleccionFx = Math.min(seleccionFx + 1, 11);
           volFx = Math.max(volFx - 1, 0);
        }
    }

    public void derecha(){

        if (seleccion == OpcionesEnum.MUSICA) {
            seleccionMusica = Math.max(seleccionMusica - 1, 0);
            volMusica = Math.min(volMusica + 1, 11);
        } else if (seleccion == OpcionesEnum.EFECTOS) {
           seleccionFx = Math.max(seleccionFx - 1, 0);
           volFx = Math.min(volFx + 1, 11);
        }
    }

    public void aceptar(){

        if (seleccion == OpcionesEnum.CONTROLES){
            System.out.println("tambien aqui");
            estado = EstadoEnum.CONTROLES;
        }

    }

    public void atras(){

        if (estado == EstadoEnum.CONTROLES){
            estado = EstadoEnum.OPCIONES;
        }else{
            estado = EstadoEnum.MENU;
        }

    }

    // GETTERS
    public OpcionesEnum[] getOpciones() {return valores;}
    public OpcionesEnum getOpcionActual() {return seleccion;}

    public EstadoEnum getEstadoActual() {return estado;}
    public void EstadoEnum(EstadoEnum estado) {this.estado = estado;}


    public int getseleccionMusica() {return seleccionMusica;}
    public int getseleccionFx() {return seleccionFx;}

    public int getVolMusica(){return volMusica;}
    public int getVolFx(){return volFx;}
}
