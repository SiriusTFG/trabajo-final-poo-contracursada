package Controlador;

import java.util.Scanner;

import Modelo.*;
import Strategy.ComportamientoAgresivo;
import Strategy.ComportamientoDefensivo;
import Vista.ConsolaVista;

public class JuegoControlador {

    private ConsolaVista vista;
    private Entidad enemigo;
    private Heroe[] personajes = new Heroe[2];

    public JuegoControlador() {
        this.vista = new ConsolaVista();
        crearPersonaje();
    }

    private void ejecutarSimulacion() {
        Entidad zombie = new Enemigo("Herrero Zombie", 100, 50, new ComportamientoAgresivo());
        Entidad cuervo = new Enemigo("Cuervo Sombrio", 120, 100, new ComportamientoDefensivo());

        zombie.setHabilidad(0, new HabilidadAtaque("Desgarrar", "Ulti", "Ataque", 30, 40, 0.5, 10));
        zombie.setHabilidad(1, new HabilidadAtaque("Mordida", "Daño leve", "Ataque", 10, 10, 0.2, 10));
        zombie.setHabilidad(2, new HabilidadAtaque("Rasguño", "Daño leve", "Ataque", 5, 5, 0.1, 10));
        zombie.setHabilidad(3, new HabilidadMana("Descansar", "Aumenta mana", "Mana", 0, 20, 5));

        cuervo.setHabilidad(0, new HabilidadAtaque("Golpe Aereo", "Ulti", "Ataque", 35, 30, 0.5, 10));
        cuervo.setHabilidad(1, new HabilidadCuracion("Curacion", "Cura heridas", "Curacion", 30,45, 3));
        cuervo.setHabilidad(2, new HabilidadDefensa("Escudo de alas", "Defensa de ataque", "Defensa", 20, 0, 0.5));
        cuervo.setHabilidad(3, new HabilidadMana("Descansar", "Aumenta mana", "Mana", 0, 20, 5));

       vista.mostrarMensaje("=== INICIO DE LA SIMULACIÓN DE COMBATE ===");
       vista.mostrarMensaje(zombie.getNombre() + " vs " + cuervo.getNombre());

       int turno = 1;
       while (zombie.getVidaActual() > 0 && cuervo.getVidaActual() > 0) {
        vista.mostrarMensaje("\n--- TURNO " + turno + " ---");

        // Turno del Zombie
        zombie.realizarTurno(cuervo);
        
        if (cuervo.getVidaActual() <= 0) {
            vista.mostrarMensaje("¡El " + zombie.getNombre() + " ha ganado la simulación!");
            break;
        }

        // Turno del Jefe
        cuervo.realizarTurno(zombie);

        if (zombie.getVidaActual() <= 0) {
            vista.mostrarMensaje("¡El " + cuervo.getNombre() + " ha ganado la simulación!");
            break;
        }

        turno++;
        if(turno > 20) break;
       }
       vista.mostrarMensaje("=== FIN DE LA SIMULACIÓN ===");

    }

    private void crearPersonaje() {
        personajes[0] = new Heroe("Caballero", 100, 100);
        personajes[1] = new Heroe("Mago", 80, 120);
    }

    public void iniciarJuego() {
        Scanner sc = new Scanner(System.in);

        
        vista.mostrarMensaje("¡Bienvenido a Mortal Tower!");
       
        int opcion;

        Heroe heroe = null;
        do { 
            vista.mostrarMenuHeroes();
            opcion = sc.nextInt();

            if (opcion >= 1 && opcion <= 2) {
                heroe = personajes[opcion - 1];
                break;
             } else {
                vista.mostrarMensaje("Opción no válida, por favor selecciona una opción entre 1 y 2.");
            }
        } while (true);
     
        vista.mostrarMensaje("Elegiste: " + heroe.getNombre());
        vista.mostrarMensaje("Vida actual: " + heroe.getVidaActual());
        vista.mostrarMensaje("Mana actual: " + heroe.getManaActual());

        ejecutarSimulacion();
    }
    private void turnoHeroe(Heroe heroe, Entidad enemigo, Scanner sc) {
    int opcion;
    do {
        vista.mostrarMensaje( "=== TURNO DEL HÉROE ===");
        vista.mostrarMensaje("1. Atacar");
        vista.mostrarMensaje("2 - Defender");
        vista.mostrarMensaje("3 - Usar Habilidad");

        opcion = sc.nextInt();

        switch (opcion) {
            case 1:
                heroe.atacar(enemigo);
                return;
            case 2:
                // Implementar defensa
                heroe.activarDefensa(0.5);
                vista.mostrarMensaje(heroe.getNombre() + " se defiende, reduciendo el daño recibido en un 50% durante el próximo turno.");
                return;
            case 3:
                // Implementar uso de habilidad
               vista.mostrarMensaje("Uso de habilidad pendiente"); // Usar la primera habilidad como ejemplo
                return;
            default:
                vista.mostrarMensaje("Opción no válida, por favor selecciona una opción entre 1 y 3.");
        }
      
    } while (true);

}
}