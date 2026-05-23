package com.mortaTower.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mortaTower.Controlador.CargarControlador;
import com.mortaTower.Controlador.CargarControlador.Action;
import com.mortaTower.DAO.PartidaDao;
import com.mortaTower.Main;
import com.mortaTower.Modelo.CargarModelo;
import com.mortaTower.Modelo.Partida;
import com.mortaTower.Vista.CargarVista;

public class CargarScreen extends Screens {

    private final CargarModelo modelo;
    private final CargarControlador controlador;
    private final CargarVista vista;
    private final Screens anteriorScreen;

    public CargarScreen(Main game, Screens anteriorScreen){

        super(game);
        this.anteriorScreen = anteriorScreen;
        this.modelo = new CargarModelo();
        this.controlador = new CargarControlador(modelo, game.teclado, game.audio);
        this.vista = new CargarVista(modelo, viewport, game);
        Gdx.input.setInputProcessor(vista.getStage());
        for (int i = 0; i < vista.getBotonesPartidas().size(); i++) {
            final int index = i;
            vista.getBotonesPartidas().get(i).addListener(new ClickListener() {
                @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                game.audio.play(1);
            }
            public void clicked(InputEvent event, float x, float y) {
                game.audio.play(2);
            modelo.setSeleccion(index);
            cargarPartidaSeleccionada();
                }
            });
        }
    }
   

    private void cargarPartidaSeleccionada() {
    int seleccion = modelo.getSeleccion();

    String resume = modelo.getPartidas().get(seleccion);
    int idPartida = Integer.parseInt(resume.split(" - ")[0]);

    try {
        PartidaDao partidaDao = new PartidaDao();
        Partida partida = partidaDao.cargarPartida(idPartida);

        if (partida != null) {
            game.setPartida(partida);
            game.setScreen(new TransicionScreen(game, this, new CombateScreen(game)));
        }

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error al cargar la partida: " + e.getMessage());
    }
    }

    public void update(float delta) {

        Action action = controlador.update();

        switch (action) {

                case CONFIRM_LOAD -> {cargarPartidaSeleccionada();}
                
                case CANCEL -> { game.setScreen(new TransicionScreen(game, this, anteriorScreen));
            }
                case NONE -> {}
            }

        }
    @Override
    public void render(float delta) {
        super.render(delta);
        vista.getStage().act(delta);
        vista.getStage().draw();
    }
    public void draw(float delta) {}

           
    @Override
    public void dispose() {
        vista.cerrar ();
    }

}