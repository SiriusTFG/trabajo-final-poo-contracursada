package com.mortaTower.Vista;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mortaTower.Modelo.MenuModelo;

import static com.mortaTower.Screens.Screens.WORLD_WIDTH;
import static com.mortaTower.Screens.Screens.WORLD_HEIGHT;

public class MenuVista {

    private MenuModelo modelo;
    
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
    }

}
