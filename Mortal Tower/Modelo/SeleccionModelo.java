package Modelo;

public class SeleccionModelo {

    private String[] heroe = {"caballero", "mago"};
    private int seleccion = 0;

    public void izquierda(){seleccion = (seleccion - 1 + heroe.length) % heroe.length;}
    public void derecha(){seleccion = (seleccion + 1) % heroe.length;}


    // Getters
    public String[] getOpciones() {return heroe;}
    public int getSeleccion() {return seleccion;}
    
    
}