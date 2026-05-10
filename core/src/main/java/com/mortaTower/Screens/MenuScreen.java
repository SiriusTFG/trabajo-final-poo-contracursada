package com.mortaTower.Screens;

import com.mortaTower.Modelo.MenuModelo;
import com.mortaTower.Vista.MenuVista;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mortaTower.Main;
import com.mortaTower.Controlador.MenuControlador;
import com.mortaTower.Controlador.MenuControlador.Action;


public class MenuScreen extends Screens {

    private final MenuModelo modelo;
    private final MenuControlador controlador;
    private final MenuVista vista;

    private OpcionesOverlay opcionesOverlay;
    private boolean mostrarOpciones;

    public MenuScreen(Main game) {

        super(game);

        this.modelo = new MenuModelo();
        this.controlador = new MenuControlador(modelo, game.teclado, game.audio);
        this.vista = new MenuVista(modelo);

        opcionesOverlay = new OpcionesOverlay(game);
    }

    @Override
    public void update(float delta) {

        if (mostrarOpciones) {

            opcionesOverlay.update(delta);

            if (opcionesOverlay.shouldClose()) {mostrarOpciones = false;}

            return;
        }

        Action action = controlador.update();

        switch (action) {

            case START_GAME -> game.setScreen(new SeleccionScreen(game));

            case OPTIONS -> { mostrarOpciones = true; opcionesOverlay.open();}

            case CREDITS -> System.out.println("Ir a créditos (futuro screen)");

            case EXIT -> System.exit(0);

            case NONE -> {
                // no hacer nada
            }
        }
    }

    @Override
    public void draw(float delta) {
       // menu principal
        vista.draw(spriteBatch);

        // overlay
        if(mostrarOpciones) {

            opcionesOverlay.draw(spriteBatch);
        }
    }
}