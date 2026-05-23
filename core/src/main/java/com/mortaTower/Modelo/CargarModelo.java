package com.mortaTower.Modelo;

import java.util.List;

import com.mortaTower.DAO.PartidaDao;

public class CargarModelo {

    private List<String> partidas;
    private int seleccion = 0;
    private PartidaDao partidaDao = new PartidaDao();

    public CargarModelo() {
        actualizarLista();
    }

    public void actualizarLista() {
        try {
            partidas = partidaDao.obtenerResumenPartidas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void arriba() {if (partidas.size() > 0) seleccion = (seleccion - 1 + partidas.size()) % partidas.size();}
    public void abajo() {if (partidas.size() > 0) seleccion = (seleccion + 1) % partidas.size();}

    public List<String> getPartidas() {return partidas;}
    public int getSeleccion() {return seleccion;}
    public void setSeleccion(int seleccion) {this.seleccion = seleccion;}

    
}
