package com.mortaTower.Vista;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.mortaTower.Main;
import com.mortaTower.Modelo.CombateModelo;

import static com.mortaTower.Screens.Screens.WORLD_HEIGHT;
import static com.mortaTower.Screens.Screens.WORLD_WIDTH;

public class CombateVista {

    private Stage stage;

    private float ancho;
    private float alto;

    private float x;
    private float y;
    
    private CombateModelo modelo;
    private ShapeRenderer sr;

    private Texture fondo, statusEnemigo, statusHeroe;
    private Texture victoria, derrota;

    private Image imgFondoGeneral, stsHeroe, stsEnemigo;


    //Fuente
    private BitmapFont fuente, nombreHeroe;
    private GlyphLayout layout; 

    //Texturas de enem y heroe.
    private Map<String, Texture> texturasEntidades = new HashMap<>();

    //Constructor
    public CombateVista(FitViewport viewport, CombateModelo modelo, Main game) {

        stage = new Stage(viewport, game.batch);

        this.modelo = modelo;
        sr = new ShapeRenderer();
        fuente = new BitmapFont();
        layout = new GlyphLayout();
        fuente.getData().setScale(1.5f);
        fuente.setColor(Color.WHITE);

        // Imagenes
        fondo = game.assets.get("Imagenes/Combate/nivel1.png", Texture.class);
        imgFondoGeneral = new Image(fondo);
        imgFondoGeneral.setPosition(0, 0);
        imgFondoGeneral.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        stage.addActor(imgFondoGeneral); 


        statusHeroe = game.assets.get("Imagenes/Combate/vidaHeroe.png", Texture.class);
        stsHeroe = new Image(statusHeroe);
        stsHeroe.setSize(400, 200);
        stsHeroe.setPosition(30, 50);
        stage.addActor(stsHeroe); 

        statusEnemigo = game.assets.get("Imagenes/Combate/vidaEnemigo.png", Texture.class);
        stsEnemigo = new Image(statusEnemigo);
        stsEnemigo.setSize(400, 200);
        stsEnemigo.setPosition( 860, 50);
        stage.addActor(stsEnemigo); 

        victoria = game.assets.get("Imagenes/Combate/victoria.png", Texture.class);
        derrota = game.assets.get("Imagenes/Combate/derrota.png", Texture.class);

    }

   private Texture getTextureEntidad(String ruta) {

        if (ruta == null || ruta.isEmpty()) return null;
            String rutaCorregida = ruta;
        if (ruta.startsWith("assets/")) {
            rutaCorregida = ruta.substring(7);
        }
        if (!Gdx.files.internal(rutaCorregida).exists()) {
            System.err.println("ERROR: No se encontró la imagen en: " + rutaCorregida);
            return null; 
        }
        if (!texturasEntidades.containsKey(rutaCorregida)) {
            texturasEntidades.put(rutaCorregida, new Texture(rutaCorregida));
            System.out.println("Sprite cargado exitosamente: " + rutaCorregida);
        }
        return texturasEntidades.get(rutaCorregida);
    }

    public void comentarista(SpriteBatch batch){
    
        String mensaje = modelo.getMensajeCombate();
        if (mensaje != null && !mensaje.isEmpty()) {
            fuente.setColor(Color.WHITE);
            layout.setText(fuente, mensaje);

            float xMensaje = (WORLD_WIDTH - layout.width) / 2f;
            float yMensaje = WORLD_HEIGHT * 0.90f;
            fuente.draw(batch, mensaje, xMensaje, yMensaje);
        }

    }
    
    public void resultado(SpriteBatch batch) {

        ancho = WORLD_WIDTH * 0.5f;
        alto = WORLD_HEIGHT * 0.3f;

        x = (WORLD_WIDTH - ancho) / 2f;
        y = (WORLD_HEIGHT - alto) / 2f + WORLD_HEIGHT * 0.25f;

        if (modelo.getResultado() == CombateModelo.Resultado.VICTORIA) {
            batch.draw(victoria, x, y, ancho, alto);
        } else if (modelo.getResultado() == CombateModelo.Resultado.DERROTA) {
            batch.draw(derrota, x, y, ancho, alto);
        }
    }

    public void dibujarInterfaz(SpriteBatch batch) {

        sr.setProjectionMatrix(batch.getProjectionMatrix());
        sr.begin(ShapeRenderer.ShapeType.Filled);

        if (modelo.getHeroe() != null && modelo.getEnemigo() != null) {
            dibujarBarra(120, 132, modelo.getHeroe().getVidaActual(), modelo.getHeroe().getVidaMax(), Color.GREEN);
            dibujarBarra(120, 106, modelo.getHeroe().getManaActual(), modelo.getHeroe().getManaMax(), Color.BLUE);
            dibujarBarra(977, 136, modelo.getEnemigo().getVidaActual(), modelo.getEnemigo().getVidaMax(), Color.RED);
            dibujarBarra(977, 110, modelo.getEnemigo().getManaActual(), modelo.getEnemigo().getVidaMax(), Color.BLUE);
        }
        
        sr.end();
    }

    private void dibujarBarra(float x, float y, int actual, int max, Color color) {
        
        ancho = 200f;
        float porcentaje = (float) actual / max;

        sr.setColor(Color.BLACK);
        sr.rect(x, y, ancho, 15);
        sr.setColor(color);
        sr.rect(x, y, ancho * porcentaje, 20);
    }

    public void dibujarSprite(SpriteBatch batch) {
        
        fuente.setColor(Color.WHITE);

        if (modelo.getHeroe() != null) {
            String rutaHeroe = modelo.getHeroe().getRutaImagenEstadoActual();
            Texture texHeroe = getTextureEntidad(rutaHeroe);
            String nombreHeroe = modelo.getHeroe().getNombre();
            if (texHeroe != null) {
                // Ajusta estas coordenadas (X, Y, Ancho, Alto) según el tamaño de pantalla
                batch.draw(texHeroe, WORLD_WIDTH * 0.15f, WORLD_HEIGHT * 0.40f, 150, 150);
                fuente.draw(batch, nombreHeroe, 120, 180);
            }
        }
        if (modelo.getEnemigo() != null) {
            String rutaEnemigo = modelo.getEnemigo().getRutaImagenEstadoActual();
            Texture texEnemigo = getTextureEntidad(rutaEnemigo);   
            String nombreEnemigo = modelo.getEnemigo().getNombre();
            if (texEnemigo != null) {
                // Ajusta las coordenadas para que quede del lado derecho
                batch.draw(texEnemigo, WORLD_WIDTH * 0.70f, WORLD_HEIGHT * 0.40f, 150, 150);
                fuente.draw(batch, nombreEnemigo, 977, 188);
            }
        }
        
    }

    public void cerrar() {
        stage.dispose();
    }

    // GETTERS
    public Stage getStage() {return stage;}
}

