package com.mortaTower.Modelo;

import com.mortaTower.Strategy.ComportamientoEnemigo;

public class Enemigo extends Entidad {

    private ComportamientoEnemigo comportamiento;
    private String ultimaHabilidadUsada = "";

    public Enemigo(int id, String nombre, int vida, int mana, int ataque, double defensa) {
        super(nombre, vida, mana);
        this.id = id;
        this.ataque = ataque;
        this.defensa = defensa;
    }

    public void setUltimaHabilidadUsada (String ultimaHabilidadUsada) {
        this.ultimaHabilidadUsada = ultimaHabilidadUsada;
    }

     public String getUltimaHabilidadUsada() {
        return ultimaHabilidadUsada;
    }

    @Override
    public void realizarTurno(Entidad objetivo) {
        comportamiento.accionEnemigo(this, objetivo);
    }

    public void cambiarComportamiento(ComportamientoEnemigo nuevoComportamiento) {
        this.comportamiento = nuevoComportamiento;
    }

    @Override
    protected void inicializarSprites() {
    // Por ahora vacío
    }
}
