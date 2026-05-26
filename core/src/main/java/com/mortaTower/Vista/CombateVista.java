package com.mortaTower.Vista;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.mortaTower.Main;
import com.mortaTower.Modelo.CombateModelo;
import com.mortaTower.Modelo.Enemigo;
import com.mortaTower.Modelo.Entidad;
import com.mortaTower.Modelo.Habilidad;
import com.mortaTower.Modelo.Heroe;

import static com.mortaTower.Screens.Screens.WORLD_HEIGHT;
import static com.mortaTower.Screens.Screens.WORLD_WIDTH;

public class CombateVista {

    private CombateModelo modelo;
    private Stage stage;
    private Stack stack;

    private float ancho, x;
    private float alto, y;
    
    // TEXTURAS
    private ShapeRenderer sr;
    private Image imgFondoGeneral, stsHeroe, stsEnemigo;
    private Image imgInventario;
    private Texture fondo, statusEnemigo, statusHeroe;
    private Texture victoria, derrota;
    private Texture inventario, categorias, habilidad;

    // BOTONES
    private ImageButton btnAtaque, btnDefensa, btnCuracion, btnMana, btn;

    //Fuente
    private BitmapFont fuente, font;
    private GlyphLayout layout; 
    private Label lbl;

    // TABLAS
    private Table tabla, tablaHab;

    //Texturas de enem y heroe.
    private Map<String, Texture> texturasEntidades = new HashMap<>();

    private List<ImageButton> botonesHabilidades = new ArrayList<>();
    private List<Label> labelsHabilidades = new ArrayList<>();

    //ANIMACIONES
    private Map<Entidad.Estado, Animation<TextureRegion>> animacionesHeroe = new HashMap<>();
    private Map<Entidad.Estado, Animation<TextureRegion>> animacionesEnemigo = new HashMap<>();
    private TextureAtlas atlas;

    // CONSTRUCTOR
    public CombateVista(FitViewport viewport, CombateModelo modelo, Main game, int nivel) {

        stage = new Stage(viewport, game.batch);

        inventario = game.assets.get("Imagenes/Combate/inventario.png", Texture.class);
        categorias = game.assets.get("Imagenes/Combate/categorias.png", Texture.class);
        habilidad = game.assets.get("Imagenes/Combate/cuadroHabilidad.png", Texture.class);
        
        this.modelo = modelo;
        sr = new ShapeRenderer();
        fuente = new BitmapFont();
        layout = new GlyphLayout();
        fuente.getData().setScale(1.5f);
        fuente.setColor(Color.WHITE);

        // Imagenes
        fondo = game.assets.get("Imagenes/Combate/nivel" + nivel + ".png", Texture.class);
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
        
        inventario();

        victoria = game.assets.get("Imagenes/Combate/victoria.png", Texture.class);
        derrota = game.assets.get("Imagenes/Combate/derrota.png", Texture.class);
        cargarAnimaciones();
    }

    // INVENTARIO
    public void inventario(){
       
        imgInventario = new Image(inventario);
        float ancho = WORLD_WIDTH - 860;
        float alto = WORLD_HEIGHT - 380;

        imgInventario.setSize(ancho, alto);
        imgInventario.setPosition((WORLD_WIDTH - ancho) / 2f, 100);
        
        //creacion de botones
        btnAtaque = crearBoton(categorias, 0);
        btnDefensa = crearBoton(categorias, 1);
        btnCuracion = crearBoton(categorias, 2);
        btnMana = crearBoton(categorias, 3);
        
        // Layout
        tabla = new Table();
        //tabla.setDebug(true);

        // Posicion de tabla
        tabla.bottom().padBottom(200);
        tabla.left().padLeft(475);
        
        

        tabla.add(btnAtaque).width(50).height(50).pad(0).row();
        tabla.add(btnDefensa).width(50).height(50).pad(0).row();
        tabla.add(btnCuracion).width(50).height(50).pad(0).row();
        tabla.add(btnMana).width(50).height(50).pad(0).row();

        stage.addActor(imgInventario);
        stage.addActor(tabla);

        ocultarInventario();
    }

