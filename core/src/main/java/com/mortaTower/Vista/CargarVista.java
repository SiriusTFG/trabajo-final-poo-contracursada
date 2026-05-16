package com.mortaTower.Vista;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mortaTower.Modelo.CargarModelo;
import static com.mortaTower.Screens.Screens.WORLD_HEIGHT;
import static com.mortaTower.Screens.Screens.WORLD_WIDTH;

public class CargarVista {

    private CargarModelo cargarModelo;
    private Texture fondo;
    private BitmapFont font;
  
    private Texture slotNormal;
    private Texture slotSelected;


  

    public CargarVista(CargarModelo cargarModelo) {
        this.cargarModelo = cargarModelo;
        
        fondo = new Texture ("Imagenes/MenuInicio/Fondo1.png");
        
        slotNormal = new Texture ("Imagenes/MenuInicio/boton.png");
        slotSelected = new Texture("Imagenes/MenuInicio/boton1.png");
        font = new BitmapFont();
        font.getData().setScale(2f);
        font.setColor(Color.WHITE);
        
    }
    public void draw(SpriteBatch batch) {
        
        batch.draw(fondo, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);


        float optionX = WORLD_WIDTH * 0.42f;
        float optionY = WORLD_HEIGHT * 0.7f;
        float separacion = WORLD_HEIGHT * 0.15f;

        var partidas = cargarModelo.getPartidas();
        int seleccion = cargarModelo.getSeleccion();

        font.setColor(Color.WHITE);
        for (int i = 0; i < partidas.size(); i++) {

          
            Texture slot = (i == seleccion) ? slotSelected : slotNormal;
            
        
            float slotWidth = WORLD_WIDTH * 0.44f;
            float slotHeight = 90;

            batch.draw (slot, WORLD_WIDTH * 0.28f, optionY - separacion * i - 45, slotWidth, slotHeight);
                

            if(i == seleccion){
               font.setColor(Color.YELLOW);
               
                font.draw(batch, partidas.get(i), WORLD_WIDTH * 0.37f, optionY - separacion * i);
                font.setColor(Color.WHITE);
            } else {
             
                font.draw(batch, partidas.get (i), WORLD_WIDTH * 0.37f, optionY - separacion * i);
            }
            }

    }
    public void dispose() {
        slotNormal.dispose();
        slotSelected.dispose();
        fondo.dispose();
        font.dispose();
    }

}
