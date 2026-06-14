package com.mortaTower.Vista;

import com.mortaTower.Main;
import com.mortaTower.Modelo.Goblin;
import com.mortaTower.Screens.GameAssets;
import com.mortaTower.Modelo.Entidad;
import com.mortaTower.Modelo.DatosSprite;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

//import static com.mortaTower.Screens.Screens.WORLD_HEIGHT;
//import static com.mortaTower.Screens.Screens.WORLD_WIDTH;

public class CombateVista {

    private Main game;
    private Stage stage;

    private float ancho, alto, x, y, w, h;
    private String nombre, tipo;

    private List<ImageButton> botonesHabilidades = new ArrayList<>();
    private ImageButton btnPausa, btn;

    private Table tabla, fila;
    private Stack stack;

    private GlyphLayout layout; 
    private Label.LabelStyle estilo, estilo2, estilo3;
    private Label lblDescripcion,  lblDanio, lblMana;
    private Label lbl;

    private TextureRegionDrawable drawable;
    private Texture categoriasTex, habilidadTex;
    private Texture victoria, derrota;
    private Image imgFondoGeneral, steroe, stsEnemigo;
    private Image imgInventario, iconCategoria;
    
    private ShapeRenderer sr;

    // Animacion Goblin
    private Map<Goblin.EstadoGoblin, Animation<TextureRegion>> animacionesGoblin = new HashMap<>();    
    
    // Animaciones
    private final Map<Entidad.Estado, Animation<TextureRegion>> animHeroe = new HashMap<>();
    private final Map<Entidad.Estado, Animation<TextureRegion>> animEnemigo = new HashMap<>();
    
    public CombateVista(FitViewport viewport, Main game, int nivel,List<DatosSprite> spritesHeroe, List<DatosSprite> spritesEnemigo) {

        this.stage = new Stage(viewport, game.batch);
        this.game = game;

        w = stage.getViewport().getWorldWidth();
        h = stage.getViewport().getWorldHeight();

        sr = new ShapeRenderer();
        layout = new GlyphLayout();

        estilo = new Label.LabelStyle();
        estilo.font = GameAssets.fuenteMedieval;

        estilo3 = new Label.LabelStyle();
        estilo3.font = GameAssets.fuenteMedievalChico;

        estilo2 = new Label.LabelStyle();
        estilo2.font = GameAssets.font;

        // fondo nivel
        imgFondoGeneral = new Image(game.assets.get("Imagenes/Combate/nivel" + nivel + ".png", Texture.class));
        imgFondoGeneral.setFillParent(true);

        // Boton Pausa
        drawable = new TextureRegionDrawable(new TextureRegion(game.assets.get("Imagenes/Opciones/pausa.png", Texture.class)));
        btnPausa = new ImageButton(drawable);
        btnPausa.setSize(100, 100);
        btnPausa.setPosition(0, 620);

        // cuadro stats del Enemigo
        stsEnemigo = new Image(game.assets.get("Imagenes/Combate/vidaEnemigo.png", Texture.class));
        stsEnemigo.setSize(w * 0.65f, w * 0.08f);
        stsEnemigo.setPosition( (w - stsEnemigo.getWidth()) /2, h - 120);

        // inventario
        categoriasTex = game.assets.get("Imagenes/SeccionRecompensa/tipoHabilidad.png", Texture.class);
        habilidadTex = game.assets.get("Imagenes/SeccionRecompensa/cuadroHabilidad.png", Texture.class);

        // resultado combate
        victoria = game.assets.get("Imagenes/Combate/victoria.png", Texture.class);
        derrota = game.assets.get("Imagenes/Combate/derrota.png", Texture.class);
        
        stage.addActor(imgFondoGeneral); 
        stage.addActor(btnPausa);

        stage.addActor(stsEnemigo); 

        crearUIInventario();

        cargarAnimacionesDB(spritesHeroe, spritesEnemigo);
        cargarAnimacionesGoblin();
    }

