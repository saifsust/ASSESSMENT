package com.customerprocessor.util;

import com.customerprocessor.model.Customer;

import java.util.*;

/**
 * Class is used to read/write data into file.
 */
public final class FileProcessor {
    private static final String FILE_PATH = "/1M-customers.txt";
    private static FileProcessor instance;
    private final Scanner scanner;

    private FileProcessor() {
        this.scanner = new Scanner(getClass().getResourceAsStream(FILE_PATH));
    }

    public static FileProcessor getInstance() {
        if (instance == null) {
            instance = new FileProcessor();
        }
        return instance;
    }

    /**
     * Method is used to read all customers information.
     *
     * @return {@link List} of customers.
     */
    public List<Customer> readAllCustomer() {
        var customers = new ArrayList<Customer>();
        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            customers.add(Customer
                    .builder(line)
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

    /**
     * Method is used to read all uniques customers information.
     *
     * @return {@link Set} of customers.
     */
    public Set<Customer> readUniqueCustomers() {
        var customers = new HashSet<Customer>();
        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            customers.add(Customer
                    .builder(line)
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
