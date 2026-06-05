package com.mortaTower.State;

import com.mortaTower.Modelo.Enemigo;
import com.mortaTower.Modelo.Entidad;

public interface ComportamientoEnemigo {

    void accionEnemigo(Enemigo enemigo, Entidad objetivo);
    
}