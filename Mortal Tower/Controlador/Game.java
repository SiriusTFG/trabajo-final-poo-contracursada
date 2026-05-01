package Controlador;

import javax.swing.*;
import java.awt.*;
import GameState.GameState;
import GameState.MenuState;

public class Game extends JPanel implements Runnable {

    private Thread thread;
    private boolean running;

    private GameState currentState;
    private GameState overlayState;
    private Teclado input;

    public Game(Teclado input) {

        this.input = input;

        setFocusable(true);
        addKeyListener(input);

        setState(new MenuState(input, this)); //nos muestra la primer pantalla(menuPrincipal)
    }

    public void setOverlay(GameState overlay) {
        this.overlayState = overlay;
    }

    public void setState(GameState newState) {
        this.currentState = newState;
    }

    public void startGame() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

        while (running) {

            update();
            repaint();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {

    if (overlayState != null) {
        overlayState.update();
        return; // bloquea el juego debajo
    }

    if (currentState != null) {
        currentState.update();
    }
}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (currentState != null) {
            currentState.draw(g2);
        }

        if (overlayState != null) {
            overlayState.draw(g2);
        }
    } 
}