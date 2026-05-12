package com.mortaTower.Modelo;

public class CombateModelo {
    
    public enum Opciones {HAB_1, HAB_2, HAB_3, HAB_4, OPCIONES};
    public enum Turno {JUGADOR, ENEMIGO, PROCESANDO};

    private Opciones seleccion = Opciones.HAB_1;
    private Turno turnoActual = Turno.JUGADOR;
    private Heroe heroe;
    private Enemigo enemigo;

    public CombateModelo(Heroe heroe, Enemigo enemigo) {
        this.heroe = heroe;
        this.enemigo = enemigo;
    }

    public void izquierda() {
        if (seleccion == Opciones.OPCIONES) return;
        int indice = (seleccion.ordinal() - 1 + 4) % 4;
        seleccion = Opciones.values()[indice];
    }

    public void derecha() {
        if (seleccion == Opciones.OPCIONES) return;
        int indice = (seleccion.ordinal() + 1 + 4) % 4;
        seleccion = Opciones.values()[indice];
    }

    public void abajo() {
        if (seleccion != Opciones.OPCIONES) {
            seleccion = Opciones.OPCIONES;
        }
    }

    public void arriba() {
        if (seleccion == Opciones.OPCIONES) {
            seleccion = Opciones.HAB_1;
        }
    }

    public String getNombreHabilidad(int indice) {
        if (heroe.getHabilidades()[indice] != null) {
            return heroe.getHabilidades()[indice].getNombre();
        }
        return "---";
    }

    // Getters
    public Opciones getOpcionActual() {return seleccion;}
    public Turno getTurnoActual() {return turnoActual;}
    public void setTurnoActual(Turno turno) {this.turnoActual = turno;}
    public Heroe getHeroe() {return heroe;}
    public Enemigo getEnemigo() {return enemigo;}
}  