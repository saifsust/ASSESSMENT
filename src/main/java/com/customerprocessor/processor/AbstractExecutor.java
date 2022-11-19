package com.customerprocessor.processor;

import com.customerprocessor.database.MysqlConnector;
import com.customerprocessor.model.Customer;
import com.customerprocessor.model.CustomerType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

import static com.customerprocessor.constant.Constants.FILE_WRITE_PARTITION_SIZE;

public abstract class AbstractExecutor extends Thread {
    private static final Log LOGGER = LogFactory.getLog(AbstractExecutor.class);
    private final Set<Customer> customers;
    private final Supplier<MysqlConnector> connector;
    private final CustomerType customerType;
    private final ConcurrentLinkedQueue<Thread> runner = new ConcurrentLinkedQueue<>();

    public AbstractExecutor(Set<Customer> customers, Supplier<MysqlConnector> connector, CustomerType customerType) {
        this.customers = customers;
        this.connector = connector;
        this.customerType = customerType;
        this.setPriority(1);
    }

    public static Log getLOGGER() {
        return LOGGER;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public Supplier<MysqlConnector> getConnector() {
        return connector;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    protected String query() {
        return String
                .format("INSERT INTO oragetoolz.%s(first_name, last_name, city, state, postal_zip_code, " +
                        "phone_number, email, ip_address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", customerType.getTable());
    }

    protected String query(int start) {
        return String
                .format("SELECT * FROM oragetoolz.%s LIMIT %s,%s", customerType.getTable(),start, FILE_WRITE_PARTITION_SIZE);
    }

    public ConcurrentLinkedQueue<Thread> getRunner() {
        return runner;
    }

    protected void parallelism(){
        while (!runner.isEmpty()){
            if(Thread.activeCount() < Runtime.getRuntime().availableProcessors()){
                System.out.printf("Activated: %d\n" , Thread.activeCount());
                var thread = runner.poll();
                thread.start();
            }
        }
    }
}
