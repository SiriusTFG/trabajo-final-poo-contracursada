package Modelo;

public abstract class Habilidad {
    private String nombre;
    private String descripcion;
    private String tipo;
    private int costoMana;
    protected int valorBase; //Puede representar daño, curación, etc., dependiendo del tipo de habilidad

    public Habilidad(String nombre, String descripcion, String tipo, int costoMana, int valorBase) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.costoMana = costoMana;
        this.valorBase = valorBase;
    }

    public abstract void ejecutarHabilidad(Entidad usuario, Entidad objetivo); //atributos necesarios para ejecutar la habilidad, como el personaje objetivo, el personaje que la ejecuta, etc.

    public boolean puedeUsarse(Entidad usuario) { //falta definir la clase Personaje, pero se asume que tiene un método getMana() que devuelve la cantidad de mana actual del personaje
        return usuario.getManaActual() >= costoMana;
    }

    //Getters
    public String getNombre() {return nombre;}
    public String getDescripcion() {return descripcion;}
    public String getTipo() {return tipo;}
    public int getCostoMana() {return costoMana;}
    public int getValorBase() {return valorBase;}
}
