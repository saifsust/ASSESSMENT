package com.customerprocessor.processor;

import com.customerprocessor.util.ReadProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.MessageFormat;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Class is used to process data of customer with parallel.
 */
public final class Executor extends Thread {
    private static final Log LOGGER = LogFactory.getLog(Executor.class);
    private final Supplier<ReadProcessor> processorSupplier;
    private final boolean isValidCustomer;
    private static final int PARTITION_SIZE = 10000;
    public Executor(Supplier<ReadProcessor> processorSupplier,
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
        var customers = this.processorSupplier.get().getAllUniqueCustomers()
                .stream()
                .filter(customer -> !(customer.isValid() ^ this.isValidCustomer))
                .collect(Collectors.toList());
        var message = isValidCustomer ? "VALID CUSTOMERS : {0}" : "INVALID CUSTOMERS : {0}";
        LOGGER.info(MessageFormat.format(message,customers.size()));
        var service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for(int start = 0; start < customers.size(); start += PARTITION_SIZE){
            var partition = customers.subList(start, Math.min(customers.size(), start + PARTITION_SIZE));
            service.submit(new Task(partition));
        }
        service.shutdown();
    }
}
