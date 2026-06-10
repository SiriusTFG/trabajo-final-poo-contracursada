package com.mortaTower.Modelo;

public class HabilidadAtaque extends Habilidad {

    private double probabilidadCritico; 
    private int bonusCritico;           

    public HabilidadAtaque(int id, String nombre, String descripcion, String tipo, int costoMana, int valorBase, double probabilidadCritico, int bonusCritico, int cooldownMax) {

        super(id, nombre, descripcion, tipo, costoMana, valorBase, cooldownMax);

        this.probabilidadCritico = probabilidadCritico;
        this.bonusCritico = bonusCritico;
    }

    @Override
    public void ejecutarHabilidad(Entidad usuario, Entidad objetivo) {

        if (!puedeUsarse(usuario)) return;

        int daño = getValorBase() + usuario.getAtaque();
        daño = Math.max(daño, 0); //asegura que el daño nunca sea negativo.

        if (Math.random() < probabilidadCritico) {
            daño += bonusCritico;
            System.out.println("¡GOLPE CRÍTICO!");
        }

        objetivo.recibirDanio(daño);
        usuario.usarMana(getCostoMana());
    }
    
    @Override
    public Entidad.Estado getEstadoEjecucion() {
        return Entidad.Estado.ATAQUE;
    }

    @Override
    public Entidad.Estado getEstadoReaccion() {
        return Entidad.Estado.DANIO;
    }
}
