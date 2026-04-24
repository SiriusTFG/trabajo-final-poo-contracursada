package Controlador;

import Modelo.MenuModelo;

public class MenuControlador {

    private SonidoControlador musica = new SonidoControlador();
    private SonidoControlador efectos = new SonidoControlador();

    private long ultimoInput = 0;
    private final long cooldown = 120;

    private MenuModelo menuModelo;
    private Teclado teclado;

    public MenuControlador(MenuModelo menuModelo, Teclado teclado) {
        this.menuModelo = menuModelo;
        this.teclado = teclado;

        musica.loop(0); // música de fondo
    }

    public void update() {

        long now = System.currentTimeMillis();

        if (teclado.up && now - ultimoInput > cooldown) {
            efectos.play(1);
            System.out.println("arriba");
            menuModelo.arriba();
            teclado.up = false;

            ultimoInput = now;
        }

        if (teclado.down && now - ultimoInput > cooldown) {
            efectos.play(1);
            System.out.println("abajo");
            menuModelo.abajo();
            teclado.down = false;

            ultimoInput = now;
        }

        if (teclado.select && now - ultimoInput > cooldown) {
            System.out.println("enter");
            ejecutar();
            teclado.select = false;

            ultimoInput = now;
        }
    }

    private void ejecutar() {
        int opcion = menuModelo.getSeleccion();
        System.out.println("Seleccion actual: " + opcion);

        switch (opcion) {
            case 0:
                efectos.play(2);
                musica.stop(0);
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