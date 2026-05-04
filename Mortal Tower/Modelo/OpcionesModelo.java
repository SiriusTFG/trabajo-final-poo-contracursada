package Modelo;

public class OpcionesModelo {

    public enum EstadoMenu {PRINCIPAL,OPCIONES,CONTROLES}
    private EstadoMenu estado = EstadoMenu.OPCIONES;
    private int seleccionEstado = 0;

    private String[] opciones = {"Musica","Efectos", "Controles"};
    private int seleccion = 0;

    // subnivel de volumen
    private int volumenMusica= 11;
    private int volumenFX= 11;

    private int SheetMusica = 11;
    private int SheetFx = 11;
    private int controles= 0;

    public void arriba() {
    if (estado == EstadoMenu.OPCIONES) {
        seleccion = (seleccion - 1 + opciones.length) % opciones.length;
    }
}

public void abajo() {
    if (estado == EstadoMenu.OPCIONES) {
        seleccion = (seleccion + 1) % opciones.length;
    }
}

    public void derecha() {

        if (seleccion == 0) {
            SheetMusica = Math.min(SheetMusica+ 1, 11);
            volumenMusica = Math.min(volumenMusica + 1, 11);
        } else if (seleccion == 1) {
            SheetFx = Math.min(SheetFx + 1, 11);
            volumenFX = Math.min(volumenFX + 1, 11);
        }
    }

    public void izquierda() {

        if (seleccion == 0) {
            SheetMusica = Math.max(SheetMusica - 1, 0);
            volumenMusica = Math.max(volumenMusica - 1, 0);
        } else if (seleccion == 1) {
            SheetFx = Math.max(SheetFx - 1, 0);
            volumenFX = Math.max(volumenFX - 1, 0);
        }
    }

    public void aceptar() {

        if (estado == EstadoMenu.OPCIONES) {

            if (seleccion == 2) { // "Controles"
                estado = EstadoMenu.CONTROLES;
            }
        }
    }


public void atras() {

    if (estado == EstadoMenu.CONTROLES) {
        estado = EstadoMenu.OPCIONES;
       seleccionEstado = 2;
    } 
    
    else if (estado == EstadoMenu.OPCIONES) {
        estado = EstadoMenu.PRINCIPAL;
    }
}

    // getters

    public EstadoMenu getEstado() {
    return estado;
}

    public int getSeleccion() {return seleccion;}
    public String[] getOpciones() {return opciones;}

    public int getVolumenMusica() {
        return volumenMusica;
    }

    public int getMusica(){

        return SheetMusica;
    }

    public int getFx(){

        return SheetFx;
    }

    public int getcontroles() {
        return controles;
    }

    public int getVolumenFX() {
        return volumenFX;
    }
}