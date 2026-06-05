package com.mortaTower.State;

import com.mortaTower.Modelo.Enemigo;
import com.mortaTower.Modelo.Entidad;
import com.mortaTower.Modelo.Habilidad;

public class ComportamientoFase1 implements ComportamientoEnemigo {

    @Override 
    public void accionEnemigo(Enemigo enemigo, Entidad objetivo) {
        if (enemigo.getVidaActual() <= (enemigo.getVidaMax() * 0.5)) {
            System.out.println("¡" + enemigo.getNombre() + " entra en FASE 2");

            enemigo.setAtaque(enemigo.getAtaque() + 10);
            enemigo.activarDefensa(0.8);

            enemigo.cambiarComportamiento(new ComportamientoFase2());
            enemigo.realizarTurno(objetivo);
            return;
        }

        Habilidad habilidadActual = buscarHabilidadPrioritaria(enemigo);
        if (habilidadActual != null) {
            enemigo.setUltimaHabilidadUsada(habilidadActual.getNombre());
            habilidadActual.ejecutarHabilidad(enemigo, objetivo);
        }
    }

    private Habilidad buscarHabilidadPrioritaria(Enemigo enemigo) {
        for (Habilidad h : enemigo.getHabilidades()) {
            if (h != null && h.getTipo().equalsIgnoreCase("Ataque") && h.puedeUsarse(enemigo)) {
                return h;
            } else if (h != null && h.getTipo().equalsIgnoreCase("Mana") && h.puedeUsarse(enemigo)) {
                return h;
            }     
        }
        return null;
    }
    
}
