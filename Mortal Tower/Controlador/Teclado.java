package Controlador;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Teclado implements KeyListener {

    public boolean arriba, abajo, derecha, izquierda;

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_UP){arriba = true;}

        if(code == KeyEvent.VK_DOWN){abajo = true;}

        if(code == KeyEvent.VK_LEFT){izquierda = true;}

        if(code == KeyEvent.VK_RIGHT){derecha = true;}

    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_UP){arriba = false;}

        if(code == KeyEvent.VK_DOWN){abajo = false;}

        if(code == KeyEvent.VK_LEFT){izquierda = false;}

        if(code == KeyEvent.VK_RIGHT){derecha = false;}

    }

    
    
}
