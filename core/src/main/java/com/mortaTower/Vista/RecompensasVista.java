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


public class RecompensasVista {

    // BOTONES
    private ImageButton btn, btnRemplazo, btnAtras;
    private Stage stage;
    private Stack stack;

    private int ancho, x;
    private int alto, y;
    
    // TEXTURAS;
    private Texture fondo, categorias, cuadro, cuadroRemplazo, barraRemplazo;

    //Fuente
    private BitmapFont font;
    private Label lbl;
    private Image img;

    // TABLAS
    private Table tablaHab , tr;

    private List<ImageButton> botonesHabilidades = new ArrayList<>();
    private List<ImageButton> botonesRemplazo = new ArrayList<>();

    public RecompensasVista(Main game) {


        stage = new Stage();

        fondo = game.assets.get("Imagenes/black.png", Texture.class);

        Image fondoImg = new Image(new TextureRegionDrawable(fondo));
        fondoImg.setFillParent(true);
        fondoImg.setColor(0, 0, 0, 0.6f); // opcional translúcido
        
        cuadro = game.assets.get("Imagenes/SeccionRecompensa/cuadroHabilidad.png", Texture.class);
        categorias = game.assets.get("Imagenes/SeccionRecompensa/tipoHabilidad.png", Texture.class);

        cuadroRemplazo = game.assets.get("Imagenes/SeccionRecompensa/cuadroRemplazo.png", Texture.class);
        
        barraRemplazo = game.assets.get("Imagenes/SeccionRecompensa/barraRemplazo.png", Texture.class);

        stage.addActor(fondoImg);

        btnAtras = crearBoton(new Texture("Imagenes/Opciones/atras.png"));
        btnAtras.setSize(250, 250);
        btnAtras.setPosition(260, 760);
    
    }

    public void listaHabilidades(Habilidad[] habilidad, int[] tipo) {

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

        for (int i = 0; i < habilidad.length; i++) {

            String nombre = habilidad[i].getNombre();

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

    public void cuadroRemplazo(Habilidad[] habilidad){
        
        Image fondoRemplazo = new Image(new TextureRegionDrawable(cuadroRemplazo));
        fondoRemplazo.setPosition(600, 100);

        font = new BitmapFont();

        Label.LabelStyle styles = new Label.LabelStyle();
        styles.font = font;
        styles.fontColor = Color.WHITE;

        if (tr != null) {
            tr.remove();
            tr.clear();
        }

        tr = new Table();
        tr.setFillParent(true);
        //tr.defaults().space(5);
        tr.bottom().padBottom(220);
        tr.left().padLeft(730);
        //tr.setDebug(true);

        botonesRemplazo.clear();

        for (int i = 0; i < habilidad.length; i++) {

            btnRemplazo = crearBoton(barraRemplazo);
            btnRemplazo.setSize(600, 445);

            //String nombre2 = habilidad[i].getNombre();

            /*lbl = new Label(nombre2, styles);
            lbl.setAlignment(Align.center);
            lbl.setTouchable(Touchable.disabled);*/

            stack = new Stack();
            stack.add(btnRemplazo);
            //stack.add(lbl);

            botonesRemplazo.add(btnRemplazo);

            tr.add(stack).width(575).height(145).row();;
        }

        stage.addActor(btnAtras);
        stage.addActor(fondoRemplazo);
        stage.addActor(tr);
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

    public void limpiar() {
        stage.clear();

        Image fondoImg = new Image(new TextureRegionDrawable(fondo));
        fondoImg.setFillParent(true);
        fondoImg.setColor(0, 0, 0, 0.6f);

        stage.addActor(fondoImg);

        botonesHabilidades.clear();
        tablaHab = null;
    }

    // GETTERS
    public Stage getStage() { return stage; }
    public ImageButton getBoton(int index) {return botonesHabilidades.get(index);} 
    public ImageButton getBtnRemplazo(int index) {return botonesRemplazo.get(index);}  
    public int getCantidadHabilidades() {return botonesHabilidades.size();}
    public int getCantidadRemplazo() {return botonesRemplazo.size();}
    public ImageButton getBtnAtras() {return btnAtras;}
}
