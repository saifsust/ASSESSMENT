package com.customerprocessor.processor;

import com.customerprocessor.model.Customer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.customerprocessor.constant.Constants.USER_DIRECTORY;

public final class Task extends Thread {
    private static final Log LOGGER = LogFactory.getLog(Task.class);
    private final Path path = Paths.get(USER_DIRECTORY, "src/main", "resources", this.getName().concat(".csv"));
    private PrintWriter writer;
    private final List<Customer> customers;

    public Task(List<Customer> customers) {
        this.customers = customers;
        try {
            this.writer = new PrintWriter(path.toFile());
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void run() {
        LOGGER.info(String.format("part size : %d \n",customers.size()));
        customers.forEach(customer -> this.writer.write(customer.toString()));
        this.writer.close();
    }
}
