package com.mortaTower.State;

import com.mortaTower.Modelo.Enemigo;
import com.mortaTower.Modelo.Entidad;
import com.mortaTower.Modelo.Habilidad;

public class ComportamientoJefeFase3 implements ComportamientoEnemigo {
    
    @Override
    public void accionEnemigo(Enemigo enemigo, Entidad objetivo) {
        
        Habilidad habilidadActual = null;
        int maxDanio = -1;

        for (Habilidad h : enemigo.getHabilidades()) {
            if (h != null && h.getTipo().equalsIgnoreCase("Ataque") && h.puedeUsarse(enemigo)) {
                if (h.getValorBase() > maxDanio) {
                    maxDanio = h.getValorBase();
                    habilidadActual = h;
                }
            }
        }

        if (habilidadActual == null) habilidadActual = buscarHabilidad(enemigo, "Mana");

        if (habilidadActual != null) {
            enemigo.setUltimaHabilidadUsada(habilidadActual.getNombre());
            habilidadActual.ejecutarHabilidad(enemigo, objetivo);
        } else {
            System.out.println("¡" + enemigo.getNombre() + " ruge cegado por la furia, pero está agotado!");
            enemigo.setUltimaHabilidadUsada("Rugido de Furia (Falla)");
        }
    }

    private Habilidad buscarHabilidad(Enemigo enemigo, String tipo) {
        for (Habilidad h : enemigo.getHabilidades()) {
            if (h != null && h.getTipo().equalsIgnoreCase(tipo) && h.puedeUsarse(enemigo)) return h;
        }
        return null;
    }
}
