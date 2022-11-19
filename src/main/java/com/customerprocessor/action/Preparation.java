package com.customerprocessor.action;


import com.customerprocessor.exception.ApplicationException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
        try {
            while (pool.awaitTermination(10, TimeUnit.SECONDS));
            pool.shutdownNow();
        } catch (InterruptedException exception) {
            throw new ApplicationException(exception);
        }
    }
}
