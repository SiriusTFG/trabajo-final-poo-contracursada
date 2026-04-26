
package Vista;

import Modelo.MenuModelo;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuPrincipal {

    private MenuModelo menuModelo;

    private Image fondo;

    private BufferedImage nueva;
    private BufferedImage opciones;
    private BufferedImage salir;

    public MenuPrincipal(MenuModelo menuModelo) {
        this.menuModelo = menuModelo;

        // Fondo
        fondo = new ImageIcon(getClass().getResource("/assets/Imagenes/MenuInicio/Fondo.jpg")).getImage();

        // Sprites
        try {
            nueva = ImageIO.read(getClass().getResource("/assets/Imagenes/MenuInicio/nuevaPartida.png"));
            opciones = ImageIO.read(getClass().getResource("/assets/Imagenes/MenuInicio/opciones.png"));
            salir = ImageIO.read(getClass().getResource("/assets/Imagenes/MenuInicio/salir.png"));

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

    // 🔥 ahora es draw(), no paintComponent()
    public void draw(Graphics2D g) {

        // Fondo
        g.drawImage(fondo, 0, 0,
                (int) g.getClipBounds().getWidth(),
                (int) g.getClipBounds().getHeight(),
                null);

        BufferedImage[] imgs = {nueva, opciones, salir};

        int x = 100;
        int y = 430;
        int spacing = 120;

        for (int i = 0; i < imgs.length; i++) {

            boolean selected = (i == menuModelo.getSeleccion());

            Image sprite = getSprite(imgs[i], selected);

            g.drawImage(sprite, x, y + i * spacing, 300, 100, null);
        }
    }
}