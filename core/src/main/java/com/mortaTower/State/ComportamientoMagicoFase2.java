package com.mortaTower.State;

import com.mortaTower.Modelo.Enemigo;
import com.mortaTower.Modelo.Entidad;
import com.mortaTower.Modelo.Habilidad;

public class ComportamientoMagicoFase2 implements ComportamientoEnemigo{
    
    @Override
    public void accionEnemigo(Enemigo enemigo, Entidad objetivo) {
        if (enemigo.getVidaActual() <= enemigo.getVidaMax() * 0.3) {
            System.out.println("¡" + enemigo.getNombre() + " está herido y retrocede a postura defensiva FASE 3");

            enemigo.setDefensa(1.5);
            enemigo.curarVida(enemigo.getVidaActual());
            enemigo.setAtaque(enemigo.getAtaque() + 20);
            
            enemigo.setFaseVisual(3);
            enemigo.cambiarComportamiento(new ComportamientoMagicoFase3());
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
