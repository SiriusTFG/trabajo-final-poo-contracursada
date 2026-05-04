package Controlador;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Teclado implements KeyListener {

    public boolean up, down, left, right, select, back;

    public boolean upPressed, downPressed, leftPressed, rightPressed, selectPressed, backPressed;

    //Se ejecuta al apretar una tecla
    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        switch (key) {

            case KeyEvent.VK_UP -> {if (!up) upPressed = true; up = true;}
            case KeyEvent.VK_DOWN -> {if (!down) downPressed = true;down = true;}
            case KeyEvent.VK_LEFT -> {if (!left) leftPressed = true; left = true;}
            case KeyEvent.VK_RIGHT -> {if (!right) rightPressed = true; right = true;}
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

    public void resetPressed() {
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        selectPressed = false;
        backPressed = false;
    }


    //se usa para escribir texto y capturar caracteres reales
    @Override
    public void keyTyped(KeyEvent e) {}

}