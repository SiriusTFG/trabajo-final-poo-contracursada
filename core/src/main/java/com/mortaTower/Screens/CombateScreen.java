package com.mortaTower.Screens;

import com.mortaTower.Controlador.CombateControlador;
import com.mortaTower.Main;
import com.mortaTower.Modelo.CombateModelo;
import com.mortaTower.Modelo.Enemigo;
import com.mortaTower.Modelo.Heroe;
import com.mortaTower.Strategy.ComportamientoAgresivo;
import com.mortaTower.Vista.CombateVista;

public class CombateScreen extends Screens {

    private CombateModelo modelo;
    private CombateVista vista;
    private CombateControlador controlador;

    //constructor
    public CombateScreen(Main game) {

        super(game);
        Heroe heroe = game.getPartidaActual().getHeroe();
        Enemigo zombie = new Enemigo("Zombie Herrero", 100, 20, new ComportamientoAgresivo());
        zombie.setAtaque(10);
        
        modelo = new CombateModelo(heroe, zombie);
        vista = new CombateVista(modelo);
        controlador = new CombateControlador(modelo, game.teclado, game.audio);
    }

    @Override
    public void update(float delta) {
        controlador.update();
    }

    @Override
    public void draw(float delta) {
        vista.draw(spriteBatch);
        vista.dibujarInterfaz(spriteBatch);
    }
}
