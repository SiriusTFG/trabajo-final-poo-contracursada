package Modelo;

public abstract class Habilidad {
    private String nombre;
    private String descripcion;
    private String tipo;         //Ataque, Defensa, Curacion, Maná
    private int costoMana;
    protected int valorBase;    //Puede representar daño, curación, etc., dependiendo del tipo de habilidad
    private int cooldownMax;    
    private int cooldownActual;

    public Habilidad(String nombre, String descripcion, String tipo, int costoMana, int valorBase, int cooldownMax) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.costoMana = costoMana;
        this.valorBase = valorBase;

        this.cooldownMax = cooldownMax;
        this.cooldownActual = cooldownMax;
    }

    public abstract void ejecutarHabilidad(Entidad usuario, Entidad objetivo); //atributos necesarios para ejecutar la habilidad, como el personaje objetivo, el personaje que la ejecuta, etc.

    public boolean puedeUsarse(Entidad usuario) { //falta definir la clase Personaje, pero se asume que tiene un método getMana() que devuelve la cantidad de mana actual del personaje
        return usuario.getManaActual() >= costoMana && cooldownActual == 0;
    }

    public void activarCooldown() {
        this.cooldownActual = cooldownMax;
    }

    public void tickCooldown() {
        if (cooldownActual > 0) {
            cooldownActual--; //decrementa el cooldown
        }
    }

    //Getters
    public String getNombre() {return nombre;}
    public String getDescripcion() {return descripcion;}
    public String getTipo() {return tipo;}
    public int getCostoMana() {return costoMana;}
    public int getValorBase() {return valorBase;}
    public int getCooldownMax() {return cooldownMax;}
    public int getCooldownActual() {return cooldownActual;}
}
