package com.mortaTower.Modelo;

import java.time.LocalDateTime;

public class Partida {

    private int id;
    private String nombrePartida;
    private Heroe heroe;
    private int pisoActual;

    public Partida(int id, String nombrePartida, Heroe heroe, int pisoActual, LocalDateTime fechaGuardado) {
        this.id = id;
        this.nombrePartida = nombrePartida;
        this.heroe = heroe;
        this.pisoActual = pisoActual;
    }

    //Getters y Setters
    public int getId() {return id;}
    public String getNombrePartida() {return nombrePartida;}
    public Heroe getHeroe() {return heroe;}
    public int getPisoActual() {return pisoActual;}
    public void setPisoActual(int numPiso) {pisoActual = numPiso;}
}
