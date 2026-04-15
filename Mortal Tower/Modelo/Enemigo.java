package Modelo;

import Strategy.ComportamientoEnemigo;

public class Enemigo extends Entidad {

    private ComportamientoEnemigo comportamiento;

    public Enemigo(String nombre, int vida, int mana, ComportamientoEnemigo comportamiento) {
        super(nombre, vida, mana);
        this.comportamiento = comportamiento;
    }

    @Override
    public void realizarTurno(Entidad objetivo) {
        comportamiento.accionEnemigo(this, objetivo);
    }

    public void cambiarComportamiento(ComportamientoEnemigo nuevoComportamiento) {
        this.comportamiento = nuevoComportamiento;
    }
}
