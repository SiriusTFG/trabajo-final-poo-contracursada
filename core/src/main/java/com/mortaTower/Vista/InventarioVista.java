package com.mortaTower.Vista;


import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import com.mortaTower.Main;
import com.mortaTower.Modelo.InventarioModelo;


//import static com.mortaTower.Screens.Screens.WORLD_HEIGHT;
//import static com.mortaTower.Screens.Screens.WORLD_WIDTH;

public class InventarioVista {

    private Stage stage;


    // BOTONES
    private ImageButton btnJugar, btnCargar, btnOpciones, btnSalir;

    private Texture inventario;

    public InventarioVista(InventarioModelo modelo, Main game){

        //this.modelo = modelo;
        stage = new Stage();
        
        inventario = game.assets.get("Imagenes/Combate/inventario.png", Texture.class);
        Texture categorias = game.assets.get("Imagenes/Combate/categorias.png", Texture.class);

        //creacion de botones
        btnJugar = crearBoton(categorias, 0);
        btnCargar = crearBoton(categorias, 1);
        btnOpciones = crearBoton(categorias, 2);
        btnSalir = crearBoton(categorias, 3);

        // Layout
        Table tabla = new Table();
        tabla.setFillParent(true); //ocupa la pantalla
        //tabla.setDebug(true);

        // Posicion de tabla
        tabla.bottom().padBottom(380);
        tabla.left().padLeft(540);

        tabla.add(btnJugar).width(380).height(80).padBottom(0).row();
        tabla.add(btnCargar).width(380).height(80).padBottom(0).row();
        tabla.add(btnOpciones).width(380).height(80).padBottom(0).row();
        tabla.add(btnSalir).width(380).height(80).padBottom(0).row();

        stage.addActor(tabla);
    }

    private ImageButton crearBoton(Texture textura, int fila) {

        int ancho = textura.getWidth() / 2;
        int alto = textura.getHeight() / 4;

        int y = fila * alto;

        TextureRegion normal = new TextureRegion(textura, 0, y, ancho, alto);

        TextureRegion seleccionado =new TextureRegion(textura, ancho, y, ancho, alto);

        ImageButton.ImageButtonStyle style =new ImageButton.ImageButtonStyle();

        style.imageUp = new TextureRegionDrawable(normal);
        style.imageOver = new TextureRegionDrawable(seleccionado);

        return new ImageButton(style);
    }
    public void render(float delta) {
        stage.act(delta);
        stage.getBatch().begin();
        stage.getBatch().draw(inventario, 640, 200, 680, 560);
        stage.getBatch().end();
        stage.draw();
    }

    // GETTERS
    public Stage getStage() { return stage; }
    public ImageButton getBtnJugar() { return btnJugar; }
    public ImageButton getBtnCargar() { return btnCargar; }
    public ImageButton getBtnOpciones() { return btnOpciones; }
    public ImageButton getBtnSalir() { return btnSalir; }
}