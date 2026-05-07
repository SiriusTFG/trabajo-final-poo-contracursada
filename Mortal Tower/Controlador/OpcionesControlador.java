package Controlador;

import Modelo.OpcionesModelo;

public class OpcionesControlador {

    private OpcionesModelo modelo;
    private Teclado teclado;
    private Game game;

    public OpcionesControlador(OpcionesModelo modelo, Teclado teclado, Game game) {
        this.modelo = modelo;
        this.teclado = teclado;
        this.game = game;
    }

    public void update() {

        if (teclado.upPressed) {
            modelo.arriba();
            teclado.upPressed = false;
        }

        if (teclado.downPressed) {
            modelo.abajo();
            teclado.downPressed = false;
        }

        if (teclado.back){

            System.out.println(modelo.getEstado());

            if (modelo.getEstado() == OpcionesModelo.EstadoMenu.OPCIONES) {
                System.out.println("atrasss");
                game.setOverlay(null); // salir del menú opciones
            } else {
                modelo.atras(); // volver dentro del menú
            }

            teclado.back = false;
        }

        if (teclado.rightPressed) {
            modelo.derecha();
            actualizarVolumen();
            teclado.rightPressed = false;
            System.out.println(modelo.getVolumenMusica());
        }

        if (teclado.leftPressed) {
            modelo.izquierda();
            actualizarVolumen();
            teclado.leftPressed = false;
        }

        if (teclado.select) {
            modelo.aceptar();
            teclado.select = false;
        }
    }

    private void actualizarVolumen() {
        game.musica.setVolumen(modelo.getVolumenMusica() / 10f);
        game.efectos.setVolumen(modelo.getVolumenFX() / 10f);
    }

}