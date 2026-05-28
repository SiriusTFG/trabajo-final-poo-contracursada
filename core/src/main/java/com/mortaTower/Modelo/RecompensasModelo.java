package com.mortaTower.Modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import com.mortaTower.DAO.HabilidadDao;

public class RecompensasModelo {

    private HabilidadDao habilidadDao;
    private List<Habilidad> habilidades;
    private int cantRecompensas = 3;

    public enum Recompensa {ATAQUE, DEFENSA, CURACION, MANA}
    private int[] tipo = new int[3];
    
    public enum Opcion {HAB1, HAB2, HAB3}
    private static final Opcion[] valores = Opcion.values();
    private Opcion seleccion = Opcion.HAB1;

    private Random random = new Random();

    // estas son las 3 opciones que se muestran en la vista
    private Habilidad[] rewards = new Habilidad[3];
    private Recompensa[] tipos = new Recompensa[3];

    public RecompensasModelo() {

        habilidadDao = new HabilidadDao();

        try {
            habilidades = habilidadDao.obtenerPorPartida(1);
        } catch (SQLException e) {
            e.printStackTrace();
            habilidades = new ArrayList<>();
        }

        generarRewards();
    }


    // genera habilidades + tipo visual
    public void generarRewards() {

        if (habilidades.isEmpty()) return;

        for (int i = 0; i < cantRecompensas; i++) {
            rewards[i] = habilidades.get(random.nextInt(habilidades.size()));
            tipos[i] = Recompensa.valueOf(rewards[i].getTipo().toUpperCase());
            tipo[i] = tipos[i].ordinal();
        }
    }  

    public void reemplazarHab(int idNuevaHabilidad, int slot) {
    try {
        habilidadDao.reemplazarHabilidadEnPartida(1, idNuevaHabilidad, slot);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    public Opcion getOpcionActual(){return seleccion;}

    public Habilidad[] getHabilidad() {return rewards;}
    public int[] getipo(){return tipo;}

    
}
