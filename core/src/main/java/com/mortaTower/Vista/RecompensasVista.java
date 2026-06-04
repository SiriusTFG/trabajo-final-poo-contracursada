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


public class RecompensasVista {

    // BOTONES
    private ImageButton btn, btnRemplazo, btnAtras, btnConfirmar, btnOmitir;
    private Stage stage;
    private Stack stack;

    private int ancho, x;
    private int alto;
    
    // TEXTURAS;
    private Texture fondo, categorias, cuadro, cuadroRemplazo, barraRemplazo, cuadroRecompensa;

    //Fuente
    private BitmapFont font;
    private Label lbl;
    private Image img, imgRemp, fondoImg, cuadroRec;
    private Label lblNueva, lblReemplaza;

    // TABLAS
    private Table tablaHab , tr;

    private List<ImageButton> botonesHabilidades = new ArrayList<>();
    private List<ImageButton> botonesRemplazo = new ArrayList<>();


    public RecompensasVista(Main game) {
        stage = new Stage();

        fondo = game.assets.get("Imagenes/black.png", Texture.class);

        fondoImg = new Image(new TextureRegionDrawable(fondo));
        fondoImg.setFillParent(true);
        fondoImg.setColor(0, 0, 0, 0.6f); // opcional translúcido

        cuadroRecompensa = game.assets.get("Imagenes/SeccionRecompensa/cuadroRecompensa.png", Texture.class);
        cuadroRec = new Image(new TextureRegionDrawable(cuadroRecompensa));
        cuadroRec.setFillParent(true);

        cuadro = game.assets.get("Imagenes/SeccionRecompensa/cuadroHabilidad.png", Texture.class);
        categorias = game.assets.get("Imagenes/SeccionRecompensa/tipoHabilidad.png", Texture.class);

        cuadroRemplazo = game.assets.get("Imagenes/SeccionRecompensa/cuadroRemplazo.png", Texture.class);
        
        barraRemplazo = game.assets.get("Imagenes/SeccionRecompensa/barraRemplazo.png", Texture.class);

        btnConfirmar = crearBoton(new Texture("Imagenes/SeccionRecompensa/confirmar.png"));
        btnConfirmar.setSize(260, 140);
        btnConfirmar.setPosition(1100, 68);

        btnOmitir = crearBoton(new Texture("Imagenes/SeccionRecompensa/cancelar.png"));
        btnOmitir.setSize(260, 140);
        btnOmitir.setPosition(1430, 68);
    }

