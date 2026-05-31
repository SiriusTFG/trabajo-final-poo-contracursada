package com.mortaTower.Modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.mortaTower.DAO.HabilidadDao;

public class RecompensasModelo {

    private HabilidadDao habilidadDao;
    private List<Habilidad> habilidades;
    private int cantRecompensas = 3;

    private Heroe heroeActual;
    
    public enum Opcion {HAB1, HAB2, HAB3}
    private Opcion seleccion = Opcion.HAB1;

    private Random random = new Random();

    // estas son las 3 opciones que se muestran en la vista
    private Habilidad[] rewards = new Habilidad[3];
    private String[] tiposRecompensas = new String[3];

    public RecompensasModelo(Heroe heroe) {
        this.heroeActual = heroe;
        habilidadDao = new HabilidadDao();

        try {
            habilidades = habilidadDao.obtenerTodas();
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
        Habilidad[] habilidadesHereo = heroeActual.getHabilidades();

        for (Habilidad habEquipada : habilidadesHereo) {
            if (habEquipada != null) {
                for (int i = 0; i < habilidades.size(); i++) {
                    if (habilidades.get(i).getId() == habEquipada.getId()) {
                        usados.add(i);
                        break;
                    }
                }
            }
        }

        int habilidadesDisponibles = habilidades.size() - usados.size();
        int max = Math.min(cantRecompensas, habilidadesDisponibles);

        for (int i = 0; i < max; i++) {
            int index;
            
            do {
                index = random.nextInt(habilidades.size());
            } while (usados.contains(index));

            usados.add(index);
            rewards[i] = habilidades.get(index);;

            tiposRecompensas[i] = rewards[i].getTipo();
        }
    }

    public void reemplazarHab(int idPertida, int indiceReward , int slot) throws SQLException {
        Habilidad nueva = rewards[indiceReward];
        habilidadDao.reemplazarHabilidadEnPartida(idPertida, nueva.getId(), slot);
    }

    public String[] getNombresRecompensas() {
        String[] nombres = new String[3];
        for (int i = 0; i < 3; i++) {
            nombres[i] = rewards[i] != null ? rewards[i].getNombre() : "Vacio";
        }
        return nombres;
    }

    public String[] getNombresHabilidadesActuales() {
        String[] nombres = new String[4];
        Habilidad[] habilidadesHeroe = heroeActual.getHabilidades();
        for (int i = 0; i < 4; i++) {
            if (habilidadesHeroe[i] != null) {
                nombres[i] = habilidadesHeroe[i].getNombre();
            } else {
                nombres[i] = "Vacío";
            }
        }
        return nombres;
    }

    public Opcion getOpcionActual(){return seleccion;}

    public Habilidad[] getHabilidad() {return rewards;}
    public String[] getTipoRecompensas(){return tiposRecompensas;}

    
}
