package com.mortaTower.Vista;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mortaTower.Main;

public class MenuVista {

    private Stage stage;
    private Table capaPrincipal;

    // botones menu
    private ImageButton btnJugar, btnCargar, btnOpciones, btnSalir;

    public MenuVista(FitViewport viewport, Main game) {

        stage = new Stage(viewport, game.batch);

        // fondo
        Texture fondo = game.assets.get("Imagenes/MenuInicio/Fondo.png", Texture.class);
        Image imgFondoGeneral = new Image(fondo);
        imgFondoGeneral.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(imgFondoGeneral);

        // =========================
        // MENU PRINCIPAL
        // =========================
        capaPrincipal = new Table();
        capaPrincipal.setFillParent(true);
        capaPrincipal.bottom().padBottom(70);
        capaPrincipal.left().padLeft(160);

        btnJugar = crearBoton(game.assets.get("Imagenes/MenuInicio/nueva.png", Texture.class));
        btnCargar = crearBoton(game.assets.get("Imagenes/MenuInicio/cargar.png", Texture.class));
        btnOpciones = crearBoton(game.assets.get("Imagenes/MenuInicio/opciones.png", Texture.class));
        btnSalir = crearBoton(game.assets.get("Imagenes/MenuInicio/salir.png", Texture.class));

        capaPrincipal.add(btnJugar).width(280).height(80).row();
        capaPrincipal.add(btnCargar).width(280).height(80).row();
        capaPrincipal.add(btnOpciones).width(280).height(80).row();
        capaPrincipal.add(btnSalir).width(280).height(80).row();

        stage.addActor(capaPrincipal);

    }
    
    // =========================
    // BOTONES
    // =========================

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
    public Table getCapaPrincipal() { return capaPrincipal; }

    public ImageButton getBtnJugar() { return btnJugar; }
    public ImageButton getBtnCargar() { return btnCargar; }
    public ImageButton getBtnOpciones() { return btnOpciones; }
    public ImageButton getBtnSalir() { return btnSalir; }
}