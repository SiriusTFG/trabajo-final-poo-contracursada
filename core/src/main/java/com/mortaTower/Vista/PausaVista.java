package com.mortaTower.Vista;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mortaTower.Modelo.OpcionesModelo;

import static com.mortaTower.Screens.Screens.WORLD_WIDTH;
import static com.mortaTower.Screens.Screens.WORLD_HEIGHT;

public class PausaVista {

    private OpcionesModelo modelo;

    private static final int NORMAL = 1;
    private static final int SELECTED = 0;

    private float x, y;
    private float ancho, alto;
    private float separacion;

    private Texture transparencia, fondo, controles;
    private final Texture[] texturas;
    private TextureRegion[][][] sprites;

    private int[] columnas;
    private int[] filas;
    
    public PausaVista(){

        transparencia = new Texture("Imagenes/black.png");

        texturas = new Texture[] {

            new Texture("Imagenes/Opciones/controles.png"),
            new Texture("Imagenes/MenuInicio/opciones.png"),
            new Texture("Imagenes/MenuInicio/salir.png"),
            
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

        x = WORLD_WIDTH * 0.33f;
        y = WORLD_HEIGHT * 0.60f;

        ancho = WORLD_WIDTH * 0.35f;
        alto = WORLD_HEIGHT * 0.12f;

        separacion = WORLD_HEIGHT * 0.15f;
        
        // opción seleccionada
        //int seleccion = modelo.getOpcionActual().ordinal(); //conecta el enum del modelo con un índice numérico.

        batch.setColor(0, 0, 0, 0.8f);
        batch.draw(transparencia, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        batch.setColor(1, 1, 1, 1);

        for (int i = 0; i < sprites.length; i++) {

            //int estado = (i == seleccion) ? SELECTED : NORMAL;
            int nivel = 0;

            batch.draw(sprites[i][0][nivel], x, y - separacion * i, ancho, alto);
        }
    }

    public void dispose() {

        fondo.dispose();
        controles.dispose();

        for (Texture tex : texturas) {
            tex.dispose();
        }
    }
    
}

    

