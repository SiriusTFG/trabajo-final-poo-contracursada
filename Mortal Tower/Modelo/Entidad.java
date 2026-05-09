package Modelo;

import java.util.ArrayList;
import java.util.List;

public abstract class Entidad {
    protected int id;
    protected String nombre;
    protected int vidaActual, vidaMax;
    protected int manaActual, manaMax;
    protected int ataque;
    protected double defensa = 1.0;
    protected Habilidad[] habilidades = new Habilidad[4];
    protected String rutaImagenPerfil;
    protected List <Sprite> animaciones = new ArrayList<>();

    public Entidad(String nombre, int vida, int mana) {
        ataque = 0;
        this.nombre = nombre;
        this.vidaMax = vida;
        this.vidaActual = vidaMax;
        this.manaMax = mana;
        this.manaActual = manaMax;
    }

    public abstract void realizarTurno(Entidad objetivo);

    public void recibirDanio(int cantidad) {
        int danioFinal = (int) (cantidad * defensa);
        vidaActual -= danioFinal;
        if (this.vidaActual < 0) this.vidaActual = 0;
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
        defensa = cantidad;
    }

    public String getRutaSprites(String estado,int orden){
        for (Sprite s : animaciones){
            if ( s.getEstado().equalsIgnoreCase(estado) && s.getOrden() == orden){
                return s.getRutaImagen();
            }
        }
        return rutaImagenPerfil;
    }

    public void setAnimaciones(List<Sprite> animaciones) {
        this.animaciones = animaciones;
    }

    public void setHabilidad(int indice, Habilidad habilidad) {
        if (indice >= 0 && indice < 4) {
            habilidades[indice] = habilidad;
        }
    }

    //Getters y Setters
    public String getNombre() {return nombre;}
    public Habilidad[] getHabilidades() {return habilidades;}
    public int getVidaActual() {return vidaActual;}
    public int getVidaMax() {return vidaMax;}
    public int getManaActual() {return manaActual;}
    public int getManaMax() {return manaMax;}
    public int getAtaque() {return ataque;}
    public double getDefensa() {return defensa;}
    public int getId() {return id;}
    public List <Sprite> getAnimaciones(){return animaciones;}
    public void setId(int id) {this.id = id;}
    public void setAtaque(int ataque) {this.ataque = ataque;}
    public void setRutaImagen(String ruta) {this.rutaImagenPerfil = ruta;}     
}
