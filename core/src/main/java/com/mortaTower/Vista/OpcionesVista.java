package com.mortaTower.Vista;

import com.mortaTower.Main;

import com.badlogic.gdx.scenes.scene2d.ui.*;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class OpcionesVista {

    private Stage stage;

    private float w, h;
    private float tamañoBtn = 50f;

    private ImageButton btnMusicaMenos, btnMusicaMas;
    private ImageButton btnEfectosMenos, btnEfectosMas;
    private ImageButton btnAtras;

    private Table tabla;

    private TextureRegion[] musicaFrames;
    private TextureRegion[] efectosFrames;
    private Texture transparencia, fondo;
    private Texture musicaTex, efectosTex;
    private Image imgOscura, imgOpcionesPanel, imgMusica, imgEfectos;

    public OpcionesVista(FitViewport viewport, Main game) {

        stage = new Stage(viewport, game.batch);

        w = stage.getViewport().getWorldWidth();
        h = stage.getViewport().getWorldHeight();

        imgOscura = new Image(game.assets.get("Imagenes/black.png", Texture.class));
        imgOscura.setFillParent(true);
        imgOscura.setColor(0, 0, 0, 0.8f);
        
        imgOpcionesPanel = new Image(game.assets.get("Imagenes/Opciones/menuOpciones.png", Texture.class));
        imgOpcionesPanel.setSize(w - 600, h - 200);
        imgOpcionesPanel.setPosition((w - imgOpcionesPanel.getWidth()) / 2f, (h - imgOpcionesPanel.getHeight()) / 2f);
        
        btnAtras = crearBotonDoble(game.assets.get("Imagenes/Opciones/atras.png", Texture.class));
        btnAtras.setSize(w* 0.05f, w* 0.05f);
        btnAtras.setPosition(w - 1040, h - 160);

        musicaFrames = crearBarra(game.assets.get("Imagenes/Opciones/volMusica.png", Texture.class));
        imgMusica = new Image(new TextureRegionDrawable(musicaFrames[0]));

        efectosFrames = crearBarra(game.assets.get("Imagenes/Opciones/volEfectos.png", Texture.class));
        imgEfectos = new Image(new TextureRegionDrawable(efectosFrames[0]));

        btnMusicaMenos = crearBotonDoble(game.assets.get("Imagenes/Opciones/btnMenos.png", Texture.class));
        btnMusicaMas = crearBotonDoble(game.assets.get("Imagenes/Opciones/btnMas.png", Texture.class));
        btnEfectosMenos = crearBotonDoble(game.assets.get("Imagenes/Opciones/btnMenos.png", Texture.class));
        btnEfectosMas = crearBotonDoble(game.assets.get("Imagenes/Opciones/btnMas.png", Texture.class));

        tabla = new Table();
        tabla.setPosition(w /2, h /2);

        tabla.add(btnMusicaMenos).size(tamañoBtn, tamañoBtn).padRight(10);
        tabla.add(imgMusica).size(400, 60);
        tabla.add(btnMusicaMas).size(tamañoBtn, tamañoBtn).padLeft(10).row();

        tabla.add(btnEfectosMenos).size(tamañoBtn, tamañoBtn).padRight(10).padTop(10);
        tabla.add(imgEfectos).size(400, 60).padTop(10);
        tabla.add(btnEfectosMas).size(tamañoBtn, tamañoBtn).padLeft(10).padTop(10).row();

        stage.addActor(imgOscura);
        stage.addActor(imgOpcionesPanel);
        stage.addActor(btnAtras);
        stage.addActor(tabla);
    }

    // CREACION SPRITE PARA MUSICA Y FX
    private TextureRegion[] crearBarra(Texture textura) {

        int frames = 11;

        int ancho = textura.getWidth();
        int alto = textura.getHeight() / frames;

        TextureRegion[] regiones = new TextureRegion[frames];

        for (int i = 0; i < frames; i++) {

            regiones[i] = new TextureRegion(textura, 0, i * alto, ancho, alto);
        }

        return regiones;
    }

    // CREACION BOTONES + Y -
    private ImageButton crearBotonDoble(Texture textura) {

        int ancho = textura.getWidth() / 2;
        int alto = textura.getHeight();

        TextureRegion normal = new TextureRegion(textura, 0, 0, ancho, alto);
        TextureRegion seleccionado = new TextureRegion(textura, ancho, 0, ancho, alto);

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(normal);
        style.imageOver = new TextureRegionDrawable(seleccionado);

        return new ImageButton(style);
    }

    // CAMBIA EL ESTADO DEL SPRITE DE MUSICA
    public void actualizarBarraMusica(int nivel) {

        nivel = MathUtils.clamp(nivel, 0, musicaFrames.length - 1);
        int invertido = (musicaFrames.length - 1) - nivel;
        imgMusica.setDrawable(new TextureRegionDrawable(musicaFrames[invertido]));
    }

    // CAMBIA EL ESTADO DEL SPRITE DE FX
    public void actualizarBarraEfectos(int nivel) {

        nivel = MathUtils.clamp(nivel, 0, efectosFrames.length - 1);
        int invertido = (efectosFrames.length - 1) - nivel;
        imgEfectos.setDrawable(new TextureRegionDrawable(efectosFrames[invertido]));
    }

    public void cerrar() {
        
        stage.dispose();

        transparencia.dispose();
        fondo.dispose();
        musicaTex.dispose();
        efectosTex.dispose();
    }

    // GETTERS
    public Stage getStage() { return stage; }

    public ImageButton getBtnMusicaMenos() { return btnMusicaMenos; }
    public ImageButton getBtnMusicaMas() { return btnMusicaMas; }
    public ImageButton getBtnFxMenos() { return btnEfectosMenos; }
    public ImageButton getBtnFxMas() { return btnEfectosMas; }
    public ImageButton getBtnAtras() {return btnAtras;}
}