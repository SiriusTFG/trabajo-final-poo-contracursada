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

    private Random random = new Random();

    // estas son las 3 opciones que se muestran en la vista
    private Habilidad[] rewards = new Habilidad[3];
    private String[] tiposRecompensas = new String[3];
    private int[] danioRecompensa = new int[3];
    private int[] manaRecompensa = new int[3];

    private String[] tiposHabActual = new String[4];
    private int[] danioActual = new int[4];
    private int[] manaActual = new int[4];

    public RecompensasModelo(Heroe heroe) {
        this.heroeActual = heroe;
        habilidadDao = new HabilidadDao();

        try {
            habilidades = habilidadDao.obtenerRecompensasHeroe();
        } catch (SQLException e) {
            e.printStackTrace();
            habilidades = new ArrayList<>();
        }
        generarRewards();
    }

    // genera habilidades random y con usados guarda los indices de aquellas que fueron seleccionadas
    public void generarRewards() {
        if (habilidades.isEmpty()) return;

        Set<Integer> usados = new HashSet<>(); //colección que no permite elementos duplicados
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
            rewards[i] = habilidades.get(index);

            tiposRecompensas[i] = rewards[i].getTipo();
            danioRecompensa[i] = rewards [i].getValorBase();
            manaRecompensa[i] = rewards [i].getCostoMana();
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
                tiposHabActual[i] = habilidadesHeroe[i].getTipo();
                danioActual[i] = habilidadesHeroe[i].getValorBase();
                manaActual[i] = habilidadesHeroe[i].getCostoMana();
            } else {
                nombres[i] = "Vacío";
            }
        }
        
        return nombres;
    }

    public Habilidad[] getHabilidad() {return rewards;}
    public String[] getTipoRecompensas(){return tiposRecompensas;}
    public int[] getValorRecompensa() {return danioRecompensa;}
    public int[] getCostoManaRecompensa() {return manaRecompensa;}

    public String[] getTipoHabilidad(){return tiposHabActual;}
    public int[] getValorActual() {return danioActual;}
    public int[] getCostoActual() {return manaActual;}

    
}
