package com.customerprocessor.database;

import com.customerprocessor.constant.Constants;
import com.customerprocessor.exception.ApplicationException;
import com.customerprocessor.util.SourceProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.util.Optional;

public class MysqlConnector {
    private static final Log LOGGER = LogFactory.getLog(MysqlConnector.class);
    private static final SourceProperty SOURCE_PROPERTY = SourceProperty.getInstance();
    private Connection connection;
    private static  MysqlConnector instance;

    private MysqlConnector() {
        try {
            Class.forName(SOURCE_PROPERTY.getProperty(Constants.DRIVER));
            this.connection = DriverManager.getConnection(
                    SOURCE_PROPERTY.getProperty(Constants.URL),
                    SOURCE_PROPERTY.getProperty(Constants.USER_NAME),
                    SOURCE_PROPERTY.getProperty(Constants.PASSWORD));
            LOGGER.info("MYSQL Connection Established successfully");
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error(e);
        }
    }

    public static MysqlConnector getInstance() {
        if (instance == null) {
            instance = new MysqlConnector();
        }
        return instance;
    }

    public Optional<Statement> getStatement() {
        try {
            return Optional.ofNullable(this.connection.createStatement());
        } catch (SQLException exception) {
            LOGGER.error(exception);
        }
        return Optional.empty();
    }

    public Optional<PreparedStatement> prepareStatement(String query) {
        try {
            return Optional.ofNullable(this.connection.prepareStatement(query));
        } catch (SQLException exception) {
            LOGGER.error(exception);
        }
        return Optional.empty();
    }

    public ResultSet getResultSet(String query) {
        var statement = getStatement();
        if (statement.isPresent()) {
            try {
                return statement.get().executeQuery(query);
            } catch (SQLException exception) {
                LOGGER.error(exception);
            }
        }
        throw new ApplicationException("ResultSet can not be created.");
    }

    public boolean execute(String query) {
        var statement = getStatement();
        if (statement.isPresent()) {
            try {
                return statement.get().execute(query);
            } catch (SQLException exception) {
                LOGGER.error(exception);
            }
        }
        return false;
    }

    public void close() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException exception) {
                LOGGER.error(exception);
            }
        }
    }
}
