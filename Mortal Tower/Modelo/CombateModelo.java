package Modelo;

public class CombateModelo {
    
    private String[] opciones = {"Luchar", "Habilidad", "Opciones"};
    private int seleccion = 0;

    public void derecha() {
        seleccion++;
        if (seleccion >= opciones.length) {
            seleccion = 0;
        }
    }

    public void izquierda() {
        seleccion--;
        if (seleccion < 0) {
            seleccion = opciones.length - 1;
        }
    }

    public String[] getOpciones() {return opciones;}
    public int getSeleccion() {return seleccion;}
}  