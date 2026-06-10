package com.mortaTower.Modelo;

public abstract class Entidad {

    public enum Estado {PARADO, ATAQUE, DEFENSA, DANIO, MUERTE, MANA, CURACION, VIDA};  
    protected Estado estadoActual = Estado.PARADO;

    protected int id;
    protected String nombre;
    protected int vidaActual, vidaMax;
    protected int manaActual, manaMax;
    protected int ataque;
    protected double defensaBase;
    protected double defensaTemporal = 1.0;

    protected Habilidad[] habilidades = new Habilidad[4];

    protected float statetime = 0f;

    public Entidad(String nombre, int vida, int mana) {

        ataque = 0;
        this.nombre = nombre;
        this.vidaMax = vida;
        this.vidaActual = vidaMax;
        this.manaMax = mana;
        this.manaActual = manaMax;
    }

    public void actualizarAnimacion(float delta) {
        statetime += delta;
    }

    public abstract void realizarTurno(Entidad objetivo);

    public void recibirDanio(int cantidad) {

        System.out.println("DEBUG " + nombre + " -> Atacado con: " + cantidad + " | Mi defensa: " + defensaTemporal);
        int danioFinal = (int) (cantidad * defensaBase * defensaTemporal);
        vidaActual -= danioFinal;
        if (this.vidaActual < 0) this.vidaActual = 0;
        defensaTemporal = 1.0;
        System.out.println(nombre + ": recibio daño, su vida actual es: " + vidaActual);
    }

    public void usarMana(int cantidad) {

        this.manaActual -= cantidad;
        if (this.manaActual < 0) this.manaActual = 0;
        System.out.println(nombre + ": utilizo mana, su mana es: " + manaActual);
    }

    public void recuperarMana(int cantidad) {

        manaActual += cantidad;
        if (manaActual > manaMax) manaActual = manaMax;
        System.out.println(nombre + " recuperó maná. Actual: " + manaActual);
    }

    public void curarVida(int cantidad) {

        this.vidaActual += cantidad;
        if (vidaActual > vidaMax) vidaActual = vidaMax;
        System.out.println(nombre + ": se curo, su vida actual es: " + vidaActual);
    }

    public void activarDefensa(double cantidad) {
        defensaTemporal = cantidad;
    }

    public void setHabilidad(int indice, Habilidad habilidad) {

        if (indice >= 0 && indice < 4) {
            habilidades[indice] = habilidad;
        }
    }

    // GETTERS/SETTERS
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public Habilidad[] getHabilidades() {return habilidades;}
    public int getVidaActual() {return vidaActual;}
    public int getVidaMax() {return vidaMax;}
    public int getManaActual() {return manaActual;}
    public int getManaMax() {return manaMax;}
    public int getAtaque() {return ataque;}
    public double getDefensaBase() {return defensaBase;}
    public int getId() {return id;}
    public Estado getEstadoActual() {return estadoActual;}
    public float getStatetime() {return statetime;}
    public void setId(int id) {this.id = id;}
    public void setAtaque(int ataque) {this.ataque = ataque;}
    public void setDefensa(double defensa) {this.defensaBase = defensa;}
    
    public void setEstadoActual(Estado nuevoEstado) {
        if ( this.estadoActual != nuevoEstado) {
        this.estadoActual = nuevoEstado;
        this.statetime = 0f; // Reiniciar el tiempo de animación al cambiar de estado
        }
    }
}