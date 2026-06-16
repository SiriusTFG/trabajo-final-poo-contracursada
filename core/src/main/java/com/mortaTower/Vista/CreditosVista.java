package com.mortaTower.Vista;

import com.mortaTower.Main;
import com.mortaTower.Screens.GameAssets;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class CreditosVista {

        private final Stage stage;
        private final Table table;


        private float w, h;

        private float scrollY;
        private float logox, logoY;

        private float delay = 6f;
        private boolean startScroll = false;

        //private boolean finalTriggered = false;
        private boolean finished = false;

        private Label.LabelStyle estilo, estilo2;
        private ImageButton btnSalir;
        private TextureRegionDrawable drawable;

        private Image logo, imgFondo;

        private static final float INTRO_TIME = 6f;

        public CreditosVista(FitViewport viewport, Main game) {

                this.stage = new Stage(viewport, game.batch);

                w = stage.getViewport().getWorldWidth();
                h = stage.getViewport().getWorldHeight();

                estilo = new Label.LabelStyle();
                estilo.font = GameAssets.fuenteMedieval;

                estilo2 = new Label.LabelStyle();
                estilo2.font = GameAssets.fuenteMedieval;
                estilo2.fontColor = Color.GOLDENROD;

                drawable = new TextureRegionDrawable(new TextureRegion(game.assets.get("Imagenes/Opciones/pausa.png", Texture.class)));

                btnSalir = new ImageButton(drawable);
                btnSalir.setSize(200, 80);
                btnSalir.setPosition(w - 1320, h - 100);

                // ===== FONDO =====
                imgFondo = new Image(game.assets.get("Imagenes/black.png", Texture.class));
                imgFondo.setFillParent(true);

                // ===== LOGO =====
                logo = new Image(game.assets.get("Imagenes/Creditos/mortalTower.png", Texture.class));
                logo.setSize(500, 300);
                logox = (w - logo.getWidth()) / 2f;
                logoY = (h - logo.getHeight()) /2f;
                logo.setPosition(logox, logoY);
                logo.setColor(1, 1, 1, 0f);
                logo.addAction(Actions.fadeIn(INTRO_TIME, Interpolation.fade));

                delay = INTRO_TIME;

                // CREDITOS
                table = new Table();
                table.center();

                table.add(new Label("PROGRAMACION GENERAL", estilo2)).padBottom(20).row();
                table.add(new Label("David", estilo)).padBottom(10).row();
                table.add(new Label("Jordan", estilo)).padBottom(10).row();
                table.add(new Label("Maximiliano", estilo)).padBottom(10).row();
                table.add(new Label("Isaac", estilo)).padBottom(40).row();
                table.add(new Label("ARTE Y DISEÑO", estilo2)).padBottom(20).row();
                table.add(new Label("DAVID", estilo)).padBottom(10).row();
                table.add(new Label("Isaac", estilo)).padBottom(10).row();
                table.add(new Label("Jordan", estilo)).padBottom(10).row();
                table.add(new Label("Maximiliano", estilo)).padBottom(10).row();
                table.add(new Label("¡GRACIAS POR JUGAR!", estilo)).padTop(40).row();
                table.pack();

                scrollY = -table.getHeight(); // el scroll empieza fuera de la pantalla

                table.setPosition((w - table.getWidth()) / 2f, scrollY);

                // ===== ACTORS =====
                stage.addActor(imgFondo);
                stage.addActor(btnSalir);
                stage.addActor(table);
                stage.addActor(logo);
                
        }

        public void update(float delta, float speed) {

                stage.act(delta);

                // Espera mientras se muestra el logo
                if (!startScroll) {

                        delay -= delta;

                        if (delay <= 0) {
                                startScroll = true;
                        }

                        return;
                }

                float movement = speed * delta;

                // Créditos
                scrollY += movement;
                table.setY(scrollY);

                // Logo (sube junto con los créditos)
                logoY += movement;
                logo.setY(logoY);

                float endOfScroll = table.getHeight() + 180;

                if (scrollY >= endOfScroll) {finished = true;} // devuelve true si el scroll ya paso la pantalla
        }

        public boolean isFinished() {return finished;}

        public void draw(float delta) {stage.draw();}
        public Stage getStage() {return stage;}
        public Button getBtnSalir() {return btnSalir;}
}