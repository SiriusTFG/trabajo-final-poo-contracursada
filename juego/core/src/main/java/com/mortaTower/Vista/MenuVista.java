package com.mortaTower.Vista;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mortaTower.Modelo.MenuModelo;

import static com.mortaTower.Screens.Screens.WORLD_WIDTH;
import static com.mortaTower.Screens.Screens.WORLD_HEIGHT;

public class MenuVista {

    private MenuModelo menuModelo;

    // ESTADOS VISUALES
    private static final int NORMAL = 0;
    private static final int SELECTED = 1;

    private Texture fondo;
    private final Texture[] texturas;
    private final TextureRegion[][] sprites;

    public MenuVista(MenuModelo menuModelo) {
        this.menuModelo = menuModelo;

        fondo = new Texture("Imagenes/MenuInicio/Fondo.png");

         texturas = new Texture[] {
            new Texture("Imagenes/MenuInicio/nueva.png"),
            new Texture("Imagenes/MenuInicio/op.png"),
            new Texture("Imagenes/MenuInicio/creditos.png"),
            new Texture("Imagenes/MenuInicio/sal.png")
        };

        sprites = new TextureRegion[texturas.length][2];

        for (int i = 0; i < texturas.length; i++) {

            Texture tex = texturas[i];

            int width = tex.getWidth() / 2;
            int height = tex.getHeight();

            // NORMAL
            sprites[i][NORMAL] = new TextureRegion(tex, 0, 0, width, height);

            // SELECTED
            sprites[i][SELECTED] = new TextureRegion(tex, width, 0, width, height);
        }
    }
    

    public void draw(SpriteBatch batch) {

        batch.draw(fondo, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);

        float x = WORLD_WIDTH * 0.09f;
        float y = WORLD_HEIGHT * 0.42f;
        float separacion = WORLD_HEIGHT * 0.12f;

        float ancho = WORLD_WIDTH * 0.3f;
        float alto = WORLD_HEIGHT * 0.1f;

        // opción seleccionada
        int seleccion = menuModelo.getOpcionActual().ordinal(); //conecta el enum del modelo con un índice numérico.


        for (int i = 0; i < sprites.length; i++) {

            int estado = (i == seleccion) ? SELECTED : NORMAL;

            batch.draw(sprites[i][estado], x, y - separacion * i, ancho, alto);
        }
    }

    public void dispose() {

        fondo.dispose();

        for (Texture tex : texturas) {
            tex.dispose();
        }
    }
}