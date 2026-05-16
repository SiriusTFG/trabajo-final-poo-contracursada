package com.mortaTower.Modelo;

import java.util.ArrayList;

public class InventarioModelo {
    
    private Heroe heroe;

    public enum PanelFocus {CATEGORIA, HABILIDADES}
    public enum Categoria {ATAQUE, DEFENSA, CURACION, MANA}
    public enum SlotHabilidades {SLOT1, SLOT2, SLOT3, SLOT4}

    private PanelFocus focus = PanelFocus.CATEGORIA;
    private Categoria seleccion = Categoria.ATAQUE;
    private SlotHabilidades seleccion2 = SlotHabilidades.SLOT1;

    private static final Categoria[] categoria = Categoria.values();
    private static final SlotHabilidades[] slot = SlotHabilidades.values(); 

    public InventarioModelo(Heroe heroe){

        this.heroe = heroe;
    }


    public void arriba(){
        
        if (focus == PanelFocus.CATEGORIA){
            int index = (seleccion.ordinal() - 1 + categoria.length) % categoria.length;
            seleccion = categoria[index];
        }else{

            int index = (seleccion2.ordinal() - 1 + slot.length) % slot.length;
            seleccion2 = slot[index];   
        }
    
    }

    public void abajo(){

        if (focus == PanelFocus.CATEGORIA){
            int index = (seleccion.ordinal() +1) % categoria.length;
            seleccion = categoria[index];
        }else{

            int index = (seleccion2.ordinal() + 1) % slot.length;
            seleccion2 = slot[index];   
        }
    }

    public void izquierda(){

        if (focus == PanelFocus.HABILIDADES) {
            focus = PanelFocus.CATEGORIA;
        }
    }

    public void derecha(){

        if (focus == PanelFocus.CATEGORIA) {
            focus = PanelFocus.HABILIDADES;
        }
    }

    // OBTIENE HABILIDADES DE HEROE POR CATEGORIA
    public String getNombreHabilidad(int indice) {

        Habilidad h = heroe.getHabilidades()[indice];

        if (h != null && h.getTipo().equalsIgnoreCase(seleccion.name())) {
            return h.getNombre();
        }

        return "---";
    }

    // OBTIENE DESCRIPCION DE HABILIDADES
    public String getDescripcionHabilidad(int indice){

        Habilidad h = heroe.getHabilidades()[indice];

        if (h != null && h.getTipo().equalsIgnoreCase(seleccion.name())) {
            return h.getDescripcion();
        }

        return "";
    }

    public Categoria getCategoriaActual(){return seleccion;}
    public SlotHabilidades getHabilidadActual(){return seleccion2;}
    public PanelFocus getFocus(){return focus;}
}