    public void listaHabilidades(String[] nombreHabilidades, String[] nombreHabilidadesActuales, String[] tipo, String[] tipoActual) {

        stage.clear();
        
        stage.addActor(fondoImg);
        stage.addActor(cuadroRec);
        stage.addActor(btnConfirmar);
        stage.addActor(btnOmitir);

        botonesHabilidades.clear();
        botonesRemplazo.clear();

        if (tablaHab != null) {
            tablaHab.remove();
            tablaHab.clear();
        }

        font = new BitmapFont();
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        style.fontColor = Color.WHITE;

        tablaHab = new Table();
        tablaHab.defaults().space(10);
        tablaHab.setFillParent(true);
        tablaHab.top().left().padTop(300).padLeft(250);

        int max = Math.max(nombreHabilidades.length, nombreHabilidadesActuales.length);

        for (int i = 0; i < max; i++) {

            // ====== ACTUAL ======
            if (i < nombreHabilidades.length) {
                btn = crearBoton(cuadro);
                float h = btn.getPrefHeight() - 20;

                img = crearIconoTipo(tipo[i]);

                lbl = new Label(nombreHabilidades[i], style);
                lbl.setAlignment(Align.center);
                lbl.setTouchable(Touchable.disabled);

                stack = new Stack();
                stack.add(btn);
                stack.add(lbl);

                botonesHabilidades.add(btn);

                tablaHab.add(img).size(h, h).padRight(10);
                tablaHab.add(stack).size(btn.getPrefWidth() - 100, h).padRight(125);
            } else {
                tablaHab.add().size(0); // placeholder
                tablaHab.add().size(100);
            }

            // ====== REEMPLAZO ======
            if (i < nombreHabilidadesActuales.length) {
                btnRemplazo = crearBoton(cuadro);
                float h = btnRemplazo.getPrefHeight() - 20;

                imgRemp = crearIconoTipo(tipoActual[i]);

                lbl = new Label(nombreHabilidadesActuales[i], style);
                lbl.setAlignment(Align.center);
                lbl.setTouchable(Touchable.disabled);

                stack = new Stack();
                stack.add(btnRemplazo);
                stack.add(lbl);

                botonesRemplazo.add(btnRemplazo);

                //tablaHab.add().width(100); // separador opcional
                tablaHab.add(imgRemp).size(h, h).padRight(10);
                tablaHab.add(stack).size(btnRemplazo.getPrefWidth() - 100, h);
            }

            tablaHab.row();
        }

        lblNueva = new Label("",new Label.LabelStyle(font, Color.WHITE));
        lblNueva.setFontScale(2f);
        lblNueva.setPosition(365, 165);

        lblReemplaza = new Label("",new Label.LabelStyle(font, Color.WHITE));
        lblReemplaza.setFontScale(2f);
        lblReemplaza.setPosition(365, 145);

        stage.addActor(tablaHab);
        stage.addActor(lblNueva);
        stage.addActor(lblReemplaza);
    }

    private Image crearIconoTipo(String tipo) {
        if (tipo == null || tipo.equalsIgnoreCase("vacio")) return null;

        int fila = 0;
        if (tipo.equalsIgnoreCase("Ataque")) fila = 0;
        else if (tipo.equalsIgnoreCase("Defensa")) fila = 1;
        else if (tipo.equalsIgnoreCase("Curacion")) fila = 2;
        else if (tipo.equalsIgnoreCase("Mana")) fila = 3;

        int ancho = categorias.getWidth();
        int alto = categorias.getHeight()/4;

        TextureRegion region = new TextureRegion(categorias, 0, fila * alto, ancho, alto);
        return new Image(new TextureRegionDrawable(region));
    }

    public void pantallaExperiencia(int nivelHeroe, int nivel, int expGanada, int expTotal, int expNecesaria) {
        font = new BitmapFont();
        font.getData().setScale(1.5f);
        Label.LabelStyle styleBlanco = new Label.LabelStyle(font, Color.WHITE);
        Label.LabelStyle styleOro = new Label.LabelStyle(font, Color.GOLD);

        Table tablaExp = new Table();
        tablaExp.setFillParent(true);
        tablaExp.center();

        Label lblTitulo = new Label("Nivel " +  nivel + " de 5 superado", styleOro);
        lblTitulo.setFontScale(2f);
        
        Label lblNivel = new Label("Nivel del Héroe: " + nivelHeroe, styleBlanco);
        Label lblExpGanada = new Label("Experiencia Obtenida: +" + expGanada + " EXP", styleOro);
        Label lblExpTotal = new Label("Progreso de Experiencia: " + expTotal + " / " + expNecesaria, styleBlanco);

        //botón continuar

        tablaExp.add(lblTitulo).padBottom(50).row();
        tablaExp.add(lblNivel).padBottom(20).row();
        tablaExp.add(lblExpGanada).padBottom(20).row();
        tablaExp.add(lblExpTotal).padBottom(60).row();

        stage.addActor(tablaExp);
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

    public ImageButton getBtnConfirmar() {return btnConfirmar;}
    public ImageButton getBtnOmitir() {return btnOmitir;}

    public void setNueva(String nueva) {lblNueva.setText(nueva);}
    public void setReemplaza(String reemplaza) {lblReemplaza.setText(reemplaza);}


}