    // BOTONES QUE INCLUYEN NOMBRES DE HABILIDADES(segun el tipo)
    public void listaHabilidades(Habilidad[] tipo) {

        font = new BitmapFont();

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        style.fontColor = Color.WHITE;

        if (tablaHab != null) {
            tablaHab.remove();
            tablaHab.clear();
        }

        tablaHab = new Table();
        tablaHab.left().padLeft(550);
        tablaHab.defaults().space(0);
        tablaHab.bottom().padBottom(360);

        botonesHabilidades.clear();
        labelsHabilidades.clear();

        for (int i = 0; i < tipo.length; i++) {

            final int index = i;

            String nombre = tipo[i].getNombre();
            btn = crearBotonHab(habilidad);

            lbl = new Label(nombre, style);
            lbl.setAlignment(Align.center);
            lbl.setTouchable(Touchable.disabled);

            stack = new Stack();
            stack.add(btn);
            stack.add(lbl);

            botonesHabilidades.add(btn);
            labelsHabilidades.add(lbl);

            tablaHab.add(stack).width(290).height(50).row();
        }

        stage.addActor(tablaHab);
    }


    public void mostrarInventario() {
        imgInventario.setVisible(true);
        tabla.setVisible(true);
    }

    public void ocultarInventario() {
        imgInventario.setVisible(false);
        tabla.setVisible(false);
    }

    // PARA CATEGORIAS
    private ImageButton crearBoton(Texture textura, int fila) {

        int ancho = textura.getWidth() / 2;
        int alto = textura.getHeight() / 4;

        int y = fila * alto;

        TextureRegion normal = new TextureRegion(textura, 0, y, ancho, alto);

        TextureRegion seleccionado =new TextureRegion(textura, ancho, y, ancho, alto);

        ImageButton.ImageButtonStyle style =new ImageButton.ImageButtonStyle();

        style.imageUp = new TextureRegionDrawable(normal);
        style.imageOver = new TextureRegionDrawable(seleccionado);

        return new ImageButton(style);
    }

    // PARA HABILIDADES
    private ImageButton crearBotonHab(Texture textura) {

        int ancho = textura.getWidth() / 2;
        int alto = textura.getHeight();

        TextureRegion normal = new TextureRegion(textura, 0, 0, ancho, alto);

        TextureRegion seleccionado = new TextureRegion(textura, ancho, 0, ancho, alto);

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();

        style.imageUp = new TextureRegionDrawable(normal);
        style.imageOver = new TextureRegionDrawable(seleccionado);

        return new ImageButton(style);
    }

    // PARA LAS ENTIDADES
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

    // COMENTA LA HABILIDAD EJECUTADA (de una entidad)
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
    
    // IMAGEN VICTORIA/DERROTA AL FINALIZAR LA PARTIDA
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

