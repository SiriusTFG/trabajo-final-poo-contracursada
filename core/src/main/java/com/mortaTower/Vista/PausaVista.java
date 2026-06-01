package com.mortaTower.Vista;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mortaTower.Main;

public class PausaVista {

    private Stage stage;
    private Texture fondo;

    // BOTONES
    private ImageButton btnRenudar, btnReintentar, btnOpciones, btnSalir;
    
    public PausaVista(Main game){

        stage = new Stage();

        fondo = game.assets.get("Imagenes/black.png", Texture.class);

        Image fondoImg = new Image(new TextureRegionDrawable(fondo));
        fondoImg.setFillParent(true);
        fondoImg.setColor(0, 0, 0, 0.6f); // opcional translúcido

        btnRenudar = crearBoton(game.assets.get("Imagenes/MenuInicio/renudar.png", Texture.class));
        btnReintentar = crearBoton(game.assets.get("Imagenes/MenuInicio/reintentar.png", Texture.class));
        btnOpciones = crearBoton(game.assets.get("Imagenes/MenuInicio/opciones.png", Texture.class));
        btnSalir = crearBoton(game.assets.get("Imagenes/MenuInicio/menuPrincipal.png", Texture.class));

        // Layout
        Table tabla = new Table();
        tabla.setFillParent(true); //ocupa la pantalla

        // Posicion de tabla
        tabla.center();

        tabla.add(btnRenudar).width(380).height(80).padBottom(0).row();
        tabla.add(btnReintentar).width(380).height(80).padBottom(0).row();
        tabla.add(btnOpciones).width(380).height(80).padBottom(0).row();
        tabla.add(btnSalir).width(380).height(80).padBottom(0).row();

        stage.addActor(fondoImg);
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
        stage.draw();
    }

    // GETTERS
    public Stage getStage() { return stage; }
    public ImageButton getBtnRenudar() { return btnRenudar; }
    public ImageButton getBtnReintentar() { return btnReintentar; }
    public ImageButton getBtnOpciones() { return btnOpciones; }
    public ImageButton getBtnSalir() { return btnSalir; }
    
}

    

