package com.mortaTower.Screens;

import java.sql.SQLException;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.mortaTower.DAO.PartidaDao;
import com.mortaTower.Main;
import com.mortaTower.Modelo.CombateModelo;
import com.mortaTower.Modelo.Habilidad;
import com.mortaTower.Modelo.Heroe;
import com.mortaTower.Modelo.Partida;
import com.mortaTower.Modelo.RecompensasModelo;
import com.mortaTower.Vista.RecompensasVista;

public class RecompensaControlador  extends Screens{

    //private Main game;
    private RecompensasModelo modeloRecompensa;
    private CombateModelo combateModelo;

    private Habilidad recompensaSeleccionada;
    private RecompensasVista vistaRecompensa;
    private int nivel;
    private Habilidad[] habilidad;
    private List<Habilidad> habilidadActual;
    private int habSelecionada = -1;
    private int habRemplazar = -1;
    private int[] tipo, valorRecompensa, costoManaRecompensa, valorActual, costoManaActual;

    private enum EstadoRecompensa {LISTA_HABILIDADES, RESUMEN_EXP}
    private int expPorGanar = 50;
    private EstadoRecompensa estado = EstadoRecompensa.LISTA_HABILIDADES;

    public RecompensaControlador(Main game, int nivel) {

        super(game);
        this.nivel = nivel;
        Heroe heroe = game.getPartidaActual().getHeroe();
        modeloRecompensa= new RecompensasModelo(heroe);
        combateModelo = new CombateModelo(heroe, nivel);

        habilidad = modeloRecompensa.getHabilidad();

        cargarAssets();

        vistaRecompensa = new RecompensasVista(viewport, game);
    }

    public void mostrar() {
        String[] nombresRecompensas = modeloRecompensa.getNombresRecompensas();
        String[] nombresActuales = modeloRecompensa.getNombresHabilidadesActuales();
        String[] tipoRecompensas = modeloRecompensa.getTipoRecompensas();
        String[] tipoHabilidadActual = modeloRecompensa.getTipoHabilidad();

        valorRecompensa = modeloRecompensa.getValorRecompensa();
        costoManaRecompensa = modeloRecompensa.getCostoManaRecompensa();

        valorActual = modeloRecompensa.getValorActual();
        costoManaActual = modeloRecompensa.getCostoActual();

        vistaRecompensa.listaHabilidades(nombresRecompensas, nombresActuales, tipoRecompensas, tipoHabilidadActual);
        Gdx.input.setInputProcessor(vistaRecompensa.getStage());
        listenersRecompensas();   
    }

    public void render(float delta) {

        vistaRecompensa.getStage().act(delta);
        vistaRecompensa.getStage().draw();

        if(habRemplazar != -1 && habSelecionada != -1){

            vistaRecompensa.getBtnConfirmar().setTouchable(Touchable.enabled);
        }
    }

    private void listenersRecompensas() {
        int cantidad = vistaRecompensa.getCantidadHabilidades();

        String[] nueva = modeloRecompensa.getNombresRecompensas();
        String[] reemplaza = modeloRecompensa.getNombresHabilidadesActuales();

        for (int i = 0; i < cantidad; i++) {
            int id = i;
            vistaRecompensa.getBoton(i).addListener(new ClickListener() {

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                    
                    vistaRecompensa.setTextoDanio(Integer.toString(valorRecompensa[id]));
                    vistaRecompensa.setTextoMana(Integer.toString(costoManaRecompensa[id]));
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                    
                    vistaRecompensa.setTextoDanio("");
                    vistaRecompensa.setTextoMana("");
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    
                    habSelecionada = id;
                    vistaRecompensa.setNueva(nueva[id]);
                }
            });
        }

