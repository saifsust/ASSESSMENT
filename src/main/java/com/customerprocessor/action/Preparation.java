package com.customerprocessor.action;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Preparation {
    private static final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static Preparation prepare(){
        return new Preparation();
    }

    public Preparation add(Runnable runnable){
        pool.submit(runnable);
        return this;
    }

    public void shutdown() {
        pool.shutdown();
    }
}
