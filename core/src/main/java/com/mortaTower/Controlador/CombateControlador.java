package com.mortaTower.Controlador;

import com.mortaTower.Modelo.CombateModelo;
import com.mortaTower.Modelo.Entidad;
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
        //this.audio = audio;
    }

    public void update(float delta){
        teclado.update();

        switch (modelo.getTurnoActual()) {
            case JUGADOR -> manejarEntradaJugador();

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
                    modelo.getHeroe().setEstadoActual(Entidad.Estado.PARADO);
                    modelo.getEnemigo().setEstadoActual(Entidad.Estado.PARADO);
                    if (modelo.getEnemigo().getVidaActual() <= 0) {
                        System.out.println("Victoria");
                        modelo.getEnemigo().setEstadoActual(Entidad.Estado.MUERTE);
                    } else if (modelo.getHeroe().getVidaActual() <= 0) {
                        System.out.println("Game Over");
                        modelo.getHeroe().setEstadoActual(Entidad.Estado.MUERTE);
                    } else {
                        modelo.setTurnoActual(proximoTurnoJugador ? CombateModelo.Turno.JUGADOR : CombateModelo.Turno.ENEMIGO);  
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
                    System.out.println("Turno del " + modelo.getHeroe().getNombre());
                    //probar sprites y estados.
                    heroe.setEstadoActual(Entidad.Estado.ATAQUE);
                    modelo.getEnemigo().setEstadoActual(Entidad.Estado.DANIO);
                    //
                    heroe.seleccionarHabilidad(indiceHabilidad);
                    heroe.realizarTurno(modelo.getEnemigo());
                    proximoTurnoJugador = false;
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
            //probar sprites y estados.
            modelo.getEnemigo().setEstadoActual(Entidad.Estado.ATAQUE);
            modelo.getHeroe().setEstadoActual(Entidad.Estado.DANIO);
            //
            modelo.getEnemigo().realizarTurno(modelo.getHeroe());
            teclado.resetPresiones();
        }
    }
}