        int cant = vistaRecompensa.getCantidadRemplazo();
        for (int i = 0; i < cant; i++) {
            int slot = i;

            
            vistaRecompensa.getBtnRemplazo(i).addListener(new ClickListener() {

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                    
                    vistaRecompensa.setTextoDanioAct(Integer.toString(valorActual[slot]));
                    vistaRecompensa.setTextoManaAct(Integer.toString(costoManaActual[slot]));
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                    
                    vistaRecompensa.setTextoDanioAct("");
                    vistaRecompensa.setTextoManaAct("");
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    
                    habRemplazar = slot;
                    vistaRecompensa.setReemplaza(reemplaza[slot]);

                }
                
            });
        }

        vistaRecompensa.getBtnConfirmar().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    int idPartidaActual = game.getPartidaActual().getId();
                    modeloRecompensa.reemplazarHab(idPartidaActual, habSelecionada, habRemplazar);
                    Habilidad nuevaHabilidad = modeloRecompensa.getHabilidad()[habSelecionada];
                    game.getPartidaActual().getHeroe().setHabilidad(habRemplazar, nuevaHabilidad);
                } catch (SQLException e) {

                    e.printStackTrace();
                }
            
                cambiarEstado(EstadoRecompensa.RESUMEN_EXP);
            }
        });

        vistaRecompensa.getBtnOmitir().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            
                cambiarEstado(EstadoRecompensa.RESUMEN_EXP);
            }
        });


    }

    private void cambiarEstado(EstadoRecompensa nuevoEstado) {

        estado = nuevoEstado;

        switch (estado) {

            case LISTA_HABILIDADES:

                //game.audio.play(8);
                vistaRecompensa.limpiar();
                String[] nombreRecompensas = modeloRecompensa.getNombresRecompensas();
                String[] nombresActuales = modeloRecompensa.getNombresHabilidadesActuales();
                String[] tipoRecompensa = modeloRecompensa.getTipoRecompensas();
                String[] tipoHabilidadActual = modeloRecompensa.getTipoHabilidad();
                vistaRecompensa.listaHabilidades(nombreRecompensas, nombresActuales, tipoRecompensa, tipoHabilidadActual);
                listenersRecompensas();
                break;

            case RESUMEN_EXP : 
                game.audio.play(9);
                vistaRecompensa.limpiar();
                Heroe heroe = game.getPartidaActual().getHeroe();
                heroe.ganarExperiencia(expPorGanar);
                vistaRecompensa.pantallaExperiencia(heroe.getNivel(), nivel, expPorGanar, heroe.getExperiencia(), heroe.getExperienciaNecesaria());

                // Programamos un cambio de pantalla automático a los 5 segundos
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        
                        Partida partidaActual = game.getPartidaActual();
                        String nombreHeroe = partidaActual.getNombrePartida();
                        
                        try {   
                            partidaActual.setPisoActual(nivel + 1);
                            PartidaDao partidaDao = new PartidaDao();
                            partidaDao.guardarProgreso(partidaActual);
                            

                            System.out.println("Partida guardada exitosamente. Avanzando al piso " + (nivel + 1));
                        } catch (SQLException e) {
                            e.printStackTrace();
                            System.err.println("Error al intentar guardar la partida.");
                        }
                        
                        game.setScreen(new TransicionScreen(game, RecompensaControlador.this,new CombateScreen(game, nombreHeroe, ++nivel)));
                    }
                }, 5f);
                break;
        }
    }

    private void cargarAssets() {

        game.assets.load("Imagenes/black.png", Texture.class);
        game.assets.load("Imagenes/SeccionRecompensa/cuadroHabilidad.png", Texture.class);
        game.assets.load("Imagenes/SeccionRecompensa/tipoHabilidad.png", Texture.class);
        game.assets.load("Imagenes/SeccionRecompensa/cuadroRemplazo.png", Texture.class);
        game.assets.load("Imagenes/SeccionRecompensa/barraRemplazo.png", Texture.class);

        game.assets.finishLoading();
    }

    public RecompensasVista getVista() {
        return vistaRecompensa;
    }

    public void cerrar() {
        vistaRecompensa.cerrar();
    }
}