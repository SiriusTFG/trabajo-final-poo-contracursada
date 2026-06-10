package com.mortaTower.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class GameAssets {

    public static BitmapFont font;
    public static BitmapFont fontStats;
    public static BitmapFont fuenteMedieval, fuenteMedievalChico;

    public static void load(AssetManager manager) {

        // Para Botones
        manager.load("Imagenes/MenuInicio/opciones.png", Texture.class);
        manager.load("Imagenes/MenuInicio/renudar.png", Texture.class);
        manager.load("Imagenes/MenuInicio/nuevaPartida.png", Texture.class);
        manager.load("Imagenes/MenuInicio/salirDelJuego.png", Texture.class);
        manager.load("Imagenes/MenuInicio/cargarPartida.png", Texture.class);
        manager.load("Imagenes/MenuInicio/reintentar.png", Texture.class);
        manager.load("Imagenes/MenuInicio/menuPrincipal.png", Texture.class);

        manager.load("Imagenes/Opciones/pausa.png", Texture.class);
        manager.load("Imagenes/Opciones/btnMas.png", Texture.class);
        manager.load("Imagenes/Opciones/btnMenos.png", Texture.class);
        manager.load("Imagenes/Opciones/atras.png", Texture.class);

        manager.load("Imagenes/SeccionRecompensa/barraRemplazo.png", Texture.class);
        manager.load("Imagenes/SeccionRecompensa/cuadroHabilidad.png", Texture.class);
        manager.load("Imagenes/SeccionRecompensa/cancelar.png", Texture.class);  
        manager.load("Imagenes/SeccionRecompensa/confirmar.png", Texture.class);     

        manager.load("Imagenes/SeleccionPersonaje/atras.png", Texture.class);
        manager.load("Imagenes/SeleccionPersonaje/iniciarPartida.png", Texture.class);
        manager.load("Imagenes/SeleccionPersonaje/seleccion.png", Texture.class);
        manager.load("Imagenes/SeleccionPersonaje/seleccionMago.png", Texture.class);

        manager.load("Imagenes/Combate/cuadroHabilidad.png", Texture.class);

        manager.load("Imagenes/CargarPartida/boton.png", Texture.class);
        manager.load("Imagenes/CargarPartida/boton1.png", Texture.class);
        manager.load("Imagenes/CargarPartida/cancelarBoton.png", Texture.class);
        manager.load("Imagenes/CargarPartida/cancelarBoton1.png", Texture.class);
        manager.load("Imagenes/CargarPartida/eliminarBoton.png", Texture.class);
        manager.load("Imagenes/CargarPartida/eliminarBoton1.png", Texture.class);
        manager.load("Imagenes/CargarPartida/IrPartidasGuardadas.png", Texture.class);
        manager.load("Imagenes/CargarPartida/IrPartidasGuardadas1.png", Texture.class);
        manager.load("Imagenes/CargarPartida/calaberaBorrar.png", Texture.class);
        manager.load("Imagenes/CargarPartida/calaberaBorrar1.png", Texture.class);

        // Para Escenarios
        manager.load("Imagenes/black.png", Texture.class);

        manager.load("Imagenes/MenuInicio/Fondo.png", Texture.class);
        manager.load("Imagenes/SeleccionPersonaje/seleccionPersonaje.png", Texture.class);

        manager.load("Imagenes/Combate/nivel1.png", Texture.class);
        manager.load("Imagenes/Combate/nivel2.png", Texture.class);
        manager.load("Imagenes/Combate/nivel3.png", Texture.class);
        manager.load("Imagenes/Combate/nivel4.png", Texture.class);
        manager.load("Imagenes/Combate/nivel5.png", Texture.class);

        manager.load("Imagenes/CargarPartida/Fondo1.png", Texture.class);
        manager.load("Imagenes/Creditos/mortalTower.png", Texture.class);

        // Para Cuadros y otros
        manager.load("Imagenes/SeleccionPersonaje/nombrePersonaje.png", Texture.class);

        manager.load("Imagenes/SeccionRecompensa/cuadroRecompensa.png", Texture.class); 
        manager.load("Imagenes/SeccionRecompensa/cuadroRemplazo.png", Texture.class);   
        manager.load("Imagenes/SeccionRecompensa/tipoHabilidad.png", Texture.class); 

        manager.load("Imagenes/Opciones/menuOpciones.png", Texture.class);
        manager.load("Imagenes/Opciones/volEfectos.png", Texture.class);
        manager.load("Imagenes/Opciones/volMusica.png", Texture.class);

        manager.load("Imagenes/Combate/categorias.png", Texture.class);
        manager.load("Imagenes/Combate/derrota.png", Texture.class);
        manager.load("Imagenes/Combate/victoria.png", Texture.class);
        manager.load("Imagenes/Combate/inventario2.png", Texture.class);
        manager.load("Imagenes/Combate/vidaEnemigo.png", Texture.class);
        manager.load("Imagenes/Combate/vidaHeroe.png", Texture.class);

        manager.load("Imagenes/CargarPartida/CuadroEliminar.png", Texture.class);
        manager.load("Imagenes/CargarPartida/menuSinespacio.png", Texture.class);
    }

    public static void crearFuentes() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Jersey10-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();

        p.size = 30;
        fuenteMedieval = generator.generateFont(p);

        p.size = 18;
        fuenteMedievalChico = generator.generateFont(p);

        generator.dispose();

        // Fuente default de LibGDX
        font = new BitmapFont();
        font.getData().setScale(1.5f);
        font.setColor(Color.WHITE);

        // Otra fuente default
        fontStats = new BitmapFont();
        fontStats.getData().setScale(1.0f);
        fontStats.setColor(Color.WHITE);
    }
}