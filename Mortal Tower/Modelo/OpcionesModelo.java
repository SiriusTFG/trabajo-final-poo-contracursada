package Modelo;

public class OpcionesModelo {

    public enum EstadoMenu {OPCIONES,CONTROLES}
    private EstadoMenu estado = EstadoMenu.OPCIONES;
    private int seleccionEstado = 0;

    private String[] opciones = {"Musica","Efectos", "Controles"};
    private int seleccion = 0;

    // subnivel de volumen
    private int volumenMusica= 11;
    private int volumenFX= 11;

    private int sheetMusica = 11;
    private int sheetFx = 11;
    private int sheetControles= 0;

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
            sheetMusica = Math.min(sheetMusica+ 1, 11);
            volumenMusica = Math.min(volumenMusica + 1, 11);
        } else if (seleccion == 1) {
            sheetFx = Math.min(sheetFx + 1, 11);
            volumenFX = Math.min(volumenFX + 1, 11);
        }
    }

    public void izquierda() {

        if (seleccion == 0) {
            sheetMusica = Math.max(sheetMusica - 1, 0);
            volumenMusica = Math.max(volumenMusica - 1, 0);
        } else if (seleccion == 1) {
            sheetFx = Math.max(sheetFx - 1, 0);
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
    }

    // Getters
    public EstadoMenu getEstado() {return estado;}

    public int getSeleccion() {return seleccion;}
    public String[] getOpciones() {return opciones;}

    public int getVolumenMusica() {return volumenMusica;}
    public int getVolumenFX() {return volumenFX;}

    public int getMusica(){return sheetMusica;}
    public int getFx(){return sheetFx;}
    public int getcontroles() {return sheetControles;}

    
}