package Vista;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class MenuPrincipal extends JPanel {

    private BufferedImage nueva;
    private BufferedImage opciones;
    private BufferedImage salir;

    private Image fondo;

    private int selectedIndex = 0;

    public MenuPrincipal() {

        // Fondo
        fondo = new ImageIcon(
                getClass().getResource("/assets/Imagenes/MenuInicio/Fondo.jpg")
        ).getImage();

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

        addKeyListener(new KeyAdapter() {
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
        });
    }

    // 🔥 Sprites verticales (normal / seleccionado)
    private Image getSprite(BufferedImage img, boolean selected) {

        int width = img.getWidth();
        int height = img.getHeight() / 2;

        int y = selected ? height : 0;

        return img.getSubimage(0, y, width, height);
    }

    // 🎮 Acciones del menú
    private void ejecutarOpcion() {

        switch (selectedIndex) {

            case 0:
                System.out.println("Nueva Partida");
                break;

            case 1:
                System.out.println("Opciones");
                break;

            case 2:
                System.out.println("Salir");
                System.exit(0);
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Fondo
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);

        int x = 100;
        int y = 430;
        int spacing = 120;

        BufferedImage[] imgs = {nueva, opciones, salir};

        for (int i = 0; i < imgs.length; i++) {

            boolean selected = (i == selectedIndex);

            Image sprite = getSprite(imgs[i], selected);

            g.drawImage(sprite, x, y + i * spacing, 300, 100, this);
        }
    }
}