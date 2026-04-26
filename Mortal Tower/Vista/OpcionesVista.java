package Vista;

import Modelo.MenuOpcionesModelo;
import java.awt.Color;
import java.awt.Graphics;

public class OpcionesVista {

    private MenuOpcionesModelo modelo;

    public OpcionesVista(MenuOpcionesModelo modelo) {
        this.modelo = modelo;
    }

    public void render(Graphics g, int width, int height) {

        // 🌑 overlay oscuro
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRect(0, 0, width, height);

        // 🪟 panel central
        int panelW = 400;
        int panelH = 300;
        int x = (width - panelW) / 2;
        int y = (height - panelH) / 2;

        g.setColor(Color.DARK_GRAY);
        g.fillRoundRect(x, y, panelW, panelH, 20, 20);

        g.setColor(Color.WHITE);
        g.drawString("OPCIONES", x + 160, y + 40);

        // 📋 opciones normales
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

        // 🎚️ submenú volumen
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