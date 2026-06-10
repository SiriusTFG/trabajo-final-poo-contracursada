package com.mortaTower.Modelo;

public abstract class Habilidad {
    
    private int id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private int costoMana;
    private int valorBase;    
    private int cooldownMax;    
    private int cooldownActual;

    public Habilidad(int id, String nombre, String descripcion, String tipo, int costoMana, int valorBase, int cooldownMax) {
        
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.costoMana = costoMana;
        this.valorBase = valorBase;

        this.cooldownMax = cooldownMax;
        this.cooldownActual = 0;
    }

    public abstract void ejecutarHabilidad(Entidad usuario, Entidad objetivo);

    public boolean puedeUsarse(Entidad usuario) {
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

    // GETTERS
    public abstract Entidad.Estado getEstadoEjecucion(); 
    public abstract Entidad.Estado getEstadoReaccion(); 
     
    public int getId() {return id;}
    public String getNombre() {return nombre;}
    public String getDescripcion() {return descripcion;}
    public String getTipo() {return tipo;}
    public int getCostoMana() {return costoMana;}
    public int getValorBase() {return valorBase;}
    public int getCooldownMax() {return cooldownMax;}
    public int getCooldownActual() {return cooldownActual;}
}
