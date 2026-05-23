package com.mortaTower.Vista;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mortaTower.Main;
import com.mortaTower.Modelo.CargarModelo;


public class CargarVista {

 private CargarModelo modelo;
 private Stage stage;
 private List<ImageButton> botonesPartidas = new ArrayList<>();

 public CargarVista(CargarModelo modelo, FitViewport viewport, Main game) {
    this.modelo = modelo;
    this.stage = new Stage(viewport, game.batch);

    Texture fondo = new Texture("Imagenes/MenuInicio/Fondo1.png");
    Image imgFondo = new Image(fondo);
    imgFondo.setSize(stage.getWidth(), stage.getHeight());
    stage.addActor(imgFondo);

    Table tabla = new Table();
    tabla.setFillParent(true);
    tabla.top().padTop(190);

    Texture texNormal = new Texture("Imagenes/MenuInicio/boton.png");
    Texture texSelected = new Texture("Imagenes/MenuInicio/boton1.png");

    for (int i = 0; i < modelo.getPartidas().size(); i++) {
        ImageButton boton = crearBoton (texNormal, texSelected);

        Label.LabelStyle estilo = new Label.LabelStyle();
        estilo.font = new com.badlogic.gdx.graphics.g2d.BitmapFont();
        
        String resumen = modelo.getPartidas().get(i);

        String[] partes = resumen.split(" - ", 2);
        String textoVisible = (partes.length > 1) ? partes[1] : resumen;
        Label texto = new Label(textoVisible, estilo);

        Stack stack = new Stack();

        texto.setAlignment(1); // centro

        Table textoCentrado = new Table();
        textoCentrado.setFillParent(true);
        textoCentrado.add(texto).center();

        stack.add(boton);
        stack.add(textoCentrado);

        tabla.add(stack).width(560).height(90).padBottom(5).row();
        botonesPartidas.add(boton);


    }
    stage.addActor(tabla);
    }

    private ImageButton crearBoton(Texture normal, Texture seleccionado) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();

        style.imageUp = new TextureRegionDrawable(new TextureRegion(normal));
        style.imageOver = new TextureRegionDrawable(new TextureRegion(seleccionado));
        return new ImageButton(style);
    }

    public Stage getStage() {
        return stage;
    }

    public List<ImageButton> getBotonesPartidas() {
        return botonesPartidas;
    }
    public void cerrar() {
        stage.dispose();
    }
    }
    
    