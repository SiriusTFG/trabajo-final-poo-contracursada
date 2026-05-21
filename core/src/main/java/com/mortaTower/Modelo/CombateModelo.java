package com.mortaTower.Modelo;

import com.mortaTower.DAO.EnemigoDao;
import com.mortaTower.DAO.HeroeDao;

public class CombateModelo {
    
    public enum Opciones {HAB_1, HAB_2, HAB_3, HAB_4, PAUSA};
    public enum Turno {JUGADOR, ENEMIGO, PROCESANDO};

    private static final Opciones[] valores = Opciones.values();
    private Opciones seleccion = Opciones.HAB_1;
    private Turno turnoActual = Turno.JUGADOR;
    private Heroe heroe;
    private Enemigo enemigo;
    private String mensajeCombate = "";
    private float tiempoMensaje = 0;

    public CombateModelo(Heroe heroe, int numPiso) {
    //De forma temporal,optimizar despues.
    try {
    HeroeDao hDao = new HeroeDao();
    this.heroe = hDao.obtenerPorId(heroe.getId());
    } catch (Exception e) {
    e.printStackTrace();
    this.heroe = heroe;}
    this.enemigo = cargarEnemigo(numPiso);
    }

   /* public void izquierda() {
        int indice = (seleccion.ordinal() - 1 + 4) % 4;
        seleccion = valores[indice];
    }

    public void derecha() {
        int indice = (seleccion.ordinal() + 1 + 4) % 4;
        seleccion = valores[indice];
    }*/

    /*public void abajo() {
        if (seleccion != Opciones.OPCIONES) {
            seleccion = Opciones.OPCIONES;
        }
    }

    public void arriba() {
        if (seleccion == Opciones.OPCIONES) {
            seleccion = Opciones.HAB_1;
        }
    }*/

    public void pausa(){

        if(seleccion == Opciones.PAUSA){

            seleccion = Opciones.HAB_1;
        }else{
            seleccion = Opciones.PAUSA;
        }
    }

    public String getNombreHabilidad(int indice) {
        if (heroe.getHabilidades()[indice] != null) {
            return heroe.getHabilidades()[indice].getNombre();
        }
        return "---";
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
 
        // Mostrar el mensaje durante
    // Getters
    public Opciones getOpcionActual() {return seleccion;}
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