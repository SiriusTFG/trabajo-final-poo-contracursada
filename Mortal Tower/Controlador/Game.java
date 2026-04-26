package Controlador;

import javax.swing.*;

import GameState.GameState;
import GameState.MenuState;

import java.awt.*;

public class Game extends JPanel implements Runnable {

    private Thread thread;
    private boolean running;

    private GameState currentState;
    private Teclado input;

    public Game(Teclado input) {
        this.input = input;

        setFocusable(true);
        addKeyListener(input);

        setState(new MenuState(input, this));
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
        if (currentState != null) {
            currentState.update();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (currentState != null) {
            currentState.draw((Graphics2D) g);
        }
    }
}