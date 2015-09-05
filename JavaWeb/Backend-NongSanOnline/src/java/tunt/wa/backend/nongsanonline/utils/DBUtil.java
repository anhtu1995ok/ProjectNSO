/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunt.wa.backend.nongsanonline.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

/**
 *
 * @author Thanh Tu
 */
public class DBUtil {

    private static Connection connect;

    private Connection createConnect() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            String dbURL = "jdbc:sqlserver://localhost\\TELSOFT;databaseName=itplus;integratedSecurity=true";
            String dbURL = "jdbc:sqlserver://localhost\\TUNTINSTANCE;databaseName=webservice";
            String user = "tunt";
            String pass = "123456";
            conn = DriverManager.getConnection(dbURL, user, pass);
            if (conn != null) {
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
                System.out.println("Connected");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    public static Connection connectSQL() {
        if (connect == null) {
            connect = new DBUtil().createConnect();
        }
        return connect;
    }

}
