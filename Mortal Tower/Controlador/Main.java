package Controlador;

import java.awt.Menu;
import javax.swing.*;

import Modelo.MenuModelo;
import Vista.MenuPrincipal;
import Vista.Ventana;

public class Main {

    public static void main(String[] args) {
        
        MenuModelo menuModelo = new MenuModelo();
        MenuPrincipal menuPrincipal = new MenuPrincipal(menuModelo);
        Teclado teclado = new Teclado();

        menuPrincipal.addKeyListener(teclado);

        MenuControlador menuControlador = new MenuControlador(menuModelo, teclado);

        Ventana ventana = new Ventana();
        ventana.add(menuPrincipal);
        ventana.setVisible(true);

        //menuPrincipal.setFocusable(true);
        menuPrincipal.requestFocusInWindow();

        Timer timer = new Timer(16, e-> {menuControlador.update(); menuPrincipal.repaint();});
        timer.start();
    }
}