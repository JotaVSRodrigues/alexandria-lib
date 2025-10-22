package org.example.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private static final String URL = "jdbc:sqlite:C:/Users/Suporte/Documents/alexandria-lib/alexandria.db";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Wasn't possible to connect your database - " + e.getMessage());
            return null;
        }
    }
}
