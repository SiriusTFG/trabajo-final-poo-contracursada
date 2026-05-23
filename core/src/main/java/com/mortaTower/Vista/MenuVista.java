package com.mortaTower.Vista;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    //capas
    private Table capaPrincipal;
    private Group capaOpciones;
    private Image capaControles;
    
    //botones menu
    private ImageButton btnJugar, btnCargar, btnOpciones, btnSalir;

    //botones opciones
    private ImageButton btnMusicaMenos, btnMusicaMas, btnFxMenos, btnFxMas, btnControles, btnAtras;
    private Image imgMusica, imgEfectos;

    private TextureRegion[][] sprites;


    public MenuVista(FitViewport viewport, Main game) {
        stage = new Stage(viewport, game.batch);

        //carga de texturas
        Texture fondo = game.assets.get("Imagenes/MenuInicio/Fondo.png", Texture.class);
        Image imgFondoGeneral = new Image(fondo);
        imgFondoGeneral.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(imgFondoGeneral); //se dibuja primero y queda de fondo

        //capa de menu
        capaPrincipal = new Table();
        capaPrincipal.setFillParent(true);
        capaPrincipal.bottom().padBottom(70);
        capaPrincipal.left().padLeft(160);

        btnJugar = crearBoton(game.assets.get("Imagenes/MenuInicio/nueva.png", Texture.class));
        btnCargar = crearBoton(game.assets.get("Imagenes/MenuInicio/cargar.png", Texture.class));
        btnOpciones = crearBoton(game.assets.get("Imagenes/MenuInicio/opciones.png", Texture.class));
        btnSalir = crearBoton(game.assets.get("Imagenes/MenuInicio/salir.png", Texture.class));

        capaPrincipal.add(btnJugar).width(280).height(80).padBottom(0).row();
        capaPrincipal.add(btnCargar).width(280).height(80).padBottom(0).row();
        capaPrincipal.add(btnOpciones).width(280).height(80).padBottom(0).row();
        capaPrincipal.add(btnSalir).width(280).height(80).padBottom(0).row();

        stage.addActor(capaPrincipal);

        //capa opciones
        capaOpciones = new Group();
        capaOpciones.setSize(stage.getWidth(), stage.getHeight());
        capaOpciones.setVisible(false);

        Image imgOscura = new Image(new Texture("Imagenes/black.png"));
        imgOscura.setSize(stage.getWidth(), stage.getHeight());
        imgOscura.setColor(1, 1, 1, 0.8f);
        capaOpciones.addActor(imgOscura);

        Image imgOpciones = new Image(new Texture("Imagenes/Opciones/menuOpciones.png"));
        imgOpciones.setSize(stage.getWidth() - 700, stage.getHeight() - 200);
        imgOpciones.setPosition(350, 100);
        capaOpciones.addActor(imgOpciones);

        Texture texVolMusica = new Texture("Imagenes/Opciones/volMusica.png");
        Texture texVolFx = new Texture("Imagenes/Opciones/volEfectos.png");

        sprites = new TextureRegion[2][12]; //[tipo][estado][nivel]
        int anchoVol = texVolMusica.getWidth();
        int anchoFx = texVolFx.getWidth();
        int altoVolMusica = texVolMusica.getHeight() / 12;
        int altoVolFx = texVolFx.getHeight() / 12;

            for (int nivel = 0; nivel < 12; nivel++) {
                sprites[0][nivel] = new TextureRegion(texVolMusica, 0, nivel * altoVolMusica, anchoVol, altoVolMusica);
                sprites[1][nivel] = new TextureRegion(texVolFx, 0, nivel * altoVolFx, anchoFx, altoVolFx);
            }


        imgMusica = new Image(new TextureRegion(sprites[0][0]));
        imgEfectos = new Image(new TextureRegion(sprites[1][0]));

        Texture texMenos = new Texture("Imagenes/Opciones/btnMenos.png");
        Texture texMas = new Texture("Imagenes/Opciones/btnMas.png");

        btnMusicaMenos = crearBoton(texMenos);
        btnMusicaMas = crearBoton(texMas);
        btnFxMenos = crearBoton(texMenos);
        btnFxMas = crearBoton(texMas);

        btnControles = crearBoton(new Texture("Imagenes/Opciones/controles.png"));
        btnAtras = crearBoton(new Texture("Imagenes/Opciones/atras.png"));

        Table tablaOpciones = new Table();
        tablaOpciones.setFillParent(true);
        tablaOpciones.center();
        //tablaOpciones.debug();

        float tamañoBtn = 50f;

        tablaOpciones.add(btnControles).size(tamañoBtn, tamañoBtn).padRight(20).row();

        tablaOpciones.add(btnMusicaMenos).size(tamañoBtn, tamañoBtn).padRight(20);
        tablaOpciones.add(imgMusica).size(300, 50).padBottom(10);
        tablaOpciones.add(btnMusicaMas).size(tamañoBtn, tamañoBtn).padLeft(20).row();

        tablaOpciones.add(btnFxMenos).size(tamañoBtn, tamañoBtn).padRight(20);
        tablaOpciones.add(imgEfectos).size(300, 50).padBottom(10);
        tablaOpciones.add(btnFxMas).size(tamañoBtn, tamañoBtn).padLeft(20).row();
        
        tablaOpciones.add(btnAtras).size(100,100).padTop(50);

        capaOpciones.addActor(tablaOpciones);
        stage.addActor(capaOpciones);

        //capa controles
        capaControles = new Image(new Texture("Imagenes/Opciones/menuControles.png"));
        capaControles.setSize(stage.getWidth() - 700, stage.getHeight() - 200);
        capaControles.setPosition((stage.getWidth() - capaControles.getWidth()) / 2, (stage.getHeight() - capaControles.getHeight()) / 2);
        capaControles.setVisible(false);
        stage.addActor(capaControles);
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

    public void actualizarBarraMusica(int nivel) {
        int indiceSprite = 11 - nivel;
        ((TextureRegionDrawable) imgMusica.getDrawable()).setRegion(sprites[0][indiceSprite]);
    }

    public void actualizarBarraEfectos(int nivel) {
        int indiceSprite = 11 - nivel;
        ((TextureRegionDrawable) imgEfectos.getDrawable()).setRegion(sprites[1][indiceSprite]);
    }

    /* public void render(float delta) {
        stage.act(delta);
        stage.getBatch().begin();
        stage.getBatch().draw(fondo, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().end();
        stage.draw();
    } */

    public void cerrar() {
        stage.dispose();
    }

    // GETTERS
    public Stage getStage() {return stage;}
    public Table getCapaPrincipal() {return capaPrincipal;}
    public Group getCapaOpciones() {return capaOpciones;}
    public Image getCapaControles() {return capaControles;}
    public ImageButton getBtnJugar() {return btnJugar;}
    public ImageButton getBtnCargar() {return btnCargar;}
    public ImageButton getBtnOpciones() {return btnOpciones;}
    public ImageButton getBtnSalir() {return btnSalir;}
    public ImageButton getBtnMusicaMenos() {return btnMusicaMenos;}
    public ImageButton getBtnMusicaMas() {return btnMusicaMas;}
    public ImageButton getBtnFxMenos() {return btnFxMenos;}
    public ImageButton getBtnFxMas() {return btnFxMas;}
    public ImageButton getBtnControles() {return btnControles;}
    public ImageButton getBtnAtras() {return btnAtras;}
}

