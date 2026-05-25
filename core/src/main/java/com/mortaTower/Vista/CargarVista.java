package com.mortaTower.Vista;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mortaTower.Main;
import com.mortaTower.Modelo.CargarModelo;


public class CargarVista {

 private CargarModelo modelo;
 private Stage stage;
 private List<ImageButton> botonesPartidas = new ArrayList<>();
 private List<ImageButton> botonesBorrar = new ArrayList<>();

 private Group modalEliminar;
 private Label textoPartidaEliminar;
 private ImageButton btnCancelarEliminar;
 private ImageButton btnConfirmarEliminar;

 public CargarVista(CargarModelo modelo, FitViewport viewport, Main game) {
    this.modelo = modelo;
    this.stage = new Stage(viewport, game.batch);

    Texture fondo = new Texture("Imagenes/CargarPartida/Fondo1.png");
    Image imgFondo = new Image(fondo);
    imgFondo.setSize(stage.getWidth(), stage.getHeight());
    stage.addActor(imgFondo);

    Table tabla = new Table();
    tabla.setFillParent(true);
    tabla.top().padTop(190);

    Texture texNormal = new Texture("Imagenes/CargarPartida/boton.png");
    Texture texSelected = new Texture("Imagenes/CargarPartida/boton1.png");
    Texture texBorrar = new Texture("Imagenes/CargarPartida/calaberaBorrar.png");
    Texture texBorrarHover = new Texture("Imagenes/CargarPartida/calaberaBorrar1.png"); 

    for (int i = 0; i < modelo.getPartidas().size(); i++) {
        ImageButton boton = crearBoton (texNormal, texSelected);
        ImageButton botonBorrar = crearBoton(texBorrar, texBorrarHover);

        Label.LabelStyle estilo = new Label.LabelStyle();
        estilo.font = new com.badlogic.gdx.graphics.g2d.BitmapFont();
        textoPartidaEliminar = new Label("", estilo);
        
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

        Table fila = new Table();
        fila.add(stack).width(560).height(90).padRight(-50);
        fila.add(botonBorrar).width(56).height(56);

        tabla.add(fila).padBottom(-2).row();

        botonesPartidas.add(boton);
        botonesBorrar.add(botonBorrar);


    }
    stage.addActor(tabla);
    crearModalEliminar();
    }
    private void crearModalEliminar() {
        modalEliminar = new Group();
        modalEliminar.setSize(stage.getWidth(), stage.getHeight());
        modalEliminar.setVisible(false);

        Image cuadro = new Image(new Texture("Imagenes/CargarPartida/CuadroEliminar.png"));
        cuadro.setSize(620, 360);
        cuadro.setPosition((stage.getWidth() - 620) / 2f, (stage.getHeight() - 320) / 2f);
        modalEliminar.addActor(cuadro);
        Label.LabelStyle estilo = new Label.LabelStyle();
        estilo.font = new com.badlogic.gdx.graphics.g2d.BitmapFont();

        
        textoPartidaEliminar.setAlignment(Align.center);
        textoPartidaEliminar.setWidth(500);
        textoPartidaEliminar.setPosition((stage.getWidth() - 500) / 2f, (stage.getHeight() + 95) / 2f - 15); 
        modalEliminar.addActor(textoPartidaEliminar);
        btnCancelarEliminar = crearBoton( new Texture("Imagenes/CargarPartida/cancelarBoton.png"), new Texture("Imagenes/CargarPartida/cancelarBoton1.png"));

        btnConfirmarEliminar = crearBoton(new Texture("Imagenes/CargarPartida/eliminarBoton.png"), new Texture("Imagenes/CargarPartida/eliminarBoton1.png"));
        
        btnCancelarEliminar.setSize(190, 55);
        btnConfirmarEliminar.setSize(190, 55);

        btnCancelarEliminar.setPosition(stage.getWidth() / 2f - 220, stage.getHeight() / 2f - 85);
        btnConfirmarEliminar.setPosition(stage.getWidth() / 2f + 30, stage.getHeight() / 2f - 85);

        modalEliminar.addActor(btnCancelarEliminar);
        modalEliminar.addActor(btnConfirmarEliminar);

    stage.addActor(modalEliminar);
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

    public List<ImageButton> getBotonesBorrar() {
        return botonesBorrar;
    }
    public ImageButton getBtnCancelarEliminar() {
        return btnCancelarEliminar;
    }
    public ImageButton getBtnConfirmarEliminar() {
        return btnConfirmarEliminar;
    }
    public void ocultarModalEliminar() {
        modalEliminar.setVisible(false);
    }

    public void mostrarModalEliminar(String textoPartida) {
        textoPartidaEliminar.setText(textoPartida);
        modalEliminar.setVisible(true);
        
    }
    public void cerrar() {
        stage.dispose();
    }
    }
    
    