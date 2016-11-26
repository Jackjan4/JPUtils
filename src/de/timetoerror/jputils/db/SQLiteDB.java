/*
 * Copyright 2016, Jan-Philipp Roslan, Alle Rechte vorbehalten
 */
package de.timetoerror.jputils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Jackjan
 */
public class SQLiteDB extends SQLDB {

    public SQLiteDB(String hostName, String dbName) {
        super(hostName, dbName);
    }

    @Override
    public Connection getSeperateConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + getHostName() + getDbName());
        } catch (SQLException ex) {
            return null;
        }
        
    }
}
