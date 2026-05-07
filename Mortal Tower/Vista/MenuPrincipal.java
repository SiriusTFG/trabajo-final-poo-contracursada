
package Vista;

import Modelo.MenuModelo;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuPrincipal {

    private MenuModelo menuModelo;

    private Image fondo;

    private BufferedImage nueva, opciones, creditos, salir;

    public MenuPrincipal(MenuModelo menuModelo) {
        this.menuModelo = menuModelo;

        // Sprites
        try {
            fondo = new ImageIcon(getClass().getResource("/assets/Imagenes/MenuInicio/Fondo.png")).getImage();

            nueva = ImageIO.read(getClass().getResource("/assets/Imagenes/MenuInicio/nueva.png"));
            opciones = ImageIO.read(getClass().getResource("/assets/Imagenes/MenuInicio/op.png"));
            creditos = ImageIO.read(getClass().getResource("/assets/Imagenes/MenuInicio/creditos.png"));
            salir = ImageIO.read(getClass().getResource("/assets/Imagenes/MenuInicio/sal.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // convierte sprite (normal / seleccionado)
    private Image getSprite(BufferedImage img, boolean selected) {

        int width = img.getWidth()/ 2;
        int height = img.getHeight() ;

          int x = selected ? width : 0;


        return img.getSubimage(x, 0, width, height);
    }

    // ahora es draw(), no paintComponent()
    public void draw(Graphics2D g) {

        // Fondo
        g.drawImage(fondo, 0, 0, (int) g.getClipBounds().getWidth(), (int) g.getClipBounds().getHeight(),null);

        BufferedImage[] imgs = {nueva, opciones, creditos, salir};

        int x = 150;
        int y = 410;
        int spacing = 100;

        for (int i = 0; i < imgs.length; i++) {

            boolean selected = (i == menuModelo.getSeleccion());

            Image sprite = getSprite(imgs[i], selected);

            g.drawImage(sprite, x, y + i * spacing, 435, 80, null);
        }
    }
}