package Controlador;

import java.util.Scanner;
import Modelo.*;
import Vista.ConsolaVista;

public class JuegoControlador {

    private ConsolaVista vista;

    public JuegoControlador() {
        this.vista = new ConsolaVista();
    }

    public void iniciarJuego() {
        Scanner sc = new Scanner(System.in);

        
        vista.mostrarMensaje("¡Bienvenido a Mortal Tower!");
        vista.mostrarMenuHeroes();
        int opcion = sc.nextInt();

        Heroe heroe = null;
        switch (opcion) {
            case 1:
                heroe = new Mago();
                break;
            case 2:
                heroe = new Orco();
                break;
            case 3:
                heroe = new Elfo();
                break;
            case 4:
                heroe = new Caballero();
                break;
            default:
                vista.mostrarMensaje("Opción inválida. Se seleccionará Mago por defecto.");
                heroe = new Mago();

        }
        vista.mostrarMensaje("Elegiste: " + heroe.getClass().getSimpleName());
        vista.mostrarMensaje("Vida actual: " + heroe.getVidaActual());
        vista.mostrarMensaje("Mana actual: " + heroe.getManaActual());
    }
}