package Controlador;

import java.awt.event.KeyListener;

import Modelo.MenuModelo;
import Vista.MenuPrincipal;

public class MenuControlador{

    private MenuModelo menuModelo;
    private Teclado teclado;
    //private MenuPrincipal menuPrincipal;

    public MenuControlador(MenuModelo menuModelo,  Teclado teclado){

        this.menuModelo = menuModelo;
        //this.menuPrincipal = menuPrincipal;
        this.teclado= teclado;

        //menuPrincipal.addKeyListener(teclado);


    }

    public void update() {

        if (teclado.up) {
            System.out.println("aribaaaaaaaa");
            menuModelo.arriba();
            teclado.up = false;
        }

        if (teclado.down){
            System.out.println("abajoooooooooooooo");
            menuModelo.abajo();
            teclado.down = false;
        }

        if (teclado.select){

            ejecutar();
            teclado.select = false;
        }

    }

    private void ejecutar() {
        int opcion = menuModelo.getSeleccion();
        System.out.println("Seleccion actual: " + opcion);

        switch (opcion) {
            case 0:
                System.out.println("nueva partida");
                break;
            case 1:
                System.out.println("opciones");
                break;
            case 2:
                System.out.println("saliendo...");
                System.exit(0);
                break;
            default:
                System.out.println("Opción inválida: " + opcion);
                break;
    }
}

    
}
