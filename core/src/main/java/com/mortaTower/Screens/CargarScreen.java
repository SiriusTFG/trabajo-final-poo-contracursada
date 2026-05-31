package com.mortaTower.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mortaTower.DAO.PartidaDao;
import com.mortaTower.Main;
import com.mortaTower.Modelo.CargarModelo;
import com.mortaTower.Modelo.Partida;
import com.mortaTower.Vista.CargarVista;

public class CargarScreen extends Screens {

    private final CargarModelo modelo;
    private final CargarVista vista;
    private final Screens anteriorScreen;
    private int indicePartidaAEliminar = -1;

    public CargarScreen(Main game, Screens anteriorScreen) {
        super(game);

        this.anteriorScreen = anteriorScreen;
        this.modelo = new CargarModelo();
        this.vista = new CargarVista(modelo, viewport, game);

        Gdx.input.setInputProcessor(vista.getStage());

        configurarBotonesPartidas();
        configurarBotonesBorrar();
        configurarBotonesModal();
    }

    private void configurarBotonesPartidas() {
        for (int i = 0; i < vista.getBotonesPartidas().size(); i++) {
            final int index = i;

            vista.getBotonesPartidas().get(i).addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer,
                                  com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                    game.audio.play(1);
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    
                    game.audio.play(2);
                    modelo.setSeleccion(index);
                    cargarPartidaSeleccionada();
                }
            });
        }
    }

    private void configurarBotonesBorrar() {
        for (int i = 0; i < vista.getBotonesBorrar().size(); i++) {
            final int index = i;

            vista.getBotonesBorrar().get(i).addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer,
                                  com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                    game.audio.play(1);
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.audio.play(2);

                    indicePartidaAEliminar = index;

                    String resumen = modelo.getPartidas().get(index);
                    String[] partes = resumen.split(" - ", 2);
                    String textoVisible = (partes.length > 1) ? partes[1] : resumen;

                    vista.mostrarModalEliminar(textoVisible);
                }
            });
        }
    }

    private void configurarBotonesModal() {
        vista.getBtnCancelarEliminar().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.audio.play(1);
                indicePartidaAEliminar = -1;
                vista.ocultarModalEliminar();
            }
        });

        vista.getBtnConfirmarEliminar().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.audio.play(2);

                if (indicePartidaAEliminar >= 0) {
                    eliminarPartidaSeleccionada();
                }
            }
        });
    }

    private void cargarPartidaSeleccionada() {
        int seleccion = modelo.getSeleccion();

        String resumen = modelo.getPartidas().get(seleccion);
        int idPartida = Integer.parseInt(resumen.split(" - ")[0]);

        try {
            PartidaDao partidaDao = new PartidaDao();
            Partida partida = partidaDao.cargarPartida(idPartida);

            if (partida != null) {
                game.setPartida(partida);
                game.setScreen(new TransicionScreen(game, this, new CombateScreen(game, partida.getPisoActual())));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar la partida: " + e.getMessage());
        }
    }

    private void eliminarPartidaSeleccionada() {
        String resumen = modelo.getPartidas().get(indicePartidaAEliminar);
        int idPartida = Integer.parseInt(resumen.split(" - ")[0]);

        try {
            PartidaDao partidaDao = new PartidaDao();
            partidaDao.borrarPartida(idPartida);

            game.setScreen(new CargarScreen(game, anteriorScreen));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al borrar la partida: " + e.getMessage());
        }
    }

    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            game.audio.play(1);
            game.setScreen(new TransicionScreen(game, this, anteriorScreen));
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        vista.getStage().act(delta);
        vista.getStage().draw();
    }

    @Override
    public void dispose() {
        vista.cerrar();
    }
}