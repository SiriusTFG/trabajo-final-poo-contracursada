package com.mortaTower.State;

import com.mortaTower.Modelo.Enemigo;
import com.mortaTower.Modelo.Entidad;
import com.mortaTower.Modelo.Habilidad;

public class ComportamientoFase3 implements ComportamientoEnemigo {
    
    @Override
    public void accionEnemigo(Enemigo enemigo, Entidad objetivo) {
        Habilidad mejorHabilidad = null;

        boolean vidaEstable = enemigo.getVidaActual() >= (enemigo.getVidaMax() * 0.5);

        if (vidaEstable) {
            mejorHabilidad = buscarHabilidad(enemigo, "Ataque");
            
            if (mejorHabilidad == null) mejorHabilidad = buscarHabilidad(enemigo, "Mana");
            if (mejorHabilidad == null) mejorHabilidad = buscarHabilidad(enemigo, "Defensa");
            
        } else {
            mejorHabilidad = buscarHabilidad(enemigo, "Curacion");
            
            if (mejorHabilidad == null) mejorHabilidad = buscarHabilidad(enemigo, "Defensa");
            if (mejorHabilidad == null) mejorHabilidad = buscarHabilidad(enemigo, "Mana");
            
            if (mejorHabilidad == null) mejorHabilidad = buscarHabilidad(enemigo, "Ataque");
        }

        if (mejorHabilidad != null) {
            enemigo.setUltimaHabilidadUsada(mejorHabilidad.getNombre());
            mejorHabilidad.ejecutarHabilidad(enemigo, objetivo);
        } else {
            // Failsafe por si todas las habilidades están en cooldown o sin maná
            System.out.println(enemigo.getNombre() + " está exhausto y pierde el turno.");
            enemigo.setUltimaHabilidadUsada("nada (Exhausto)");
        }
    }

    private Habilidad buscarHabilidad(Enemigo enemigo, String tipo) {
        for (Habilidad h : enemigo.getHabilidades()) {
            if (h != null && h.getTipo().equalsIgnoreCase(tipo) && h.puedeUsarse(enemigo)) {
                return h;
            }
        }
        return null;
    }
}
