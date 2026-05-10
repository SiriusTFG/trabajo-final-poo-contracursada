package com.mortaTower.Vista;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mortaTower.Modelo.SeleccionModelo;

import static com.mortaTower.Screens.Screens.WORLD_WIDTH;
import static com.mortaTower.Screens.Screens.WORLD_HEIGHT;

public class SeleccionVista {
    
    private SeleccionModelo modelo;

    private final TextField nameField;
    private final BitmapFont font;
    private final TextField.TextFieldStyle style;

    // ESTADOS VISUALES
    private static final int NORMAL = 1;
    private static final int SELECTED = 0;

    private Texture fondo, cuadro;
    private final Texture[] texturas;
    // cantidad de columnas (estados)
    private int[] columnas;

    // cantidad de filas (niveles)
    private int[] filas;

    // [objeto][estado][nivel]
    private TextureRegion[][][] sprites;


    public SeleccionVista(SeleccionModelo modelo, Stage stage){

        this.modelo = modelo;

        fondo = new Texture("Imagenes/SeleccionPersonaje/seleccionPersonaje.png");
        cuadro = new Texture("Imagenes/SeleccionPersonaje/nombrePersonaje.png");

        texturas = new Texture[] {
            new Texture("Imagenes/SeleccionPersonaje/seleccionCaballero.png"),
            new Texture("Imagenes/SeleccionPersonaje/seleccionMago.png"),
            
        };

        columnas = new int[] {2,2};
        filas = new int[] {1,1};

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
        
        font = new BitmapFont();

        style = new TextField.TextFieldStyle();
        style.font = font;
        style.fontColor = Color.GOLD;

        // cursor invisible
        style.cursor = crearCursor(Color.GOLD, 2, 20);

        // sin fondo
        style.background = null;
        style.focusedBackground = null;

        nameField = new TextField("", style);
        nameField.setSize(400, 100);
        nameField.setPosition(WORLD_WIDTH / 2f - 200, WORLD_HEIGHT / 2.7f);
        nameField.setMaxLength(25);
        nameField.setVisible(false);
        stage.setKeyboardFocus(nameField);

        stage.addActor(nameField);

        Gdx.input.setInputProcessor(stage);

    }

    public void draw(SpriteBatch batch) {

        batch.draw(fondo, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);

        float x = WORLD_HEIGHT * 0.19f;
        float y = WORLD_WIDTH * 0.394f;
        float separacion = WORLD_HEIGHT * 0.36f;

        float ancho = WORLD_WIDTH * 0.18f;
        float alto = WORLD_HEIGHT * 0.10f;

        // opción seleccionada
        int seleccion = modelo.getOpcionActual().ordinal(); //conecta el enum del modelo con un índice numérico.

        if(modelo.getEstadoActual() == SeleccionModelo.Estado.NOMBRE){

            batch.draw(cuadro, 350, 150, WORLD_WIDTH - 700, WORLD_HEIGHT - 300);

            nameField.setVisible(true);
        }else{

            nameField.setVisible(false);
        }

        for (int i = 0; i < sprites.length; i++) {

            int estado = (i == seleccion) ? NORMAL : SELECTED;
            int nivel = 0;
            batch.draw(sprites[i][estado][nivel], x + separacion * i, y, ancho, alto);

        }
    }

    public void dispose() {

        fondo.dispose();

        for (Texture tex : texturas) {
            tex.dispose();
        }
    }

    private Drawable crearCursor(Color color, int width, int height) {

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        pixmap.setColor(color);

        pixmap.fill();

        Texture texture = new Texture(pixmap);

        pixmap.dispose();

        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    public String getNombre() {return nameField.getText();}
}
