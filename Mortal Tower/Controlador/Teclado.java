package Controlador;

import java.awt.event.KeyEvent;

public class Teclado {

    public boolean up, down, left, right, select, back;

    public void keyPressed(int key) {

        switch (key) {

            case KeyEvent.VK_UP -> up = true;
            case KeyEvent.VK_DOWN -> down = true;
            case KeyEvent.VK_LEFT -> left = true;
            case KeyEvent.VK_RIGHT -> right = true;
            case KeyEvent.VK_ENTER -> select = true;
            case KeyEvent.VK_ESCAPE -> back = true;
        }
    }

    public void keyReleased(int key) {

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
}