package com.customerprocessor.processor;

import com.customerprocessor.util.FileProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.MessageFormat;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Class is used to process data of customer with parallel.
 */
public final class Processor extends Thread {
    private static final Log LOGGER = LogFactory.getLog(Processor.class);
    private final Supplier<FileProcessor> processorSupplier;
    private final boolean isValidCustomer;

    public Processor(Supplier<FileProcessor> processorSupplier,
                     boolean isValidCustomer) {
        this.processorSupplier = processorSupplier;
        this.isValidCustomer = isValidCustomer;
    }

    /**
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
        var customers = processorSupplier.get().readUniqueCustomers();
        var customerList = customers
                .stream()
                .filter(customer -> !(customer.isValid() ^ this.isValidCustomer))
                .collect(Collectors.toList());
        var message = isValidCustomer ? "VALID CUSTOMERS : {0}" : "INVALID CUSTOMERS : {0}";
        LOGGER.info(MessageFormat.format(message, customerList.size()));
    }
}
