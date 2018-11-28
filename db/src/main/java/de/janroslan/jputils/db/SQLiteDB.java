/*
 * Copyright 2016, Jan-Philipp Roslan, Alle Rechte vorbehalten
 */
package de.janroslan.jputils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Jackjan
 */
public class SQLiteDB extends SQLDB {



    /**
     * Creates a new SQLiteDB instance with the given hostname and db name
     * @param hostName
     * @param dbName
     */
    public SQLiteDB(String hostName, String dbName) {
        super(hostName, dbName);
    }



    /**
     * Connects to the initiated db
     * @return
     */
    @Override
    public Connection getSeparateConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + getHostName() + getDbName());
        } catch (SQLException ex) {
            return null;
        }
        
    }
}
