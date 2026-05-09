package com.mortaTower.Controlador;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Teclado {

    // estado continuo (mantener tecla)
    public boolean up, down, left, right;

    // pulsación única (un solo frame)
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean selectPressed, backPressed;

    public boolean select, back;

    public void update() {

        // RESET DE PRESSED CADA FRAME
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        selectPressed = false;
        backPressed = false;

        // HOLD (estado continuo)
        up = Gdx.input.isKeyPressed(Input.Keys.UP);
        down = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);

        select = Gdx.input.isKeyPressed(Input.Keys.ENTER);
        back = Gdx.input.isKeyPressed(Input.Keys.ESCAPE);

        // JUST PRESSED (equivalente a tu KeyPressed)
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) upPressed = true;
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) downPressed = true;
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) leftPressed = true;
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) rightPressed = true;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) selectPressed = true;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) backPressed = true;
    }
}
