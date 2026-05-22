package com.mortaTower.Screens;

import com.mortaTower.Controlador.RecompensaControlador;
import com.mortaTower.Controlador.SeleccionControlador;
import com.mortaTower.Controlador.SeleccionControlador.Action;
import com.mortaTower.Main;
import com.mortaTower.Modelo.Partida;
import com.mortaTower.Modelo.RecompensasModelo;
import com.mortaTower.Modelo.SeleccionModelo;
import com.mortaTower.Vista.NombreVista;
import com.mortaTower.Vista.RecompensasVista;
import com.mortaTower.Vista.SeleccionVista;

public class SeleccionScreen extends Screens {

    private final SeleccionModelo modelo;
    private final SeleccionControlador controlador;

    private final SeleccionVista seleccionVista;
    private final NombreVista nombreVista;

    private RecompensasVista recoVista;
    private RecompensasModelo modelo2;
    private RecompensaControlador cont;

    public SeleccionScreen(Main game) {

        super(game);

        modelo = new SeleccionModelo();
        controlador = new SeleccionControlador(modelo, game.teclado, game.audio);
        seleccionVista = new SeleccionVista(modelo, stage);
        
        nombreVista = new NombreVista(stage);

        modelo2 = new RecompensasModelo();
        recoVista = new RecompensasVista(modelo2);
        cont = new RecompensaControlador(modelo2, game.teclado);
    }

    @Override
    public void update(float delta) {

        Action action = controlador.update();
        //cont.update();

        switch (action) {

            case IR_MENU -> { game.setScreen(new TransicionScreen(game,this,new MenuScreen(game))); }

            case INICIAR_PARTIDA -> { 
                try {
                    String nombrePartida = nombreVista.getNombre();
                    modelo.setNombreJugador(nombrePartida);
                    Partida pNueva = modelo.confirmarYCrearPartida();
                    
                    game.setPartida(pNueva);
                    game.setScreen(new TransicionScreen(game, this,new CombateScreen(game)));
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

            case SELECCION -> { seleccionVista.draw(spriteBatch); nombreVista.dispose(); }

            case NOMBRE -> {

                seleccionVista.draw(spriteBatch);

                //recoVista.draw(spriteBatch);
                nombreVista.draw(spriteBatch);
            }



        }
    }

    @Override
    public void input() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'input'");
    }
}
