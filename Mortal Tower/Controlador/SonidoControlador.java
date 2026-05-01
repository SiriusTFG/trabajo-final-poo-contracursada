package Controlador;

import java.net.URL;
import javax.sound.sampled.*;

public class SonidoControlador {

    private Clip[] clips = new Clip[10];
    private URL[] sonido = new URL[10];

    private float volumen = 1.0f; // 0.0 - 1.0

    public SonidoControlador() {

        sonido[0] = getClass().getResource("/assets/Sonidos/Intro/PushForward.wav");
        sonido[1] = getClass().getResource("/assets/Sonidos/Fx/vgmenuhighlight.wav");
        sonido[2] = getClass().getResource("/assets/Sonidos/Fx/Fantasy_UI (21).wav");

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

    // PLAY
    public void play(int i) {
        if (clips[i] != null) {
            clips[i].stop();
            clips[i].setFramePosition(0);
            clips[i].start();
        }
    }

    // LOOP
    public void loop(int i) {
        if (clips[i] != null) {
            clips[i].loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    // STOP
    public void stop(int i) {
        if (clips[i] != null) {
            clips[i].stop();
        }
    }

    // VOLUMEN GLOBAL
    public void setVolumen(float volumen) {
    this.volumen = volumen;

    for (Clip clip : clips) {
        if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {

            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            float min = control.getMinimum();
            float max = control.getMaximum();

            // evitar 0 absoluto (silencio total)
            if (volumen <= 0.0f) {
                control.setValue(min);
            } else {
                float gain = (float) (20f * Math.log10(volumen));
                gain = Math.max(min, Math.min(max, gain));
                control.setValue(gain);
            }
        }
    }
}

    public float getVolumen() {
        return volumen;
    }
}