package com.mortaTower.Vista;

import static com.mortaTower.Screens.Screens.WORLD_HEIGHT;
import static com.mortaTower.Screens.Screens.WORLD_WIDTH;

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
import com.mortaTower.Modelo.NombreModelo;
import com.mortaTower.Modelo.SeleccionModelo;
import com.mortaTower.Modelo.SeleccionModelo.Estado;

public class NombreVista {
    
    private SeleccionModelo modelo;

    private final TextField nameField;
    private final BitmapFont font;
    private final TextField.TextFieldStyle style;

    private Texture cuadro;

    public NombreVista(SeleccionModelo modelo, Stage stage){

        this.modelo = modelo;

        cuadro = new Texture("Imagenes/SeleccionPersonaje/nombrePersonaje.png");

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


        batch.draw(cuadro, 350, 150, WORLD_WIDTH - 700, WORLD_HEIGHT - 300);
        nameField.setVisible(true);
        
    }
    public void dispose() {

        cuadro.dispose();
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
    public TextField getNamTextField(){return nameField;}
}
