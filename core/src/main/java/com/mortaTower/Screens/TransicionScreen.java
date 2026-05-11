package com.mortaTower.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mortaTower.Main;

public class TransicionScreen implements Screen {

    private final Game game;
    private final Screen currentScreen;
    private final Screen nextScreen;

    private ShapeRenderer shapeRenderer;

    private float alpha = 0f;
    private boolean changing = false;

    public TransicionScreen(Main game, Screen currentScreen, Screen nextScreen) {
        this.game = game;
        this.currentScreen = currentScreen;
        this.nextScreen = nextScreen;

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {

        if (!changing) {
            currentScreen.render(delta);

            alpha += delta;

            if (alpha >= 1f) {
                alpha = 1f;
                changing = true;
            }

        } else {

            nextScreen.render(delta);

            alpha -= delta;

            if (alpha <= 0f) {
                game.setScreen(nextScreen);
            }
        }

        Gdx.gl.glEnable(GL20.GL_BLEND);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, alpha);
        shapeRenderer.rect(0, 0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void show() {}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}