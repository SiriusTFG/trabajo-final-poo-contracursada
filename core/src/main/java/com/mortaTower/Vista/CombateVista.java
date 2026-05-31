package com.mortaTower.Vista;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mortaTower.Main;
import com.mortaTower.Modelo.Entidad;
import com.mortaTower.Modelo.Goblin;
<<<<<<< Updated upstream
=======
import com.mortaTower.Modelo.Habilidad;
import com.mortaTower.Modelo.Heroe;
>>>>>>> Stashed changes
import static com.mortaTower.Screens.Screens.WORLD_HEIGHT;
import static com.mortaTower.Screens.Screens.WORLD_WIDTH;

public class CombateVista {

    private final Stage stage;

    private float ancho, x;
    private float alto, y;
    
    // TEXTURAS
    private Image imgFondoGeneral, stsHeroe, stsEnemigo;
    private Texture fondo, statusEnemigo, statusHeroe;
    
    private GlyphLayout layout; 
    private ImageButton btnPausa;

    private List<ImageButton> botonesHabilidades = new ArrayList<>();

    //ANIMACION GOBLIN
    private Map<Goblin.EstadoGoblin, Animation<TextureRegion>> animacionesGoblin = new HashMap<>();

    // UI
    private Table tabla;
    private Image imgInventario;

    // Assets
    private Texture inventarioTex;
    private Texture categoriasTex;
    private Texture habilidadTex;
    private Label lblDescripcion;

    // Render
    private BitmapFont font;
    private ShapeRenderer sr;

    // Animaciones
    private final Map<Entidad.Estado, Animation<TextureRegion>> animHeroe = new HashMap<>();
    private final Map<Entidad.Estado, Animation<TextureRegion>> animEnemigo = new HashMap<>();

    // Resultado
    private Texture victoria;
    private Texture derrota;

    // CONSTRUCTOR
<<<<<<< Updated upstream
    public CombateVista(FitViewport viewport, Main game, int nivel) {
=======
    public CombateVista(FitViewport viewport, CombateModelo modelo, Main game, int nivel, List<Habilidad> hab) {
>>>>>>> Stashed changes

        //this.modelo = modelo;
        this.stage = new Stage(viewport, game.batch);

        // fondo nivel
        fondo = game.assets.get("Imagenes/Combate/nivel" + nivel + ".png", Texture.class);
        imgFondoGeneral = new Image(fondo);
        imgFondoGeneral.setPosition(0, 0);
        imgFondoGeneral.setSize(WORLD_WIDTH, WORLD_HEIGHT);

        // Boton Pausa
        Texture texBoton = game.assets.get("Imagenes/Opciones/pausa.png", Texture.class);
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(texBoton));
        btnPausa = new ImageButton(drawable);
        btnPausa.setSize(100, 100);
        btnPausa.setPosition(0, 620);

        // cuadro stats del Heroe
        statusHeroe = game.assets.get("Imagenes/Combate/vidaHeroe.png", Texture.class);
        stsHeroe = new Image(statusHeroe);
        stsHeroe.setSize(400, 200);
        stsHeroe.setPosition(30, 50);

        // cuadro stats del Enemigo
        statusEnemigo = game.assets.get("Imagenes/Combate/vidaEnemigo.png", Texture.class);
        stsEnemigo = new Image(statusEnemigo);
        stsEnemigo.setSize(400, 200);
        stsEnemigo.setPosition( 860, 50);

        // inventario
        inventarioTex = game.assets.get("Imagenes/Combate/inventario2.png", Texture.class);
        categoriasTex = game.assets.get("Imagenes/Combate/categorias.png", Texture.class);
        habilidadTex = game.assets.get("Imagenes/Combate/cuadroHabilidad.png", Texture.class);

        // resultado combate
        victoria = game.assets.get("Imagenes/Combate/victoria.png", Texture.class);
        derrota = game.assets.get("Imagenes/Combate/derrota.png", Texture.class);
        
        sr = new ShapeRenderer();
        font = new BitmapFont();
        layout = new GlyphLayout();
        font.getData().setScale(1.5f);
        font.setColor(Color.WHITE);

        lblDescripcion = new Label("", new Label.LabelStyle(font, Color.YELLOW));
        lblDescripcion.setAlignment(Align.center);
        lblDescripcion.setPosition(WORLD_WIDTH / 2f - 150, WORLD_HEIGHT * 0.28f); //y
        lblDescripcion.setSize(150, 50);
        lblDescripcion.setWrap(true);

        stage.addActor(imgFondoGeneral); 
        stage.addActor(btnPausa);
        stage.addActor(stsHeroe); 
        stage.addActor(stsEnemigo); 
        crearUIInventario();
        stage.addActor(lblDescripcion);
        cargarAnimaciones();
    }

