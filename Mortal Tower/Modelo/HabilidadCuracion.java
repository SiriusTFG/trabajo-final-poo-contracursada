package Modelo;

public class HabilidadCuracion extends Habilidad{

    public HabilidadCuracion(String nombre, String descripcion, String tipo, int costoMana, int valorBase, int cooldownMax, int cooldownActual) {
        
        super(nombre, descripcion, tipo, costoMana, valorBase, cooldownMax, cooldownActual);
    }

    @Override
    public void ejecutarHabilidad(Entidad usuario, Entidad objetivo) {

        if (!puedeUsarse(usuario)) return;

        usuario.curarVida(valorBase);
        usuario.usarMana(getCostoMana()); //no consume mana la habilidad

        activarCooldown();
    }

}
