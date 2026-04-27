package Modelo;

public class Heroe extends Entidad {

    public Heroe(String nombre, int vida, int mana) {
        super(nombre, vida, mana);
    }

public void atacar(Entidad objetivo) {
    int danio = 10; // Ejemplo de da
    
    
    System.out.println(
    this.getNombre() + " ataca a " + objetivo.getNombre() + " causando " + danio + " puntos de daño.");
    objetivo.recibirDanio(danio);


}
@Override
public void realizarTurno (Entidad objetivo) {
}
}