    private void crearUIInventario() {

        imgInventario = new Image(inventarioTex);

        float w = WORLD_WIDTH - 780;
        float h = WORLD_HEIGHT - 280;

        imgInventario.setSize(w, h);
        imgInventario.setPosition((WORLD_WIDTH - w) / 2f, 100);

        tabla = new Table();
        tabla.left().padLeft(495);
        tabla.bottom().padBottom(292);
        tabla.defaults().pad(2);

        stage.addActor(imgInventario);
        stage.addActor(tabla);
    }

    public void cargarInventarioHabilidades(String[] nombresHabilidades, String[] tipoHabilidades) {
        tabla.clearChildren();
        tabla.setPosition(100, 0);
        botonesHabilidades.clear();
        //tabla.debug();

        for (int i = 0; i < 4; i++) {
            String nombre = nombresHabilidades[i];
            String tipo = tipoHabilidades[i];

            ImageButton btn = crearBotonHab(habilidadTex);
            botonesHabilidades.add(btn);

            Label lbl = new Label(nombre, new Label.LabelStyle(font, Color.WHITE));
            lbl.setAlignment(Align.center);
            lbl.setTouchable(Touchable.disabled);

            Stack stack = new Stack();
            stack.add(btn);
            stack.add(lbl);

            Image iconoCat = crearIconoTipo(tipo);

            Table fila = new Table();
            if (iconoCat != null) {
                fila.add(iconoCat).size(40, 40).padRight(5);
            }
            fila.add(stack).size(200, 40);

            tabla.add(fila).size(100, 40).row();
        }
    }

    private Image crearIconoTipo(String tipo) {
        if (tipo.equalsIgnoreCase("vacio")) return null;

        int fila = 0;
        if (tipo.equalsIgnoreCase("Ataque")) fila = 0;
        else if (tipo.equalsIgnoreCase("Defensa")) fila = 1;
        else if (tipo.equalsIgnoreCase("Curacion")) fila = 2;
        else if (tipo.equalsIgnoreCase("Mana")) fila = 3;

        int ancho = categoriasTex.getWidth();
        int alto = categoriasTex.getHeight() / 4;

        TextureRegion region = new TextureRegion(categoriasTex, 0, fila * alto, ancho, alto);
        return new Image(new TextureRegionDrawable(region));
    }

    private ImageButton crearBotonHab(Texture tex) {

        int w = tex.getWidth() / 2;
        int h = tex.getHeight();

        TextureRegion up = new TextureRegion(tex, 0, 0, w, h);
        TextureRegion over = new TextureRegion(tex, w, 0, w, h);

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(up);
        style.imageOver = new TextureRegionDrawable(over);

        return new ImageButton(style);
    }

    public void mostrarInventario() {
        imgInventario.setVisible(true);
        tabla.setVisible(true);
    }

    public void ocultarInventario() {
        imgInventario.setVisible(false);
        tabla.setVisible(false);
    }

    // COMENTA LA HABILIDAD EJECUTADA (de una entidad)
    public void comentarista(SpriteBatch batch, String mensaje){

        if (mensaje != null && !mensaje.isEmpty()) {
            font.setColor(Color.WHITE);
            layout.setText(font, mensaje);

            float xMensaje = (WORLD_WIDTH - layout.width) / 2f;
            float yMensaje = WORLD_HEIGHT * 0.90f;
            font.draw(batch, mensaje, xMensaje, yMensaje);
        }

    }
    
    // IMAGEN VICTORIA/DERROTA AL FINALIZAR LA PARTIDA
    public void resultado(SpriteBatch batch, String resultado) {

        ancho = WORLD_WIDTH * 0.5f;
        alto = WORLD_HEIGHT * 0.3f;

        x = (WORLD_WIDTH - ancho) / 2f;
        y = (WORLD_HEIGHT - alto) / 2f + WORLD_HEIGHT * 0.25f;

        if (resultado == "victoria") {
            batch.draw(victoria, x, y, ancho, alto);
        } else if (resultado == "derrota") {
            batch.draw(derrota, x, y, ancho, alto);
        }
    }