    private void crearUIInventario() {

        imgInventario = new Image(game.assets.get("Imagenes/Combate/inventario2.png", Texture.class));

        imgInventario.setSize(w - 780, h - 280);
        imgInventario.setPosition((w- imgInventario.getWidth()) / 2f, h - 680);

        lblDescripcion = new Label("", estilo);
        lblDescripcion.setAlignment(Align.center);
        lblDescripcion.setWidth(250);
        lblDescripcion.setPosition(w / 2f - 200, h * 0.20f); //y
        lblDescripcion.setWrap(true);
        
        lblDanio = new Label("", estilo3);
        lblDanio.setAlignment(Align.center);
        lblDanio.setPosition(w - 455, h * 0.229f);

        lblMana = new Label("", estilo3);
        lblMana.setAlignment(Align.center);
        lblMana.setPosition(w - 480, h * 0.174f);

        tabla = new Table();
        tabla.left().padLeft(500);
        tabla.bottom().padBottom(228);
        
        stage.addActor(imgInventario);
        stage.addActor(lblDescripcion);
        stage.addActor(lblDanio);
        stage.addActor(lblMana);
        stage.addActor(tabla);
    }

    public void cargarInventarioHabilidades(String[] nombresHabilidades, String[] tipoHabilidades) {

        botonesHabilidades.clear();

        for (int i = 0; i < 4; i++) {

            nombre = nombresHabilidades[i];
            tipo = tipoHabilidades[i];

            btn = crearBotonHab(habilidadTex);
            botonesHabilidades.add(btn);

            lbl = new Label(nombre, estilo3);
            lbl.setAlignment(Align.center);
            lbl.setTouchable(Touchable.disabled);

            stack = new Stack();
            stack.add(btn);
            stack.add(lbl);

            iconCategoria = crearIconoTipo(tipo);

            tabla.add(iconCategoria).size(38, 38).padRight(10);
            tabla.add(stack).size(200, 38).padBottom(5).row();
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
        
            layout.setText(GameAssets.fuenteMedieval, mensaje);

            float xMensaje = (w - layout.width) / 2f;
            float yMensaje = h * 0.80f;
            GameAssets.fuenteMedieval.draw(batch, mensaje, xMensaje, yMensaje);
        }

    }
    
    // IMAGEN VICTORIA/DERROTA AL FINALIZAR LA PARTIDA
    public void resultado(SpriteBatch batch, String resultado) {

        ancho = w * 0.3f;
        alto = h * 0.5f;

        x = (w - ancho) / 2f;
        y = (h - alto) / 2f + h * 0.18f;

        if (resultado == "victoria") {
            batch.draw(victoria, x, y, ancho, alto);
        } else if (resultado == "derrota") {
            batch.draw(derrota, x, y, ancho, alto);
        }
    }

    // BARRA DE VIDA/MANA
    public void dibujarInterfazHeroe(SpriteBatch batch, String nombreHeroe, int vidaActual, int vidaMax, int manaActual, int manaMax) {

        float barraVidaX = w - 952;
        float barraVidaY = h - 88;

        float barraManaX = w - 952;
        float barraManaY = h - 114;

        sr.setProjectionMatrix(batch.getProjectionMatrix());
        sr.begin(ShapeRenderer.ShapeType.Filled);
        

        dibujarBarra(barraVidaX, barraVidaY, 230f, vidaActual, vidaMax, new Color(180/255f, 30/255f, 30/255f, 1f), false);
        dibujarBarra(barraManaX, barraManaY, 158f, manaActual, manaMax, new Color(40/255f, 90/255f, 180/255f, 1f), false);

        sr.end();

        batch.begin();

        GameAssets.fuenteMedieval.draw(batch, nombreHeroe, w - 900, h - 42);

        String hp = "HP: " + vidaActual + " / " + vidaMax;
        layout.setText(GameAssets.fontStats, hp);
        GameAssets.fontStats.draw(batch, hp, barraVidaX + 2, barraVidaY + 14);

        String mp = "MP: " + manaActual + " / " + manaMax;
        layout.setText(GameAssets.fontStats, mp);
        GameAssets.fontStats.draw(batch, mp, barraManaX + 2, barraManaY + 14);

        batch.end();
    }

