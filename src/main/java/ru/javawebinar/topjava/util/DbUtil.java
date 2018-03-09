package ru.javawebinar.topjava.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {

    private static Connection connection = null;

    public static Connection getConnection(){
        if (connection != null) return connection;
        else {
            try {
            Properties properties = new Properties();
            InputStream in = DbUtil.class.getClassLoader().getResourceAsStream("/db.properties");
            properties.load(in);
            Class.forName(properties.getProperty("driver"));
            connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"), properties.getProperty("password"));
            } catch (IOException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

        }
        return connection;
    }
}
