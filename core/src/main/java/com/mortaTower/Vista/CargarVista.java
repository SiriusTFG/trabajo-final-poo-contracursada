package com.mortaTower.Vista;

import com.mortaTower.Main;
import com.mortaTower.Screens.GameAssets;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class CargarVista {

    private Main game;
    private Stage stage;

    private float w, h;
    private String resumen, textoVisible;

    private List<ImageButton> botonesPartidas = new ArrayList<>();
    private List<ImageButton> botonesBorrar = new ArrayList<>();
    private ImageButton btnCancelarEliminar, btnConfirmarEliminar, btnAtras, boton, botonBorrar;

    private Group modalEliminar;
    private Table tabla, textoCentrado, fila;
    private Stack stack;

    private Label.LabelStyle estilo;
    private Label textoPartidaEliminar, texto;

    private Texture texNormal, texSelected, texBorrar, texBorrarHover;
    private Image imgFondo, cuadro;
    
    public CargarVista(List<String> partidas, FitViewport viewport, Main game) {
        
        this.game = game;
        this.stage = new Stage(viewport, game.batch);

        w = stage.getViewport().getWorldWidth();
        h = stage.getViewport().getWorldHeight();

        estilo = new Label.LabelStyle();
        estilo.font = GameAssets.fuenteMedieval;

        imgFondo = new Image(game.assets.get("Imagenes/CargarPartida/Fondo1.png", Texture.class));
        imgFondo.setFillParent(true);

        btnAtras = crearBotonSprite(game.assets.get("Imagenes/Opciones/atras.png", Texture.class));
        btnAtras.setSize(w * 0.05f, w * 0.05f);
        btnAtras.setPosition(w - 1200, h - 116);
        
        texNormal = game.assets.get("Imagenes/CargarPartida/boton.png", Texture.class);
        texSelected = game.assets.get("Imagenes/CargarPartida/boton1.png", Texture.class);
        texBorrar = game.assets.get("Imagenes/CargarPartida/calaberaBorrar.png", Texture.class);
        texBorrarHover = game.assets.get("Imagenes/CargarPartida/calaberaBorrar1.png", Texture.class); 

        tabla = new Table();
        tabla.setFillParent(true);
        tabla.top().padTop(190);

        for (int i = 0; i < partidas.size(); i++) {
            
            boton = crearBoton (texNormal, texSelected);
            botonBorrar = crearBoton(texBorrar, texBorrarHover);
            
            botonesPartidas.add(boton);
            botonesBorrar.add(botonBorrar);

            resumen = partidas.get(i);

            String[] partes = resumen.split(" - ", 2);
            textoVisible = (partes.length > 1) ? partes[1] : resumen;
            texto = new Label(textoVisible, estilo);
            texto.setTouchable(Touchable.disabled); // Evita que el texto reciba eventos táctiles, permitiendo que el botón los maneje
            
            texto.setAlignment(1); // centro

            textoCentrado = new Table();
            textoCentrado.setFillParent(true);
            textoCentrado.add(texto).center();
        
            stack = new Stack();
            stack.add(boton);
            stack.add(textoCentrado);

            fila = new Table();
            fila.add(stack).width(560).height(90).padRight(-50);
            fila.add(botonBorrar).width(56).height(56);

            tabla.add(fila).padBottom(-2).row();
        }
    
        stage.addActor(imgFondo);
        stage.addActor(btnAtras);
        stage.addActor(tabla);

        crearModalEliminar();
    }

    // CREACION VISTA ELIMINAR
    private void crearModalEliminar() {

        modalEliminar = new Group();
        modalEliminar.setSize(stage.getWidth(), stage.getHeight());
        modalEliminar.setVisible(false);

        cuadro = new Image(game.assets.get("Imagenes/CargarPartida/CuadroEliminar.png", Texture.class));
        cuadro.setSize(w * 0.5f, w* 0.3f);
        cuadro.setPosition((w - cuadro.getWidth()) / 2f, (h - cuadro.getHeight()) / 2f);

        textoPartidaEliminar = new Label("", estilo);
        textoPartidaEliminar.setAlignment(Align.center);
        textoPartidaEliminar.setPosition(w / 2f, (h + 20) / 2f); 
        
        btnCancelarEliminar = crearBoton( game.assets.get("Imagenes/CargarPartida/cancelarBoton.png", Texture.class), game.assets.get("Imagenes/CargarPartida/cancelarBoton1.png", Texture.class));
        btnCancelarEliminar.setSize(w * 0.15f, w* 0.1f);
        btnCancelarEliminar.setPosition((w / 2f) - 220, (h / 2f) - 150);

        btnConfirmarEliminar = crearBoton(game.assets.get("Imagenes/CargarPartida/eliminarBoton.png", Texture.class), game.assets.get("Imagenes/CargarPartida/eliminarBoton1.png", Texture.class));
        btnConfirmarEliminar.setSize(w * 0.15f, w* 0.1f);
        btnConfirmarEliminar.setPosition((w / 2f) + 30, (h / 2f) - 150);

        modalEliminar.addActor(cuadro);
        modalEliminar.addActor(textoPartidaEliminar);
        modalEliminar.addActor(btnCancelarEliminar);
        modalEliminar.addActor(btnConfirmarEliminar);

        stage.addActor(modalEliminar);
    }

    // CREACION BOTONES 
    private ImageButton crearBoton(Texture normal, Texture seleccionado) {

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();

        style.imageUp = new TextureRegionDrawable(new TextureRegion(normal));
        style.imageOver = new TextureRegionDrawable(new TextureRegion(seleccionado));
        
        return new ImageButton(style);
    }

    // CREACION BOTON ATRAS
    private ImageButton crearBotonSprite(Texture textura) {

        int ancho = textura.getWidth() / 2;
        int alto = textura.getHeight();

        TextureRegion normal = new TextureRegion(textura, 0, 0, ancho, alto);
        TextureRegion hover = new TextureRegion(textura, ancho, 0, ancho, alto);

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(normal);
        style.imageOver = new TextureRegionDrawable(hover);

        return new ImageButton(style);
    }

    public void cerrar() {stage.dispose();}

    // GETTERS
    public Stage getStage() {return stage;}

    public List<ImageButton> getBotonesPartidas() {return botonesPartidas;}
    public List<ImageButton> getBotonesBorrar() {return botonesBorrar;}
    public ImageButton getBtnCancelarEliminar() {return btnCancelarEliminar;}
    public ImageButton getBtnConfirmarEliminar() {return btnConfirmarEliminar;}
    public ImageButton getBtnAtras() {return btnAtras;}

    public void ocultarModalEliminar() {modalEliminar.setVisible(false);}

    public void mostrarModalEliminar(String textoPartida) {
        textoPartidaEliminar.setText(textoPartida);
        modalEliminar.setVisible(true);
    }
}
    
    