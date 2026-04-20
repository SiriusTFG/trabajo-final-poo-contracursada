package Controlador;

import Modelo.*;
import Strategy.ComportamientoAgresivo;
import Strategy.ComportamientoEnemigo;
import Vista.ConsolaVista;
import java.util.Scanner;

public class JuegoControlador {

    private ConsolaVista vista;
    private Entidad enemigo;
    private Entidad[] personajes = new Entidad[4];

    public JuegoControlador() {
        this.vista = new ConsolaVista();
        crearEnemigo();
        crearPersonaje();
    }

    private void crearEnemigo() {
        ComportamientoEnemigo comportamiento = new ComportamientoAgresivo();
        enemigo = new Enemigo("zombie", 100, 100, comportamiento);
    }

    private void crearPersonaje() {
        personajes[1] = new Heroe("Caballero", 100, 100);
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

        enemigo.realizarTurno(heroe);
        vista.mostrarMensaje("Elegiste: " + heroe.getClass().getSimpleName());
        vista.mostrarMensaje("Vida actual: " + heroe.getVidaActual());
        vista.mostrarMensaje("Mana actual: " + heroe.getManaActual());
    }
}