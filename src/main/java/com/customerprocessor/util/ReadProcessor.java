package com.customerprocessor.util;

import com.customerprocessor.model.Customer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.customerprocessor.constant.Constants.USER_DIRECTORY;

/**
 * Class is used to read/write data into file.
 */
public final class ReadProcessor {
    private static final Log LOGGER = LogFactory.getLog(ReadProcessor.class);
    private static final String FILE_PATH = "/1M-customers.txt";
    private static final Path path = Paths.get(USER_DIRECTORY, "src/main", "resources", FILE_PATH);
    private static ReadProcessor instance;
    private  BufferedReader bufferedReader;

    private ReadProcessor() {
        try {
            File file = path.toFile();
           this.bufferedReader =  new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ReadProcessor getInstance() {
        if (instance == null) {
            instance = new ReadProcessor();
        }
        return instance;
    }

    public Optional<String> readLine() {
        try {
            return Optional.ofNullable(bufferedReader.readLine());
        } catch (IOException e) {
            LOGGER.warn(e);
            return Optional.empty();
        }
    }

    public void close() {
        if (this.bufferedReader != null) {
            try {
                this.bufferedReader.close();
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
    }

    /**
     * Method is used to read all uniques customers information.
     *
     * @return {@link Set} of customers.
     */
    public Set<Customer> getAllUniqueCustomers() {
        var customers = new HashSet<Customer>();
        Optional<String> line;
        while ((line = readLine()).isPresent()) {
            customers.add(
                    Customer
                    .builder(line.get())
                    .name()
                    .branch()
                    .city()
                    .geoCode()
                    .postalZipCode()
                    .phoneNumber()
                    .email()
                    .ipAddress()
                    .build());
        }
        return customers;
    }
}
