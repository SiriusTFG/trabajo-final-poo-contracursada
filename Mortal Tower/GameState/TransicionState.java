package GameState;

import java.awt.Color;
import java.awt.Graphics2D;

import Controlador.Game;

public class TransicionState implements GameState {

    private Game game;
    private int alpha = 0;
    private boolean fadeOut = true;
    private boolean esperando = false;

    private long inicioEspera;
    private long duracionEspera = 1500;
    private Runnable cambioEstado;

    public TransicionState(Game game, Runnable onComplete) {
        this.game = game;
        this.cambioEstado = onComplete;
    }

    @Override
    public void update() {

        // FADE OUT
        if (fadeOut) {

            alpha += 8;

            if (alpha >= 255) {

                alpha = 255;

                cambioEstado.run();

                fadeOut = false;

                esperando = true;

                inicioEspera = System.currentTimeMillis();
            }

            return;
        }

        // ESPERA
        if (esperando) {

            long ahora = System.currentTimeMillis();

            if (ahora - inicioEspera >= duracionEspera) {

                esperando = false;
            }

            return;
        }

        // FADE IN
        alpha -= 5;

        if (alpha <= 0) {

            alpha = 0;

            game.setOverlay(null);
        }
    }

    @Override
    public void draw(Graphics2D g) {

        g.setColor(new Color(0, 0, 0, alpha));
        g.fillRect(
            0,
            0,
            g.getClipBounds().width,
            g.getClipBounds().height
        );
    }
}