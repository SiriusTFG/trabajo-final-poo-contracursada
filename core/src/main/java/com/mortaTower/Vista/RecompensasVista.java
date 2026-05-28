package com.mortaTower.Vista;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mortaTower.Main;
import com.mortaTower.Modelo.Habilidad;
import com.mortaTower.Modelo.RecompensasModelo;


public class RecompensasVista {

    private RecompensasModelo modelo;

    // BOTONES
    private ImageButton btn;

    private Stage stage;
    private Stack stack;

    private int ancho, x;
    private int alto, y;
    
    // TEXTURAS;
    private Texture fondo, categorias, cuadro;

    //Fuente
    private BitmapFont font;
    private Label lbl;
    private Image img;

    // TABLAS
    private Table tablaHab;

    private List<ImageButton> botonesHabilidades = new ArrayList<>();

    public RecompensasVista(Main game) {

        modelo = new RecompensasModelo();

        stage = new Stage();

        fondo = game.assets.get("Imagenes/black.png", Texture.class);

        Image fondoImg = new Image(new TextureRegionDrawable(fondo));
        fondoImg.setFillParent(true);
        fondoImg.setColor(0, 0, 0, 0.6f); // opcional translúcido
        
        cuadro = game.assets.get("Imagenes/SeccionRecompensa/cuadroHabilidad.png", Texture.class);
        categorias = game.assets.get("Imagenes/SeccionRecompensa/tipoHabilidad.png", Texture.class);

        stage.addActor(fondoImg);
        listaHabilidades();
    }

    public void listaHabilidades() {

        font = new BitmapFont();

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        style.fontColor = Color.WHITE;

        if (tablaHab != null) {
            tablaHab.remove();
            tablaHab.clear();
        }

        tablaHab = new Table();
        tablaHab.setFillParent(true);
        tablaHab.defaults().space(0);
        tablaHab.center();

        botonesHabilidades.clear();

        for (int i = 0; i < 3; i++) {

            Habilidad[] hab = modelo.getHabilidad();
            int[] tipo = modelo.getipo();

            String nombre = hab[i].getNombre();

            btn = crearBoton(cuadro);
            img = crearCategoria(categorias, tipo[i]);

            lbl = new Label(nombre, style);
            lbl.setAlignment(Align.center);
            lbl.setTouchable(Touchable.disabled);

            stack = new Stack();
            stack.add(btn);
            stack.add(lbl);

            botonesHabilidades.add(btn);

            tablaHab.add(img).size(210, 180);
            tablaHab.add(stack).width(800).height(200).row();;
        }

        stage.addActor(tablaHab);
    }

    private ImageButton crearBoton(Texture textura) {

        ancho = textura.getWidth() / 2;
        alto = textura.getHeight();

        TextureRegion normal = new TextureRegion(textura, 0, 0, ancho, alto);
        TextureRegion seleccionado =new TextureRegion(textura, ancho, 0, ancho, alto);

        ImageButton.ImageButtonStyle style =new ImageButton.ImageButtonStyle();

        style.imageUp = new TextureRegionDrawable(normal);
        style.imageOver = new TextureRegionDrawable(seleccionado);

        return new ImageButton(style);
    }

    private Image crearCategoria(Texture textura, int columna){

        ancho = textura.getWidth()/4;
        alto = textura.getHeight();

        x = columna * ancho;

        TextureRegion region = new TextureRegion(textura, x, 0, ancho, alto);

        return new Image(new TextureRegionDrawable(region));
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void cerrar() {
        stage.dispose();
    }

    // GETTERS
    public Stage getStage() { return stage; }
    public ImageButton getBoton(int index) {return botonesHabilidades.get(index);}   
    public int getCantidadHabilidades() {return botonesHabilidades.size();}
}
