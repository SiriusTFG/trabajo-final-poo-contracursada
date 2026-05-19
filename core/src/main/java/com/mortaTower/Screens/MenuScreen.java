package com.mortaTower.Screens;

import com.mortaTower.Controlador.Audio;
import com.mortaTower.Controlador.Teclado;
import com.badlogic.gdx.graphics.Texture;
import com.mortaTower.Main;
import com.mortaTower.Modelo.MenuModelo;
import com.mortaTower.Modelo.MenuModelo.Estado;
import com.mortaTower.Modelo.OpcionesModelo.EstadoEnum;
import com.mortaTower.Modelo.OpcionesModelo;
import com.mortaTower.Vista.MenuVista;
import com.mortaTower.Vista.OpcionesVista;


public class MenuScreen extends Screens {

    private boolean loaded;

    private Teclado teclado;
    private Audio audio;

    private final MenuModelo modelo;
    private final MenuVista vista;

    private OpcionesModelo opcionModelo;
    private OpcionesVista opcionesVista;

    public MenuScreen(Main game) {

        super(game);

        this.teclado = game.teclado;
        this.audio = game.audio;

        this.modelo = new MenuModelo();
        this.vista = new MenuVista(modelo);

        this.opcionModelo = new OpcionesModelo();
        this.opcionesVista = new OpcionesVista(opcionModelo);

        audio.loop(0);
    }

    @Override
    public void show() {

        loaded = false;

        game.assets.load("Imagenes/MenuInicio/Fondo.png", Texture.class);
        game.assets.load("Imagenes/MenuInicio/nueva.png", Texture.class);
        game.assets.load("Imagenes/MenuInicio/cargar.png", Texture.class);
        game.assets.load("Imagenes/MenuInicio/opciones.png", Texture.class);
        game.assets.load("Imagenes/MenuInicio/salir.png", Texture.class);
    }

    @Override
    public void update(float delta) {

        input();
        
        if (!loaded) {

            if (game.assets.update()) {

                loaded = true;

                vista.init(game.assets);
            }

            return;
        }
        
    }

    @Override
    public void draw(float delta) {

        if (!loaded) {
            return;
        }

       // menu principal
        vista.draw(spriteBatch);

        // overlay
        if(modelo.getEstadoActual() != Estado.MENU) {
            //vista.draw(spriteBatch);
            opcionesVista.draw(spriteBatch);
        }
    }

    @Override
    public void input(){

        teclado.update();
        
        if(teclado.upPressed){
            audio.play(0);
            if (modelo.getEstadoActual() == Estado.MENU){
                modelo.arriba();
                
            }else{

                opcionModelo.arriba();
            }
        }

        if (teclado.downPressed){
            audio.play(0);
            if (modelo.getEstadoActual() == Estado.MENU){
                modelo.abajo();
                
            }else{

                opcionModelo.abajo();
            }
        }

        if (teclado.leftPressed && modelo.getEstadoActual() == Estado.OPCIONES){

            opcionModelo.izquierda();
            audio.setVolumenMusica(opcionModelo.getVolMusica() / 10f);
            audio.setVolumenFx(opcionModelo.getVolFx() / 10f);
        }

        if (teclado.rightPressed && modelo.getEstadoActual() == Estado.OPCIONES){

            opcionModelo.derecha();
            audio.setVolumenMusica(opcionModelo.getVolMusica() / 10f);
            audio.setVolumenFx(opcionModelo.getVolFx() / 10f);
        }
        
        if (teclado.selectPressed) {

            if (modelo.getEstadoActual() == Estado.MENU){
            switch (modelo.getOpcionActual()) {

                case JUGAR -> {   
                    game.setScreen(new TransicionScreen( game,this, new SeleccionScreen(game)));
                    audio.play(3); // confirm
                    audio.stop(0);
                }

                case OPCIONES -> { 
                    modelo.setEstadoActual(Estado.OPCIONES);
                    audio.play(5);
                }

                case CARGAR -> {
                    game.setScreen(new TransicionScreen(game, this, new CargarScreen(game, this))); 
                    audio.play(2);
                }
                
                case SALIR -> System.exit(0);

            }
            }else{
                audio.play(5);
                modelo.setEstadoActual(Estado.CONTROLES);
                opcionModelo.aceptar();
            }
        }

        if (teclado.backPressed){
            
            if (modelo.getEstadoActual() == Estado.OPCIONES){
                audio.play(4);
                modelo.setEstadoActual(Estado.MENU);
            }
            
            if (modelo.getEstadoActual() == Estado.CONTROLES){
                audio.play(4);
                modelo.setEstadoActual(Estado.OPCIONES);
                opcionModelo.atras();
                
            }
            
        }

    }
}