package com.customerprocessor.action;

import com.customerprocessor.ProcessingApplication;
import com.customerprocessor.database.MysqlConnector;
import com.customerprocessor.model.Customer;
import com.customerprocessor.model.CustomerType;
import com.customerprocessor.processor.InsertionExecutor;
import com.customerprocessor.util.ReadProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.MessageFormat;
import java.time.LocalTime;
import java.util.Set;
import java.util.function.Supplier;

public class Insertion {
    private static final Log LOGGER = LogFactory.getLog(ProcessingApplication.class);
    private static final Supplier<MysqlConnector> CONNECTOR = MysqlConnector::getInstance;
    private static final Set<Customer> customers = ReadProcessor.getInstance().getAllUniqueCustomers();


    public static void start(){
        LOGGER.info(MessageFormat.format("{0}", "APPLICATION IS STARTED"));
        var start = LocalTime.now();
        Preparation
                .prepare()
                .add(new InsertionExecutor(customers, CONNECTOR, CustomerType.VALID))
                .add(new InsertionExecutor(customers, CONNECTOR, CustomerType.INVALID))
                .shutdown();
        var end = LocalTime.now();
        LOGGER.info(MessageFormat.format("{0}", "APPLICATION IS ENDED"));
        LOGGER.info(MessageFormat.format("Execution Time : {0} ns", end.getNano() - start.getNano()));
    }
}
