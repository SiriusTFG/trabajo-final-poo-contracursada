package Strategy;

import Modelo.Enemigo;
import Modelo.Entidad;
import Modelo.Habilidad;

public class ComportamientoAgresivo implements ComportamientoEnemigo {

    @Override 
    public void accionEnemigo(Enemigo enemigo, Entidad objetivo) {
        Habilidad mejorHabilidad = null;
        int maxDanio = -1;

        // busca la mejor habilidad para utilizar teniendo en cuenta el mana actual
        for (Habilidad hab : enemigo.getHabilidades()) {
            if (hab != null && hab.getTipo().equalsIgnoreCase("Ataque") && hab.puedeUsarse(enemigo)) {
                if (hab.getValorBase() > maxDanio) {
                    maxDanio = hab.getValorBase();
                    mejorHabilidad = hab; 
                }
            }
        }        

        if (mejorHabilidad != null) {
            System.out.println(enemigo.getNombre() + " usa: " + mejorHabilidad.getNombre());
            mejorHabilidad.ejecutarHabilidad(enemigo, objetivo); 
        } else {
            mejorHabilidad = buscarHabilidad(enemigo);
            System.out.println(enemigo.getNombre() + " usa: " + mejorHabilidad.getNombre());
            mejorHabilidad.ejecutarHabilidad(enemigo, objetivo);
        }
        
    }

    private Habilidad buscarHabilidad(Enemigo enemigo) {
        for (Habilidad h : enemigo.getHabilidades()) {
            if (h != null && enemigo.getManaActual() >= h.getCostoMana()) return h;
        }
        return null;
    }
    
}
