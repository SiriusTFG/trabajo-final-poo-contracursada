package com.mortaTower.Screens;

import java.sql.SQLException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mortaTower.DAO.PartidaDao;
import com.mortaTower.Main;
import com.mortaTower.Modelo.Partida;
import com.mortaTower.Vista.SeleccionVista;

public class SeleccionScreen extends Screens {

    private SeleccionVista vista;
    private PartidaDao partidaDao;
    private int idHereoSelc = -1;

    public SeleccionScreen(Main game) {
        super(game);
        partidaDao = new PartidaDao();
    }

    @Override
    public void show() {

        vista = new SeleccionVista(viewport, game);
        Gdx.input.setInputProcessor(vista.getStage());

        vista.getBtnCaballero().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                game.audio.play(3);
                idHereoSelc = 1;
                mostrarIngresoNombre();
            }
        });
        vista.getBtnIrPartidasGuardadas().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                game.audio.play(2);
                game.setScreen(new TransicionScreen(game, SeleccionScreen.this, new CargarScreen(game,SeleccionScreen.this)));
                }
                       });

        vista.getBtnMago().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                game.audio.play(3);
                idHereoSelc = 2;
                mostrarIngresoNombre();
            }
        });

        vista.getBtnAtrasNombre().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                game.audio.play(4);
                idHereoSelc = -1;

                vista.ingresoNombre(false);
               // vista.getNombrePartida().setVisible(false);
                //vista.getCapaIngresoNombre().setVisible(false);
                vista.getCapaPrincipal().setVisible(true);
            }
        });

        vista.getBtnIniciarPartida().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent eveto, float x, float y) {
                game.audio.play(2);
                String nombrePartida = vista.getNombreDelField().trim();

                if (nombrePartida.isEmpty()) {
                    return;
                }

                try {
                    Partida partida = confirmarYCrearPartida(nombrePartida, idHereoSelc);
                    game.setPartida(partida);
                    game.setScreen(new TransicionScreen(game, SeleccionScreen.this, new CombateScreen(game, nombrePartida, 1)));
                } catch (SQLException e) {
                    e.printStackTrace();
                    vista.mostrarSinEspacio();
                }
            }
        });

        vista.getBtnAtras().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evento, float x, float y) {
                vista.cerrar();
                game.setScreen(new TransicionScreen(game, SeleccionScreen.this, new MenuScreen(game)));
            }
        });
    }

    private void mostrarIngresoNombre() {
        
        vista.getCapaPrincipal().setVisible(false);
        vista.ingresoNombre(true);
    }

    public Partida confirmarYCrearPartida(String nombrePartida, int idHeroe) throws SQLException {
        int idPartida = partidaDao.nuevaPartida(nombrePartida, idHeroe);
        return partidaDao.cargarPartida(idPartida);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        vista.getStage().act(delta);
        vista.getStage().draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        vista.cerrar();
    }
}
