package com.mortaTower.Controlador;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Audio {

    private Sound[] sounds = new Sound[10];
    private Music[] music = new Music[10];

    private float volFx = 1.0f;
    private float volMusica = 1.0f;

    public Audio() {

        sounds[0] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/sound1.wav"));
        sounds[1] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/vgmenuhighlight.wav"));
        sounds[2] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/Fantasy_UI (21).wav"));
        sounds[3] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/sound2.wav"));
        sounds[4] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/sound3.wav"));
        sounds[5] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/sound4.wav"));
        sounds[6] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/victory.wav"));
        sounds[7] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/defeat.mp3"));
        sounds[8] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/openBook.wav"));
        sounds[9] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/closeBook.wav"));

        music[0] = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/Intro/untitled.wav"));
        music[1] = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/Intro/battleLvl1.mp3"));
        music[2] = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/Intro/starWars.mp3"));
    }

    // PLAY (SFX)
    public void play(int i) { 
        if (sounds[i] != null) {
            sounds[i].play(volFx);
        }
    }

    // LOOP (música)
    public void loop(int i) {
        if (music[i] != null) {
            music[i].setLooping(true);
            music[i].setVolume(volMusica);
            music[i].play();
        }
    }

    // STOP
    public void stop(int i) {
        if (sounds[i] != null) sounds[i].stop();
        if (music[i] != null) music[i].stop();
    }

    // VOLUMEN GLOBAL
    public void setVolumenMusica(float volMusica) {
        this.volMusica = Math.max(0f, Math.min(volMusica, 1f));

        for (Music m : music) {
            if (m != null) {
                m.setVolume(this.volMusica);
            }
        }
    }

    public void setVolumenFx(float volFx) {
        this.volFx = Math.max(0f, Math.min(volFx, 1f));
    }  
    

    public float getVolumenMusica() {return volMusica;}
    public float getVolumenFx() {return volFx;}

    public void dispose() {
        for (Sound s : sounds) if (s != null) s.dispose();
        for (Music m : music) if (m != null) m.dispose();
    }
}
