package com.mortaTower.Vista;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mortaTower.Modelo.Habilidad;
import com.mortaTower.Modelo.RecompensasModelo;

import static com.mortaTower.Screens.Screens.WORLD_WIDTH;
import static com.mortaTower.Screens.Screens.WORLD_HEIGHT;

public class RecompensasVista {

    private RecompensasModelo modelo;

    private BitmapFont font;

    private static final int NORMAL = 0;
    private static final int SELECTED = 1;

    private final Texture[] texturas;
    private int[] columnas;
    private int[] filas;

    // [objeto][estado][nivel]
    private TextureRegion[][][] sprites;

    public RecompensasVista(RecompensasModelo modelo) {

        this.modelo = modelo;
        font = new BitmapFont();

        texturas = new Texture[] {

            new Texture("Imagenes/SeccionRecompensa/tipoHabilidad.png"),
            new Texture("Imagenes/SeccionRecompensa/cuadroHabilidad.png")

        };

        columnas = new int[] {4, 2};
        filas = new int[] {1, 1};

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
                    sprites[i][estado][nivel] =
                        new TextureRegion(tex, estado * width, nivel * height, width, height);
                }
            }
        }
    }

    public void draw(SpriteBatch batch) {

    float x = WORLD_HEIGHT * 0.19f;
    float y = WORLD_WIDTH * 0.394f;
    float separacion = WORLD_HEIGHT * 0.3f;

    float ancho = WORLD_WIDTH * 0.12f;
    float alto = WORLD_HEIGHT * 0.20f;

    int seleccion = modelo.getOpcionActual().ordinal();

    for (int i = 0; i < 3; i++) {

        RecompensasModelo.Recompensa tipo = modelo.getTipo(i);

        int estado = tipo.ordinal();
        int estado2 = (i == seleccion) ? SELECTED : NORMAL;

        batch.draw(
            sprites[0][estado][0],
            x,
            y - separacion * i,
            ancho,
            alto
        );

        batch.draw(
            sprites[1][estado2][0],
            x + 160,
            y - separacion * i,
            ancho + 600,
            alto - 10
        );

        Habilidad hab = modelo.getHabilidad(i);

        font.draw(batch, hab.getNombre(), x + 200, (y + 50) - separacion * i + 20);
    }
}


}
