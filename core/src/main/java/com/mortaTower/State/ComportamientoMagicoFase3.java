package com.mortaTower.State;

import com.mortaTower.Modelo.Enemigo;
import com.mortaTower.Modelo.Entidad;
import com.mortaTower.Modelo.Habilidad;

public class ComportamientoMagicoFase3 implements ComportamientoEnemigo{
    
    @Override
    public void accionEnemigo(Enemigo enemigo, Entidad objetivo) {
        Habilidad mejorHabilidad = null;

        for (Habilidad h : enemigo.getHabilidades()) {
            if (h != null && h.getTipo().equalsIgnoreCase("Ataque") && h.puedeUsarse(enemigo)) {
                if ((h.getValorBase() + enemigo.getAtaque()) >= objetivo.getVidaActual()) {
                    mejorHabilidad = h;
                    break;
                }
            }
        }

        if (mejorHabilidad == null) mejorHabilidad = buscarHabilidad(enemigo, "Ataque");

        if (mejorHabilidad == null) mejorHabilidad = buscarHabilidad(enemigo, "Mana");

        if (mejorHabilidad != null) {
            enemigo.setUltimaHabilidadUsada(mejorHabilidad.getNombre());
            mejorHabilidad.ejecutarHabilidad(enemigo, objetivo);
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
