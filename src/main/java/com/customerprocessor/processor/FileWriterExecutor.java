package com.customerprocessor.processor;

import com.customerprocessor.database.MysqlConnector;
import com.customerprocessor.exception.ApplicationException;
import com.customerprocessor.model.Customer;
import com.customerprocessor.model.CustomerType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Supplier;

import static com.customerprocessor.constant.Constants.FILE_WRITE_PARTITION_SIZE;

public final class FileWriterExecutor extends AbstractExecutor {

    public FileWriterExecutor(Supplier<MysqlConnector> connector, CustomerType customerType) {
        super(null, connector, customerType);
    }

    @Override
    public void run() {
        synchronized (getConnector()) {
            var statement = getConnector().get().getStatement();
            if (statement.isPresent()) {
                int start = 0;
                try {
                    while (true) {
                        var resultSet = statement.get().executeQuery(query(start));
                        var customers = new ArrayList<Customer>();
                        while (resultSet.next()) {
                            customers.add(Customer
                                    .builder()
                                    .firstName(resultSet.getString("first_name"))
                                    .lastName(resultSet.getString("last_name"))
                                    .city(resultSet.getString("city"))
                                    .state(resultSet.getString("state"))
                                    .postalZipCode(resultSet.getString("postal_zip_code"))
                                    .phoneNumber(resultSet.getString("phone_number"))
                                    .email(resultSet.getString("email"))
                                    .ipAddress(resultSet.getString("ip_address"))
                                    .build());

                        }
                        start += FILE_WRITE_PARTITION_SIZE;
                        if (customers.isEmpty()) {
                            break;
                        }
                        getRunner().add(new WriterProcessor(customers, getCustomerType()));
                    }
                    statement.get().close();
                } catch (SQLException exception) {
                    throw new ApplicationException(exception);
                }
            }
            parallelism();
            getLOGGER().info("Submitted all Threads");
        }
    }
}
