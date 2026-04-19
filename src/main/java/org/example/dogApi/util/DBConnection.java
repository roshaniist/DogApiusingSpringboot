package org.example.dogApi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DBConnection {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Properties props = new Properties();
            InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("db.properties");

            if (in == null) {
                throw new RuntimeException("db.properties file not found in resources folder!");
            }

            props.load(in);
            in.close();

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return connection;
    }
}
