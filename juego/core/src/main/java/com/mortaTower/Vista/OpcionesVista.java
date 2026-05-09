package com.mortaTower.Vista;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mortaTower.Modelo.OpcionesModelo;
import com.mortaTower.Modelo.OpcionesModelo.OpcionesEnum;

import static com.mortaTower.Screens.Screens.WORLD_WIDTH;
import static com.mortaTower.Screens.Screens.WORLD_HEIGHT;

public class OpcionesVista {

    private OpcionesModelo modelo;

    // ESTADOS VISUALES
    private static final int NORMAL = 1;
    private static final int SELECTED = 0;

    private Texture fondo;
    private final Texture[] texturas;
    // cantidad de columnas (estados)
    private int[] columnas;

    // cantidad de filas (niveles)
    private int[] filas;

    // [objeto][estado][nivel]
    private TextureRegion[][][] sprites;


    public OpcionesVista(OpcionesModelo modelo){

        this.modelo = modelo;

        fondo = new Texture("Imagenes/Opciones/menuOpciones.png");

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

        batch.draw(fondo, 350, 100, WORLD_WIDTH - 700, WORLD_HEIGHT - 200);

        float x = WORLD_WIDTH * 0.33f;
        float y = WORLD_HEIGHT * 0.60f;
        float separacion = WORLD_HEIGHT * 0.15f;

        float ancho = WORLD_WIDTH * 0.35f;
        float alto = WORLD_HEIGHT * 0.12f;

        // opción seleccionada
        int seleccion = modelo.getOpcionActual().ordinal(); //conecta el enum del modelo con un índice numérico.
        int nivelMusica = modelo.getseleccionMusica();
        int nivelFx = modelo.getseleccionFx();


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

    public void dispose() {

        fondo.dispose();

        for (Texture tex : texturas) {
            tex.dispose();
        }
    }
    
}
