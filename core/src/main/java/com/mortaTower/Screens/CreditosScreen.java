package com.mortaTower.Screens;

import com.mortaTower.Main;
import com.mortaTower.Vista.CreditosVista;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class CreditosScreen extends Screens {

    private CreditosVista view;

    private float speed = 30;
    private boolean cambiandoPantalla = false;

    public CreditosScreen(Main game, SpriteBatch batch) {
        super(game);

        game.audio.loop(6);
    }

    @Override
    public void show() {

        view = new CreditosVista(viewport, game);
        Gdx.input.setInputProcessor(view.getStage());

        view.getBtnSalir().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.audio.stop(6);
                game.setScreen(new TransicionScreen(game,CreditosScreen.this,new MenuScreen(game)));
            }
        });
    }

    @Override
    public void render(float delta) {

        super.render(delta);

        view.update(delta, speed);

        if (view.isFinished() && !cambiandoPantalla) {

            cambiandoPantalla = true;

            game.audio.stop(6);
            game.setScreen(new TransicionScreen(game, CreditosScreen.this, new MenuScreen(game)));

            return;
        }

        view.draw(delta);
    }

    
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}