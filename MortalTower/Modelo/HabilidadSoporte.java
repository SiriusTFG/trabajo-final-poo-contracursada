package MortalTower.Modelo;

public abstract class HabilidadSoporte extends Habilidad {

    private int cooldownMax;
    private int cooldownActual;

    public HabilidadSoporte(String nombre, String descripcion, int costoMana, int valorBase, int cooldownMax) {

        super(nombre, descripcion, costoMana, valorBase);

        this.cooldownMax = cooldownMax;
        this.cooldownActual = 0;
    }

    @Override
    public boolean puedeUsarse(Personaje usuario) {
        return super.puedeUsarse(usuario) && cooldownActual == 0;
    }

    protected void activarCooldown() {
        this.cooldownActual = cooldownMax;
    }

    public void tickCooldown() {
        if (cooldownActual > 0) {
            cooldownActual--;
        }
    }
}
