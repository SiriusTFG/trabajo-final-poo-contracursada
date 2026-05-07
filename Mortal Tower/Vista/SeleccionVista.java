package Vista;

import java.awt.*;
import javax.swing.*;

import Modelo.MenuModelo;
import Modelo.SeleccionModelo;

public class SeleccionVista {
    
    private Image cuadro;
    private SeleccionModelo modelo;

    
    public SeleccionVista(SeleccionModelo modelo){

        this.modelo = modelo;
        // Sprites
        try {
            cuadro = new ImageIcon(getClass().getResource("/assets/Imagenes/SeleccionPersonaje/seleccionPersonaje.png")).getImage();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {


        g.drawImage(cuadro, 0, 0, (int) g.getClipBounds().getWidth(), (int) g.getClipBounds().getHeight(),null);
    }

}