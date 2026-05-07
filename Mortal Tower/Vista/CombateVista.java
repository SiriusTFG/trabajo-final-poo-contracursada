package Vista;

import Modelo.CombateModelo;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class CombateVista {
    private CombateModelo model;
    private Image Fondo;
    private BufferedImage luchar, habilidad, opciones;

    //Constructor
    public CombateVista(CombateModelo model) {
        this.model = model;

        // Sprites
        try {
            Fondo = new ImageIcon(getClass().getResource("/assets/Imagenes/Combate/Fondo.jpg")).getImage();

            luchar = ImageIO.read(getClass().getResource("/assets/Imagenes/Combate/luchar.png"));
            habilidad = ImageIO.read(getClass().getResource("/assets/Imagenes/Combate/habilidad.png"));
            opciones = ImageIO.read(getClass().getResource("/assets/Imagenes/Combate/opciones.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Atributos como botones etc
    public CombateVista() {
        //Inicializar componentes gráficos
    }

    public void draw(Graphics2D g) {
        //Dibujar la interfaz de combate
    }
}