    // BARRA DE VIDA/MANA
    public void dibujarInterfazHeroe(SpriteBatch batch, int vidaActual, int vidaMax, int manaActual, int manaMax) {
        sr.setProjectionMatrix(batch.getProjectionMatrix());
        sr.begin(ShapeRenderer.ShapeType.Filled);

        dibujarBarra(120, 132, vidaActual, vidaMax, Color.GREEN);
        dibujarBarra(120, 106, manaActual, manaMax, Color.BLUE);
        
        sr.end();
    }

    public void dibujarInterfazEnemigo(SpriteBatch batch, int vidaActual, int vidaMax, int manaActual, int manaMax) {
        sr.setProjectionMatrix(batch.getProjectionMatrix());
        sr.begin(ShapeRenderer.ShapeType.Filled);

        dibujarBarra(977, 136, vidaActual, vidaMax, Color.RED);
        dibujarBarra(977, 110, manaActual, manaMax, Color.BLUE);
        
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

    public void dibujarSprite(SpriteBatch batch, Entidad.Estado estadoHeroe, float timeHeroe, String nombreHeroe, 
                                Entidad.Estado estadoEnemigo, float timeEnemigo, String nombreEnemigo) {
        font.setColor(Color.WHITE);;

        Animation <TextureRegion> animacionH = animHeroe.get(estadoHeroe);
        if (animacionH != null) {
            TextureRegion frame = animacionH.getKeyFrame(timeHeroe, true);
            batch.draw(frame, WORLD_WIDTH * 0.15f, WORLD_HEIGHT * 0.40f, 150, 150);
            font.draw(batch, nombreHeroe, 157, 188);
        }

        Animation <TextureRegion> animacionE = animEnemigo.get(estadoEnemigo);   
        if (animacionE != null) {
            TextureRegion frame = animacionE.getKeyFrame(timeEnemigo, true);
            batch.draw(frame, WORLD_WIDTH * 0.65f, WORLD_HEIGHT * 0.40f, 150, 150);;
            font.draw(batch, nombreEnemigo, 957, 188);
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
        animHeroe.put(Entidad.Estado.PARADO, new Animation<>(0.35f, framesParadoH, Animation.PlayMode.LOOP));
    
    // 2. Héroe Ataque
        Array<TextureRegion> framesAtaqueH = new Array<>();
        for (int i = 1; i <= 3; i++) { 
            String ruta = "Imagenes/Personajes/Heroes/Caballero/CaballeroAtaque" + i + ".png";
            Texture textura = new Texture(Gdx.files.internal(ruta));
            framesAtaqueH.add(new TextureRegion(textura));
        }
        animHeroe.put(Entidad.Estado.ATAQUE, new Animation<>(0.25f, framesAtaqueH, Animation.PlayMode.NORMAL));

        // 3. Héroe Daño (¡Corregido con su propio Array!)
        Array<TextureRegion> framesDanioH = new Array<>();
        for (int i = 1; i <= 3; i++) {
            String ruta = "Imagenes/Personajes/Heroes/Caballero/CaballeroDano" + i + ".png";
            Texture textura = new Texture(Gdx.files.internal(ruta));
            framesDanioH.add(new TextureRegion(textura));
        }
        animHeroe.put(Entidad.Estado.DANIO, new Animation<>(0.30f, framesDanioH, Animation.PlayMode.NORMAL));

        // 1. Enemigo Parado (Limpio y separado)
        Array<TextureRegion> framesParadoE = new Array<>();
        for (int i = 1; i <= 1; i++) {
            String ruta = "Imagenes/Personajes/Enemigo/Zombie/ZombieParado" + i + ".png";
            Texture textura = new Texture(Gdx.files.internal(ruta));
            framesParadoE.add(new TextureRegion(textura));
        }
        animEnemigo.put(Entidad.Estado.PARADO, new Animation<>(0.55f, framesParadoE, Animation.PlayMode.LOOP));

        // 2. Enemigo Ataque
        Array<TextureRegion> framesAtaqueE = new Array<>();
        for (int i = 1; i <= 2; i++) {
            String ruta = "Imagenes/Personajes/Enemigo/Zombie/ZombieAtaque" + i + ".png";
            Texture textura = new Texture(Gdx.files.internal(ruta));
            framesAtaqueE.add(new TextureRegion(textura));
        }
        animEnemigo.put(Entidad.Estado.ATAQUE, new Animation<>(0.60f, framesAtaqueE, Animation.PlayMode.NORMAL));

        // 3. Enemigo Daño (¡Corregido con su propio Array!)
        Array<TextureRegion> framesDanioE = new Array<>();
        for (int i = 1; i <= 2; i++) {
            String ruta = "Imagenes/Personajes/Enemigo/Zombie/ZombieDano" + i + ".png";
            Texture textura = new Texture(Gdx.files.internal(ruta));
            framesDanioE.add(new TextureRegion(textura));
        }
        animEnemigo.put(Entidad.Estado.DANIO, new Animation<>(0.60f, framesDanioE, Animation.PlayMode.NORMAL));

        //goblin corriendo
        Array<TextureRegion> framesGoblinC = new Array<>();
        for (int i = 1; i <= 4; i++) {
            String ruta = "Imagenes/Personajes/Enemigo/duende/duendeCorriendo" + i + ".png";
            Texture textura = new Texture(Gdx.files.internal(ruta));
            framesGoblinC.add(new TextureRegion(textura));
        }
        animacionesGoblin.put(Goblin.EstadoGoblin.CORRIENDO, new Animation<>(0.15f, framesGoblinC, Animation.PlayMode.LOOP));

        //goblin atacando
        Array<TextureRegion> framesGoblinA = new Array<>();
        for (int i = 1; i <= 2; i++) {
            String ruta = "Imagenes/Personajes/Enemigo/duende/duendeAtaque" + i + ".png";
            Texture textura = new Texture(Gdx.files.internal(ruta));
            framesGoblinA.add(new TextureRegion(textura));
        }
        animacionesGoblin.put(Goblin.EstadoGoblin.ATACANDO, new Animation<>(0.15f, framesGoblinA, Animation.PlayMode.LOOP));

        Array<TextureRegion> framesGoblinS = new Array<>();
        for (int i = 1; i <= 2; i++) {
            String ruta = "Imagenes/Personajes/Enemigo/duende/duendeSaltando" + i + ".png";
            Texture textura = new Texture(Gdx.files.internal(ruta));
            framesGoblinS.add(new TextureRegion(textura));
        }
        animacionesGoblin.put(Goblin.EstadoGoblin.ESCAPANDO, new Animation<>(0.8f, framesGoblinS, Animation.PlayMode.NORMAL));
    }

    public void dibujarGoblin(SpriteBatch batch, float x, float y, float stateTime, Goblin.EstadoGoblin estado) {
        Animation<TextureRegion> animacionActual = animacionesGoblin.get(estado);
        if (animacionActual != null) {
            TextureRegion frame = animacionActual.getKeyFrame(stateTime, true);
            batch.draw(frame, x, y, 150, 150);
        }
    }

    public void cerrar() {
        stage.dispose();
    }

    // GETTERS
    public Stage getStage() {return stage;}
    public ImageButton getBtnPausa() {return btnPausa;}
    public ImageButton getBotonHabilidad(int index) {return botonesHabilidades.get(index);}   
    public int getCantidadHabilidades() {return botonesHabilidades.size();}

    public float getTiempoAnimacionHeroe(Entidad.Estado estado){
        Animation<TextureRegion> animacion = animHeroe.get(estado);
        if (animacion != null) {
            return animacion.getAnimationDuration(); // Devuelve la duración de la animación
        }
        return 1.0f;
    }
    
    public float getTiempoAnimacionEnemigo(Entidad.Estado estado){
        Animation<TextureRegion> animacion = animEnemigo.get(estado);
        if (animacion != null) {
            return animacion.getAnimationDuration(); // Devuelve la duración de la animación
        }
        return 1.0f;
    }

    public float getTiempoAnimacionGoblin(Goblin.EstadoGoblin estado) {
        Animation<TextureRegion> animacion = animacionesGoblin.get(estado);
        if (animacion != null) {
            return animacion.getAnimationDuration(); 
        }
        return 1.0f;
    }

    public void setTextoDescripcion(String texto) {
        lblDescripcion.setText(texto);
    }
}