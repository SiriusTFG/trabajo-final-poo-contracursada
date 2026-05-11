package com.mortaTower.Vista;

import static com.mortaTower.Screens.Screens.WORLD_HEIGHT;
import static com.mortaTower.Screens.Screens.WORLD_WIDTH;

import javax.imageio.ImageIO;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mortaTower.Modelo.CombateModelo;

public class CombateVista {

    private CombateModelo modelo;

    // ESTADOS VISUALES
    private static final int NORMAL = 1;
    private static final int SELECTED = 0;

    private Texture fondo;
    private final Texture[] texturas;
    
    private int[] columnas;
    private int[] filas;

    // [objeto][estado][nivel]
    private TextureRegion[][][] sprites;

    //Constructor
    public CombateVista(CombateModelo modelo) {
        this.modelo = modelo;

        // Sprites
        fondo = new Texture("/assets/Imagenes/Combate/FondoCombate.png");

        texturas = new Texture[] {
            new Texture("/assets/Imagenes/Combate/luchar.png"),
            new Texture("/assets/Imagenes/Combate/habilidades.png"),
            new Texture("/assets/Imagenes/MenuInicio/opciones.png"),
        };

        columnas = new int[] {2,2,2};
        filas = new int[] {1,1,1};

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

        float x = WORLD_WIDTH * 0.33f;
        float y = WORLD_HEIGHT * 0.60f;
        float separacion = WORLD_HEIGHT * 0.15f;

        float ancho = WORLD_WIDTH * 0.35f;
        float alto = WORLD_HEIGHT * 0.12f;

        int seleccion = modelo.getOpcionActual().ordinal();

        batch.draw(fondo, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);

        for (int i = 0; i < sprites.length; i++) {

            int estado = (i == seleccion) ? SELECTED : NORMAL;
            int nivel = 0;

            batch.draw(sprites[i][estado][nivel], x, y - separacion * i, ancho, alto);
        }
    }
}

