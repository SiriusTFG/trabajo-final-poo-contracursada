package com.mortaTower.Screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mortaTower.Main;
import com.mortaTower.Controlador.OpcionesControlador;
import com.mortaTower.Modelo.OpcionesModelo;
import com.mortaTower.Modelo.OpcionesModelo.EstadoEnum;
import com.mortaTower.Vista.OpcionesVista;

public class OpcionesOverlay {

    private final OpcionesModelo modelo;
    private final OpcionesVista vista;
    private final OpcionesControlador controlador;

    private boolean cerrar = false;

    public OpcionesOverlay(Main game) {

        modelo = new OpcionesModelo();
        controlador = new OpcionesControlador(modelo,game.teclado,game.audio);
        vista = new OpcionesVista(modelo);

    }

    public void update(float delta) {

        controlador.update();

       if (modelo.getEstadoActual() == EstadoEnum.MENU) {cerrar = true; modelo.EstadoEnum(EstadoEnum.OPCIONES);}
        
    }

    public void draw(SpriteBatch batch) {
        vista.draw(batch);
    }
    
    public void open() {
       cerrar = false;
    }
    
    public boolean shouldClose() {
        return cerrar;
    }
}
