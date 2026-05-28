package com.mortaTower.Vista;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mortaTower.Main;
import com.mortaTower.Modelo.OpcionesModelo;

public class OpcionesVista {

    private Stage stage;

    private Texture transparencia, fondo, controles;
    private Texture musicaTex, efectosTex;

    private TextureRegion[] musicaFrames;
    private TextureRegion[] efectosFrames;

    // actores
    private Image imgMusica, imgEfectos, imgPantallaControles;

    // botones
    private ImageButton btnMusicaMenos, btnMusicaMas;
    private ImageButton btnEfectosMenos, btnEfectosMas;
    private ImageButton btnControles;

    public OpcionesVista(FitViewport viewport, Main game) {

        stage = new Stage(viewport, game.batch);

        // TEXTURAS
        transparencia = new Texture("Imagenes/black.png");
        fondo = new Texture("Imagenes/Opciones/menuOpciones.png");
        controles = new Texture("Imagenes/Opciones/menuControles.png");

        musicaTex = new Texture("Imagenes/Opciones/volMusica.png");
        efectosTex = new Texture("Imagenes/Opciones/volEfectos.png");

        Texture texMenos = new Texture("Imagenes/Opciones/btn_menos.png");
        Texture texMas = new Texture("Imagenes/Opciones/btn_mas.png");

        // =========================
        // SPLIT CORRECTO (10 niveles)
        // =========================
        musicaFrames = TextureRegion.split(
                musicaTex,
                musicaTex.getWidth() / 10,
                musicaTex.getHeight()
        )[0];

        efectosFrames = TextureRegion.split(
                efectosTex,
                efectosTex.getWidth() / 10,
                efectosTex.getHeight()
        )[0];

        // =========================
        // ACTORES
        // =========================
        imgMusica = new Image(new TextureRegionDrawable(musicaFrames[0]));
        imgEfectos = new Image(new TextureRegionDrawable(efectosFrames[0]));

        btnMusicaMenos = crearBotonSimple(texMenos);
        btnMusicaMas = crearBotonSimple(texMas);
        btnEfectosMenos = crearBotonSimple(texMenos);
        btnEfectosMas = crearBotonSimple(texMas);

        btnControles = crearBotonDoble(new Texture("Imagenes/Opciones/controles.png"));

        imgPantallaControles = new Image(controles);
        imgPantallaControles.setVisible(false);

        // =========================
        // UI
        // =========================
        Table tabla = new Table();
        tabla.setFillParent(true);
        tabla.center();
        
        float tamañoBtn = 50f;

        tabla.add(btnMusicaMenos).size(tamañoBtn, tamañoBtn).padRight(15);
        tabla.add(imgMusica).padBottom(20);
        tabla.add(btnMusicaMas).size(tamañoBtn, tamañoBtn).padLeft(15).row();

        tabla.add(btnControles).colspan(3).padTop(20).row();

        stage.addActor(tabla);
        stage.addActor(imgPantallaControles);
    }

    // =========================
    // BOTONES
    // =========================

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

    private ImageButton crearBotonSimple(Texture textura) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(textura));
        return new ImageButton(style);
    }

    // =========================
    // ACTUALIZACIÓN DE BARRAS
    // =========================

    public void actualizarBarraMusica(int nivel) {
        nivel = MathUtils.clamp(nivel, 0, musicaFrames.length - 1);
        imgMusica.setDrawable(new TextureRegionDrawable(musicaFrames[nivel]));
    }

    public void actualizarBarraEfectos(int nivel) {
        nivel = MathUtils.clamp(nivel, 0, efectosFrames.length - 1);
        imgEfectos.setDrawable(new TextureRegionDrawable(efectosFrames[nivel]));
    }

    // =========================
    // RENDER
    // =========================

    public void render(float delta, boolean mostrarControles) {

        imgPantallaControles.setVisible(mostrarControles);

        stage.act(delta);
        stage.draw();
    }

    // =========================
    // CIERRE
    // =========================

    public void cerrar() {
        stage.dispose();

        transparencia.dispose();
        fondo.dispose();
        controles.dispose();
        musicaTex.dispose();
        efectosTex.dispose();
    }

    // GETTERS
    public Stage getStage() { return stage; }

    public ImageButton getBtnMusicaMenos() { return btnMusicaMenos; }
    public ImageButton getBtnMusicaMas() { return btnMusicaMas; }
    public ImageButton getBtnFxMenos() { return btnEfectosMenos; }
    public ImageButton getBtnFxMas() { return btnEfectosMas; }
    public ImageButton getBtnControles() { return btnControles; }
}