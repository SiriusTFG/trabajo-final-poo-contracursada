package com.mortaTower.Screens;

import com.mortaTower.Controlador.MenuControlador;
import com.mortaTower.Controlador.MenuControlador.Action;
import com.badlogic.gdx.graphics.Texture;
import com.mortaTower.Main;
import com.mortaTower.Modelo.MenuModelo;
import com.mortaTower.Vista.MenuVista;


public class MenuScreen extends Screens {

    private boolean loaded;

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
    public void show() {

        loaded = false;

        game.assets.load("Imagenes/MenuInicio/Fondo.png", Texture.class);
        game.assets.load("Imagenes/MenuInicio/nueva.png", Texture.class);
        game.assets.load("Imagenes/MenuInicio/cargar.png", Texture.class);
        game.assets.load("Imagenes/MenuInicio/opciones.png", Texture.class);
        game.assets.load("Imagenes/MenuInicio/salir.png", Texture.class);
    }

    @Override
    public void update(float delta) {


        if (!loaded) {

            if (game.assets.update()) {

                loaded = true;

                vista.init(game.assets);
            }

            return;
        }

        if (mostrarOpciones) {

            opcionesOverlay.update(delta);

            if (opcionesOverlay.shouldClose()) {mostrarOpciones = false;}

            return;
        }
        
        Action action = controlador.update();

        switch (action) {

            case START_GAME -> game.setScreen(new TransicionScreen( game,this, new SeleccionScreen(game)));

            case OPTIONS -> { mostrarOpciones = true; opcionesOverlay.open();}

            case LOAD_GAME -> game.setScreen(new TransicionScreen(game, this, new CargarScreen(game, this)));
            
            case CREDITS -> System.out.println("Ir a créditos (futuro screen)");

            case EXIT -> System.exit(0);

            case NONE -> {}
        }
    }

    @Override
    public void draw(float delta) {

        if (!loaded) {
            return;
        }

       // menu principal
        vista.draw(spriteBatch);

        // overlay
        if(mostrarOpciones) {
            //vista.draw(spriteBatch);
            opcionesOverlay.draw(spriteBatch);
        }
    }
}