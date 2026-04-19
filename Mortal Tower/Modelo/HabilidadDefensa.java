package Modelo;

public class HabilidadDefensa extends Habilidad {

    private double reduccionDaño; // ej: 0.5 = reduce 50%

    public HabilidadDefensa(String nombre, String descripcion, String tipo, int costoMana, int valorBase, double reduccionDaño) {

        super(nombre, descripcion, tipo, costoMana, valorBase);

        this.reduccionDaño = reduccionDaño;
    }

    @Override
    public void ejecutarHabilidad(Entidad usuario, Entidad objetivo) {

        if (!puedeUsarse(usuario)) return;

        // Activa estado defensivo en el usuario
        usuario.activarDefensa(reduccionDaño);
    }
}
