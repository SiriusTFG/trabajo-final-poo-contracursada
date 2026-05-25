package com.mortaTower.Vista;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mortaTower.Main;
import com.mortaTower.Modelo.RecompensasModelo;


public class RecompensasVista {

     private Stage stage;


    // BOTONES
    private ImageButton btnHab1, btnHab2, btnHab3;
    private Image cat1 , cat2, cat3;

    private Texture fondo, categorias, cuadro;

    public RecompensasVista(Main game) {

        stage = new Stage();

        fondo = game.assets.get("Imagenes/black.png", Texture.class);

        Image fondoImg = new Image(new TextureRegionDrawable(fondo));
        fondoImg.setFillParent(true);
        fondoImg.setColor(0, 0, 0, 0.6f); // opcional translúcido
        
        cuadro = game.assets.get("Imagenes/SeccionRecompensa/cuadroHabilidad.png", Texture.class);
        categorias = game.assets.get("Imagenes/SeccionRecompensa/tipoHabilidad.png", Texture.class);
        
        

        //creacion de botones
        btnHab1 = crearBoton(cuadro, 0);
        btnHab2 = crearBoton(cuadro, 0);
        btnHab3 = crearBoton(cuadro, 0);

        cat1 = crearCategoria(categorias, 0);
        cat2 = crearCategoria(categorias, 1);
        cat3 = crearCategoria(categorias, 2);

        // Layout
        Table tabla = new Table();
        tabla.setFillParent(true); //ocupa la pantalla
        //tabla.setDebug(true);

        // Posicion de tabla
        tabla.center();
        

        tabla.add(cat1).size(210, 180).pad(30);
        tabla.add(btnHab1).size(800, 200).row();

        tabla.add(cat2).size(210, 180).pad(30);
        tabla.add(btnHab2).size(800, 200).row();

        tabla.add(cat3).size(210, 180).pad(30);
        tabla.add(btnHab3).size(800, 200).row();

        stage.addActor(fondoImg);
        stage.addActor(tabla);
    }

    private ImageButton crearBoton(Texture textura, int fila) {

        int ancho = textura.getWidth() / 2;
        int alto = textura.getHeight();

        int y = fila * alto;

        TextureRegion normal = new TextureRegion(textura, 0, y, ancho, alto);
        TextureRegion seleccionado =new TextureRegion(textura, ancho, y, ancho, alto);

        ImageButton.ImageButtonStyle style =new ImageButton.ImageButtonStyle();

        style.imageUp = new TextureRegionDrawable(normal);
        style.imageOver = new TextureRegionDrawable(seleccionado);

        return new ImageButton(style);
    }

    private Image crearCategoria(Texture textura, int columna){

        int ancho = textura.getWidth()/4;
        int alto = textura.getHeight();

        int x = columna * ancho;

        TextureRegion region = new TextureRegion(textura, x, 0, ancho, alto);

        return new Image(new TextureRegionDrawable(region));
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    // GETTERS
    public Stage getStage() { return stage; }
    public ImageButton getBtnHab1() { return btnHab1; }
    public ImageButton getBtnHab2() { return btnHab2; }
    public ImageButton getBtnHab3() { return btnHab3; }
}
