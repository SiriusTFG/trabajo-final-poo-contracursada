package Controlador;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Teclado implements KeyListener {

    public boolean up, down, left, right, select, back;

    //Se ejecuta al apretar una tecla
    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        switch (key) {

            case KeyEvent.VK_UP -> up = true;
            case KeyEvent.VK_DOWN -> down = true;
            case KeyEvent.VK_LEFT -> left = true;
            case KeyEvent.VK_RIGHT -> right = true;
            case KeyEvent.VK_ENTER -> select = true;
            case KeyEvent.VK_ESCAPE -> back = true;
        }
    }

    //Se ejecuta al soltar una tecla
    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        switch (key) {

            case KeyEvent.VK_UP -> up = false;
            case KeyEvent.VK_DOWN -> down = false;
            case KeyEvent.VK_LEFT -> left = false;
            case KeyEvent.VK_RIGHT -> right = false;
            case KeyEvent.VK_ENTER -> select = false;
            case KeyEvent.VK_ESCAPE -> back = false;
        }
    }

    public void resetActions() {
        select = false;
        back = false;
    }


    //se usa para escribir texto y capturar caracteres reales
    @Override
    public void keyTyped(KeyEvent e) {}

}