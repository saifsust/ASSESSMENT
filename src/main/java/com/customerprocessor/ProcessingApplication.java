package com.customerprocessor;

import com.customerprocessor.util.FileProcessor;
import com.customerprocessor.processor.Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.MessageFormat;
import java.time.LocalTime;
import java.util.function.Supplier;

public class ProcessingApplication {
    private static final Log LOGGER = LogFactory.getLog(ProcessingApplication.class);
    private static final Supplier<FileProcessor> PROCESSOR_SUPPLIER = FileProcessor::getInstance;

    public static void main(String[] args) {
        LOGGER.info(MessageFormat.format("{0}", "APPLICATION IS STARTED"));
        var start = LocalTime.now();
        var processingValidCustomers = new Processor(PROCESSOR_SUPPLIER, true);
        var processingInvalidCustomers = new Processor(PROCESSOR_SUPPLIER, false);
        var processingAllCustomers = new Thread(() -> {
            var allCustomers = PROCESSOR_SUPPLIER.get().readAllCustomer();
            LOGGER.info(MessageFormat.format("All Customers : {0}", allCustomers.size()));
        });
        processingValidCustomers.start();
        processingInvalidCustomers.start();
        processingAllCustomers.start();
        var end = LocalTime.now();
        LOGGER.info(MessageFormat.format("{0}", "APPLICATION IS ENDED"));
        LOGGER.info(MessageFormat.format("Execution Time : {0} ns", end.getNano() - start.getNano()));
    }
}
