package com.mortaTower.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TransicionScreen implements Screen {

    private final Game game;
    private final Screen fromScreen; // pantalla actual
    private final Screen toScreen; // pantalla proxima

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    private float alpha = 0f; // transparencia del negro
    private boolean switching = false;

    private final float speed = 1.5f; // velocidad del fade

    public TransicionScreen(Game game, Screen fromScreen, Screen toScreen) {
        this.game = game;
        this.fromScreen = fromScreen;
        this.toScreen = toScreen;
    }

    @Override
    public void render(float delta) {

        // Actualiza lógica del fade
        alpha += delta * speed;

        if (alpha >= 1f && !switching) { // si alpha llego a 1(negro total)
            alpha = 1f;
            switching = true;

            // cambia la pantalla en el punto negro total
            game.setScreen(toScreen);
        }

        if (alpha >= 2f) {
            
            game.setScreen(toScreen);
            return;
        }

        // Render del screen activo automáticamente
        Screen current = switching ? toScreen : fromScreen;
        current.render(delta);

        // Overlay de transición
        Gdx.gl.glEnable(GL20.GL_BLEND);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        float fadeAlpha;
        if (alpha <= 1f) {
            // fade out
            fadeAlpha = alpha;
        } else {
            // fade in
            fadeAlpha = 2f - alpha;
        }

        shapeRenderer.setColor(0, 0, 0, fadeAlpha);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override public void dispose() {shapeRenderer.dispose();}
    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}