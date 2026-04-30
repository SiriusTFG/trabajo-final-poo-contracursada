package Modelo;

public class Sprite {
    
    private int id;
    private String rutaImagen;
    private String estado;
    private int orden;

    public Sprite(int id, String rutaImagen, String estado, int orden) {
        this.id = id;
        this.rutaImagen = rutaImagen;
        this.estado = estado;
        this.orden = orden;
    }

    // Getters
    public String getRutaImagen() { return rutaImagen; }
    public String getEstado() { return estado; }
    public int getOrden() { return orden; }
}
