package com.mortaTower.Screens;

import com.mortaTower.Main;
import com.mortaTower.Controlador.SeleccionControlador;
import com.mortaTower.Modelo.SeleccionModelo;
import com.mortaTower.Modelo.SeleccionModelo.Estado;
import com.mortaTower.Vista.NombreVista;
import com.mortaTower.Vista.SeleccionVista;

public class SeleccionScreen extends Screens {

    private final SeleccionModelo modelo;
    private final SeleccionVista vista;
    private final NombreVista nombreVista;
    private final SeleccionControlador controlador;

    public SeleccionScreen(Main game){

        super(game);

        this.modelo = new SeleccionModelo();
        this.controlador = new SeleccionControlador(modelo, game.teclado, game.audio);
        this.vista = new SeleccionVista(modelo, stage);
        this.nombreVista= new NombreVista(modelo, stage);
        
    }

    @Override
    public void update(float delta) {

        controlador.update();

        if (modelo.getEstadoActual() == Estado.MENU){

            game.setScreen(new MenuScreen(game));
            dispose();

        }

    }

    @Override
    public void draw(float delta) {
       // menu principal
        vista.draw(spriteBatch);

        if (modelo.getEstadoActual() == Estado.NOMBRE){
            
            nombreVista.draw(spriteBatch);
            
        }else{
            nombreVista.getNamTextField().setVisible(false);
        }

        

    }

    
    
}
