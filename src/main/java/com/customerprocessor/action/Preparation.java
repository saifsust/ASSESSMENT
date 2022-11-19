package com.customerprocessor.action;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Preparation {
    private static final ExecutorService pool = Executors.newFixedThreadPool(1000);

    public static Preparation prepare(){
        return new Preparation();
    }

    public Preparation add(Runnable runnable){
        pool.submit(runnable);
        return this;
    }

    public void shutdown() {
        pool.shutdown();
        while (true) {
            try {
                if (!pool.awaitTermination(22, TimeUnit.MINUTES)) break;
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
        pool.shutdownNow();
    }
}
