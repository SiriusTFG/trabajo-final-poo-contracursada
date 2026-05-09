package com.mortaTower.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mortaTower.Main;


public abstract class Screens implements Screen {

    // Resolucion Logica
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;

    // Resolucion del Mundo/Juego
    public static final float WORLD_WIDTH = 1280f;
    public static final float WORLD_HEIGHT = 720f;

    
    public Main game;
    public OrthographicCamera camera;    // define qué parte del mundo se ve
    public FitViewport viewport;        // adapta el juego a distintas pantallas evitando desformidad
    public SpriteBatch spriteBatch;    // dibuja texturas/sprites
    public Stage stage;               // la escena interactiva 2D que administra actores(button, label, etc) automáticamente

    public Screens(Main game){

        this.game = game;

        camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        camera.position.set(SCREEN_WIDTH / 2f, SCREEN_HEIGHT / 2f, 0);

        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
        stage = new Stage(viewport);

        spriteBatch = game.batch;
    }

    @Override
    public void render(float delta){

        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // borra el frame anterior

        update(delta);

        camera.update(); // 👈 clave

        spriteBatch.setProjectionMatrix(camera.combined); // 👈 clave

        spriteBatch.begin();
        draw(delta);
        spriteBatch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height, true);
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}

    public abstract void draw(float delta);
    public abstract void update(float delta);
}