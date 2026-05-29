package com.mortaTower.Modelo;

import java.util.ArrayList;
import java.util.List;

import com.mortaTower.DAO.EnemigoDao;

public class CombateModelo {
    
    public enum Turno {JUGADOR, ENEMIGO, PROCESANDO};
    public enum Resultado {NINGUNO, VICTORIA, DERROTA};

    private Turno turnoActual = Turno.JUGADOR;
    private Heroe heroe;
    private Enemigo enemigo;

    private Resultado resultado = Resultado.NINGUNO;

    private String mensajeCombate = "";
    private float tiempoMensaje = 0;

    public CombateModelo(Heroe heroe, int numPiso) {
        //De forma temporal,optimizar despues.
        try {
        //HeroeDao hDao = new HeroeDao();
        //this.heroe = hDao.obtenerPorId(heroe.getId());
        this.heroe = heroe;
        } catch (Exception e) {
        e.printStackTrace();
        this.heroe = heroe;}
        this.enemigo = cargarEnemigo(numPiso);
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    // Obtiene el Nombre de la Habilidad usada
    public String getNombreHabilidad(int indice) {
        if (heroe.getHabilidades()[indice] != null) {
            return heroe.getHabilidades()[indice].getNombre();
        }
        return "---";
    }

    public Habilidad[] getHabilidadesPorTipo(Class<? extends Habilidad> tipo) {
        Habilidad[] habilidades = heroe.getHabilidades();

        if (habilidades == null) {
            return new Habilidad[0];
        }

        List<Habilidad> filtradas = new ArrayList<>();

        for (Habilidad h : habilidades) {
            if (h != null && tipo.isInstance(h)) {
                filtradas.add(h);
            }
        }

        return filtradas.toArray(new Habilidad[0]);
    }   

    private Enemigo cargarEnemigo(int numPiso) {
        try {
            EnemigoDao eDao = new EnemigoDao();
            Enemigo enemigo = eDao.obtenerEnemigoPorPiso(numPiso);
            return enemigo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
    
    // Getters
    public Turno getTurnoActual() {return turnoActual;}
    public void setTurnoActual(Turno turno) {this.turnoActual = turno;}
    public Heroe getHeroe() {return heroe;}
    public Enemigo getEnemigo() {return enemigo;}
    public String getMensajeCombate() {return mensajeCombate;}

    public void mostrarMensaje(String mensaje) {
        this.mensajeCombate = mensaje;
        this.tiempoMensaje = 2.0f; // El mensaje se mostrará durante 2 segundos
    }   

    public void mostrarMensajeEnemigo (String nombreEnemigo, String habilidad) {
        this.mensajeCombate = nombreEnemigo + " usa " + habilidad + "!";
        this.tiempoMensaje = 2.0f; // El mensaje se mostrará
    }

    public void actualizarMensaje(float delta) {
        if (tiempoMensaje > 0) {
            tiempoMensaje -= delta;
            if (tiempoMensaje <= 0) {
                mensajeCombate = "";
            }
        }
    }
}  