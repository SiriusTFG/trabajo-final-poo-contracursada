package com.mortaTower.Vista;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mortaTower.Main;

public class MenuVista {

    private Stage stage;
    private Texture fondo;
    
    // BOTONES
    private ImageButton btnJugar, btnCargar, btnOpciones, btnSalir;

    public MenuVista(FitViewport viewport, Main game) {
        stage = new Stage(viewport, game.batch);

        //carga de texturas
        fondo = game.assets.get("Imagenes/MenuInicio/Fondo.png", Texture.class);

        //creacion de botones
        btnJugar = crearBoton(game.assets.get("Imagenes/MenuInicio/nueva.png", Texture.class));
        btnCargar = crearBoton(game.assets.get("Imagenes/MenuInicio/cargar.png", Texture.class));
        btnOpciones = crearBoton(game.assets.get("Imagenes/MenuInicio/opciones.png", Texture.class));
        btnSalir = crearBoton(game.assets.get("Imagenes/MenuInicio/salir.png", Texture.class));

        // Layout
        Table tabla = new Table();
        tabla.setFillParent(true); //ocupa la pantalla
        //tabla.setDebug(true);

        // Posicion de tabla
        tabla.bottom().padBottom(70);
        tabla.left().padLeft(120);

        tabla.add(btnJugar).width(380).height(80).padBottom(0).row();
        tabla.add(btnCargar).width(380).height(80).padBottom(0).row();
        tabla.add(btnOpciones).width(380).height(80).padBottom(0).row();
        tabla.add(btnSalir).width(380).height(80).padBottom(0).row();

        stage.addActor(tabla);
    }

    private ImageButton crearBoton(Texture textura) {
        int ancho = textura.getWidth() / 2;
        int alto = textura.getHeight();

        TextureRegion normal = new TextureRegion(textura, 0, 0, ancho, alto);
        TextureRegion seleccionado = new TextureRegion(textura, ancho, 0, ancho, alto);

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(normal);
        style.imageOver = new TextureRegionDrawable(seleccionado);
        return new ImageButton(style);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.getBatch().begin();
        stage.getBatch().draw(fondo, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().end();
        stage.draw();
    }

    public void cerrar() {
        stage.dispose();
    }

    // GETTERS
    public Stage getStage() { return stage; }
    public ImageButton getBtnJugar() { return btnJugar; }
    public ImageButton getBtnCargar() { return btnCargar; }
    public ImageButton getBtnOpciones() { return btnOpciones; }
    public ImageButton getBtnSalir() { return btnSalir; }

}
