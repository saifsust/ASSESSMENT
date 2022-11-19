package com.customerprocessor.processor;

import com.customerprocessor.model.Customer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.customerprocessor.constant.Constants.PARTITION_SIZE;

public final class InserterProcessor extends Thread{
    private static final Log LOGGER = LogFactory.getLog(InserterProcessor.class);
    private final int start;
    private final int end;
    private final List<Customer> customers;
    private final Optional<PreparedStatement> statement;

    public InserterProcessor(int start, int end,
                             List<Customer> customers,
                             Optional<PreparedStatement> statement) {
        this.start = start;
        this.end = end;
        this.customers = customers;
        this.statement = statement;
        super.setPriority(1);
        super.setName("Thread PART - ".concat(String.valueOf(start)));
    }

    @Override
    public void run() {
        System.out.printf("====================== running thread ================== \n %s\n", this.getName());
        try {
            var statement = this.statement.get();

            for(int index = this.start ; index < this.end; index++){
                var customer = customers.get(index);
                statement.setObject(1, customer.getFirstName());
                statement.setObject(2, customer.getLastName());
                statement.setObject(3, customer.getCity());
                statement.setObject(4, customer.getState());
                statement.setObject(5, customer.getPostalZipCode());
                statement.setObject(6, customer.getPhoneNumber());
                statement.setObject(7, customer.getEmail());
                statement.setObject(8, customer.getIpAddress());
                statement.addBatch();
            }
            statement.executeBatch();
            statement.close();
        } catch (SQLException exception) {
            LOGGER.error(exception);
        }
        LOGGER.info(String.format("Successfully inserted all customers. %d %d",this.start, this.end));
        this.stop();
    }
}
