package Vista;

import Modelo.OpcionesModelo;
import java.awt.Color;
import java.awt.Graphics2D;

public class OpcionesVista {

    private OpcionesModelo modelo;

    public OpcionesVista(OpcionesModelo modelo) {
        this.modelo = modelo;
    }

    public void draw(Graphics2D g) {

        // overlay oscuro
        g.setColor(new Color(0, 0, 0, 140));
        g.fillRect(0, 0, 600, 800);

        // 🪟 panel central
        int panelW = 400;
        int panelH = 300;
        int x = (600 - panelW) / 2;
        int y = (800 - panelH) / 2;

        g.setColor(Color.DARK_GRAY);
        g.fillRoundRect(x, y, panelW, panelH, 20, 20);

        g.setColor(Color.WHITE);
        g.drawString("OPCIONES", x + 160, y + 40);

        // opciones normales
        if (!modelo.isEnVolumen()) {

            String[] ops = modelo.getOpciones();

            for (int i = 0; i < ops.length; i++) {

                if (i == modelo.getSeleccion()) {
                    g.setColor(Color.YELLOW);
                } else {
                    g.setColor(Color.WHITE);
                }

                g.drawString(ops[i], x + 120, y + 120 + i * 40);
            }

            g.setColor(Color.LIGHT_GRAY);
            g.drawString("ENTER = seleccionar | ESC = volver", x + 80, y + panelH - 30);
        }

        // submenú volumen
        else {

            g.setColor(Color.WHITE);
            g.drawString("VOLUMEN", x + 150, y + 100);

            g.drawString("Musica: " + modelo.getVolumenMusica(), x + 120, y + 160);
            g.drawString("FX: " + modelo.getVolumenFX(), x + 120, y + 200);

            g.setColor(Color.LIGHT_GRAY);
            g.drawString("ESC = volver", x + 130, y + 260);
        }
    }
}