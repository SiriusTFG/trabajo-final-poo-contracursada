package Modelo;

public class OpcionesModelo {

    private String[] opciones = {
        "Volumen",
        "Controles",
        "Volver"
    };

    private int seleccion = 0;

    // subnivel de volumen
    private int volumenMusica = 5;
    private int volumenFX = 5;

    // estado interno
    private boolean enVolumen = false;

    public void arriba() {
        if (enVolumen) return;

        seleccion = (seleccion - 1 + opciones.length) % opciones.length;
    }

    public void abajo() {
        if (enVolumen) return;

        seleccion = (seleccion + 1) % opciones.length;
    }

    public void enter() {

        switch (seleccion) {

            case 0 -> enVolumen = true; // entra a volumen
            case 1 -> System.out.println("Controles (futuro)");
            case 2 -> {} // volver (lo maneja el controlador)
        }
    }

    public void back() {
        enVolumen = false;
    }

    // volumen control
    public void subirMusica() {
        volumenMusica = Math.min(volumenMusica + 1, 10);
    }

    public void bajarMusica() {
        volumenMusica = Math.max(volumenMusica - 1, 0);
    }

    public void subirFX() {
        volumenFX = Math.min(volumenFX + 1, 10);
    }

    public void bajarFX() {
        volumenFX = Math.max(volumenFX - 1, 0);
    }

    // getters
    public int getSeleccion() {
        return seleccion;
    }

    public String[] getOpciones() {
        return opciones;
    }

    public boolean isEnVolumen() {
        return enVolumen;
    }

    public int getVolumenMusica() {
        return volumenMusica;
    }

    public int getVolumenFX() {
        return volumenFX;
    }
}