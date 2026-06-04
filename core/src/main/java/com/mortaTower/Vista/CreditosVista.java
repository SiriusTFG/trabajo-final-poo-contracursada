package com.mortaTower.Vista;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mortaTower.Main;

public class CreditosVista {

    private final Stage stage;
    private final Table table;

    private float scrollY;

    private final Image logo;
    private final Label finalText;

    private float delay = 6f;
    private boolean startScroll = false;

    private boolean finalTriggered = false;

    private ImageButton btnSalir;

    private static final float INTRO_TIME = 6f;

    public CreditosVista(FitViewport viewport, Main game) {

        stage = new Stage(viewport, game.batch);

        BitmapFont font = new BitmapFont();

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;

        Texture texBoton = game.assets.get("Imagenes/Opciones/pausa.png", Texture.class);
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(texBoton));
        btnSalir = new ImageButton(drawable);
        btnSalir.setSize(200, 80);
        btnSalir.setPosition(viewport.getWorldWidth() - 1320, viewport.getWorldHeight() - 100);

        // ===== FONDO =====
        Texture fondo = game.assets.get("Imagenes/black.png", Texture.class);
        Image background = new Image(fondo);
        background.setFillParent(true);

        // ===== LOGO =====
        logo = new Image(game.assets.get("Imagenes/Creditos/mortalTower.png", Texture.class));
        logo.setSize(500, 300);

        logo.setColor(1, 1, 1, 0f);
        logo.addAction(Actions.fadeIn(INTRO_TIME, Interpolation.fade));

        delay = INTRO_TIME;

        float xLogo = (viewport.getWorldWidth() - 500) / 2f;
        float yLogo = viewport.getWorldHeight() * 0.50f;

        logo.setPosition(xLogo, yLogo);

        // ===== CREDITOS =====
        table = new Table();
        table.center();

        table.add(new Label("PROGRAMACION GENERAL", style)).padBottom(20).row();

        table.add(new Label("David", style)).padBottom(10).row();
        table.add(new Label("Sandoval Jordan", style)).padBottom(10).row();
        table.add(new Label("Almonacid Maximiliano", style)).padBottom(10).row();
        table.add(new Label("Leal Isaac", style)).padBottom(40).row();

        table.add(new Label("ARTE Y DISEÑO", style)).padBottom(20).row();

        table.add(new Label("DAVID", style)).padBottom(10).row();
        table.add(new Label("Leal Isaac", style)).padBottom(10).row();
        table.add(new Label("Sandoval Jordan", style)).padBottom(10).row();
        table.add(new Label("Almonacid Maximiliano", style)).padBottom(10).row();

        table.add(new Label("¡THANK YOU FOR PLAY!", style)).padTop(40).row();

        table.pack();

        table.setPosition(
                (viewport.getWorldWidth() - table.getWidth()) / 2f,
                -table.getHeight()
        );

        // ===== TEXTO FINAL (EPÍLOGO) =====
        finalText = new Label(
                "",
                style
        );

        finalText.setAlignment(Align.center);
        finalText.setColor(1, 1, 1, 0f);

        finalText.setPosition(
                (viewport.getWorldWidth() - finalText.getWidth()) / 2f,
                viewport.getWorldHeight() * 0.5f
        );

        finalText.addAction(
                Actions.sequence(
                        Actions.delay(12f),   // aparece al final de los créditos
                        Actions.fadeIn(2f),
                        Actions.delay(3f),
                        Actions.fadeOut(2f)
                )
        );

        scrollY = -table.getHeight();

        // ===== ADD ACTORS (ORDEN IMPORTA) =====
        stage.addActor(background);
        stage.addActor(btnSalir);
        stage.addActor(table);
        stage.addActor(logo);
        stage.addActor(finalText);
    }

    public void update(float delta, float speed) {

    stage.act(delta);

    if (!startScroll) {

        delay -= delta;

        if (delay <= 0) {
            startScroll = true;
        }

        return;
    }

    scrollY += speed * delta;

    table.setY(scrollY);

    float endOfScroll = stage.getViewport().getWorldHeight() + table.getHeight();

    if (!finalTriggered && scrollY >= endOfScroll) {

        finalTriggered = true;

        finalText.addAction(
                Actions.sequence(
                        Actions.fadeIn(2f),
                        Actions.delay(3f),
                        Actions.fadeOut(2f)
                )
        );
    }
}

    public void draw(float delta) {
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    public Button getBtnSalir() {return btnSalir;}
}