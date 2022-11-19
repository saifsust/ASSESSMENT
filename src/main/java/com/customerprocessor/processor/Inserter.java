package com.customerprocessor.processor;

import com.customerprocessor.model.Customer;
import com.customerprocessor.model.CustomerType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public final class Inserter extends Thread{
    private static final Log LOGGER = LogFactory.getLog(Inserter.class);
    private final int start;
    private final int end;
    private final List<Customer> customers;
    private final CustomerType customerType;
    private final Optional<Statement> statement;

    public Inserter(int start, int end,
                    List<Customer> customers,
                    CustomerType customerType,
                    Optional<Statement> statement) {
        this.start = start;
        this.end = end;
        this.customers = customers;
        this.customerType = customerType;
        this.statement = statement;
    }

    @Override
    public void run() {
        var queryValue = "";
        for (int index = start; index < end; index++) {
            queryValue += customers.get(index).toValue();
            if (index < end - 1) {
                queryValue += ",";
            }
        }

        try {
            var statement = this.statement.get();
            statement.execute(query(queryValue));
           // statement.close();
        } catch (SQLException exception) {
            LOGGER.error(exception);
        }
        LOGGER.info("Successfully inserted all customers");
    }

    private String query(String customer) {
        return String
                .format("INSERT INTO oragetoolz.%s(first_name, last_name, city, state, postal_zip_code, " +
                        "phone_number, email, ip_address) values %s", customerType.getTable(), customer);
    }
}
