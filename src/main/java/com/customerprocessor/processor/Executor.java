package com.customerprocessor.processor;

import com.customerprocessor.database.MysqlConnector;
import com.customerprocessor.util.ReadProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.function.Supplier;

import static com.customerprocessor.constant.Constants.INVALID_CUSTOMERS;
import static com.customerprocessor.constant.Constants.VALID_CUSTOMERS;

/**
 * Class is used to process data of customer with parallel.
 */
public final class Executor extends Thread {
    private static final Log LOGGER = LogFactory.getLog(Executor.class);
    private final Supplier<ReadProcessor> processorSupplier;
    private final Supplier<MysqlConnector> connector;
    private final boolean isValidCustomer;
    private static final int PARTITION_SIZE = 10000;
    public Executor(Supplier<ReadProcessor> processorSupplier,
                    Supplier<MysqlConnector> connector, boolean isValidCustomer) {
        this.processorSupplier = processorSupplier;
        this.connector = connector;
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
        this.processorSupplier.get().getAllUniqueCustomers()
                .stream()
                .filter(customer -> !(customer.isValid() ^ this.isValidCustomer))
                .forEach(customer ->connector.get().execute(query(customer.toValue())));
           LOGGER.info("Successfully inserted all customers");

        var message = isValidCustomer ? "VALID CUSTOMERS : {0}" : "INVALID CUSTOMERS : {0}";

//        var service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        for(int start = 0; start < customers.size(); start += PARTITION_SIZE){
//            System.out.println(customers.get(start).toValue());
//            var partition = customers.subList(start, Math.min(customers.size(), start + PARTITION_SIZE));
//            service.submit(new Task(partition));
//        }
//        service.shutdown();
    }

    private String query(String customer){
        return String
                .format("INSERT INTO oragetoolz.%s(first_name, last_name, city, state, postal_zip_code, " +
                                "phone_number, email, ip_address) value%s",
                this.isValidCustomer ? VALID_CUSTOMERS : INVALID_CUSTOMERS, customer);
    }
}
