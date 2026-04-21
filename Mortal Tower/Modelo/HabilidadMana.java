package Modelo;

public class HabilidadMana extends HabilidadSoporte {

 public HabilidadMana(String nombre, String descripcion, String tipo, int costoMana, int valorBase, int cooldownMax) {

        super(nombre, descripcion, tipo, costoMana, valorBase, cooldownMax);
    }

    @Override
    public void ejecutarHabilidad(Entidad usuario, Entidad objetivo) {

        if (!puedeUsarse(usuario)) return;

        usuario.recuperarMana(valorBase);
        usuario.usarMana(getCostoMana());

        activarCooldown();
    }
}
