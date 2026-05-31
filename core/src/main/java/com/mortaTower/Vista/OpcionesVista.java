package com.mortaTower.Vista;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mortaTower.Main;

public class OpcionesVista {

    private Stage stage;

    private Texture transparencia, fondo, atras;
    private Texture musicaTex, efectosTex;

    private TextureRegion[] musicaFrames;
    private TextureRegion[] efectosFrames;

    // actores
    private Image imgMusica, imgEfectos;

    // botones
    private ImageButton btnMusicaMenos, btnMusicaMas;
    private ImageButton btnEfectosMenos, btnEfectosMas;
    private ImageButton btnAtras;

    public OpcionesVista(FitViewport viewport, Main game) {

        stage = new Stage(viewport, game.batch);

        // TEXTURAS
        transparencia = game.assets.get("Imagenes/black.png", Texture.class);
        fondo = game.assets.get("Imagenes/Opciones/menuOpciones.png", Texture.class);
        atras = game.assets.get("Imagenes/Opciones/atras.png", Texture.class);

        musicaTex = game.assets.get("Imagenes/Opciones/volMusica.png", Texture.class);
        efectosTex = game.assets.get("Imagenes/Opciones/volEfectos.png", Texture.class);

        Texture texMenos = game.assets.get("Imagenes/Opciones/btnMenos.png", Texture.class);
        Texture texMas = game.assets.get("Imagenes/Opciones/btnMas.png", Texture.class);

        // =========================
        // SPLIT CORRECTO (10 niveles)
        // =========================
        int frames = 10;
        musicaFrames = new TextureRegion[frames];
        efectosFrames = new TextureRegion[frames];

        int anchoM = musicaTex.getWidth();
        int altoM = musicaTex.getHeight() / frames;

        int anchoF = efectosTex.getWidth();
        int altoF = efectosTex.getHeight() / frames;

        for (int i = 0; i < frames; i++) {
            musicaFrames[i] = new TextureRegion(musicaTex, 0 , i * altoM, anchoM, altoM);
            efectosFrames[i] = new TextureRegion(efectosTex, 0, i * altoF, anchoF, altoF);
        }

        // fondo
        Image imgOscura = new Image(transparencia);
        imgOscura.setSize(stage.getWidth(), stage.getHeight());
        imgOscura.setColor(0, 0, 0, 0.8f);
        stage.addActor(imgOscura);

        Image imgOpcionesPanel = new Image(fondo);
        imgOpcionesPanel.setSize(stage.getWidth() - 600, stage.getHeight() - 200);
        imgOpcionesPanel.setPosition(350, 100);
        stage.addActor(imgOpcionesPanel);

        // =========================
        // ACTORES
        // =========================
        imgMusica = new Image(new TextureRegionDrawable(musicaFrames[0]));
        imgEfectos = new Image(new TextureRegionDrawable(efectosFrames[0]));

        btnMusicaMenos = crearBotonDoble(texMenos);
        btnMusicaMas = crearBotonDoble(texMas);
        btnEfectosMenos = crearBotonDoble(texMenos);
        btnEfectosMas = crearBotonDoble(texMas);

        btnAtras = crearBotonDoble(atras);
        btnAtras.setSize(200, 200);
        btnAtras.setPosition(220, 480);
        stage.addActor(btnAtras);

        // =========================
        // UI
        // =========================
        Table tabla = new Table();
       /*  tabla.setFillParent(true);
        tabla.center(); */
        tabla.pack();
        tabla.setPosition(690, 380);
        tabla.debug();
        
        float tamañoBtn = 50f;

        tabla.add(btnMusicaMenos).size(tamañoBtn, tamañoBtn).padRight(15);
        tabla.add(imgMusica).size(380, 80);
        tabla.add(btnMusicaMas).size(tamañoBtn, tamañoBtn).padLeft(15).row();

        tabla.add(btnEfectosMenos).size(tamañoBtn, tamañoBtn).padRight(15).padTop(10);
        tabla.add(imgEfectos).size(380, 80).padTop(10);
        tabla.add(btnEfectosMas).size(tamañoBtn, tamañoBtn).padLeft(15).padTop(10).row();

        stage.addActor(tabla);
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
        int invertido = (musicaFrames.length - 1) - nivel;
        imgMusica.setDrawable(new TextureRegionDrawable(musicaFrames[invertido]));
    }

    public void actualizarBarraEfectos(int nivel) {
        nivel = MathUtils.clamp(nivel, 0, efectosFrames.length - 1);
        int invertido = (efectosFrames.length - 1) - nivel;
        imgEfectos.setDrawable(new TextureRegionDrawable(efectosFrames[invertido]));
    }

    // =========================
    // RENDER
    // =========================

    public void render(float delta) {
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