package com.mortaTower.State;

import com.mortaTower.Modelo.Enemigo;
import com.mortaTower.Modelo.Entidad;
import com.mortaTower.Modelo.Habilidad;

public class ComportamientoJefeFase1 implements ComportamientoEnemigo{

    @Override
    public void accionEnemigo(Enemigo enemigo, Entidad objetivo) {
        
        if (enemigo.getVidaActual() <= (enemigo.getVidaMax() * 0.6)) {
            System.out.println("FASE 2");

            enemigo.setDefensa(0.5); 
            enemigo.recuperarMana(enemigo.getManaMax());
            enemigo.setFaseVisual(2);

            enemigo.cambiarComportamiento(new ComportamientoJefeFase2());
            enemigo.realizarTurno(objetivo);
            return;
        }

        Habilidad habilidadActual = null;

        if (enemigo.getVidaActual() < (enemigo.getVidaMax() * 0.85)) {
            habilidadActual = buscarHabilidad(enemigo, "Curacion");
        }

        if (habilidadActual == null) habilidadActual = buscarHabilidad(enemigo, "Ataque");

        if (habilidadActual == null) habilidadActual = buscarHabilidad(enemigo, "Mana");

        if (habilidadActual != null) {
            enemigo.setUltimaHabilidadUsada(habilidadActual.getNombre());
            habilidadActual.ejecutarHabilidad(enemigo, objetivo);
        }
    }

    private Habilidad buscarHabilidad(Enemigo enemigo, String tipo) {
        for (Habilidad h : enemigo.getHabilidades()) {
            if (h != null && h.getTipo().equalsIgnoreCase(tipo) && h.puedeUsarse(enemigo)) return h;
        }
        return null;
    }
    
}
