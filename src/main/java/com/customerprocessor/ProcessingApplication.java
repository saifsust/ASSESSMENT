package com.customerprocessor;

import com.customerprocessor.processor.Executor;
import com.customerprocessor.util.ReadProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.MessageFormat;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class ProcessingApplication {
    private static final Log LOGGER = LogFactory.getLog(ProcessingApplication.class);
    private static final Supplier<ReadProcessor> PROCESSOR_SUPPLIER = ReadProcessor::getInstance;

    public static void main(String[] args) throws InterruptedException {
        LOGGER.info(MessageFormat.format("{0}", "APPLICATION IS STARTED"));
        var start = LocalTime.now();
        var preparationPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        preparationPool.submit(new Executor(PROCESSOR_SUPPLIER, true));
        preparationPool.submit(new Executor(PROCESSOR_SUPPLIER, false));
        while (preparationPool.awaitTermination(1000, TimeUnit.MICROSECONDS));
        preparationPool.shutdown();
        var end = LocalTime.now();
        LOGGER.info(MessageFormat.format("{0}", "APPLICATION IS ENDED"));
        LOGGER.info(MessageFormat.format("Execution Time : {0} ns", end.getNano() - start.getNano()));
        LOGGER.info(Runtime.getRuntime().availableProcessors());
    }
}
