package com.mortaTower.Controlador;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Audio {

    private Sound[] sounds = new Sound[10];
    private Music[] music = new Music[10];

    private float volumen = 1.0f;

    public Audio() {

        sounds[0] = Gdx.audio.newSound(Gdx.files.internal("assets/Sonidos/Intro/untitled2.wav"));
        sounds[1] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/vgmenuhighlight.wav"));
        sounds[2] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/Fantasy_UI (21).wav"));
        
        music[3] = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/Intro/untitled.wav"));
    }

    // PLAY (SFX)
    public void play(int i) { 
        if (sounds[i] != null) {
            sounds[i].play(volumen);
        }
    }

    // LOOP (música)
    public void loop(int i) {
        if (music[i] != null) {
            music[i].setLooping(true);
            music[i].setVolume(volumen);
            music[i].play();
        }
    }

    // STOP
    public void stop(int i) {
        if (sounds[i] != null) sounds[i].stop();
        if (music[i] != null) music[i].stop();
    }

    // VOLUMEN GLOBAL
    public void setVolumen(float volumen) {
        this.volumen = volumen;

        for (Sound s : sounds) {
            if (s != null) {
                s.setVolume(s.play(), volumen); // alternativa simple
            }
        }

        for (Music m : music) {
            if (m != null) {
                m.setVolume(volumen);
            }
        }
    }

    public float getVolumen() {
        return volumen;
    }

    public void dispose() {
        for (Sound s : sounds) if (s != null) s.dispose();
        for (Music m : music) if (m != null) m.dispose();
    }
}
