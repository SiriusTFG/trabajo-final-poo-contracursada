package com.mortaTower.Modelo;

public class Goblin {

    public enum EstadoGoblin {CORRIENDO, ATACANDO, ESCAPANDO}
    
    private boolean activo;
    private float x;
    private float y;
    private float stateTime;
    private int danio;
    private EstadoGoblin estadoActual;

    public Goblin() {
        this.activo = false;
        this.x = 0;
        this.y = 0;
        this.stateTime = 0;
        this.danio= 10;
        this.estadoActual = EstadoGoblin.CORRIENDO;
    }

    public void prepararCorrida(float posicionInicialx, float posicionInicialy) {
        this.activo = true;
        this.x = posicionInicialx;
        this.y = posicionInicialy;
        this.stateTime = 0;
        this.estadoActual = EstadoGoblin.CORRIENDO;
    }

    public void cambiarEstado(EstadoGoblin nuevoEstado) {
        this.estadoActual = nuevoEstado;
        this.stateTime = 0;
    }

    // GETTERS Y SETTERS
    public boolean getActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}

    public float getX() {return x;}
    public void setX(float x) {this.x = x;}

    public float getY() {return y;}
    public void setY(float y) {this.y = y;}

    public float getStateTime() {return stateTime;}
    public void sumarStateTime(float delta) {this.stateTime += delta;}

    public int getDanio() {return danio;}
    public void setDanio(int danio) {this.danio = danio;}

    public EstadoGoblin getEstadoActual() {return estadoActual;}
}
