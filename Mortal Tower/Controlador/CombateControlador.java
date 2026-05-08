package Controlador;

import Modelo.CombateModelo;

public class CombateControlador {
    
    private CombateModelo model;
    private Teclado teclado;
    private Game game;

    public CombateControlador(CombateModelo model, Teclado teclado, Game game) {
        this.model = model;
        this.teclado = teclado;
        this.game = game;

        game.playLoop(3);
    }

    public void update(){

        if (teclado.leftPressed) {
            model.izquierda();
            teclado.leftPressed = false;
        }
        
        if (teclado.rightPressed) {
            model.derecha();
            teclado.rightPressed = false;
        }
            

        if(teclado.select){
            System.out.println("Enter");

            switch(model.getSeleccion()){
                case 0:
                    System.out.println("Luchar");
                    break;
                case 1:
                    System.out.println("Habilidad");
                    break;
                case 2:
                    System.out.println("Opciones");
                    break;
            }
        }
    }

    public void ejecutar(){}
    

}