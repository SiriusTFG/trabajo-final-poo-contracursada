package Modelo;

public class HabilidadAtaque extends Habilidad {

    private double probabilidadCritico; //porcentaje que representa la probabilidad de un golpe crítico
    private int bonusCritico;           //cantidad adicional de daño que se aplica si el ataque es crítico

    public HabilidadAtaque(String nombre, String descripcion, String tipo, int costoMana, int valorBase, double probabilidadCritico, int bonusCritico) {

        super(nombre, descripcion, tipo, costoMana, valorBase);

        this.probabilidadCritico = probabilidadCritico;
        this.bonusCritico = bonusCritico;
    }

    @Override
    public void ejecutarHabilidad(Entidad usuario, Entidad objetivo) {

        if (!puedeUsarse(usuario)) return;

        int daño = valorBase;
        daño = Math.max(daño, 0); //asegura que el daño nunca sea negativo.

        boolean critico = Math.random() < probabilidadCritico;

        if (critico) {
            daño += bonusCritico; //aplica el bonus de crítico al daño total
        }

        objetivo.recibirDanio(daño);
    }
}
