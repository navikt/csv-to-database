package no.nav.tools.csvtodatabase;

import java.sql.*;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseService {

    private static Connection connection;
    private static PreparedStatement statement;

    private final Properties configuration;

    AtomicInteger totalCounter = new AtomicInteger();
    AtomicInteger batchCounter = new AtomicInteger();

    public DatabaseService(Properties configuration) {
        this.configuration = configuration;
    }

    public void connect() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle Driver Class not found. Exception: " + e);
        }

        DriverManager.setLoginTimeout(5);

        try {
            connection = DriverManager.getConnection(configuration.getProperty("database-uri"), configuration.getProperty("database-username"), configuration.getProperty("database-password"));
            connection.setAutoCommit(false);
            System.out.println("Connected to database!");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }

        statement = connection.prepareStatement(configuration.getProperty("sql"));
    }

    public void insert(String[] row) {
        int columnCounter = 1;

        try {
            for(String column : row) {
                statement.setString(columnCounter, column);
                ++columnCounter;
            }
            statement.addBatch();

        } catch(SQLException e) {
            e.printStackTrace();
            disconnect();
        }

        totalCounter.incrementAndGet();
        batchCounter.incrementAndGet();

        if(batchCounter.get() >= Integer.parseInt(configuration.getProperty("batch-size")) ) {
            flush();
        }
    }

    public void flush() {
        try {
            statement.executeBatch();
            connection.commit();
            System.out.println(totalCounter + " rows committed to database");
            batchCounter.set(0);
        } catch(SQLException e) {
            System.out.println("Failed to insert identer at counter " + totalCounter);
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            statement.close();
            connection.close();
            System.out.println("Disconnected from database!");
        } catch(SQLException e) {
            System.out.println("Failed to close connection. Exception: ");
            e.printStackTrace();
        }
    }
}
