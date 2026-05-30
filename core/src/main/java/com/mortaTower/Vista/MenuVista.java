package com.mortaTower.Vista;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mortaTower.Main;

public class MenuVista {

    private Stage stage;

    // capas
    private Table capaPrincipal;
    private Group capaOpciones;
    private Image capaControles;

    // botones menu
    private ImageButton btnJugar, btnCargar, btnOpciones, btnSalir;

    // botones opciones
    private ImageButton btnMusicaMenos, btnMusicaMas, btnFxMenos, btnFxMas, btnControles, btnAtras;

    private Image imgMusica, imgEfectos;

    // ===== FIX: arrays correctos =====
    private TextureRegion[] musicaFrames;
    private TextureRegion[] fxFrames;

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

        // =========================
        // OPCIONES
        // =========================
        capaOpciones = new Group();
        capaOpciones.setSize(stage.getWidth(), stage.getHeight());
        capaOpciones.setVisible(false);

        Image imgOscura = new Image(new Texture("Imagenes/black.png"));
        imgOscura.setSize(stage.getWidth(), stage.getHeight());
        imgOscura.setColor(0, 0, 0, 0.8f);
        capaOpciones.addActor(imgOscura);

        Image imgOpcionesPanel = new Image(new Texture("Imagenes/Opciones/menuOpciones.png"));
        imgOpcionesPanel.setSize(stage.getWidth() - 600, stage.getHeight() - 200);
        imgOpcionesPanel.setPosition(350, 100);
        capaOpciones.addActor(imgOpcionesPanel);

        btnAtras = crearBoton(new Texture("Imagenes/Opciones/atras.png"));
        btnAtras.setSize(200, 200);
        btnAtras.setPosition(220, 480);
        capaOpciones.addActor(btnAtras);

        // =========================
        // SPRITES (FIX REAL)
        // =========================
        Texture texVolMusica = new Texture("Imagenes/Opciones/volMusica.png");
        Texture texVolFx = new Texture("Imagenes/Opciones/volEfectos.png");

        int frames = 10;

        musicaFrames = new TextureRegion[frames];
        fxFrames = new TextureRegion[frames];

        int anchoM = texVolMusica.getWidth();
        int altoM = texVolMusica.getHeight() / frames;

        int anchoF = texVolFx.getWidth();
        int altoF = texVolFx.getHeight() / frames;

        for (int i = 0; i < frames; i++) {
            musicaFrames[i] = new TextureRegion(texVolMusica, 0, i * altoM, anchoM, altoM);
            fxFrames[i] = new TextureRegion(texVolFx, 0, i * altoF, anchoF, altoF);
        }

        imgMusica = new Image(musicaFrames[0]);
        imgEfectos = new Image(fxFrames[0]);

        // =========================
        // BOTONES
        // =========================
        Texture texMenos = new Texture("Imagenes/Opciones/btnMenos.png");
        Texture texMas = new Texture("Imagenes/Opciones/btnMas.png");

        btnMusicaMenos = crearBoton(texMenos);
        btnMusicaMas = crearBoton(texMas);
        btnFxMenos = crearBoton(texMenos);
        btnFxMas = crearBoton(texMas);

        Table tablaOpciones = new Table();
        tablaOpciones.pack();
        tablaOpciones.setPosition(690, 380);;
        tablaOpciones.setDebug(true);
        float size = 50f;

        tablaOpciones.add(btnMusicaMenos).size(size, size).padRight(20);
        tablaOpciones.add(imgMusica).size(380, 80);
        tablaOpciones.add(btnMusicaMas).size(size, size).padLeft(20).row();

        tablaOpciones.add(btnFxMenos).size(size, size).padRight(20);
        tablaOpciones.add(imgEfectos).size(380, 80);
        tablaOpciones.add(btnFxMas).size(size, size).padLeft(20).row();

        capaOpciones.addActor(tablaOpciones);
        stage.addActor(capaOpciones);

        // =========================
        // CONTROLES
        // =========================
        capaControles = new Image(new Texture("Imagenes/Opciones/menuControles.png"));
        capaControles.setSize(stage.getWidth() - 700, stage.getHeight() - 200);
        capaControles.setPosition(
                (stage.getWidth() - capaControles.getWidth()) / 2,
                (stage.getHeight() - capaControles.getHeight()) / 2
        );
        capaControles.setVisible(false);
        stage.addActor(capaControles);
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

    // =========================
    // FIX CRÍTICO: SIN INVERSION
    // =========================

    public void actualizarBarraMusica(int nivel) {
        nivel = MathUtils.clamp(nivel, 0, musicaFrames.length - 1);
        int invertido = (musicaFrames.length - 1) - nivel;
        imgMusica.setDrawable(new TextureRegionDrawable(musicaFrames[invertido]));
    }
    public void actualizarBarraEfectos(int nivel) {
        nivel = MathUtils.clamp(nivel, 0, fxFrames.length - 1);
        int invertido = (fxFrames.length - 1) - nivel;
        imgEfectos.setDrawable(new TextureRegionDrawable(fxFrames[invertido]));
    }

    public void cerrar() {
        stage.dispose();
    }

    // GETTERS
    public Stage getStage() { return stage; }
    public Table getCapaPrincipal() { return capaPrincipal; }
    public Group getCapaOpciones() { return capaOpciones; }
    public Image getCapaControles() { return capaControles; }

    public ImageButton getBtnJugar() { return btnJugar; }
    public ImageButton getBtnCargar() { return btnCargar; }
    public ImageButton getBtnOpciones() { return btnOpciones; }
    public ImageButton getBtnSalir() { return btnSalir; }

    public ImageButton getBtnMusicaMenos() { return btnMusicaMenos; }
    public ImageButton getBtnMusicaMas() { return btnMusicaMas; }
    public ImageButton getBtnFxMenos() { return btnFxMenos; }
    public ImageButton getBtnFxMas() { return btnFxMas; }
    public ImageButton getBtnAtras() { return btnAtras; }
}