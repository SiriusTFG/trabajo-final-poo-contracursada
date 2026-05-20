package com.mortaTower.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mortaTower.Controlador.CombateControlador;
import com.mortaTower.Controlador.InventarioControlador;
import com.mortaTower.Main;
import com.mortaTower.Modelo.CombateModelo;
import com.mortaTower.Modelo.Heroe;
import com.mortaTower.Modelo.InventarioModelo;
import com.mortaTower.Modelo.CombateModelo.Opciones;
import com.mortaTower.Strategy.ComportamientoAgresivo;
import com.mortaTower.Vista.CombateVista;
import com.mortaTower.Vista.InventarioVista;
import com.mortaTower.Vista.MenuVista;
import com.mortaTower.Vista.PausaVista;

public class CombateScreen extends Screens {

    private CombateModelo modelo;
    private CombateVista vista;
    private CombateControlador controlador;

    private InventarioControlador controlador2;
    private InventarioModelo modelo2;
    private InventarioVista vista2;

    private PausaVista vista3;

    //constructor
    public CombateScreen(Main game) {

        super(game);
        Gdx.input.setInputProcessor(null);
        game.teclado.resetPresiones();
        Heroe heroe = game.getPartidaActual().getHeroe();
        
        modelo = new CombateModelo(heroe, 1);
        if (modelo.getEnemigo() != null) {
            modelo.getEnemigo().cambiarComportamiento(new ComportamientoAgresivo());
        }
        vista = new CombateVista(modelo);
        controlador = new CombateControlador(modelo, game.teclado, game.audio);

        modelo2 = new InventarioModelo(heroe);
        controlador2 = new InventarioControlador(modelo2, game.teclado, game.audio);
       

        vista3 = new PausaVista();
        
    }

    @Override
    public void show() {

        game.assets.load("Imagenes/Combate/categorias.png", Texture.class);
        game.assets.load("Imagenes/Combate/inventario.png", Texture.class);
        game.assets.finishLoading(); //obliga al juego a cargar todo antes de seguir

        vista2 = new InventarioVista(modelo2, game);
        Gdx.input.setInputProcessor(vista2.getStage());
    }

    @Override
    public void render(float delta) {
        super.render(delta); //limpia la pantalla
        vista2.render(delta); //dibuja la vista
    }
    
    @Override
    public void update(float delta) {
        controlador.update(delta);
        controlador2.update();
        
    }

    @Override
    public void draw(float delta) {
        vista.draw(spriteBatch);
        vista.dibujarInterfaz(spriteBatch);

        vista.dibujarSprite(spriteBatch);

        if (modelo.getOpcionActual() == Opciones.PAUSA){

            vista3.draw(spriteBatch);
        }
    }

    @Override
    public void input() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'input'");
    }
}
