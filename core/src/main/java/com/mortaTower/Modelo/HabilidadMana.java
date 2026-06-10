package com.mortaTower.Modelo;

public class HabilidadMana extends Habilidad {

    public HabilidadMana(int id, String nombre, String descripcion, String tipo, int costoMana, int valorBase, int cooldownMax) {

        super(id, nombre, descripcion, tipo, costoMana, valorBase, cooldownMax);
    }

    @Override
    public void ejecutarHabilidad(Entidad usuario, Entidad objetivo) {

        if (!puedeUsarse(usuario)) return;

        usuario.recuperarMana(getValorBase());
        usuario.usarMana(getCostoMana());

        activarCooldown();
    }

    @Override
    public Entidad.Estado getEstadoEjecucion() {
        return Entidad.Estado.MANA;
    }

    @Override
    public Entidad.Estado getEstadoReaccion() {
        return Entidad.Estado.PARADO; 
    }
}
