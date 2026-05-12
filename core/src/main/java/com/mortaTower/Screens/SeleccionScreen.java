package com.mortaTower.Screens;

import com.mortaTower.Controlador.SeleccionControlador;
import com.mortaTower.Controlador.SeleccionControlador.Action;
import com.mortaTower.DAO.PartidaDao;
import com.mortaTower.Main;
import com.mortaTower.Modelo.Partida;
import com.mortaTower.Modelo.SeleccionModelo;
import com.mortaTower.Vista.NombreVista;
import com.mortaTower.Vista.SeleccionVista;

public class SeleccionScreen extends Screens {

    private final SeleccionModelo modelo;
    private final SeleccionControlador controlador;

    private final SeleccionVista seleccionVista;
    private final NombreVista nombreVista;

    public SeleccionScreen(Main game) {

        super(game);

        modelo = new SeleccionModelo();
        controlador = new SeleccionControlador(modelo, game.teclado);
        seleccionVista = new SeleccionVista(modelo, stage);
        nombreVista = new NombreVista(stage);
    }

    @Override
    public void update(float delta) {

        Action action = controlador.update();

        switch (action) {

            case IR_MENU -> { game.setScreen(new TransicionScreen(game,this,new MenuScreen(game))); }

            case INICIAR_PARTIDA -> { 
                try {
                    String nombre = modelo.getNombreJugador();
                    int idHeroe = (modelo.getHeroeActual() == SeleccionModelo.Heroe.CABALLERO) ? 1 : 2;

                    PartidaDao pDao = new PartidaDao();
                    int id = pDao.nuevaPartida(nombre, idHeroe);

                    Partida pNueva = pDao.cargarPartida(id);
                    game.setPartida(pNueva);

                    game.setScreen(new CombateScreen(game)); System.out.println("comienza");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }   

            case NONE -> {}
        }
    }

    @Override
    public void draw(float delta) {

        switch (modelo.getEstadoActual()) {

            case SELECCION -> { seleccionVista.draw(spriteBatch); }

            case NOMBRE -> {

                seleccionVista.draw(spriteBatch);

                nombreVista.draw(spriteBatch);
            }

        }
    }
}
