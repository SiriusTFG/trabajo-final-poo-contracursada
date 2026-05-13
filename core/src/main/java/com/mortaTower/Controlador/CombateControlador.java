package com.mortaTower.Controlador;

import com.mortaTower.Modelo.CombateModelo;
import com.mortaTower.Modelo.CombateModelo.Opciones;
import com.mortaTower.Modelo.Heroe;

public class CombateControlador {
    
    private CombateModelo modelo;
    private Teclado teclado;
    private Audio audio;

    public CombateControlador(CombateModelo model, Teclado teclado, Audio audio) {
        this.modelo = model;
        this.teclado = teclado;

    }

    public void update(){
        if (modelo.getTurnoActual() == CombateModelo.Turno.JUGADOR) {
            manejarEntradaJugador();
        } else if (modelo.getTurnoActual() == CombateModelo.Turno.ENEMIGO) {
            ejecutarTurnoEnemigo();
        }
    }

    private void manejarEntradaJugador() {
        if (teclado.leftPressed) {modelo.izquierda();}
        if (teclado.rightPressed) {modelo.derecha();}
        if (teclado.downPressed) {modelo.abajo();}
        if (teclado.upPressed) {modelo.arriba();}

        if (teclado.selectPressed) {
            //audio.play(2);
            Opciones opt = modelo.getOpcionActual();

            if (opt == Opciones.OPCIONES) {
                System.out.println("Menu opciones");
            } else {
                int indiceHabilidad = opt.ordinal();
                Heroe heroe = modelo.getHeroe();

                if (heroe.getHabilidades()[indiceHabilidad] != null && heroe.getHabilidades()[indiceHabilidad].puedeUsarse(heroe)) {
                    heroe.usarHabilidad(indiceHabilidad, modelo.getEnemigo());
                    terminarTurnoJugador();
                }
            }
        }
    }

    private void terminarTurnoJugador() {
        if (modelo.getEnemigo().getVidaActual() <= 0) {
            System.out.println("Victoria");
        } else {
            modelo.setTurnoActual(CombateModelo.Turno.ENEMIGO);
        } 
    }

    private void ejecutarTurnoEnemigo() {
        System.out.println("Turno del " + modelo.getEnemigo().getNombre());
        modelo.getEnemigo().realizarTurno(modelo.getHeroe());
        
        if (modelo.getHeroe().getVidaActual() <= 0) {
            System.out.println("Game Over");
        } else {
            modelo.setTurnoActual(CombateModelo.Turno.JUGADOR);
        }
    }
}
