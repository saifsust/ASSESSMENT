package com.customerprocessor.processor;

import com.customerprocessor.database.MysqlConnector;
import com.customerprocessor.model.Customer;
import com.customerprocessor.model.CustomerType;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.customerprocessor.constant.Constants.PARTITION_SIZE;

/**
 * Class is used to process data of customer with parallel.
 */
public final class InsertionExecutor extends AbstractExecutor {

    public InsertionExecutor(Set<Customer> customers, Supplier<MysqlConnector> connector, CustomerType customerType) {
        super(customers, connector, customerType);
    }

    /**
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
        var filteredCustomers = getCustomers()
                .stream()
                .filter(customer -> !(customer.isValid() ^ getCustomerType().isEqual(CustomerType.VALID)))
                .collect(Collectors.toList());
        for (int index = 0; index < filteredCustomers.size(); index += PARTITION_SIZE) {
            getRunner().add(new InserterProcessor(index, Math.min(index + PARTITION_SIZE, filteredCustomers.size()),
                    filteredCustomers, getConnector().get().prepareStatement(query())) );
        }

        parallelism();
        getLOGGER().info("Submitted all Threads");
    }
}
