package com.mortaTower.State;

import com.mortaTower.Modelo.Enemigo;
import com.mortaTower.Modelo.Entidad;
import com.mortaTower.Modelo.Habilidad;

public class ComportamientoMagicoFase1 implements ComportamientoEnemigo {

    @Override 
    public void accionEnemigo(Enemigo enemigo, Entidad objetivo) {
        if (enemigo.getVidaActual() <= (enemigo.getVidaMax() * 0.5)) {
            System.out.println("¡" + enemigo.getNombre() + " entra en FASE 2");

            enemigo.setDefensa(0.5);
            enemigo.curarVida(enemigo.getVidaActual());
            enemigo.recuperarMana(enemigo.getManaMax());
            
            enemigo.setFaseVisual(2);
            enemigo.cambiarComportamiento(new ComportamientoMagicoFase2());
            enemigo.realizarTurno(objetivo);
            return;
        }

        Habilidad habilidadActual = null;

        if (enemigo.getManaActual() < (enemigo.getManaMax() * 0.5)) {
            habilidadActual = buscarHabilidad(enemigo, "Mana");
        }
        
        if (habilidadActual == null) {
            habilidadActual = buscarHabilidad(enemigo, "Ataque");
        }

        if (habilidadActual != null) {
            enemigo.setUltimaHabilidadUsada(habilidadActual.getNombre());
            habilidadActual.ejecutarHabilidad(enemigo, objetivo);
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
