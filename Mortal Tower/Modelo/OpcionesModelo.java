package Modelo;

public class OpcionesModelo {

    private String[] opciones = {"Musica","Efectos", "Controles"};
    private int seleccion = 0;

    // subnivel de volumen
    private int volumenMusica = 5;
    private int volumenFX = 5;

    private int controles;

    public void arriba() {seleccion = (seleccion - 1 + opciones.length) % opciones.length;}
    public void abajo() {seleccion = (seleccion + 1) % opciones.length;}

    /*public void back() {
        enVolumen = false;
    }*/

    public void derecha() {
        if (seleccion == 0) {
            volumenMusica = Math.min(volumenMusica + 1, 10);
        } else if (seleccion == 1) {
            volumenFX = Math.min(volumenFX + 1, 10);
        }
    }

    public void izquierda() {
        if (seleccion == 0) {
            volumenMusica = Math.max(volumenMusica - 1, 0);
        } else if (seleccion == 1) {
            volumenFX = Math.max(volumenFX - 1, 0);
        }
    }

    // getters
    public int getSeleccion() {return seleccion;}
    public String[] getOpciones() {return opciones;}

    public int getVolumenMusica() {
        return volumenMusica;
    }

    public int getcontroles() {
        return controles;
    }

    public int getVolumenFX() {
        return volumenFX;
    }
}