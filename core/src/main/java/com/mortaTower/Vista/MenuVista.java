package com.mortaTower.Vista;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mortaTower.Main;

public class MenuVista {

    private Stage stage;
    private Texture fondo;
    
    //BOTONES
    private ImageButton btnJugar, btnCargar, btnOpciones, btnSalir;

    public MenuVista(FitViewport viewport, Main game) {
        stage = new Stage(viewport, game.batch);

        //carga de texturas
        fondo = game.assets.get("Imagenes/MenuInicio/Fondo.png", Texture.class);

        //creacion de botones
        btnJugar = crearBoton(game.assets.get("Imagenes/MenuInicio/nueva.png", Texture.class));
        btnCargar = crearBoton(game.assets.get("Imagenes/MenuInicio/cargar.png", Texture.class));
        btnOpciones = crearBoton(game.assets.get("Imagenes/MenuInicio/opciones.png", Texture.class));
        btnSalir = crearBoton(game.assets.get("Imagenes/MenuInicio/salir.png", Texture.class));

        //Layout
        Table tabla = new Table();
        tabla.setFillParent(true); //ocupa la pantalla
        //tabla.setDebug(true);

        //posicion de tabla
        tabla.bottom().padBottom(70);
        tabla.left().padLeft(160);

        tabla.add(btnJugar).width(280).height(80).padBottom(0).row();
        tabla.add(btnCargar).width(280).height(80).padBottom(0).row();
        tabla.add(btnOpciones).width(280).height(80).padBottom(0).row();
        tabla.add(btnSalir).width(280).height(80).padBottom(0).row();

        stage.addActor(tabla);
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

    public void render(float delta) {
        stage.act(delta);
        stage.getBatch().begin();
        stage.getBatch().draw(fondo, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().end();
        stage.draw();
    }

    public void cerrar() {
        stage.dispose();
    }

    // GETTERS
    public Stage getStage() { return stage; }
    public ImageButton getBtnJugar() { return btnJugar; }
    public ImageButton getBtnCargar() { return btnCargar; }
    public ImageButton getBtnOpciones() { return btnOpciones; }
    public ImageButton getBtnSalir() { return btnSalir; }

/*     private MenuModelo modelo;
    
    // ESTADOS VISUALES
    private static final int NORMAL = 0;
    private static final int SELECTED = 1;

    //EJE
    private float x, y;
    private float ancho, alto;
    private float separacion;

    // TEXTURAS
    private Texture fondo;
    private Texture[] texturas;
    private TextureRegion[][][] sprites;

    // DIVISION DE IMAGENES
    private int[] columnas;
    private int[] filas;
    private int fil;
    private int col;

    public MenuVista(MenuModelo modelo) {
        this.modelo= modelo;
    }

    public void init(AssetManager assets) {

        fondo = assets.get("Imagenes/MenuInicio/Fondo.png", Texture.class);

        texturas = new Texture[] {
            assets.get("Imagenes/MenuInicio/nueva.png", Texture.class),
            assets.get("Imagenes/MenuInicio/cargar.png", Texture.class),
            assets.get("Imagenes/MenuInicio/opciones.png", Texture.class),
            assets.get("Imagenes/MenuInicio/salir.png", Texture.class)
        };

        columnas = new int[] {2,2,2,2};
        filas = new int[] {1,1,1,1};

        sprites = new TextureRegion[texturas.length][][];

        for (int i = 0; i < texturas.length; i++) {

            Texture tex = texturas[i];

            col = columnas[i];
            fil = filas[i];

            int width = tex.getWidth() / col;
            int height = tex.getHeight() / fil;

            sprites[i] = new TextureRegion[col][fil];

            for (int estado = 0; estado < col; estado++) {

                for (int nivel = 0; nivel < fil; nivel++) {

                    sprites[i][estado][nivel] = new TextureRegion(tex, estado * width, nivel * height, width, height);
                }
            }
        }
    }

    public void draw(SpriteBatch batch) {

        batch.draw(fondo, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);

        x = WORLD_HEIGHT * 0.170f;
        y = WORLD_WIDTH * 0.240f;
        
        ancho = WORLD_WIDTH * 0.28f;
        alto = WORLD_HEIGHT * 0.10f;

        separacion = WORLD_HEIGHT * 0.12f;

        // opción seleccionada
        int seleccion = modelo.getOpcionActual().ordinal(); //conecta el enum del modelo con un índice numérico.

        for (int i = 0; i < sprites.length; i++) {

            int estado = (i == seleccion) ? SELECTED: NORMAL;
            int nivel = 0;
            batch.draw(sprites[i][estado][nivel], x, y - separacion * i, ancho, alto);

        }
    } */

}
