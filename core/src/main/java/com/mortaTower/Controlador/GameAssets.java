package com.mortaTower.Controlador;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class GameAssets {
    
    public static Texture btnCargar;
    public static Texture btnJugar;
    public static Texture fondoMenu;

    public static void load(AssetManager manager) {

        // Para Botones
        manager.load("Imagenes/MenuInicio/opciones.png", Texture.class);
        manager.load("Imagenes/MenuInicio/renudar.png", Texture.class);
        manager.load("Imagenes/MenuInicio/nueva.png", Texture.class);
        manager.load("Imagenes/MenuInicio/salir.png", Texture.class);
        manager.load("Imagenes/MenuInicio/cargar.png", Texture.class);

        manager.load("Imagenes/Opciones/pausa.png", Texture.class);
        manager.load("Imagenes/Opciones/btnMas.png", Texture.class);
        manager.load("Imagenes/Opciones/btnMenos.png", Texture.class);
        manager.load("Imagenes/Opciones/atras.png", Texture.class);

        manager.load("Imagenes/SeccionRecompensa/barraRemplazo.png", Texture.class);
        manager.load("Imagenes/SeccionRecompensa/cuadroHabilidad.png", Texture.class);   

        manager.load("Imagenes/SeleccionPersonaje/atras.png", Texture.class);
        manager.load("Imagenes/SeleccionPersonaje/iniciarPartida.png", Texture.class);
        manager.load("Imagenes/SeleccionPersonaje/seleccionCaballero.png", Texture.class);
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
        manager.load("Imagenes/Combate/nivel5.png", Texture.class);

        manager.load("Imagenes/CargarPartida/Fondo1.png", Texture.class);

        // Para Cuadros y otros
        manager.load("Imagenes/SeleccionPersonaje/nombrePersonaje.png", Texture.class);

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
}