package com.mortaTower.Controlador;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mortaTower.Main;
import com.mortaTower.Screens.Screens;
import com.mortaTower.Vista.OpcionesVista;

public class OpcionesControlador extends Screens {

    private OpcionesVista opcionesVista;
    private boolean estado;

    private Stage menuStage;

    private int volMusica;
    private int volFx;

    private Preferences prefs; // sirve para guardar el ultimo valor del volumen

    public OpcionesControlador(Main game, Stage menuStage){

        super(game);
        this.menuStage = menuStage;

        // =========================
        // PREFERENCES
        // =========================
        prefs = Gdx.app.getPreferences("MisOpciones");
        volMusica = prefs.getInteger("volMusica", 9); // valor por defecto 9
        volFx = prefs.getInteger("volFx", 9);

        // =========================
        // VISTA
        // =========================
        opcionesVista = new OpcionesVista(viewport, game);

        // sincronizar vista con valores guardados
        opcionesVista.actualizarBarraMusica(volMusica);
        opcionesVista.actualizarBarraEfectos(volFx);

        // sincronizar audio
        game.audio.setVolumenMusica(volMusica / 9f);
        game.audio.setVolumenFx(volFx / 9f);

        listenersOpcines();
    }

    public void render(float delta) {
        opcionesVista.getStage().act(delta);
        opcionesVista.getStage().draw();
    }

    public void listenersOpcines() {

        // =========================
        // MUSICA +
        // =========================
        opcionesVista.getBtnMusicaMas().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                if (volMusica < 9) {
                    volMusica++;
                    game.audio.setVolumenMusica(volMusica / 9f);
                    opcionesVista.actualizarBarraMusica(volMusica);

                    prefs.putInteger("volMusica", volMusica);
                    prefs.flush();
                }
            }
        });

        // =========================
        // MUSICA -
        // =========================
        opcionesVista.getBtnMusicaMenos().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                if (volMusica > 0) {
                    volMusica--;
                    game.audio.setVolumenMusica(volMusica / 9f);
                    opcionesVista.actualizarBarraMusica(volMusica);

                    prefs.putInteger("volMusica", volMusica);
                    prefs.flush();
                }
            }
        });

        // =========================
        // EFECTOS +
        // =========================
        opcionesVista.getBtnFxMas().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                if (volFx < 9) {
                    volFx++;
                    game.audio.setVolumenFx(volFx / 9f);
                    game.audio.play(0);
                    opcionesVista.actualizarBarraEfectos(volFx);

                    prefs.putInteger("volFx", volFx);
                    prefs.flush();
                }
            }
        });

        // =========================
        // EFECTOS -
        // =========================
        opcionesVista.getBtnFxMenos().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                if (volFx > 0) {
                    volFx--;
                    game.audio.setVolumenFx(volFx / 9f);
                    game.audio.play(0);
                    opcionesVista.actualizarBarraEfectos(volFx);

                    prefs.putInteger("volFx", volFx);
                    prefs.flush();
                }
            }
        });

        // =========================
        // ATRAS
        // =========================
        opcionesVista.getBtnAtras().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                game.audio.play(4);
                estado = false;
                Gdx.input.setInputProcessor(menuStage);
            }
        });
    }

    // GETTERS
    public OpcionesVista getVista() { return opcionesVista; }
    public boolean opciones() { return estado; }
    public void setOpciones(boolean estado) { this.estado = estado; }
}