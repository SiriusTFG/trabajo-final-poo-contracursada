package com.mortaTower.Screens;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mortaTower.Main;
import com.mortaTower.Vista.CreditosVista;

public class CreditosScreen extends Screens {

    private final SpriteBatch batch;

    private CreditosVista view;

    private float scrollY;
    private float speed;

    public CreditosScreen(Main game, SpriteBatch batch) {

        super(game);
        this.batch = batch;
        game.audio.loop(2);
    }

    @Override
    public void show() {

        view = new CreditosVista(viewport, game);
        Gdx.input.setInputProcessor(view.getStage());
        scrollY = -100;
        speed = 50f;

        view.getBtnSalir().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                
                game.audio.stop(2);
                game.setScreen(new TransicionScreen(game, CreditosScreen.this, new MenuScreen(game)));
            }
        });
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        view.update(delta, 30f);

        view.draw(delta);
    }

    //@Override
    /*public void dispose() {
        view.dispose();
    }*/

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
