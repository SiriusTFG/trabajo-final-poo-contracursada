package com.mortaTower.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class GameAudio {

    private Sound[] sounds = new Sound[13];
    private Music[] music = new Music[13];

    private float volFx = 1.0f;
    private float volMusica = 1.0f;

    public GameAudio() {

        sounds[0] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/click.mp3"));
        sounds[1] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/Fantasy_UI (21).wav"));
        sounds[2] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/sound2.wav"));
        sounds[3] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/back.mp3"));
        sounds[4] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/victory.wav"));
        sounds[5] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/defeat.mp3"));
        sounds[6] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/openBook.wav"));
        sounds[7] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/closeBook.wav"));
        sounds[8] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/goblin1.mp3"));
        sounds[9] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/goblin2.wav"));
        sounds[10] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/goblin3.mp3"));
        sounds[12] = Gdx.audio.newSound(Gdx.files.internal("Sonidos/Fx/move.wav"));


        music[0] = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/Intro/menu.wav"));
        music[1] = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/Intro/battleLvl1.mp3"));
        music[2] = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/Intro/battleLvl2.mp3"));
        music[3] = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/Intro/battleLvl3.mp3"));
        music[4] = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/Intro/battleLvl4.wav"));
        music[5] = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/Intro/battleLvl5.mp3"));
        music[6] = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/Intro/starWars.mp3"));
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
