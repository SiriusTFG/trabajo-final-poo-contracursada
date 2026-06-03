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

    public boolean actualizar(float delta, Heroe heroe, float posicionImpacto, float limitePantalla, float pisoY, float duracionAtaque) {
        if (!activo) return false;
        
        this.stateTime += delta;
        boolean impactoEsteFrame = false;

        switch (estadoActual) {
            case CORRIENDO -> {
                float velocidad = 200f;
                x -= velocidad * delta;
                
                if (x <= posicionImpacto) {
                    cambiarEstado(EstadoGoblin.ATACANDO);
                    heroe.recibirDanio(danio);
                    impactoEsteFrame = true;
                }
            }

            case ATACANDO -> {
                if (stateTime >= duracionAtaque) {
                    cambiarEstado(EstadoGoblin.ESCAPANDO);
                }
            }

            case ESCAPANDO -> {
                float velocidadX = 650f;
                float fuerzaSalto = 800f;
                float gravedad = 1000f;

                x += velocidadX * delta;
                y = pisoY + (fuerzaSalto * stateTime) - (0.5f * gravedad * (stateTime * stateTime));

                if (x > limitePantalla) {
                    activo = false; // se fue de la pantalla se desactiva solo
                }
            }
        }
        
        return impactoEsteFrame;
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
