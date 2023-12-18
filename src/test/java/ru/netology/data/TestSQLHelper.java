package ru.netology.data;

import lombok.Getter;
import lombok.SneakyThrows;

import java.sql.*;

public class TestSQLHelper {
    private static String dbUrl = System.getProperty("db.url"); //для запуска из консоли
    private static String dbUser = System.getProperty("db.user");
    private static String dbPass = System.getProperty("db.pass");
    @Getter
    private static final String payTable = "payment_entity";
    @Getter
    private static final String creditTable = "credit_request_entity";

    @SneakyThrows(SQLException.class)
    public static void cleanTables() {
        String cleanCreditTables = "DELETE FROM credit_request_entity;";
        String cleanOrderTable = "DELETE FROM order_entity;";
        String cleanPaymentTable = "DELETE FROM payment_entity;";
        Connection c = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement s = c.createStatement();
        s.executeUpdate(cleanCreditTables);
        s.executeUpdate(cleanOrderTable);
        s.executeUpdate(cleanPaymentTable);
        c.close();
    }
    @SneakyThrows(SQLException.class)
    public static String getOperationStatus(String table) {
        String status = "";
        Connection c = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement s = c.createStatement();
        ResultSet resultSet = s.executeQuery("SELECT * FROM " + table + " ORDER BY id DESC LIMIT 1;");
        while (resultSet.next()) status = resultSet.getString("status");
        return status;
    }
    @SneakyThrows()
    public void run() {
        throw new SQLException();
    }
}

