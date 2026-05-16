package com.mortaTower.Vista;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mortaTower.Modelo.CombateModelo;
import static com.mortaTower.Screens.Screens.WORLD_HEIGHT;
import static com.mortaTower.Screens.Screens.WORLD_WIDTH;
import java.util.HashMap;
import java.util.Map;

public class CombateVista {

     private CombateModelo modelo;
    private ShapeRenderer sr;

    // ESTADOS VISUALES
    private static final int NORMAL = 1;
    private static final int SELECTED = 0;

    private Texture fondo, inventario, vidaHeroe, vidaEnemigo;
    private final Texture[] texturas;
    
    private int[] columnas;
    private int[] filas;

    // [objeto][estado][nivel]
    private TextureRegion[][][] sprites;

    //Fuente
    private BitmapFont fuente;

    //Texturas de enem y heroe.
    private Map<String, Texture> texturasEntidades = new HashMap<>();

    //Constructor
    public CombateVista(CombateModelo modelo) {
        this.modelo = modelo;
        sr = new ShapeRenderer();
        fuente = new BitmapFont();
        fuente.getData().setScale(1.5f);
        fuente.setColor(Color.WHITE);

        // Sprites
        fondo = new Texture("Imagenes/Combate/nivel1.png");
        //inventario =  new Texture("Imagenes/Combate/inventario.png");
        vidaHeroe = new Texture("Imagenes/Combate/vidaHeroe.png");
        vidaEnemigo = new Texture("Imagenes/Combate/vidaEnemigo.png");

        texturas = new Texture[] {
            new Texture("Imagenes/Combate/luchar.png"),
            new Texture("Imagenes/Combate/habilidades.png"),
            //new Texture("Imagenes/MenuInicio/op.png"),
        };

        columnas = new int[] {2,2};
        filas = new int[] {1,1};

        sprites = new TextureRegion[texturas.length][][];

        for (int i = 0; i < texturas.length; i++) {

            Texture tex = texturas[i];

            int cols = columnas[i];
            int rows = filas[i];

            int width = tex.getWidth() / cols;
            int height = tex.getHeight() / rows;

            sprites[i] = new TextureRegion[cols][rows];

            for (int estado = 0; estado < cols; estado++) {

                for (int nivel = 0; nivel < rows; nivel++) {

                    sprites[i][estado][nivel] = new TextureRegion(tex, estado * width, nivel * height, width, height);
                }
            }
        }
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

    public void draw(SpriteBatch batch) {

        float x = WORLD_WIDTH * 0.33f;
        float y = WORLD_HEIGHT * 0.60f;
        float separacion = WORLD_HEIGHT * 0.15f;

        float ancho = WORLD_WIDTH * 0.35f;
        float alto = WORLD_HEIGHT * 0.12f;

        // configuracion para botones de habilidad
        float anchoHab = 280f;
        float altoHab = 85f;
        float margen = 20f;
        float xInicialHab = (WORLD_WIDTH - (anchoHab * 4 + margen * 3)) / 2;
        float yHab = 160f;

        // configuracion para bortones de opciones
        float anchoOpt = 300f;
        float xOpt = (WORLD_WIDTH - anchoOpt) / 2;
        float yOpt = 50f;

        //int estadoOpt = (modelo.getOpcionActual() == CombateModelo.Opciones.PAUSA) ? NORMAL : SELECTED;

        batch.draw(fondo, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        batch.draw(vidaHeroe, 30, 50, 400, 200);
        batch.draw(vidaEnemigo, 860, 50, 400, 200);
        
    }
    public void dibujarInterfaz(SpriteBatch batch) {
        batch.end(); //pausa el batch para usar el ShapeRenderer

        sr.setProjectionMatrix(batch.getProjectionMatrix());
        sr.begin(ShapeRenderer.ShapeType.Filled);

        if (modelo.getHeroe() != null && modelo.getEnemigo() != null) {
            dibujarBarra(120, 132, modelo.getHeroe().getVidaActual(), modelo.getHeroe().getVidaMax(), Color.GREEN);
            dibujarBarra(120, 106, modelo.getHeroe().getManaActual(), modelo.getHeroe().getManaMax(), Color.BLUE);
            dibujarBarra(977, 136, modelo.getEnemigo().getVidaActual(), modelo.getEnemigo().getVidaMax(), Color.RED);
        }
        
        sr.end();
        batch. begin();
    }

    private void dibujarBarra(float x, float y, int actual, int max, Color color) {
        float ancho = 200f;
        float porcentaje = (float) actual / max;

        sr.setColor(Color.BLACK);
        sr.rect(x, y, ancho, 15);
        sr.setColor(color);
        sr.rect(x, y, ancho * porcentaje, 20);
    }

    public void dibujarSprite(SpriteBatch batch) {
        if (modelo.getHeroe() != null) {
            String rutaHeroe = modelo.getHeroe().getRutaImagenEstadoActual();
            Texture texHeroe = getTextureEntidad(rutaHeroe);
            if (texHeroe != null) {
                // Ajusta estas coordenadas (X, Y, Ancho, Alto) según el tamaño de pantalla
                batch.draw(texHeroe, WORLD_WIDTH * 0.15f, WORLD_HEIGHT * 0.40f, 150, 150);
            }
        }
        if (modelo.getEnemigo() != null) {
            String rutaEnemigo = modelo.getEnemigo().getRutaImagenEstadoActual();
            Texture texEnemigo = getTextureEntidad(rutaEnemigo);   
            if (texEnemigo != null) {
                // Ajusta las coordenadas para que quede del lado derecho
                batch.draw(texEnemigo, WORLD_WIDTH * 0.70f, WORLD_HEIGHT * 0.40f, 150, 150);
            }
        }
    }
}

