package com.abinayamart.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * AbinayaMart DB connection helper (JDBC + MySQL).
 * Edit URL / USER / PASSWORD to match your local MySQL setup.
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/abinayamart?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "Abinaya@123";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("[AbinayaMart] MySQL driver not found. Add mysql-connector-j to lib/ or pom.xml");
        }
    }

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
