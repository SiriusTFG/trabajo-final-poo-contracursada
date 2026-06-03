package com.mortaTower.Controlador;

import java.sql.SQLException;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.mortaTower.DAO.PartidaDao;
import com.mortaTower.Main;
import com.mortaTower.Modelo.CombateModelo;
import com.mortaTower.Modelo.Habilidad;
import com.mortaTower.Modelo.Heroe;
import com.mortaTower.Modelo.Partida;
import com.mortaTower.Modelo.RecompensasModelo;
import com.mortaTower.Screens.CombateScreen;
import com.mortaTower.Screens.Screens;
import com.mortaTower.Screens.TransicionScreen;
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
    private int habSelecionada;
    private int[] tipo;

    private enum EstadoRecompensa {LISTA_HABILIDADES, REEMPLAZO, RESUMEN_EXP}
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

        vistaRecompensa = new RecompensasVista(game);
    }

    public void mostrar() {
        String[] nombresRecompensas = modeloRecompensa.getNombresRecompensas();
        String[] tipoRecompensas = modeloRecompensa.getTipoRecompensas();

        vistaRecompensa.listaHabilidades(nombresRecompensas, tipoRecompensas);
        Gdx.input.setInputProcessor(vistaRecompensa.getStage());
        listenersRecompensas();   
    }

    public void render(float delta) {

        vistaRecompensa.getStage().act(delta);
        vistaRecompensa.getStage().draw();
    }

    private void listenersRecompensas() {
        int cantidad = vistaRecompensa.getCantidadHabilidades();

        for (int i = 0; i < cantidad; i++) {
            int id = i;
            vistaRecompensa.getBoton(i).addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //recompensaSeleccionada = modeloRecompensa.getHabilidad()[id];
                    habSelecionada = id;
                    cambiarEstado(EstadoRecompensa.REEMPLAZO);
                }
            });
        }

       vistaRecompensa.getBtnAtras().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    cambiarEstado(EstadoRecompensa.LISTA_HABILIDADES);
                }
        });

        int cant = vistaRecompensa.getCantidadRemplazo();
        for (int i = 0; i < cant; i++) {
            int slot = i;
            vistaRecompensa.getBtnRemplazo(i).addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    try {
                        int idPartidaActual = game.getPartidaActual().getId();
                        modeloRecompensa.reemplazarHab(idPartidaActual, habSelecionada, slot);
                        Habilidad nuevaHabilidad = modeloRecompensa.getHabilidad()[habSelecionada];
                        game.getPartidaActual().getHeroe().setHabilidad(slot, nuevaHabilidad);
                    } catch (SQLException e) {

                        e.printStackTrace();
                    }
                
                    cambiarEstado(EstadoRecompensa.RESUMEN_EXP);
                }
                
            });
        }

    }

    private void cambiarEstado(EstadoRecompensa nuevoEstado) {

        estado = nuevoEstado;

        switch (estado) {

            case LISTA_HABILIDADES:
                vistaRecompensa.limpiar();
                String[] nombreRecompensas = modeloRecompensa.getNombresRecompensas();
                String[] tipoRecompensa = modeloRecompensa.getTipoRecompensas();
                vistaRecompensa.listaHabilidades(nombreRecompensas, tipoRecompensa);
                listenersRecompensas();
                break;

            case REEMPLAZO:
                vistaRecompensa.limpiar();
                String[] nombresActuales = modeloRecompensa.getNombresHabilidadesActuales();
                vistaRecompensa.cuadroRemplazo(nombresActuales);
                listenersRecompensas();
                break;

            case RESUMEN_EXP : 
                vistaRecompensa.limpiar();
                Heroe heroe = game.getPartidaActual().getHeroe();
                heroe.ganarExperiencia(expPorGanar);
                vistaRecompensa.pantallaExperiencia(heroe.getNivel(), expPorGanar, heroe.getExperiencia(), heroe.getExperienciaNecesaria());

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
                        
                        game.setScreen(new TransicionScreen(game, RecompensaControlador.this, new CombateScreen(game, nombreHeroe, nivel + 1)));
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