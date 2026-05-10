package com.mortaTower.Screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mortaTower.Main;
import com.mortaTower.Controlador.OpcionesControlador.Action;
import com.mortaTower.Controlador.Teclado;
import com.mortaTower.Controlador.OpcionesControlador;
import com.mortaTower.Modelo.OpcionesModelo;
import com.mortaTower.Modelo.OpcionesModelo.EstadoEnum;
import com.mortaTower.Vista.OpcionesVista;

public class OpcionesOverlay {

    private final OpcionesModelo modelo;
    private final OpcionesVista vista;
    private final OpcionesControlador controlador;

    private final Teclado teclado;
    private boolean cerrar = false;

    public OpcionesOverlay(Main game) {

        modelo = new OpcionesModelo();
        controlador = new OpcionesControlador(modelo,game.teclado,game.audio);
        vista = new OpcionesVista(modelo);

        this.teclado = game.teclado;
    }

    public void update(float delta) {

        controlador.update();

       if (modelo.getEstadoActual() == EstadoEnum.MENU) {cerrar = true;}
        
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
