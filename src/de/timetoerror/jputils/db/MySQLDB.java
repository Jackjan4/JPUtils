/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.timetoerror.jputils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Jackjan
 */
public class MySQLDB implements SQLDB
{

    private static MySQLDB db;
    private Connection conn;

    public MySQLDB(String driverPath) {
        try {
            Class.forName(driverPath);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: MySQL Driver could not be loaded");
        }
    }

    @Override
    public void connect(String hostname, int port, String dbname, String username, String pass) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + dbname, username, pass);
        } catch (SQLException ex) {
            conn = null;
            System.out.println("Error: Could not connect to MySQL database");
        }
    }

    public static SQLDB getInstance(String driver) {
        if (db == null) {
            db = new MySQLDB(driver);
        }

        return db;
    }

    @Override
    public PreparedStatement getStatement(String sql) {
        PreparedStatement result = null;
        try {
            result = conn.prepareStatement(sql);
        } catch (SQLException ex) {
            System.out.println("Error: ");
        }
        
        return result;
    }


}
