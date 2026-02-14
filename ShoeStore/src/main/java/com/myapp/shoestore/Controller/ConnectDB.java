package com.myapp.shoestore.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {

    public static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        String URL = "jdbc:sqlserver://localhost:1433;databaseName=ShoeDB;trustServerCertificate=true;";
        String DBNAME = "sa";
        String DBPASS = "khang1234";
        connection = DriverManager.getConnection(URL,DBNAME,DBPASS);
        return connection;
    }

}
