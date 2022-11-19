package com.customerprocessor.processor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProcessorPool {
    private static final ExecutorService POOL = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static final ProcessorPool instance = new ProcessorPool();

    public static ProcessorPool getInstance() {
        return instance;
    }

    public void submit(Runnable runnable) {
        POOL.submit(runnable);
    }

    public void shutDown() {
        POOL.shutdown();
    }
}
