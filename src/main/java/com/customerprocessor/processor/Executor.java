package com.customerprocessor.processor;

import com.customerprocessor.database.MysqlConnector;
import com.customerprocessor.model.CustomerType;
import com.customerprocessor.util.ReadProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Class is used to process data of customer with parallel.
 */
public final class Executor extends Thread {
    private static final Log LOGGER = LogFactory.getLog(Executor.class);
    private final Supplier<ReadProcessor> processorSupplier;
    private final Supplier<MysqlConnector> connector;
    private final CustomerType customerType;
    public Executor(Supplier<ReadProcessor> processorSupplier,
                    Supplier<MysqlConnector> connector, CustomerType customerType) {
        this.processorSupplier = processorSupplier;
        this.connector = connector;
        this.customerType = customerType;
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
                .filter(customer -> !(customer.isValid() ^ customerType.isEqual(CustomerType.VALID)))
                .collect(Collectors.toList());
        final int SIZE = 1000;
        var pool = ProcessorPool.getInstance();
        for(int index = 0; index < customers.size(); index++){
            pool.submit(new Inserter(index, Math.min(index + SIZE, customers.size()), customers,this.customerType,
                    connector.get().getStatement()));
        }
        LOGGER.info("Submitted all Threads");
        pool.shutDown();
    }
}
