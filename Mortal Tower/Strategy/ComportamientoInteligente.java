package Strategy;

import Modelo.Enemigo;
import Modelo.Entidad;
import Modelo.Habilidad;

public class ComportamientoInteligente implements ComportamientoEnemigo {
    
    @Override
    public void accionEnemigo(Enemigo enemigo, Entidad Objetivo) {
        Habilidad mejorHabilidad = null;

        for (Habilidad h : enemigo.getHabilidades()) {
            if (h != null && h.getTipo().equals("Ataque") && enemigo.getManaActual() >= h.getCostoMana()) {
                if (h.getValorBase() >= Objetivo.getVidaActual()) {
                    mejorHabilidad = h;
                }
            }
        }

        if (mejorHabilidad == null && enemigo.getVidaActual() < (enemigo.getVidaMax() * 0.3)) {
            mejorHabilidad = buscarHabilidad(enemigo, "Curacion");
            if (mejorHabilidad == null) {
                mejorHabilidad = buscarHabilidad(enemigo, "Defensa");
            }
        }

        if (mejorHabilidad == null) {
            mejorHabilidad = buscarHabilidad(enemigo, "Ataque");
        }

        if (mejorHabilidad == null && enemigo.getManaActual() < (enemigo.getManaMax() * 0.5)) {
            mejorHabilidad = buscarHabilidad(enemigo, "Mana");
        }

        if (mejorHabilidad != null) {
            System.out.println(enemigo.getNombre() + "usa: " + mejorHabilidad.getNombre());
            mejorHabilidad.ejecutarHabilidad(enemigo, Objetivo);
        }
    }

    private Habilidad buscarHabilidad(Enemigo enemigo, String tipo) {
        for (Habilidad h : enemigo.getHabilidades()) {
            if (h != null && h.getTipo().equalsIgnoreCase(tipo) && enemigo.getManaActual() >= h.getCostoMana()) {
                return h;
            }
        }
        return null;
    }
}
