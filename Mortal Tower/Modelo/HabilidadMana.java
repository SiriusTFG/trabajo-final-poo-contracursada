package Modelo;

public class HabilidadMana extends Habilidad {

 public HabilidadMana(String nombre, String descripcion, String tipo, int costoMana, int valorBase, int cooldownMax, int cooldownActual) {

        super(nombre, descripcion, tipo, costoMana, valorBase, cooldownMax, cooldownActual);
    }

    @Override
    public void ejecutarHabilidad(Entidad usuario, Entidad objetivo) {

        if (!puedeUsarse(usuario)) return;

        usuario.recuperarMana(valorBase);
        usuario.usarMana(getCostoMana());

        activarCooldown();
    }
}
