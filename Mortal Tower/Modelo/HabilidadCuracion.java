package Modelo;

public class HabilidadCuracion extends HabilidadSoporte {
    
    public HabilidadCuracion(String nombre, String descripcion, int costoMana, int valorBase, int cooldownMax) {
        
        super(nombre, descripcion, costoMana, valorBase, cooldownMax);
    }

    @Override
    public void ejecutarHabilidad(Entidad usuario, Entidad objetivo) {

        if (!puedeUsarse(usuario)) return;

        usuario.curarVida(valorBase);
        usuario.gastarMana(getCostoMana()); //no consume mana la habilidad

        activarCooldown();
    }

}
