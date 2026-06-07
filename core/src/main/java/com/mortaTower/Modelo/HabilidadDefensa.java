package com.mortaTower.Modelo;

public class HabilidadDefensa extends Habilidad {

    private double reduccionDaño; // ej: 0.5 = reduce 50%

    public HabilidadDefensa(int id, String nombre, String descripcion, String tipo, int costoMana, int valorBase, double reduccionDaño, int cooldownMax) {

        super(id, nombre, descripcion, tipo, costoMana, valorBase, cooldownMax);

        this.reduccionDaño = reduccionDaño;
    }

    @Override
    public void ejecutarHabilidad(Entidad usuario, Entidad objetivo) {

        if (!puedeUsarse(usuario)) return;

        // Activa estado defensivo en el usuario
        usuario.activarDefensa(reduccionDaño);
    }
 @Override
    public Entidad.Estado getEstadoEjecucion() {
        return Entidad.Estado.DEFENSA;
    }

    @Override
    public Entidad.Estado getEstadoReaccion() {
        return Entidad.Estado.PARADO; 
    }
}
