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

public final class WriterProcessor extends Thread {
    private static final Log LOGGER = LogFactory.getLog(WriterProcessor.class);
    private PrintWriter writer;
    private final List<Customer> customers;

    public WriterProcessor(List<Customer> customers) {
        super.setName("Thread Writer_"+this.getId());
        this.customers = customers;
        try {
            this.writer = new PrintWriter(Paths.get(USER_DIRECTORY, "src/main", "resources",
                    this.getName().concat(".csv")).toFile());
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void run() {
        System.out.printf("====================== running thread ================== \n %s\n", this.getName());
        LOGGER.info(String.format("part size : %d \n",customers.size()));
        customers.forEach(customer -> this.writer.write(customer.toString()));
        this.writer.close();
    }
}
