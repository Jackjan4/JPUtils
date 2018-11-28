/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.janroslan.jputils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jackjan
 */
public class MySQLDB extends SQLDB {

    public MySQLDB(String hostName, int port, String dbName, String username, String pass) {
        super(hostName, port, dbName, username, pass);
    }


    /**
     * @return
     */
    @Override
    public Connection getSeparateConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + getHostName() + ":"
                    + getPort() + "/" + getDbName() + "?" + "user=" + getUsername() + "&"
                    + "password=" + getPass());
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDB.class.getName()).log(Level.SEVERE, null, ex);

            return null;
        }
    }

}
