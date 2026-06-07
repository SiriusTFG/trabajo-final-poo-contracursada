package com.mortaTower.State;

import com.mortaTower.Modelo.Enemigo;
import com.mortaTower.Modelo.Entidad;
import com.mortaTower.Modelo.Habilidad;

public class ComportamientoJefeFase2 implements ComportamientoEnemigo {

    @Override
    public void accionEnemigo(Enemigo enemigo, Entidad objetivo) {
        
        if (enemigo.getVidaActual() <= (enemigo.getVidaMax() * 0.3)) {
            System.out.println("FASE 3");

            enemigo.setDefensa(1.2); 
            enemigo.setAtaque(enemigo.getAtaque() + 35); 
            
            enemigo.setFaseVisual(3);
            enemigo.cambiarComportamiento(new ComportamientoJefeFase3());
            enemigo.realizarTurno(objetivo);
            return;
        }

        Habilidad habilidadActual = null;
        int maxDanio = 999;

        for (Habilidad h : enemigo.getHabilidades()) {
            if (h != null && h.getTipo().equalsIgnoreCase("Ataque") && h.puedeUsarse(enemigo)) {
                if (h.getValorBase() < maxDanio) {
                    maxDanio = h.getValorBase();
                    habilidadActual = h;
                }
            }
        }

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
