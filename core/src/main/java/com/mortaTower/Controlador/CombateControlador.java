package com.mortaTower.Controlador;

import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.mortaTower.Modelo.CombateModelo;
import com.mortaTower.Modelo.CombateModelo.Opciones;
import com.mortaTower.Modelo.Heroe;

public class CombateControlador {
    
    private CombateModelo modelo;
    private Teclado teclado;
    private Audio audio;

    private float contador = 0;
    private final float tiempoPausa = 1.2f;
    private boolean proximoTurnoJugador;

    public CombateControlador(CombateModelo model, Teclado teclado, Audio audio) {
        this.modelo = model;
        this.teclado = teclado;

    }

    public void update(float delta){
        teclado.update();
        switch (modelo.getTurnoActual()) {
            case JUGADOR -> {
                manejarEntradaJugador();
                proximoTurnoJugador = false;
            }

            case ENEMIGO -> {
                contador += delta;
                if (contador >= tiempoPausa) {
                    ejecutarTurnoEnemigo();
                    modelo.setTurnoActual(CombateModelo.Turno.PROCESANDO);
                    proximoTurnoJugador = true;
                    contador = 0;
                }
            }

            case PROCESANDO -> {
                contador += delta;
                if (contador >= tiempoPausa) {
                    if (modelo.getEnemigo().getVidaActual() <= 0) {
                        System.out.println("Victoria");
                    } else if (modelo.getHeroe().getVidaActual() <= 0) {
                        System.out.println("Game Over");
                    } else {
                        if (proximoTurnoJugador) {
                            modelo.setTurnoActual(CombateModelo.Turno.JUGADOR);
                        } else {
                            modelo.setTurnoActual(CombateModelo.Turno.ENEMIGO); 
                        }   
                    }
                    contador = 0;
                }
            }
        }
    }

    private void manejarEntradaJugador() {
        if (teclado.leftPressed) {modelo.izquierda();}
        if (teclado.rightPressed) {modelo.derecha();}
        if (teclado.downPressed) {modelo.abajo();}
        if (teclado.upPressed) {modelo.arriba();}

        if (teclado.selectPressed) {
            Opciones opt = modelo.getOpcionActual();

            if (opt == Opciones.OPCIONES) {
                System.out.println("Menu opciones");
            } else {
                int indiceHabilidad = opt.ordinal();
                Heroe heroe = modelo.getHeroe();

                if (heroe.getHabilidades()[indiceHabilidad] != null && heroe.getHabilidades()[indiceHabilidad].puedeUsarse(heroe)) {
                    heroe.usarHabilidad(indiceHabilidad, modelo.getEnemigo());
                    modelo.setTurnoActual(CombateModelo.Turno.PROCESANDO);
                    contador = 0;
                }
            }
            teclado.resetPresiones();
        }
    }

    private void ejecutarTurnoEnemigo() {
        if (modelo.getEnemigo().getVidaActual() > 0) {
            System.out.println("Turno del " + modelo.getEnemigo().getNombre());
            modelo.getEnemigo().realizarTurno(modelo.getHeroe());

            modelo.setTurnoActual(CombateModelo.Turno.JUGADOR);
            teclado.resetPresiones();
        }
    }
}
