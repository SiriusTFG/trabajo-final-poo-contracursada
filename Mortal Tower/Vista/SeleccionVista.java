package Vista;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

import Modelo.SeleccionModelo;

public class SeleccionVista {
    
    private Image fondo;
    private BufferedImage caballero, mago;
    private SeleccionModelo modelo;

    
    public SeleccionVista(SeleccionModelo modelo){

        this.modelo = modelo;
        // Sprites
        try {
            fondo = new ImageIcon(getClass().getResource("/assets/Imagenes/SeleccionPersonaje/seleccionPersonaje.png")).getImage();

            caballero = ImageIO.read(getClass().getResource("/assets/Imagenes/SeleccionPersonaje/seleccionCaballero.png"));
            mago = ImageIO.read(getClass().getResource("/assets/Imagenes/SeleccionPersonaje/seleccionMago.png"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // convierte sprite (normal / seleccionado)
    private Image getSprite(BufferedImage img, boolean selected) {

        int width = img.getWidth();
        int height = img.getHeight() / 2;

        int y = selected ? height : 0;

        return img.getSubimage(0, y, width, height);
    }

    // ahora es draw(), no paintComponent()
    public void draw(Graphics2D g) {

        // Fondo
        g.drawImage(fondo, 0, 0, (int) g.getClipBounds().getWidth(), (int) g.getClipBounds().getHeight(),null);
        

        BufferedImage[] imgs = {caballero, mago};

        int x = 155;
        int y = 155;
        int spacing = 310;

        for (int i = 0; i < imgs.length; i++) {

            boolean selected = (i == modelo.getSeleccion());

            Image sprite = getSprite(imgs[i], selected);

            g.drawImage(sprite, x + i * spacing, y, 300, 100, null);
        }
    }

}