    public void dibujarInterfazEnemigo(SpriteBatch batch, String nombreEnemigo, int vidaActual, int vidaMax, int manaActual, int manaMax) {

        float barraVidaX = w - 555;
        float barraVidaY = h - 88;

        float barraManaX = w - 485;
        float barraManaY = h - 114;

        sr.setProjectionMatrix(batch.getProjectionMatrix());
        sr.begin(ShapeRenderer.ShapeType.Filled);

        dibujarBarra(barraVidaX, barraVidaY, 230f, vidaActual, vidaMax, new Color(180/255f, 30/255f, 30/255f, 1f), true);
        dibujarBarra(barraManaX, barraManaY, 158f, manaActual, manaMax, new Color(40/255f, 90/255f, 180/255f, 1f), true);
        
        sr.end();

        batch.begin();

        GameAssets.fuenteMedieval.draw(batch, nombreEnemigo, w - 580, h - 45);

        String hp = vidaMax + " / " + vidaActual + " :HP";
        layout.setText(GameAssets.fontStats, hp);
        GameAssets.fontStats.draw(batch, hp, barraVidaX + 138, barraVidaY + 14);

        String mp = manaMax + " / " + manaActual + " :MP";
        layout.setText(GameAssets.fontStats, mp);
        GameAssets.fontStats.draw(batch, mp, barraManaX + 68, barraManaY + 14);

        batch.end();
    }

    private void dibujarBarra(float x, float y, float acho, int actual, int max, Color color, boolean invertida) {
        
        ancho = acho;
        float porcentaje = (float) actual / max;
        float fill = ancho * porcentaje;

        sr.setColor(color);
        if (!invertida) {
            sr.rect(x, y, fill, 16); // izquierda → derecha
        } else {
            sr.rect(x + (ancho - fill), y, fill, 16); // derecha → izquierda
        }
    }

    public void dibujarSprite(SpriteBatch batch, Entidad.Estado estadoHeroe, float timeHeroe, Entidad.Estado estadoEnemigo, float timeEnemigo, String nombreEnemigo, int faseVisual) {

        Animation<TextureRegion> animacionH = animHeroe.get(estadoHeroe);
        Animation<TextureRegion> animacionE = animEnemigo.get(estadoEnemigo);

        float baseAncho = 250f;
        float baseAlto = 250f;

        float baseY = h * 0.15f;

        // Margen igual para ambos lados
        float margen = 50f;

        // Posición del héroe
        float heroeX = margen;

        float escala = 1.0f;
        Color colorAura = null;

        if (faseVisual == 2) {
            escala = 1.25f;
            colorAura = new Color(1f, 0f, 0f, 0.7f);
        } else if (faseVisual == 3) {
            escala = 1.50f;
            colorAura = new Color(0.6f, 0f, 1f, 0.7f);
        }

        float actualAncho = baseAncho * escala;
        float actualAlto = baseAlto * escala;

        // Posición del enemigo (misma distancia al borde derecho)
        float enemigoX = w - margen - actualAncho;

        // Dibujar héroe
        if (animacionH != null) {
            boolean loopearHeroe = animacionH.getPlayMode() == Animation.PlayMode.LOOP;
            TextureRegion frameHeroe = animacionH.getKeyFrame(timeHeroe, loopearHeroe);

            batch.draw(frameHeroe, heroeX, baseY, baseAncho, baseAlto);
        }

        // Dibujar enemigo
        if (animacionE != null) {
            boolean loopearEnemigo = animacionE.getPlayMode() == Animation.PlayMode.LOOP;
            TextureRegion frameEnemigo = animacionE.getKeyFrame(timeEnemigo, loopearEnemigo);

            // Aura
            if (colorAura != null) {
                batch.setColor(colorAura);

                float desplazamiento = 15f;

                batch.draw(frameEnemigo, enemigoX - desplazamiento / 2f, baseY - desplazamiento / 2f, actualAncho + desplazamiento, actualAlto + desplazamiento);
            }

            batch.setColor(Color.WHITE);
            batch.draw(frameEnemigo, enemigoX, baseY, actualAncho, actualAlto);
        }
    }

    private void cargarAnimacionesDB(List<DatosSprite> spritesHeroe, List<DatosSprite> spritesEnemigo) {
        cargarAnimacionesEntidad(spritesHeroe, animHeroe);
        cargarAnimacionesEntidad(spritesEnemigo, animEnemigo);
    }
    
