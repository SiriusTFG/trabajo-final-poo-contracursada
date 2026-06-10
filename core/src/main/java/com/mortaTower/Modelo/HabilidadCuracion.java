package com.mortaTower.Modelo;

public class HabilidadCuracion extends Habilidad{

    public HabilidadCuracion(int id, String nombre, String descripcion, String tipo, int costoMana, int valorBase, int cooldownMax) {
        
        super(id, nombre, descripcion, tipo, costoMana, valorBase, cooldownMax);
    }

    @Override
    public void ejecutarHabilidad(Entidad usuario, Entidad objetivo) {

        if (!puedeUsarse(usuario)) return;

        usuario.curarVida(getValorBase());
        usuario.usarMana(getCostoMana());

        activarCooldown();
    }

    @Override
    public Entidad.Estado getEstadoEjecucion() {
        return Entidad.Estado.CURACION;
    }

    @Override
    public Entidad.Estado getEstadoReaccion() {
        return Entidad.Estado.PARADO;
    }
}
