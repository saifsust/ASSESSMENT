package com.customerprocessor.action;


import com.customerprocessor.exception.ApplicationException;

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
                if (!pool.awaitTermination(22, TimeUnit.SECONDS)) break;
            } catch (InterruptedException exception) {
                throw new ApplicationException(exception);
            }
        }
        pool.shutdownNow();
    }
}
