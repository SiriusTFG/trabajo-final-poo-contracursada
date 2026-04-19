package Strategy;

import Modelo.Enemigo;
import Modelo.Entidad;
import Modelo.Habilidad;

public class ComportamientoAgresivo implements ComportamientoEnemigo {

    @Override 
    public void accionEnemigo(Enemigo enemigo, Entidad objetivo) {
        Habilidad mejorHabildad = null;
        int maxDanio = -1;

        // busca la mejor habilidad para utilizar teniendo en cuenta el mana actual
        for (Habilidad hab : enemigo.getHabilidades()) {
            if (hab != null && enemigo.getManaActual() >= hab.getCostoMana()) {
                if (hab.getValorBase() > maxDanio) {
                    maxDanio = hab.getValorBase();
                    mejorHabildad = hab;
                }
            }        
        }

        //utiliza la habilidad elegida
        mejorHabildad.ejecutarHabilidad(enemigo, objetivo); 
    }
    
}
