package Modelo;

public class HabilidadCuracion extends HabilidadSoporte {
    
    public HabilidadCuracion(String nombre, String descripcion, String tipo, int costoMana, int valorBase, int cooldownMax) {
        
        super(nombre, descripcion, tipo, costoMana, valorBase, cooldownMax);
    }

    @Override
    public void ejecutarHabilidad(Entidad usuario, Entidad objetivo) {

        if (!puedeUsarse(usuario)) return;

        usuario.curarVida(valorBase);
        usuario.usarMana(getCostoMana()); //no consume mana la habilidad

        activarCooldown();
    }

}
