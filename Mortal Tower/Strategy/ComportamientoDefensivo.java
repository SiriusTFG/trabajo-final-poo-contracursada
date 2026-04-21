package Strategy;

import Modelo.Enemigo;
import Modelo.Entidad;
import Modelo.Habilidad;

public class ComportamientoDefensivo implements ComportamientoEnemigo {

    @Override
    public void accionEnemigo(Enemigo enemigo, Entidad objetivo) {
        Habilidad mejorHabilidad = null;

        /* if (enemigo.getManaActual() <= (enemigo.getManaMax() * 0.3)) {
            mejorHabilidad = buscarHabilidadPorTipo(enemigo, "Mana");
        } */
        
        if (enemigo.getVidaActual() < (enemigo.getVidaMax() * 0.3)) {
            mejorHabilidad = buscarHabilidadPorTipo(enemigo, "Curacion");
        }

        if (mejorHabilidad == null && enemigo.getVidaActual() <= (enemigo.getVidaMax() * 0.5)) {
            if (enemigo.getDefensa() == 1.0) { 
                mejorHabilidad = buscarHabilidadPorTipo(enemigo, "Defensa");
            }
        }

        if (mejorHabilidad == null) {
            mejorHabilidad = buscarHabilidadPorTipo(enemigo, "Ataque");
        }

        if (mejorHabilidad != null) {
            System.out.println(enemigo.getNombre() + " usa: " + mejorHabilidad.getNombre());
            mejorHabilidad.ejecutarHabilidad(enemigo, objetivo);
        } else {
            mejorHabilidad = buscarHabilidadPorTipo(enemigo, "Mana");
             System.out.println(enemigo.getNombre() + " usa: " + mejorHabilidad.getNombre());
             mejorHabilidad.ejecutarHabilidad(enemigo, objetivo);
        }
        
        

    }

    private Habilidad buscarHabilidadPorTipo(Enemigo enemigo, String tipo) {
        for (Habilidad h : enemigo.getHabilidades()) {
            if (h != null && h.getTipo().equalsIgnoreCase(tipo) && enemigo.getManaActual() >= h.getCostoMana()) {
                return h;
            }
        }
        return null;
    }
    
}
