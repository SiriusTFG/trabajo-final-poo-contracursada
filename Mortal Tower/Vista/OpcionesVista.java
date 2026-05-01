package Vista;

import Modelo.OpcionesModelo;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class OpcionesVista {

    private OpcionesModelo modelo;
    private Image cuadro;
    private BufferedImage[][] volMusica, volEfecto, controles; // [estado][nivel]

    public OpcionesVista(OpcionesModelo modelo) {
        this.modelo = modelo;

        // Sprites
        try {
            cuadro = new ImageIcon(getClass().getResource("/assets/Imagenes/Opciones/menuOpciones.png")).getImage();

            BufferedImage Musica = ImageIO.read(getClass().getResource("/assets/Imagenes/Opciones/volMusica.png"));
            BufferedImage Efecto = ImageIO.read(getClass().getResource("/assets/Imagenes/Opciones/volEfectos.png"));
            BufferedImage Controles = ImageIO.read(getClass().getResource("/assets/Imagenes/Opciones/controles.png"));

            volMusica = cargarBarra(Musica, 2, 11);
            volEfecto = cargarBarra(Efecto, 2, 11);
            controles = cargarBarra(Controles, 2, 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Image getBarra(BufferedImage[][] barra, int valor, boolean seleccionado) {

        int estado = seleccionado ? 0 : 1;

        int niveles = barra[0].length;
        int nivel = (int)((valor / 100.0) * (niveles - 1));

        if (nivel < 0) nivel = 0;
        if (nivel >= niveles) nivel = niveles - 1;

        return barra[estado][nivel];
    }
    


    // convierte sprite (normal / seleccionado)
    private BufferedImage[][] cargarBarra(BufferedImage sheet, int estados, int niveles) {

        int spriteWidth = 715;
        int spriteHeight = 125;

            BufferedImage[][] sprites = new BufferedImage[estados][niveles];

        for (int estado = 0; estado < estados; estado++) {
            for (int nivel = 0; nivel < niveles; nivel++) {

                sprites[estado][nivel] = sheet.getSubimage(
                    estado * spriteWidth,
                    nivel * spriteHeight,
                    spriteWidth,
                    spriteHeight
                );
            }
        }

        return sprites;
    }

    public void draw(Graphics2D g) {

    int width = g.getClipBounds().width;
    int height = g.getClipBounds().height;

    g.setColor(new Color(0, 0, 0, 140));
    g.fillRect(0, 0, width, height);

    int panelW = 1600;
    int panelH = 800;
    int x = (width - panelW) / 2;
    int y = (height - panelH) / 2;

    g.drawImage(cuadro, x, y, panelW, panelH, null);

    // 🎵 MUSICA
    g.drawImage(
        getBarra(volMusica, modelo.getVolumenMusica(), modelo.getSeleccion() == 0),
        x + 475, y + 200, 650, 110, null
    );

    // 🔊 EFECTOS
    g.drawImage(
        getBarra(volEfecto, modelo.getVolumenFX(), modelo.getSeleccion() == 1),
        x + 475, y + 340,650, 110, null
    );

    g.drawImage (getBarra(controles, modelo.getcontroles(), modelo.getSeleccion() == 1), x + 475, y + 480, 650, 110, null);
}
}