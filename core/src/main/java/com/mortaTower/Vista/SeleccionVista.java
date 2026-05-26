package com.mortaTower.Vista;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mortaTower.Main;


public class SeleccionVista {

    private Stage stage;

    private Table capaPrincipal;
    private Group capaIngresoNombre;
    private Group capaSinEspacio;
    private ImageButton btnIrPartidasGuardadas;

    //botones y texto
    private ImageButton btnCaballero, btnMago, btnAtras, btnAtrasNombre, btnIniciarPartida;
    private TextField nombrePartida;
    private final BitmapFont fuente;
    private final TextField.TextFieldStyle style;

    public SeleccionVista(FitViewport viewport, Main game) {
        stage = new Stage(viewport, game.batch);

        //capa seleccion
        Texture fondo = game.assets.get("Imagenes/SeleccionPersonaje/seleccionPersonaje.png", Texture.class);
        Image imgFondo = new Image(fondo);
        imgFondo.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(imgFondo);

        capaPrincipal = new Table();
        capaPrincipal.setFillParent(true);
        capaPrincipal.bottom().padBottom(50);
        capaPrincipal.left().padLeft(140);
       //capaPrincipal.debug();

        btnCaballero = crearBoton(game.assets.get("Imagenes/SeleccionPersonaje/seleccionCaballero.png", Texture.class));
        btnMago = crearBoton(game.assets.get("Imagenes/SeleccionPersonaje/seleccionMago.png", Texture.class));
        btnAtras = crearBoton(new Texture("Imagenes/Opciones/atras.png"));

        capaPrincipal.add(btnAtras).size(150, 100).row();
        capaPrincipal.add(btnCaballero).size(225, 100).padTop(450);
        capaPrincipal.add(btnMago).size(225, 100).padLeft(35).padTop(450);

        stage.addActor(capaPrincipal);

        //capa ingreso de nombre
        capaIngresoNombre = new Group();
        capaIngresoNombre.setSize(stage.getWidth(), stage.getHeight());
        capaIngresoNombre.setVisible(false);

        Image imgOscura = new Image(new Texture("Imagenes/black.png"));
        imgOscura.setSize(stage.getWidth(), stage.getHeight());
        imgOscura.setColor(1, 1, 1, 0.8f);
        capaIngresoNombre.addActor(imgOscura);

        Image imgNombrePartida = new Image(new Texture("Imagenes/SeleccionPersonaje/nombrePersonaje.png"));
        imgNombrePartida.setSize(stage.getWidth() - 700, stage.getHeight() - 300);
        imgNombrePartida.setPosition(350, 200);
        capaIngresoNombre.addActor(imgNombrePartida);

        fuente = new BitmapFont();
        fuente.getData().setScale(2.0f);
        style = new TextField.TextFieldStyle();
        style.font = fuente;
        style.fontColor = Color.GOLD;
        btnAtrasNombre = crearBoton(new Texture("Imagenes/SeleccionPersonaje/atras.png"));
        btnIniciarPartida = crearBoton(new Texture("Imagenes/SeleccionPersonaje/iniciarPartida.png"));
        
        style.cursor = crearCursor(Color.GOLD, 2, 20);

        style.background = null;
        style.focusedBackground = null;

        nombrePartida = new TextField("", style);
        //nombrePartida.setSize(450,300);
        nombrePartida.setPosition(stage.getWidth() / 2f - 200, stage.getHeight() / 2.7f);
        nombrePartida.setMaxLength(25);
        nombrePartida.setVisible(false);
        stage.setKeyboardFocus(nombrePartida);

        Table tablaNombrePartida = new Table();
        tablaNombrePartida.setFillParent(true);
        tablaNombrePartida.bottom().padBottom(230);
        //tablaNombrePartida.debug();

        tablaNombrePartida.add(nombrePartida).size(450, 50).colspan(2).row();
        tablaNombrePartida.add(btnAtrasNombre).size(200, 50).padTop(60);
        tablaNombrePartida.add(btnIniciarPartida).size(200, 50).padTop(60);
        

        capaIngresoNombre.addActor(tablaNombrePartida);
        stage.addActor(capaIngresoNombre);

        capaSinEspacio = new Group();
        capaSinEspacio.setSize(stage.getWidth(), stage.getHeight());
        capaSinEspacio.setVisible(false);

        Image imgSinEspacio = new Image(game.assets.get("Imagenes/CargarPartida/menuSinespacio.png", Texture.class));
        imgSinEspacio.setSize(520, 280);
        imgSinEspacio.setPosition((stage.getWidth() - 520) / 2f, (stage.getHeight() - 280) / 2f);
        capaSinEspacio.addActor(imgSinEspacio);

        btnIrPartidasGuardadas = crearBotonSeparado(
        game.assets.get("Imagenes/CargarPartida/IrPartidasGuardadas.png", Texture.class),
        game.assets.get("Imagenes/CargarPartida/IrPartidasGuardadas1.png", Texture.class)
    );
        
        
        
        
        
        btnIrPartidasGuardadas.setSize(430, 90);
        btnIrPartidasGuardadas.setPosition(stage.getWidth() / 2f - 215, stage.getHeight() / 2f - 100);
        capaSinEspacio.addActor(btnIrPartidasGuardadas);

        stage.addActor(capaSinEspacio);
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
    private Drawable crearCursor(Color color, int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    public void cerrar() {
        stage.dispose();
    }

    public Stage getStage() {return stage;}
    public Table getCapaPrincipal() {return capaPrincipal;}
    public Group getCapaIngresoNombre() {return capaIngresoNombre;}
    public ImageButton getBtnCaballero() {return btnCaballero;}
    public ImageButton getBtnMago() {return btnMago;}
    public ImageButton getBtnAtras() {return btnAtras;}
    public ImageButton getBtnAtrasNombre() {return btnAtrasNombre;}
    public ImageButton getBtnIniciarPartida() {return btnIniciarPartida;}
    public TextField getNombrePartida() {return nombrePartida;}
    public String getNombreDelField() {return nombrePartida.getText();}

    public void mostrarSinEspacio() {
    capaIngresoNombre.setVisible(false);
    nombrePartida.setVisible(false);
    capaSinEspacio.setVisible(true);
    }

    public ImageButton getBtnIrPartidasGuardadas() {
    return btnIrPartidasGuardadas;
}
}