    private void cargarAnimacionesEntidad(List<DatosSprite> sprites, Map<Entidad.Estado, Animation<TextureRegion>> mapaAnimaciones) {
        Map<Entidad.Estado, Array<TextureRegion>> framesPorEstado = new HashMap<>();
        for (DatosSprite sprite : sprites) {
            Entidad.Estado estado = Entidad.Estado.valueOf(sprite.getEstado().toUpperCase());
            if (!framesPorEstado.containsKey(estado)) {
                framesPorEstado.put(estado, new Array<>());
            }
            String ruta = sprite.getRutaImagen();
            if (Gdx.files.internal(ruta).exists()) {
                framesPorEstado.get(estado).add(new TextureRegion(new Texture(Gdx.files.internal(ruta))));
            } else {
                System.out.println("ADVERTENCIA: No se encontró la textura en la ruta: " + ruta);
            }
        }
         for (Map.Entry<Entidad.Estado, Array<TextureRegion>> entry : framesPorEstado.entrySet()) {
            Entidad.Estado estado = entry.getKey();
            Array<TextureRegion> frames = entry.getValue();
            float duracion = 0.3f;
            Animation.PlayMode modo = Animation.PlayMode.NORMAL;
            if (estado == Entidad.Estado.PARADO) {
                duracion = 0.35f;
                modo = Animation.PlayMode.LOOP;
            } else if (estado == Entidad.Estado.ATAQUE) {
                duracion = 0.25f;
            } else if (estado == Entidad.Estado.DANIO) {
                duracion = 0.30f;
            }
            mapaAnimaciones.put(estado, new Animation<>(duracion, frames, modo));
        }
    }

    private void cargarAnimacionesGoblin() {
        
        Array<TextureRegion> framesGoblinC = new Array<>();
        for (int i = 1; i <= 4; i++) {

            String ruta = "Imagenes/Personajes/Enemigo/duende/duendeCorriendo" + i + ".png";
            if (Gdx.files.internal(ruta).exists()) {
                framesGoblinC.add(new TextureRegion(new Texture(Gdx.files.internal(ruta))));
            }
        }
        
        if (framesGoblinC.size > 0) {
            animacionesGoblin.put(Goblin.EstadoGoblin.CORRIENDO, new Animation<>(0.15f, framesGoblinC, Animation.PlayMode.LOOP));
        }
        
        Array<TextureRegion> framesGoblinA = new Array<>();
        for (int i = 1; i <= 2; i++) {
            String ruta = "Imagenes/Personajes/Enemigo/duende/duendeAtaque" + i + ".png";
            if (Gdx.files.internal(ruta).exists()) {
                framesGoblinA.add(new TextureRegion(new Texture(Gdx.files.internal(ruta))));
            }
        }
        
        if (framesGoblinA.size > 0) {
            animacionesGoblin.put(Goblin.EstadoGoblin.ATACANDO, new Animation<>(0.15f, framesGoblinA, Animation.PlayMode.LOOP));
        }
        
        Array<TextureRegion> framesGoblinS = new Array<>();
        for (int i = 1; i <= 2; i++) {
            String ruta = "Imagenes/Personajes/Enemigo/duende/duendeSaltando" + i + ".png";
            if (Gdx.files.internal(ruta).exists()) {
                framesGoblinS.add(new TextureRegion(new Texture(Gdx.files.internal(ruta))));
            }
        }

        if (framesGoblinS.size > 0) {
            animacionesGoblin.put(Goblin.EstadoGoblin.ESCAPANDO, new Animation<>(0.8f, framesGoblinS, Animation.PlayMode.NORMAL));
        }
    }

    public void dibujarGoblin(SpriteBatch batch, float x, float y, float stateTime, Goblin.EstadoGoblin estado) {
        Animation<TextureRegion> animacionActual = animacionesGoblin.get(estado);
        if (animacionActual != null) {
            TextureRegion frame = animacionActual.getKeyFrame(stateTime, true);
            batch.draw(frame, x, y, 200, 200);
        }
    }
 
    public void cerrar() {stage.dispose();}

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

    public Image getStsCombate() {return stsEnemigo;}
    public void setTextoDescripcion(String texto) {lblDescripcion.setText(texto);}
    public void setTextoDanio(String danio) { lblDanio.setText(danio);}
    public void setTextoMana(String mana) {lblMana.setText(mana);}

}