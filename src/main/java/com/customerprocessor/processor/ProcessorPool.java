package com.customerprocessor.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProcessorPool {
    private static final Log LOGGER  = LogFactory.getLog(ProcessorPool.class);
    private static final ExecutorService POOL = Executors.newFixedThreadPool(1000);
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
