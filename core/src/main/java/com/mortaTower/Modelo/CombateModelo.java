package com.mortaTower.Modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mortaTower.DAO.EnemigoDao;
import com.mortaTower.DAO.HabilidadDao;
import com.mortaTower.Strategy.ComportamientoAgresivo;
import com.mortaTower.Strategy.ComportamientoDefensivo;
import com.mortaTower.Strategy.ComportamientoInteligente;

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
        heroe.curarAlMaximo();
        heroe.getManaMax();
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

    public List<Habilidad> getHabilidadesPorEntidad() {
        try {
            HabilidadDao dao = new HabilidadDao();

            return dao.obtenerPorEntidad(heroe.getId(), "Heroe");

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Habilidad> getHabilidadesPor() {
        try {
            HabilidadDao dao = new HabilidadDao();
            return dao.obtenerPorPartida(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Habilidad> getHabilidadesPartida(int idPartida) {
        try {
            HabilidadDao dao = new HabilidadDao();
            return dao.obtenerPorPartida(idPartida);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private Enemigo cargarEnemigo(int numPiso) {
        try {
            EnemigoDao eDao = new EnemigoDao();
            Enemigo enemigo = eDao.obtenerEnemigoPorPiso(numPiso);

            if (enemigo != null) {
                if (numPiso <= 5 && numPiso >= 3) {
                    enemigo.cambiarComportamiento(new ComportamientoInteligente());
                } else if (numPiso == 2) {
                    enemigo.cambiarComportamiento(new ComportamientoDefensivo());
                } else {
                    enemigo.cambiarComportamiento(new ComportamientoAgresivo());
                }
            }
            return enemigo;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] getNombreHabilidadesActuales() {
        String[] nombres = new String[4];
        for (int i = 0; i < 4; i++) {
            if (heroe.getHabilidades()[i] != null) {
                nombres[i] = heroe.getHabilidades()[i].getNombre();
            } else {
                nombres[i] = "vacio";
            }
        }
        return nombres;
    }

    public String[] getTipoHabilidadesActuales() {
        String[] tipos = new String[4];
        for (int i = 0; i < 4; i++) {
            if (heroe.getHabilidades()[i] != null) {
                tipos[i] = heroe.getHabilidades()[i].getTipo();
            } else {
                tipos[i] = "";
            }
        }
        return tipos;
    }

    public String[] getDescripcionesHabilidadesActuales() {
        String[] descripciones = new String[4];
        for (int i = 0; i < 4; i++) {
            if (heroe.getHabilidades()[i] != null) {
                descripciones[i] = heroe.getHabilidades()[i].getDescripcion();
            } else {
                descripciones[i] = "";
            }
        }
        return descripciones;
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