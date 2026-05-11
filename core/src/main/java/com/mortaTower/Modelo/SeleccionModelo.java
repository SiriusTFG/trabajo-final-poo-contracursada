package com.mortaTower.Modelo;

public class SeleccionModelo {

    public enum Heroe {CABALLERO, MAGO}
    public enum Estado {SELECCION, NOMBRE}

    private static final Heroe[] heroes = Heroe.values();

    private Heroe heroeActual = Heroe.CABALLERO;
    private Estado estadoActual = Estado.SELECCION;

    private String nombreJugador = "";

    public void izquierda() {

        if (estadoActual != Estado.SELECCION) return;

        int index = (heroeActual.ordinal() - 1 + heroes.length) % heroes.length;
        heroeActual = heroes[index];
    }

    public void derecha() {

        if (estadoActual != Estado.SELECCION) return;

        int index = (heroeActual.ordinal() + 1) % heroes.length;
        heroeActual = heroes[index];
    }

    public void confirmarHeroe() {

        if (estadoActual == Estado.SELECCION) {
            estadoActual = Estado.NOMBRE;
        }
    }

    public void volverSeleccion() {estadoActual = Estado.SELECCION;}

    // GETTERS / SETTERS
    public Heroe getHeroeActual() {return heroeActual;}
    public Estado getEstadoActual() {return estadoActual;}
    public String getNombreJugador() {return nombreJugador;}

    public void setNombreJugador(String nombreJugador) {this.nombreJugador = nombreJugador;}
}