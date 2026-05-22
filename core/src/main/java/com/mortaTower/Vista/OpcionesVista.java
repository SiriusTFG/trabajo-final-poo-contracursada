package com.mortaTower.Vista;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    private OpcionesModelo modelo;

    private Stage stage;
    private Texture transparencia, fondo, controles;
    private final Texture[] texturas;
    private TextureRegion[][][] sprites;

    //actores visuales
    private Image imgMusica, imgEfectos, imgPantallaControles;

    //botones
    private ImageButton btnMusicaMenos, btnMusicaMas, btnEfectosMenos, btnEfectosMas, btnControles;


    public OpcionesVista(FitViewport viewport, Main game) {
        stage = new Stage(viewport, game.batch);

        //carga de texturas
        transparencia = new Texture("Imagenes/black.png");
        fondo = new Texture("Imagenes/Opciones/menuOpciones.png");
        controles = new Texture("Imagenes/Opciones/menuControles.png");

         texturas = new Texture[] {
            new Texture("Imagenes/Opciones/volMusica.png"),
            new Texture("Imagenes/Opciones/volEfectos.png"),
            new Texture("Imagenes/Opciones/controles.png"),
            
        };

        Texture texMenos = new Texture("Imagenes/Opciones/btn_menos.png");
        Texture texMas = new Texture("Imagenes/Opciones/btn_mas.png");

            int[] cols = {2, 2, 2};
            int[] fil = {12, 12, 1};
            sprites = new TextureRegion[texturas.length][][];

            for (int i = 0; i < texturas.length; i++) {
                int columnas = cols[i];
                int filas = fil[i];
                int ancho = texturas[i].getWidth() / columnas;
                int alto = texturas[i].getHeight() / filas;
                sprites[i] = new TextureRegion[columnas][filas];

                for (int estado = 0; estado < columnas; estado++) {
                    for (int nivel = 0; nivel < filas; nivel++) {
                        sprites[i][estado][nivel] = new TextureRegion(texturas[i], estado * ancho, nivel * alto, ancho, alto);
                    }
                }
            }

            //crear actores
        imgMusica = new Image(new TextureRegionDrawable(sprites[0][1][11]));
        imgEfectos = new Image(new TextureRegionDrawable(sprites[1][1][11]));

        btnMusicaMenos = crearBotonSimple(texMenos);
        btnMusicaMas = crearBotonSimple(texMas);
        btnEfectosMenos = crearBotonSimple(texMenos);
        btnEfectosMas = crearBotonSimple(texMas);

        btnControles = crearBotonDoble(texturas[2]);

        imgPantallaControles = new Image(controles);
        imgPantallaControles.setVisible(false);

        Table tabla = new Table();
        tabla.setFillParent(true);
        tabla.center();

        float tamañoBtn = 50f;

        tabla.add(btnMusicaMenos).size(tamañoBtn, tamañoBtn).padRight(15);
        tabla.add(imgMusica).padBottom(20);
        tabla.add(btnMusicaMas).size(tamañoBtn, tamañoBtn).padLeft(15).row();
        
        tabla.add(btnControles).colspan(3).padTop(20).row();


        stage.addActor(tabla);
        
        imgPantallaControles.setPosition((stage.getWidth() - imgPantallaControles.getWidth())/2, (stage.getHeight() - imgPantallaControles.getHeight())/2);
        stage.addActor(imgPantallaControles);
    }

    private ImageButton crearBotonDoble(Texture textura) {
        int ancho = textura.getWidth() /2;
        int alto = textura.getHeight();
        TextureRegion normal = new TextureRegion(textura, 0, 0, ancho, alto);
        TextureRegion seleccionado = new TextureRegion(textura, ancho, 0, ancho, alto);
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(normal);
        style.imageOver = new TextureRegionDrawable(seleccionado);
        return new ImageButton(style);
    }

    private ImageButton crearBotonSimple(Texture textura) {
        TextureRegion normal = new TextureRegion(textura);
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(normal);
        return new ImageButton(style);
    }

    public void actualizarBarraMusica(int nivel) {
        ((TextureRegionDrawable) imgMusica.getDrawable()).setRegion(sprites[0][1][nivel]);
    }

    public void actualizarBarraEfectos(int nivel) {
        ((TextureRegionDrawable) imgEfectos.getDrawable()).setRegion(sprites[1][1][nivel]);
    }

    public void render(float delta, boolean mostrarControles) {
        imgPantallaControles.setVisible(mostrarControles);

        stage.getBatch().begin();
        stage.getBatch().setColor(0,0,0,0f);
        stage.getBatch().draw(transparencia, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().setColor(1, 1, 1, 1);
        stage.getBatch().draw(fondo, 350, 100, stage.getWidth() - 700, stage.getHeight() - 200);
        stage.getBatch().end();

        stage.act(delta);
        stage.draw();
    }

    public void cerrar() {
        stage.dispose();
        transparencia.dispose();
        fondo.dispose();
        controles.dispose();
        for (Texture tex: texturas) tex.dispose();
    }
    
    // GETTERS
    public Stage getStage() { return stage; }
    public ImageButton getBtnMusicaMenos() { return btnMusicaMenos; }
    public ImageButton getBtnMusicaMas() { return btnMusicaMas; }
    public ImageButton getBtnFxMenos() { return btnEfectosMenos; }
    public ImageButton getBtnFxMas() { return btnEfectosMas; }
    public ImageButton getBtnControles() { return btnControles; }
    
    /* public OpcionesVista(OpcionesModelo modelo){

        this.modelo = modelo;

        transparencia = new Texture("Imagenes/black.png");

        fondo = new Texture("Imagenes/Opciones/menuOpciones.png");
        controles = new Texture("Imagenes/Opciones/menuControles.png");

        texturas = new Texture[] {
            new Texture("Imagenes/Opciones/volMusica.png"),
            new Texture("Imagenes/Opciones/volEfectos.png"),
            new Texture("Imagenes/Opciones/controles.png"),
            
        };

        columnas = new int[] {2,2,2};
        filas = new int[] {12,12,1};

        sprites = new TextureRegion[texturas.length][][];

        for (int i = 0; i < texturas.length; i++) {

            Texture tex = texturas[i];

            int cols = columnas[i];
            int rows = filas[i];

            int width = tex.getWidth() / cols;
            int height = tex.getHeight() / rows;

            sprites[i] = new TextureRegion[cols][rows];

            for (int estado = 0; estado < cols; estado++) {

                for (int nivel = 0; nivel < rows; nivel++) {

                    sprites[i][estado][nivel] = new TextureRegion(tex, estado * width, nivel * height, width, height);
                }
            }
        }

    }

    public void draw(SpriteBatch batch) {

        x = WORLD_WIDTH * 0.33f;
        y = WORLD_HEIGHT * 0.60f;

        ancho = WORLD_WIDTH * 0.35f;
        alto = WORLD_HEIGHT * 0.12f;

        separacion = WORLD_HEIGHT * 0.15f;
        
        // opción seleccionada
        int seleccion = modelo.getOpcionActual().ordinal(); //conecta el enum del modelo con un índice numérico.
        int nivelMusica = modelo.getseleccionMusica();
        int nivelFx = modelo.getseleccionFx();

        batch.setColor(0, 0, 0, 0.8f);
        batch.draw(transparencia, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        batch.setColor(1, 1, 1, 1);
        
        if (modelo.getEstadoActual() == OpcionesModelo.EstadoEnum.CONTROLES){

            batch.draw(controles, 350, 100, WORLD_WIDTH - 700, WORLD_HEIGHT - 200);

        }else{

            batch.draw(fondo, 350, 100, WORLD_WIDTH - 700, WORLD_HEIGHT - 200);

            for (int i = 0; i < sprites.length; i++) {

                int estado = (i == seleccion) ? SELECTED : NORMAL;
                int nivel = 0;

                if(i == OpcionesEnum.MUSICA.ordinal()){
                    nivel = nivelMusica;
                }else if(i == OpcionesEnum.EFECTOS.ordinal()){
                    nivel = nivelFx;
                }

                batch.draw(sprites[i][estado][nivel], x, y - separacion * i, ancho, alto);
            }
        }
    }

    public void dispose() {

        fondo.dispose();
        controles.dispose();

        for (Texture tex : texturas) {
            tex.dispose();
        }
    } */
    
}
