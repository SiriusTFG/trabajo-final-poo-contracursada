package Modelo;

public class Heroe extends Entidad {

    private int experiencia;
    private int nivel;
    private int experienciaNecesaria;
    
    public Heroe(String nombre, int vida, int mana) {
        super(nombre, vida, mana);

        this.experiencia = 0;
        this.nivel = 1;
        this.experienciaNecesaria = 100; // Ejemplo de experiencia necesaria para subir de nivel
    }

public void atacar(Entidad objetivo) {
    int danio = 10; // Ejemplo de da
    
    
    System.out.println(
    this.getNombre() + " ataca a " + objetivo.getNombre() + " causando " + danio + " puntos de daño.");
    objetivo.recibirDanio(danio);


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

    vidaActual = vidaMax; // Restaura la vida al subir de nivel
    manaActual = manaMax; // Restaura el mana al subir de nivel
    
    
    System.out.println(
    this.getNombre() + " ataca a " + objetivo.getNombre() + " causando " + danio + " puntos de daño.");
    objetivo.recibirDanio(danio);


}
@Override
public void realizarTurno (Entidad objetivo) {
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
