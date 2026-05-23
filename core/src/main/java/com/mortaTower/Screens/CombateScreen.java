package com.mortaTower.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mortaTower.Controlador.CombateControlador;
import com.mortaTower.Main;
import com.mortaTower.Modelo.CombateModelo;
import com.mortaTower.Modelo.Heroe;

import com.mortaTower.Modelo.CombateModelo.Opciones;
import com.mortaTower.Strategy.ComportamientoAgresivo;
import com.mortaTower.Vista.CombateVista;
import com.mortaTower.Vista.InventarioVista;
import com.mortaTower.Vista.PausaVista;
import com.mortaTower.Vista.RecompensasVista;

public class CombateScreen extends Screens {

    private CombateModelo modelo;
    private CombateVista vista;
    private CombateControlador controlador;

    private RecompensasVista vistaRecompensa;
    private InventarioVista vistaInventario;
    private PausaVista vistaPausa;

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
        
        controlador = new CombateControlador(modelo, game.teclado, game.audio);
       
        
    }

    @Override
    public void show() {

        // INVENTARIO
        game.assets.load("Imagenes/Combate/categorias.png", Texture.class);
        game.assets.load("Imagenes/Combate/inventario.png", Texture.class);

        // PANEL STATUS
        game.assets.load("Imagenes/Combate/vidaHeroe.png", Texture.class);
        game.assets.load("Imagenes/Combate/vidaEnemigo.png", Texture.class);

        // PANEL VICTORIA/DERROTA
        game.assets.load("Imagenes/Combate/victoria.png", Texture.class);
        game.assets.load("Imagenes/Combate/derrota.png", Texture.class);

        // NIVELES TORRES
        game.assets.load("Imagenes/Combate/nivel1.png", Texture.class);
        game.assets.load("Imagenes/Combate/nivel2.png", Texture.class);
        game.assets.load("Imagenes/Combate/nivel3.png", Texture.class);
        game.assets.load("Imagenes/Combate/nivel4.png", Texture.class);
        game.assets.load("Imagenes/Combate/nivel5.png", Texture.class);

        // RECOMPENSA
        game.assets.load("Imagenes/black.png", Texture.class);
        game.assets.load("Imagenes/SeccionRecompensa/cuadroHabilidad.png", Texture.class);
        game.assets.load("Imagenes/SeccionRecompensa/tipoHabilidad.png", Texture.class);

        //obliga al juego a cargar todo antes de seguir
        game.assets.finishLoading(); 

        vista = new CombateVista(viewport, modelo, game);
        Gdx.input.setInputProcessor(vista.getStage());

        /*vista4 = new RecompensasVista(game);
        Gdx.input.setInputProcessor(vista4.getStage());*/
        
        //vista2 = new InventarioVista(viewport, game);
        //Gdx.input.setInputProcessor(vista2.getStage());

        //vista3 = new PausaVista(game);
        //Gdx.input.setInputProcessor(vista3.getStage());
    }

    @Override

    public void render(float delta) {

        super.render(delta);

        controlador.update(delta);

        vista.getStage().act(delta);
        vista.getStage().draw();

        spriteBatch.begin();

        vista.dibujarSprite(spriteBatch);
        vista.comentarista(spriteBatch);
        vista.resultado(spriteBatch);

        spriteBatch.end();

        vista.dibujarInterfaz(spriteBatch);

        
    }

    @Override
    public void dispose() {
        super.dispose();
        vista.cerrar();
    }
    
    @Override
    public void update(float delta) {}
    @Override
    public void draw(float delta) {}
}
