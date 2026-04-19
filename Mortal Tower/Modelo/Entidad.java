package Modelo;

public abstract class Entidad {
    protected String nombre;
    protected int vidaActual, vidaMax;
    protected int manaActual, manaMax;
    protected Habilidad[] habilidades = new Habilidad[4];

    public Entidad(String nombre, int vida, int mana) {
        this.nombre = nombre;
        this.vidaMax = vida;
        this.vidaActual = vidaMax;
        this.manaMax = mana;
        this.manaActual = mana;
    }

    public abstract void realizarTurno(Entidad objetivo);

    public void recibirDanio(int cantidad) {
        this.vidaActual -= cantidad;
        if (this.vidaActual < 0) this.vidaActual = 0;
        System.out.println(nombre + ": vida actual = " + vidaActual);
    }

    public void usarMana(int cantidad) {
        this.manaActual -= cantidad;
        if (this.manaActual < 0) this.manaActual = 0;
    }

    public Habilidad[] getHabilidades() {
        return habilidades;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public int getManaActual() {
        return manaActual;
    }
}
