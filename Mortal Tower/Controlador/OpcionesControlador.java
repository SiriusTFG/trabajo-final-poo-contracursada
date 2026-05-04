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

        if (teclado.up) {
            modelo.arriba();
            teclado.up = false;
        }

        if (teclado.down) {
            modelo.abajo();
            teclado.down = false;
        }

            if (teclado.back){

    if (modelo.getEstado() == OpcionesModelo.EstadoMenu.OPCIONES) {
        game.setOverlay(null); // salir del menú opciones
    } else {
        modelo.atras(); // volver dentro del menú
    }

    teclado.back = false;
}

        if (teclado.right) {
            modelo.derecha();
            actualizarVolumen();
            teclado.right = false;
            System.out.println(modelo.getVolumenMusica());
        }

        if (teclado.left) {
            modelo.izquierda();
            actualizarVolumen();
            teclado.left = false;
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