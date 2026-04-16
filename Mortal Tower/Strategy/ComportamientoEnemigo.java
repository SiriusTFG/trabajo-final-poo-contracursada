package Strategy;

import Modelo.Enemigo;
import Modelo.Entidad;

public interface ComportamientoEnemigo {

    void accionEnemigo(Enemigo enemigo, Entidad objetivo);
    
}
