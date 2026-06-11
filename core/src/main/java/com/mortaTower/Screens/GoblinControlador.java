package com.mortaTower.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mortaTower.Main;
import com.mortaTower.Hilo.GoblinAtacante;
import com.mortaTower.Modelo.CombateModelo;
import com.mortaTower.Modelo.*;
import com.mortaTower.Vista.CombateVista;

public class GoblinControlador {

    private final Goblin goblin;
    private final GoblinAtacante hilo;

    private boolean iniciado = false;

    public GoblinControlador() {
        this.goblin = new Goblin();
        this.hilo = new GoblinAtacante();
    }

    public void iniciar(Main game, CombateModelo modelo, PausaControlador pausa, Viewport viewport) {

        if (iniciado) return;
        iniciado = true;

        hilo.iniciar(10, 30, () -> {
            Gdx.app.postRunnable(() -> {

                if (!pausa.opciones() && modelo.getResultado() == CombateModelo.Resultado.NINGUNO) {

                    float posicionX = viewport.getWorldWidth() + 300;
                    float posicionY = CombateScreen.WORLD_HEIGHT * 0.18f;

                    goblin.prepararCorrida(posicionX, posicionY);

                    game.audio.play(8);
                    System.out.println("el goblin entra al combate");
                }
            });
        });
    }

    public void update(float delta, CombateModelo modelo, PausaControlador pausa, CombateVista vista, Main game, float worldWidth, float worldHeight) {

        if (!goblin.getActivo()) return;
        if (pausa.opciones()) return;

        float duracionAtaque =vista.getTiempoAnimacionGoblin(Goblin.EstadoGoblin.ATACANDO);

        boolean impacto = goblin.actualizar(delta, modelo.getHeroe(), worldWidth * 0.20f, worldWidth + 100, worldHeight * 0.40f, duracionAtaque);

        if (impacto) {

            game.audio.play(9);
            modelo.mostrarMensaje("¡Un duende te robó " + goblin.getDanio() + " de vida!");

            game.audio.play(10);

            if (modelo.getHeroe().getVidaActual() <= 0) {

                modelo.getHeroe().setEstadoActual(Entidad.Estado.MUERTE);
                modelo.setResultado(CombateModelo.Resultado.DERROTA);

            } else {

                modelo.getHeroe().setEstadoActual(Entidad.Estado.DANIO);
            }
        }
    }

    public void render(SpriteBatch batch, CombateVista vista) {

        if (goblin.getActivo()) {
            vista.dibujarGoblin(batch, goblin.getX(), goblin.getY(), goblin.getStateTime(), goblin.getEstadoActual());
        }
    }

    public void detener() {
        hilo.pararHilo();
    }

    public Goblin getGoblin() {
        return goblin;
    }
}