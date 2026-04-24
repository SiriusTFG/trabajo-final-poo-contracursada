package Controlador;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SonidoControlador {

    private Clip[] clips = new Clip[10];
    private URL[] sonido = new URL[10];

    public SonidoControlador() {

        // Rutas de sonidos
        sonido[0] = getClass().getResource("/assets/Sonidos/Intro/PushForward.wav");
        sonido[1] = getClass().getResource("/assets/Sonidos/Fx/vgmenuhighlight.wav");
        sonido[2] = getClass().getResource("/assets/Sonidos/Fx/Fantasy_UI (21).wav");

        // Precarga de todos los sonidos
        cargarSonidos();
    }

    private void cargarSonidos() {
        try {
            for (int i = 0; i < sonido.length; i++) {
                if (sonido[i] != null) {
                    AudioInputStream ais = AudioSystem.getAudioInputStream(sonido[i]);
                    clips[i] = AudioSystem.getClip();
                    clips[i].open(ais);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Reproducir una vez
    public void play(int i) {
        if (clips[i] != null) {
            clips[i].stop(); // por si ya estaba sonando
            clips[i].setFramePosition(0); // reinicia
            clips[i].start();
        }
    }

    // Reproducir en loop (música)
    public void loop(int i) {
        if (clips[i] != null) {
            clips[i].loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    // Detener sonido
    public void stop(int i) {
        if (clips[i] != null) {
            clips[i].stop();
        }
    }
}