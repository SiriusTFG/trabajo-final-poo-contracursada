package com.mortaTower.Hilo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GoblinAtacante {
    
    private ScheduledExecutorService scheduler;

    public void iniciar(int esperaInicial, int intervalo, Runnable accion) {
        
        scheduler = Executors.newScheduledThreadPool(1); //hilo dedicado para el cronometro

        Runnable tarea = () -> {
            if (accion != null) {
                accion.run();
            }
        };

        scheduler.scheduleAtFixedRate(tarea, esperaInicial, intervalo, TimeUnit.SECONDS);
        System.out.println("hilo del goblin cronometrando...");
    }

    //apaga el hilo de manera segura, asi no consume ram
    public void pararHilo() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            System.out.println("hilo del goblin apagado");
        }
    }
}
