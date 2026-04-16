package MortalTower.Modelo;

public class HabilidadMana extends HabilidadSoporte {

 public HabilidadMana(String nombre, String descripcion, int costoMana, int valorBase, int cooldownMax) {

        super(nombre, descripcion, costoMana, valorBase, cooldownMax);
    }

    @Override
    public void ejecutarHabilidad(Personaje usuario, Personaje objetivo) {

        if (!puedeUsarse(usuario)) return;

        usuario.recuperarMana(valorBase);
        usuario.gastarMana(getCostoMana());

        activarCooldown();
    }
}
