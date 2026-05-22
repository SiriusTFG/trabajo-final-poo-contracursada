package com.mortaTower.Screens;

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
        this.vista = new CargarVista(modelo);
        
    }
    @Override
    public void update(float delta) {

        Action action = controlador.update();

        switch (action) {

            case CONFIRM_LOAD -> {
                int seleccion = modelo.getSeleccion();

                String resume = modelo.getPartidas().get(seleccion);
                int idPartida = Integer.parseInt(resume.split(" - ")[0]);

                try {
                    PartidaDao partidaDao = new PartidaDao ();
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
                case CANCEL -> { game.setScreen(new TransicionScreen(game, this, anteriorScreen));
            }
                case NONE -> {}
            }

        }
    @Override
    public void draw(float delta) {
        vista.draw(spriteBatch);

    }
    @Override
    public void dispose() {
        vista.dispose();
    }

}