package Vista;

import Modelo.CombateModelo;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class CombateVista {
    private CombateModelo model;
    private Image Fondo;
    private BufferedImage habilidad, opciones;

    //Constructor
    public CombateVista(CombateModelo model) {
        this.model = model;

        // Sprites
        try {
            Fondo = new ImageIcon(getClass().getResource("/assets/Imagenes/Combate/FondoCombate.png")).getImage();

            habilidad = ImageIO.read(getClass().getResource("/assets/Imagenes/Combate/habilidades.png"));
            opciones = ImageIO.read(getClass().getResource("/assets/Imagenes/MenuInicio/opciones.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Convertir sprite (normal / seleccionado)
    private Image getSprite(BufferedImage img, boolean selected) {
        int width = img.getWidth();
        int height = img.getHeight() / 2;
        int y = selected ? height : 0;
        return img.getSubimage(0, y, width, height);
    }

    public void draw(Graphics2D g) {
        // Fondo
        g.drawImage(Fondo, 0, 0, (int) g.getClipBounds().getWidth(), (int) g.getClipBounds().getHeight(), null);

        BufferedImage[] imgs = {habilidad, opciones};

        int x = 150;
        int y = 920;
        int spacing = 525;

        for (int i = 0; i < imgs.length; i++) {
            boolean selected = (i == model.getSeleccion());
            g.drawImage(getSprite(imgs[i], selected), x + (i * spacing), y, null);
        }
    }
}