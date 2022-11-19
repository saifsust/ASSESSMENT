package com.customerprocessor.processor;

import com.customerprocessor.database.MysqlConnector;
import com.customerprocessor.model.Customer;
import com.customerprocessor.model.CustomerType;

import java.util.Set;
import java.util.function.Supplier;

public final class FileWriterExecutor extends AbstractExecutor {

    public FileWriterExecutor(Set<Customer> customers, Supplier<MysqlConnector> connector, CustomerType customerType) {
        super(customers, connector, customerType);
    }

    @Override
    public void run() {
        var statement = getConnector().get().getStatement();
        if(statement.isPresent()){
            int start = 0;

        }
    }
}
