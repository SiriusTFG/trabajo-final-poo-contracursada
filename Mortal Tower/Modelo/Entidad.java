package Modelo;

public abstract class Entidad {
    protected String nombre;
    protected double vidaActual, vidaMax;
    protected int manaActual, manaMax;
    protected double ataque, defensa;
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
        this.vidaActual -= cantidad * defensa;
        if (this.vidaActual < 0) this.vidaActual = 0;
        System.out.println(nombre + ": recibio daño, su vida actual es: " + vidaActual);
    }

    public void usarMana(int cantidad) {
        this.manaActual -= cantidad;
        if (this.manaActual < 0) this.manaActual = 0;
        System.out.println(nombre + ": utilizo mana, su mana es: " + manaActual);
    }

    public void curarVida(int cantidad) {
        this.vidaActual += cantidad;
        if (vidaActual > vidaMax) vidaActual = vidaMax;
        System.out.println(nombre + ": se curo, su vida actual es: " + vidaActual);
    }

    public void activarDefensa(double cantidad) {
        defensa = cantidad;
    }

    //Getters
    public String getNombre() {return nombre;}
    public Habilidad[] getHabilidades() {return habilidades;}
    public double getVidaActual() {return vidaActual;}
    public double getVidaMax() {return vidaMax;}
    public int getManaActual() {return manaActual;}
    public int getManaMax() {return manaMax;}
}
