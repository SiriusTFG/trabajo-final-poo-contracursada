package com.mortaTower.Vista;

import com.mortaTower.Main;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuVista {

    private Stage stage;

    private ImageButton btnJugar, btnCargar, btnOpciones, btnSalir;

    private Table tabla;

    private Image imgFondo;

    public MenuVista(FitViewport viewport, Main game) {

        this.stage = new Stage(viewport, game.batch);

        // Fondo
        imgFondo = new Image(game.assets.get("Imagenes/MenuInicio/Fondo.png", Texture.class));
        imgFondo.setFillParent(true);
        
        // Botones
        btnJugar = crearBoton(game.assets.get("Imagenes/MenuInicio/nuevaPartida.png", Texture.class));
        btnCargar = crearBoton(game.assets.get("Imagenes/MenuInicio/cargarPartida.png", Texture.class));
        btnOpciones = crearBoton(game.assets.get("Imagenes/MenuInicio/opciones.png", Texture.class));
        btnSalir = crearBoton(game.assets.get("Imagenes/MenuInicio/salirDelJuego.png", Texture.class));

        // Tabla para Botones
        tabla = new Table();
        tabla.setFillParent(true);
        tabla.bottom().padBottom(70);
        tabla.left().padLeft(160);

        tabla.add(btnJugar).width(280).height(80).row();
        tabla.add(btnCargar).width(280).height(80).row();
        tabla.add(btnOpciones).width(280).height(80).row();
        tabla.add(btnSalir).width(280).height(80).row();

        stage.addActor(imgFondo);
        stage.addActor(tabla);
    }
    
    // CREACION BOTONES
    private ImageButton crearBoton(Texture textura) {

        int ancho = textura.getWidth() / 2;
        int alto = textura.getHeight();

        TextureRegion normal = new TextureRegion(textura, 0, 0, ancho, alto);
        TextureRegion over = new TextureRegion(textura, ancho, 0, ancho, alto);

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();

        style.imageUp = new TextureRegionDrawable(normal);
        style.imageOver = new TextureRegionDrawable(over);

        return new ImageButton(style);
    }

    public void cerrar() {
        stage.dispose();
    }

    // GETTERS
    public Stage getStage() { return stage; }
    public Table getCapaPrincipal() { return tabla; }

    public ImageButton getBtnJugar() { return btnJugar; }
    public ImageButton getBtnCargar() { return btnCargar; }
    public ImageButton getBtnOpciones() { return btnOpciones; }
    public ImageButton getBtnSalir() { return btnSalir; }
}