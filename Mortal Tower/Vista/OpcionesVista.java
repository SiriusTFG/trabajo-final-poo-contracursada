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
    private Image cuadro, menuControles;
    private BufferedImage[][] volMusica, volEfecto, controles; // [nivel][estado]

    public OpcionesVista(OpcionesModelo modelo) {
        this.modelo = modelo;

        // Sprites
        try {
            cuadro = new ImageIcon(getClass().getResource("/assets/Imagenes/Opciones/menuOpciones.png")).getImage();
            menuControles = new ImageIcon(getClass().getResource("/assets/Imagenes/Opciones/menuControles.png")).getImage();

            BufferedImage Musica = ImageIO.read(getClass().getResource("/assets/Imagenes/Opciones/volMusica.png"));
            BufferedImage Efecto = ImageIO.read(getClass().getResource("/assets/Imagenes/Opciones/volEfectos.png"));
            BufferedImage Controles = ImageIO.read(getClass().getResource("/assets/Imagenes/Opciones/controles.png"));

            volMusica = cargarBarra(Musica, 12, 2);
            volEfecto = cargarBarra(Efecto, 12, 2);
            controles = cargarBarra(Controles, 1, 2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Image getBarra(BufferedImage[][] barra, int valor, boolean seleccionado) {

        int estado = seleccionado ? 0 : 1;

        int niveles = barra.length; // devuelve 11;
        System.out.println(niveles);
        int nivel = (barra.length - 1) - valor; // devuelve 0 por que valor = 11 en modelo;

        nivel = Math.max(0, Math.min(nivel, niveles - 1)); // devuelve 0;

        return barra[nivel][estado]; //tendria que devolver la primera vez 0 , 0 o 0, 1 si es selec o no
        //devuelve 10, 0 o 1
    }
    
    // convierte sprite (normal / seleccionado)
    private BufferedImage[][] cargarBarra(BufferedImage sheet, int niveles, int estados) {

    int spriteWidth = sheet.getWidth() / estados;
    int totalHeight = sheet.getHeight();

    int marginY = 4; // lo dejas fijo si sabes que es constante
    int realHeight = (totalHeight - (niveles - 1) * marginY) / niveles;

    BufferedImage[][] sprites = new BufferedImage[niveles][estados];

    for (int nivel = 0; nivel < niveles; nivel++) {
        for (int estado = 0; estado < estados; estado++) {

            int y = nivel * (realHeight + marginY);

            sprites[nivel][estado] = sheet.getSubimage(
                estado * spriteWidth,
                y,
                spriteWidth,
                realHeight
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

        int panelW = 800;
        int panelH = 600;
        int x = (width - panelW) / 2;
        int y = (height - panelH) / 2;

        if (modelo.getEstado() == OpcionesModelo.EstadoMenu.OPCIONES) {
            
        g.drawImage(cuadro, x, y, panelW, panelH, null);
        
        // MUSICA
        g.drawImage(
            getBarra(volMusica, modelo.getMusica(), modelo.getSeleccion() == 0),
            x + 78, y + 110, 650, 110, null
        );

        // EFECTOS
        g.drawImage(
            getBarra(volEfecto, modelo.getFx(), modelo.getSeleccion() == 1),
            x + 78, y + 238,650, 110, null
        );

        g.drawImage (getBarra(controles, modelo.getcontroles(), modelo.getSeleccion() == 2), x + 78, y + 370, 650, 110, null);

    }else if (modelo.getEstado() == OpcionesModelo.EstadoMenu.CONTROLES) {

        g.drawImage(menuControles, x, y, panelW, panelH, null);

    }
    }
}