package com.customerprocessor.database;

import com.customerprocessor.exception.ApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.util.Optional;

public class MysqlConnector {
    private static final Log LOGGER = LogFactory.getLog(MysqlConnector.class);
    private static final String URL = "jdbc:mysql://localhost:3306/oragetoolz";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static  final String username = "admin";
    private static final String password = "admin";
    private Connection connection;
    private static  MysqlConnector instance;

    private MysqlConnector() {
        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, username, password);
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