    // BARRA DE VIDA/MANA
    public void dibujarInterfaz(SpriteBatch batch) {

        sr.setProjectionMatrix(batch.getProjectionMatrix());
        sr.begin(ShapeRenderer.ShapeType.Filled);

        if (modelo.getHeroe() != null && modelo.getEnemigo() != null) {
            dibujarBarra(120, 132, modelo.getHeroe().getVidaActual(), modelo.getHeroe().getVidaMax(), Color.GREEN);
            dibujarBarra(120, 106, modelo.getHeroe().getManaActual(), modelo.getHeroe().getManaMax(), Color.BLUE);
            dibujarBarra(977, 136, modelo.getEnemigo().getVidaActual(), modelo.getEnemigo().getVidaMax(), Color.RED);
            dibujarBarra(977, 110, modelo.getEnemigo().getManaActual(), modelo.getEnemigo().getManaMax(), Color.BLUE);
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
        Heroe heroe = modelo.getHeroe();
        Enemigo enemigo = modelo.getEnemigo();
        fuente.setColor(Color.WHITE);

        if (heroe != null) {
           Entidad.Estado estadoHeroe = heroe.getEstadoActual();

           Animation <TextureRegion> animacion = animacionesHeroe.get(estadoHeroe);
            
            if (animacion != null) {
                TextureRegion frame = animacion.getKeyFrame(heroe.getStatetime(), true);
                batch.draw(frame, WORLD_WIDTH * 0.15f, WORLD_HEIGHT * 0.40f, 150, 150);
                String nombreHeroe = heroe.getNombre();
                fuente.draw(batch, nombreHeroe, 157, 188);
            }
        }
        if (enemigo != null) {
            Animation <TextureRegion> animacion = animacionesEnemigo.get(enemigo.getEstadoActual());
            
            if (animacion != null) {
                TextureRegion frame = animacion.getKeyFrame(enemigo.getStatetime(), true);
                batch.draw(frame, WORLD_WIDTH * 0.65f, WORLD_HEIGHT * 0.40f, 150, 150);
                String nombreEnemigo = enemigo.getNombre();
                fuente.draw(batch, nombreEnemigo, 957, 188);
            }
           }
    }

private void cargarAnimaciones() {
    // 1. Héroe Parado
    Array<TextureRegion> framesParadoH = new Array<>();
    for (int i = 1; i <= 2; i++) { 
        String ruta = "Imagenes/Personajes/Heroes/Caballero/CaballeroParado" + i + ".png";
        Texture textura = new Texture(Gdx.files.internal(ruta));
        framesParadoH.add(new TextureRegion(textura));
    }
    animacionesHeroe.put(Entidad.Estado.PARADO, new Animation<>(0.35f, framesParadoH, Animation.PlayMode.LOOP));
    
    // 2. Héroe Ataque
    Array<TextureRegion> framesAtaqueH = new Array<>();
    for (int i = 1; i <= 3; i++) { 
        String ruta = "Imagenes/Personajes/Heroes/Caballero/CaballeroAtaque" + i + ".png";
        Texture textura = new Texture(Gdx.files.internal(ruta));
        framesAtaqueH.add(new TextureRegion(textura));
    }
    animacionesHeroe.put(Entidad.Estado.ATAQUE, new Animation<>(0.25f, framesAtaqueH, Animation.PlayMode.NORMAL));

    // 3. Héroe Daño (¡Corregido con su propio Array!)
    Array<TextureRegion> framesDanioH = new Array<>();
    for (int i = 1; i <= 3; i++) {
        String ruta = "Imagenes/Personajes/Heroes/Caballero/CaballeroDano" + i + ".png";
        Texture textura = new Texture(Gdx.files.internal(ruta));
        framesDanioH.add(new TextureRegion(textura));
    }
    animacionesHeroe.put(Entidad.Estado.DANIO, new Animation<>(0.30f, framesDanioH, Animation.PlayMode.NORMAL));
    // 1. Enemigo Parado (Limpio y separado)
    Array<TextureRegion> framesParadoE = new Array<>();
    for (int i = 1; i <= 1; i++) {
        String ruta = "Imagenes/Personajes/Enemigo/Zombie/ZombieParado" + i + ".png";
        Texture textura = new Texture(Gdx.files.internal(ruta));
        framesParadoE.add(new TextureRegion(textura));
    }
    animacionesEnemigo.put(Entidad.Estado.PARADO, new Animation<>(0.55f, framesParadoE, Animation.PlayMode.LOOP));

    // 2. Enemigo Ataque
    Array<TextureRegion> framesAtaqueE = new Array<>();
    for (int i = 1; i <= 2; i++) {
        String ruta = "Imagenes/Personajes/Enemigo/Zombie/ZombieAtaque" + i + ".png";
        Texture textura = new Texture(Gdx.files.internal(ruta));
        framesAtaqueE.add(new TextureRegion(textura));
    }
    animacionesEnemigo.put(Entidad.Estado.ATAQUE, new Animation<>(0.60f, framesAtaqueE, Animation.PlayMode.NORMAL));

    // 3. Enemigo Daño (¡Corregido con su propio Array!)
    Array<TextureRegion> framesDanioE = new Array<>();
    for (int i = 1; i <= 2; i++) {
        String ruta = "Imagenes/Personajes/Enemigo/Zombie/ZombieDano" + i + ".png";
        Texture textura = new Texture(Gdx.files.internal(ruta));
        framesDanioE.add(new TextureRegion(textura));
    }
    animacionesEnemigo.put(Entidad.Estado.DANIO, new Animation<>(0.60f, framesDanioE, Animation.PlayMode.NORMAL));
}

    public void cerrar() {
        stage.dispose();
    }

    // GETTERS
    public Stage getStage() {return stage;}
    public float getTiempoAnimacionHeroe(Entidad.Estado estado){
        Animation<TextureRegion> animacion = animacionesHeroe.get(estado);
        if (animacion != null) {
            return animacion.getAnimationDuration(); // Devuelve la duración de la animación
        }
        return 1.0f;
    }
    public float getTiempoAnimacionEnemigo(Entidad.Estado estado){
        Animation<TextureRegion> animacion = animacionesEnemigo.get(estado);
        if (animacion != null) {
            return animacion.getAnimationDuration(); // Devuelve la duración de la animación
        }
        return 1.0f;
    }
    
    public ImageButton getBtnAtaque() { return btnAtaque; }
    public ImageButton getBtnDefensa() { return btnDefensa; }
    public ImageButton getBtnCuracion() { return btnCuracion; }
    public ImageButton getBtnMana() { return btnMana; }

    public ImageButton getBotonHabilidad(int index) {return botonesHabilidades.get(index);}   
    public int getCantidadHabilidades() {return botonesHabilidades.size();}
}