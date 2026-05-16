package com.mortaTower.Vista;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.mortaTower.Modelo.InventarioModelo;
import com.mortaTower.Modelo.InventarioModelo.PanelFocus;

//import static com.mortaTower.Screens.Screens.WORLD_HEIGHT;
//import static com.mortaTower.Screens.Screens.WORLD_WIDTH;

public class InventarioVista {

    private InventarioModelo modelo;

    private Texture inventario;
    private GlyphLayout layout;
    private String selector = ">";

    private BitmapFont fuente;
    private BitmapFont nombre, descripcion;

    public InventarioVista(InventarioModelo modelo){

        this.modelo = modelo;

        inventario =
            new Texture("Imagenes/Combate/inventario.png");

        fuente = new BitmapFont();
        nombre = new BitmapFont();
        descripcion = new BitmapFont();
        layout = new GlyphLayout();
    }

    public void draw(SpriteBatch batch) {

        // panel inventario
        batch.draw(inventario, 470, 50, 380, 300);

        // selector
        dibujarSelector(batch);

        // habilidades
        for (int i = 0; i < 4; i++) {

            
            String texto = modelo.getNombreHabilidad(i);
            String desc = modelo.getDescripcionHabilidad(i);

            nombre.draw(batch, texto, 600, 285 - 40 * i);

            if (modelo.getFocus() == PanelFocus.HABILIDADES) {
                layout.setText(descripcion, desc, Color.WHITE, 180, Align.left, true);
                descripcion.draw(batch, layout , 500, 116);
            }
        }
    }

    private void dibujarSelector(SpriteBatch batch) {

        int x = 0;
        int y = 0;

        // foco categorias
        if(modelo.getFocus() ==
                InventarioModelo.PanelFocus.CATEGORIA){

            switch(modelo.getCategoriaActual()){

                case ATAQUE:
                    x = 488;
                    y = 286;
                    break;

                case DEFENSA:
                    x = 488;
                    y = 246;
                    break;

                case CURACION:
                    x = 488;
                    y = 206;
                    break;

                case MANA:
                    x = 488;
                    y = 166;
                    break;
            }

        }

        // foco habilidades
        else {

            switch(modelo.getHabilidadActual()){

                case SLOT1:
                    x = 560;
                    y = 286;
                    break;

                case SLOT2:
                    x = 560;
                    y = 246;
                    break;

                case SLOT3:
                    x = 560;
                    y = 206;
                    break;

                case SLOT4:
                    x = 560;
                    y = 166;
                    break;
            }
        }

        fuente.draw(batch, selector, x, y);
    }
}