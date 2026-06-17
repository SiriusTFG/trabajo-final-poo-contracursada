package com.mortaTower.Modelo;

public class Heroe extends Entidad {

    private int experiencia;
    private int nivel;
    private int experienciaNecesaria;
    private Habilidad habilidadSeleccionada;

    public Heroe(int id, String nombre, int vida, int mana, int nivel, int experiencia, int ataque, double defensa) {
        super(nombre, vida, mana);
        this.id = id;
        this.nivel = nivel;
        this.experiencia = experiencia;
        this.ataque = ataque;
        this.defensaBase = defensa;
        this.experienciaNecesaria = 100 + ((this.nivel - 1) * 50);
    }

    public void avanzarCooldowns() {
        for (Habilidad habilidad : habilidades) {
            if (habilidad != null) habilidad.tickCooldown();
        }
    }

    public void ganarExperiencia(int puntos){
        experiencia += puntos;
    
        while (experiencia >= experienciaNecesaria) {
            experiencia -= experienciaNecesaria; // Resta la experiencia necesaria para subir de nivel
            subirNivel();
        }
    }

    private void subirNivel() {
        nivel++;
        experienciaNecesaria += 50; // Incrementa la experiencia necesaria para el siguiente nivel
        System.out.println(this.getNombre() + " ha subido al nivel " + nivel + "!");
 
        vidaMax += 20; // Incrementa la vida máxima al subir de nivel
        manaMax += 10; // Incrementa el mana máximo al subir de nivel
    }

    public void seleccionarHabilidad(Habilidad habilidad) {

        if (habilidad == null) {
            habilidadSeleccionada = null;
            return;
        }

        habilidadSeleccionada = habilidad;
    }

    public void curarAlMaximo() {
        this.vidaActual = this.vidaMax;
        this.manaActual = this.manaMax;
        System.out.println(this.getNombre() + " se ha curado por completo para el próximo piso.");
    }

    @Override
    public void realizarTurno (Entidad objetivo) {
        if (habilidadSeleccionada == null) return;

        habilidadSeleccionada.ejecutarHabilidad(this, objetivo);
        habilidadSeleccionada = null;
    }

    
    public int getNivel() {
        return nivel;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public int getExperienciaNecesaria() {
        return experienciaNecesaria;
    }
}