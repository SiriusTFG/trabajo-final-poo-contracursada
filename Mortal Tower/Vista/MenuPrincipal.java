package Vista;

import javax.swing.*;
import javax.imageio.ImageIO;
import Modelo.MenuModelo;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuPrincipal extends JPanel {

    private MenuModelo menuModelo;
    private Image fondo;

    private BufferedImage nueva;
    private BufferedImage opciones;
    private BufferedImage salir;

    private int selectedIndex = 0;

    public MenuPrincipal(MenuModelo menuModelo) {

        this.menuModelo = menuModelo;

        // Fondo
        fondo = new ImageIcon(getClass().getResource("/assets/Imagenes/MenuInicio/Fondo.jpg")).getImage();

        // Cargar sprites
        try {
            nueva = ImageIO.read(getClass().getResource("/assets/Imagenes/MenuInicio/nuevaPartida.png"));
            opciones = ImageIO.read(getClass().getResource("/assets/Imagenes/MenuInicio/opciones.png"));
            salir = ImageIO.read(getClass().getResource("/assets/Imagenes/MenuInicio/salir.png"));

        } catch (Exception e) {
            
            e.printStackTrace();
        }

        
        // Teclado
        setFocusable(true);

        /*addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    selectedIndex++;
                    if (selectedIndex > 2) selectedIndex = 0;
                    repaint();
                }

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    selectedIndex--;
                    if (selectedIndex < 0) selectedIndex = 2;
                    repaint();
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    ejecutarOpcion();
                }
            }
        });*/
    }

    //Sprites
    private Image getSprite(BufferedImage img, boolean selected) {

        int width = img.getWidth();
        int height = img.getHeight() / 2;

        int y = selected ? height : 0;

        return img.getSubimage(0, y, width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fondo
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);

        // Opciones
        int x = 100;
        int y = 430;
        int spacing = 120;

        BufferedImage[] imgs = {nueva, opciones, salir};

        for (int i = 0; i < imgs.length; i++) {

            //boolean selected = (i == selectedIndex);
            boolean selected;

            Image sprite;

            if(i ==menuModelo.getSeleccion()){
                selected = true;
                sprite = getSprite(imgs[i], selected);
            }else { 
                selected = false;
                sprite = getSprite(imgs[i], selected);
            }

            g.drawImage(sprite, x, y + i * spacing, 300, 100, this);
        }
    }

}