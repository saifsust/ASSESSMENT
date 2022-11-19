package com.customerprocessor.action;

import com.customerprocessor.ProcessingApplication;
import com.customerprocessor.database.MysqlConnector;
import com.customerprocessor.model.CustomerType;
import com.customerprocessor.processor.FileWriterExecutor;
import com.customerprocessor.processor.InsertionExecutor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.MessageFormat;
import java.time.LocalTime;
import java.util.function.Supplier;

public class Partition {
    private static final Log LOGGER = LogFactory.getLog(ProcessingApplication.class);
    private static final Supplier<MysqlConnector> CONNECTOR = MysqlConnector::getInstance;

    public static void start(){
        LOGGER.info(MessageFormat.format("{0}", "APPLICATION IS STARTED"));
        var start = LocalTime.now();
        Preparation
                .prepare()
                .add(new FileWriterExecutor(CONNECTOR, CustomerType.VALID))
                .add(new FileWriterExecutor(CONNECTOR, CustomerType.INVALID))
                .shutdown();
        var end = LocalTime.now();
        LOGGER.info(MessageFormat.format("{0}", "APPLICATION IS ENDED"));
        LOGGER.info(MessageFormat.format("Execution Time : {0} ns", end.getNano() - start.getNano()));
    }
}
