package com.mortaTower.Modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.List;

import com.mortaTower.DAO.HabilidadDao;

public class RecompensasModelo {

    private HabilidadDao habilidadDao;
    private List<Habilidad> habilidades, lista;
    //private List<Habilidad> lista;
    private int cantRecompensas = 3;

    public enum Recompensa {ATAQUE, DEFENSA, CURACION, MANA}
    private int[] tipo = new int[3];
    
    public enum Opcion {HAB1, HAB2, HAB3}
    private Opcion seleccion = Opcion.HAB1;

    private Random random = new Random();

    // estas son las 3 opciones que se muestran en la vista
    private Habilidad[] habilidadActual = new Habilidad[4];
    private Habilidad[] rewards = new Habilidad[3];
    private Recompensa[] tipos = new Recompensa[3];

    public RecompensasModelo() {

        habilidadDao = new HabilidadDao();

        try {
            habilidades = habilidadDao.obtenerTodas();
            lista = habilidadDao.obtenerPorPartida(1);
            
        } catch (SQLException e) {
            e.printStackTrace();
            habilidades = new ArrayList<>();
        }

        generarRewards();
    }

    // genera habilidades random y con usados guarda los indices de aquellas que fueron seleccionadas
    public void generarRewards() {

        if (habilidades.isEmpty()) return;

        Set<Integer> usados = new HashSet<>();

        int max = Math.min(cantRecompensas, habilidades.size());

        for (int i = 0; i < max; i++) {

            int index;

            do {
                index = random.nextInt(habilidades.size());
            } while (usados.contains(index));

            usados.add(index);

            rewards[i] = habilidades.get(index);

            tipos[i] = Recompensa.valueOf(
                rewards[i].getTipo().toUpperCase()
            );

            tipo[i] = tipos[i].ordinal();
        }
    }

    public void reemplazarHab(int indiceReward , int slot) throws SQLException {

    Habilidad nueva = rewards[indiceReward];

    habilidadDao.reemplazarHabilidadEnPartida(1, nueva.getId(), slot);
}

    public Opcion getOpcionActual(){return seleccion;}

    public Habilidad[] getHabilidad() {return rewards;}
    public Habilidad[] getHabilidadActual() {return habilidadActual;}
    public int[] getipo(){return tipo;}

    
}
