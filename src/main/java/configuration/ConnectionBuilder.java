package configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for simple access to DB*/
public class ConnectionBuilder {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private final static String DB_USER = "postgres";
    private final static String DB_PASSWORD = "postgres";
    private final static String DB_URL = "jdbc:postgresql://localhost:5432/hospital_db";

    /**
     * Gives the connection for access to DB*/
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}