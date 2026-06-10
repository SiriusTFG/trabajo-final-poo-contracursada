package com.mortaTower.Vista;

import com.mortaTower.Main;
import com.mortaTower.Screens.GameAssets;

import java.util.List;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class RecompensasVista {

    private Main game;
    private Stage stage;

    private float w, h;
    private int ancho, alto;

    private List<ImageButton> botonesHabilidades = new ArrayList<>();
    private List<ImageButton> botonesRemplazo = new ArrayList<>();
    private ImageButton btn, btnRemplazo, btnConfirmar, btnOmitir;
    
    private Table tablaHab;
    private Stack stack;

    private Label.LabelStyle estilo, estilo3;
    private Label lbl;
    private Label lblNueva, lblReemplaza;
    private Label lblDanioRec, lblManaRec, lblValorAct, lblManaAct;
    
    private BitmapFont font;
    private Texture categorias, cuadro;
    private Image img, imgRemp, imgFondo, cuadroRecompensa;

    public RecompensasVista(FitViewport viewport,Main game) {
        
        this.game = game;
        this.stage = new Stage(viewport);

        w = stage.getViewport().getWorldWidth();
        h = stage.getViewport().getWorldHeight();

        estilo = new Label.LabelStyle();
        estilo.font = GameAssets.fuenteMedieval;

        estilo3 = new Label.LabelStyle();
        estilo3.font = GameAssets.fuenteMedievalChico;

        imgFondo = new Image(game.assets.get("Imagenes/black.png", Texture.class));
        imgFondo.setFillParent(true);
        imgFondo.setColor(0, 0, 0, 0.6f); // opcional translúcido

        cuadroRecompensa = new Image(game.assets.get("Imagenes/SeccionRecompensa/cuadroRecompensa.png", Texture.class));
        cuadroRecompensa.setFillParent(true);

        cuadro = game.assets.get("Imagenes/SeccionRecompensa/cuadroHabilidad.png", Texture.class);
        categorias = game.assets.get("Imagenes/SeccionRecompensa/tipoHabilidad.png", Texture.class);

        lblDanioRec = new Label("", estilo3);
        lblDanioRec.setAlignment(Align.center);
        lblDanioRec.setPosition(w - 640, h * 0.15f);

        lblManaRec = new Label("", estilo3);
        lblManaRec.setAlignment(Align.center);
        lblManaRec.setPosition(w - 640, h * 0.10f);

        lblValorAct = new Label("", estilo3);
        lblValorAct.setAlignment(Align.center);
        lblValorAct.setPosition(w - 640, h * 0.15f);

        lblManaAct = new Label("", estilo3);
        lblManaAct.setAlignment(Align.center);
        lblManaAct.setPosition(w - 640, h * 0.10f);

        btnConfirmar = crearBoton(game.assets.get("Imagenes/SeccionRecompensa/confirmar.png", Texture.class));
        btnConfirmar.setSize(180, 60);
        btnConfirmar.setPosition(750, 60);
        btnConfirmar.setTouchable(Touchable.disabled);

        btnOmitir = crearBoton(new Texture("Imagenes/SeccionRecompensa/cancelar.png"));
        btnOmitir.setSize(180, 60);
        btnOmitir.setPosition(950, 60);

        stage.addActor(imgFondo);
        stage.addActor(cuadroRecompensa);
        stage.addActor(lblDanioRec);
        stage.addActor(lblManaRec);
        stage.addActor(lblValorAct);
        stage.addActor(lblManaAct);
        stage.addActor(btnConfirmar);
        stage.addActor(btnOmitir);
    }

    public void listaHabilidades(String[] nombreHabilidades, String[] nombreHabilidadesActuales, String[] tipo, String[] tipoActual) {

        botonesHabilidades.clear();
        botonesRemplazo.clear();

        if (tablaHab != null) {
            tablaHab.remove();
            tablaHab.clear();
        }

        tablaHab = new Table();
        tablaHab.defaults().space(10);
        tablaHab.setFillParent(true);
        tablaHab.top().left().padTop(220).padLeft(190);

        int max = Math.max(nombreHabilidades.length, nombreHabilidadesActuales.length);

        for (int i = 0; i < max; i++) {

            // ====== ACTUAL ======
            if (i < nombreHabilidades.length) {

                btn = crearBoton(cuadro);
                float h = btn.getPrefHeight() - 70;

                img = crearIconoTipo(tipo[i]);

                lbl = new Label(nombreHabilidades[i], estilo);
                lbl.setAlignment(Align.center);
                lbl.setTouchable(Touchable.disabled);

                stack = new Stack();
                stack.add(btn);
                stack.add(lbl);

                botonesHabilidades.add(btn);

                tablaHab.add(img).size(h, h).padRight(10);
                tablaHab.add(stack).size(btn.getPrefWidth() - 315, h).padRight(125);
                
            } else {

                tablaHab.add().size(0); // placeholder
                tablaHab.add().size(80);
            }

            // ====== REEMPLAZO ======
            if (i < nombreHabilidadesActuales.length) {

                btnRemplazo = crearBoton(cuadro);
                float h = btnRemplazo.getPrefHeight() - 70;

                imgRemp = crearIconoTipo(tipoActual[i]);

                lbl = new Label(nombreHabilidadesActuales[i], estilo);
                lbl.setAlignment(Align.center);
                lbl.setTouchable(Touchable.disabled);

                stack = new Stack();
                stack.add(btnRemplazo);
                stack.add(lbl);

                botonesRemplazo.add(btnRemplazo);

                //tablaHab.add().width(100); // separador opcional
                tablaHab.add(imgRemp).size(h, h).padRight(10);
                tablaHab.add(stack).size(btnRemplazo.getPrefWidth() - 315, h);
            }

            tablaHab.row();
        }

        lblNueva = new Label("", estilo);
        lblNueva.setPosition(250, 110);

        lblReemplaza = new Label("", estilo);
        lblReemplaza.setPosition(300, 75);

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

    public void cerrar() {
        stage.dispose();
    }

    public void limpiar() {
        stage.clear();

        imgFondo = new Image(new TextureRegionDrawable(game.assets.get("Imagenes/black.png", Texture.class)));
        imgFondo.setFillParent(true);
        imgFondo.setColor(0, 0, 0, 0.6f);

        stage.addActor(imgFondo);

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

    public void setTextoDanio(String daniorec) { lblDanioRec.setText(daniorec);}
    public void setTextoMana(String manarec) {lblManaRec.setText(manarec);}
    public void setTextoDanioAct(String danioact) { lblValorAct.setText(danioact);}
    public void setTextoManaAct(String manaact) {lblManaAct.setText(manaact);}

}