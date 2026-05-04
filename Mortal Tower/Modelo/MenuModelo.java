package Modelo;

public class MenuModelo {
    
    private String[] opciones = {"nuevaPartida", "opciones", "salir"};
    private int seleccion = 0;

    
    public void arriba() {seleccion = (seleccion - 1 + opciones.length) % opciones.length;}
    public void abajo() {seleccion = (seleccion + 1) % opciones.length;}
    
    public String[] getOpciones() {return opciones;}
    public int getSeleccion() {return seleccion;}

}
