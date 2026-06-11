package com.mortaTower.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mortaTower.Main;

public class IntroPisoScreen extends Screens {

    private Texture imagenIntro;
    private int nivel;
    private float tiempo = 0f;
    private final float DURACION = 4f;

    public IntroPisoScreen(Main game, int nivel) {
        super(game);
        this.nivel = nivel;
    }
    @Override
    public void show() {
    String ruta = "Imagenes/IntroPisos/piso" + nivel + ".png";

    if (!Gdx.files.internal(ruta).exists()) {
        System.out.println("NO EXISTE LA INTRO: " + ruta);
    } else {
        System.out.println("CARGANDO INTRO: " + ruta);
    }

    imagenIntro = new Texture(ruta);
}


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tiempo += delta;

        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
        spriteBatch.draw(imagenIntro, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        spriteBatch.end();

        if (tiempo >= DURACION || Gdx.input.justTouched()) {
            game.setScreen(new CombateScreen(
            game,
            game.getPartidaActual().getHeroe().getNombre(),nivel));
        } 

    }

    @Override
    public void dispose() {
        if (imagenIntro != null) {
            imagenIntro.dispose();
        }
    }
}