package com.mortaTower.Modelo;

public class HabilidadCuracion extends Habilidad{

    public HabilidadCuracion(int id, String nombre, String descripcion, String tipo, int costoMana, int valorBase, int cooldownMax) {
        
        super(id, nombre, descripcion, tipo, costoMana, valorBase, cooldownMax);
    }

    @Override
    public void ejecutarHabilidad(Entidad usuario, Entidad objetivo) {

        if (!puedeUsarse(usuario)) return;

        usuario.curarVida(valorBase);
        usuario.usarMana(getCostoMana()); //no consume mana la habilidad

        activarCooldown();
    }

}
