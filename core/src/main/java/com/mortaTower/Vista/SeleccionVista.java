package com.mortaTower.Vista;

import com.mortaTower.Main;
import com.mortaTower.Screens.GameAssets;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class SeleccionVista {

    private Main game;
    private Stage stage;

    private float w, h;
    private String[] nombresHéroes = {"CABALLERO", "MAGO"};

    private List<ImageButton> botonesSeleccion = new ArrayList<>();
    private ImageButton btnAtras, btnAtrasNombre, btnIniciarPartida, btnSeleccion;
    private ImageButton btnIrPartidasGuardadas;

    private Table tablaPrincipal;
    private Group capaIngresoNombre;
    private Group capaSinEspacio;
    private Stack stack;
    
    private Label.LabelStyle estilo;
    private Label lbl;
    
    private  TextField.TextFieldStyle style;
    private TextField nombrePartida;
    private Texture texture;
    private Pixmap pixmap;
    
    private Image imgOscura, imgNombrePartida, imgSinEspacio;

    public SeleccionVista(FitViewport viewport, Main game) {

        this.game = game;
        this.stage = new Stage(viewport, game.batch);

        w = stage.getViewport().getWorldWidth();
        h = stage.getViewport().getWorldHeight();

        estilo = new Label.LabelStyle();
        estilo.font = GameAssets.fuenteMedieval;

        Image imgFondo = new Image(game.assets.get("Imagenes/SeleccionPersonaje/seleccionPersonaje.png", Texture.class));
        imgFondo.setFillParent(true);

        imgOscura = new Image(game.assets.get("Imagenes/black.png", Texture.class));
        imgOscura.setFillParent(true);
        imgOscura.setColor(0, 0, 0, 0.8f);

        btnAtras = crearBoton(new Texture("Imagenes/Opciones/atras.png"));
        btnAtras.setSize(w * 0.05f, w * 0.05f);
        btnAtras.setPosition(w - 1140, h- 100);

        btnAtrasNombre = crearBoton(game.assets.get("Imagenes/SeleccionPersonaje/atras.png", Texture.class));
        btnIniciarPartida = crearBoton(game.assets.get("Imagenes/SeleccionPersonaje/iniciarPartida.png", Texture.class));

        btnIrPartidasGuardadas = crearBotonSeparado(game.assets.get("Imagenes/CargarPartida/IrPartidasGuardadas.png", Texture.class), game.assets.get("Imagenes/CargarPartida/IrPartidasGuardadas1.png", Texture.class));

        tablaPrincipal = new Table();
        tablaPrincipal.setFillParent(true);
        tablaPrincipal.bottom().padBottom(70);
        tablaPrincipal.left().padLeft(118);
        
        for (int i = 0; i < nombresHéroes.length; i++) {

            btnSeleccion = crearBoton(game.assets.get("Imagenes/SeleccionPersonaje/seleccion.png", Texture.class));

            botonesSeleccion.add(btnSeleccion);

            lbl = new Label(nombresHéroes[i], estilo);
            lbl.setAlignment(Align.center);
            lbl.setTouchable(Touchable.disabled);

            stack = new Stack();
            stack.add(btnSeleccion);
            stack.add(lbl);

            tablaPrincipal.add(stack).size(235, 150).padRight(30);
        }

        stage.addActor(imgFondo);
        stage.addActor(btnAtras);
        stage.addActor(tablaPrincipal);
    }

    public void sinEspacio(boolean mostrar){

        if (capaSinEspacio == null) {

            capaSinEspacio = new Group();
            capaSinEspacio.setSize(stage.getWidth(), stage.getHeight());
            capaSinEspacio.setVisible(false);

            imgSinEspacio = new Image(game.assets.get("Imagenes/CargarPartida/menuSinespacio.png", Texture.class));
            imgSinEspacio.setSize(w * 0.4f, w * 0.22f);
            imgSinEspacio.setPosition((w - imgSinEspacio.getWidth()) / 2f, (h - imgSinEspacio.getHeight()) / 2f);
            
            btnIrPartidasGuardadas.setSize(w* 0.3f, w * 0.07f);
            btnIrPartidasGuardadas.setPosition(w / 2f - 190, h / 2f - 105);
            
            capaSinEspacio.addActor(imgOscura);
            capaSinEspacio.addActor(imgSinEspacio);
            capaSinEspacio.addActor(btnIrPartidasGuardadas);

            stage.addActor(capaSinEspacio);
        }

        capaSinEspacio.setVisible(mostrar);

    }
    
    public void ingresoNombre(boolean mostrar) {

        // Si no existe la capa, la creamos (primera vez)
        if (capaIngresoNombre == null) {

            pixmap = new Pixmap(2, 40, Pixmap.Format.RGBA8888);
            pixmap.setColor(Color.GOLDENROD);
            pixmap.fill();

            texture = new Texture(pixmap);
            pixmap.dispose();

            style = new TextField.TextFieldStyle();
            style.font = GameAssets.fuenteMedieval;
            style.fontColor = Color.GOLDENROD;

            style.cursor = new TextureRegionDrawable(new TextureRegion(texture));

            capaIngresoNombre = new Group();
            capaIngresoNombre.setSize(stage.getWidth(), stage.getHeight());

            imgNombrePartida = new Image(game.assets.get("Imagenes/SeleccionPersonaje/nombrePersonaje.png", Texture.class));
            imgNombrePartida.setSize(w - 700, h - 300);
            imgNombrePartida.setPosition((w - imgNombrePartida.getWidth()) /2f, (h - imgNombrePartida.getHeight()) / 2f);
            
            // Campo de texto
            nombrePartida = new TextField("", style);
            nombrePartida.setSize(300, 50);
            nombrePartida.setPosition((w - nombrePartida.getWidth()) / 2f, (h -130) / 2f);
            nombrePartida.setMaxLength(16);
            nombrePartida.setAlignment(Align.center);
            nombrePartida.setVisible(false);
            
            btnAtrasNombre.setSize(200, 50);
            btnAtrasNombre.setPosition((w - 420) / 2, (h - 370) /2);

            btnIniciarPartida.setSize(200, 50);
            btnIniciarPartida.setPosition((w) / 2, (h - 370) /2);

            capaIngresoNombre.addActor(imgOscura);
            capaIngresoNombre.addActor(imgNombrePartida);
            capaIngresoNombre.addActor(nombrePartida);
            capaIngresoNombre.addActor(btnAtrasNombre);
            capaIngresoNombre.addActor(btnIniciarPartida);

            stage.setKeyboardFocus(nombrePartida);
            stage.addActor(capaIngresoNombre);
        }

        // Finalmente, solo mostramos u ocultamos
        capaIngresoNombre.setVisible(mostrar);

        if (nombrePartida != null) {

            nombrePartida.setVisible(mostrar);

            if (mostrar) {
                nombrePartida.setText(""); // limpia campo al mostrar
                stage.setKeyboardFocus(nombrePartida);
            } else {
                stage.setKeyboardFocus(null);
            }
        }
    }

    private ImageButton crearBoton(Texture textura) {

        int ancho = textura.getWidth() / 2;
        int alto = textura.getHeight();

        TextureRegion normal = new TextureRegion(textura, 0, 0, ancho, alto);
        TextureRegion seleccionado = new TextureRegion(textura, ancho, 0, ancho, alto);

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();

        style.imageUp = new TextureRegionDrawable(normal);
        style.imageOver = new TextureRegionDrawable(seleccionado);

        return new ImageButton(style);
    }
    
    private ImageButton crearBotonSeparado(Texture normal, Texture hover) {

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();

        style.imageUp = new TextureRegionDrawable(new TextureRegion(normal));
        style.imageOver = new TextureRegionDrawable(new TextureRegion(hover));

        return new ImageButton(style);
    }

    public void cerrar() {stage.dispose();}

    // GETTERS
    public Stage getStage() {return stage;}

    public ImageButton getBtn(int index) {return botonesSeleccion.get(index);}
    public int getCantidadBtn() {return botonesSeleccion.size();}

    public Table getTablaPrincipal() {return tablaPrincipal;}
    public Group getCapaIngresoNombre() {return capaIngresoNombre;}
    
    public ImageButton getBtnAtras() {return btnAtras;}
    public ImageButton getBtnAtrasNombre() {return btnAtrasNombre;}
    public ImageButton getBtnIniciarPartida() {return btnIniciarPartida;}
    public TextField getNombrePartida() {return nombrePartida;}
    public String getNombreDelField() {return nombrePartida.getText();}

    public ImageButton getBtnIrPartidasGuardadas() {return btnIrPartidasGuardadas;}
}