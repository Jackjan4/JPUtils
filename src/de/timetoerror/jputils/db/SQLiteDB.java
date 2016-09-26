/*
 * Copyright 2016, Jan-Philipp Roslan, Alle Rechte vorbehalten
 */
package de.timetoerror.jputils.db;

import java.sql.PreparedStatement;

/**
 *
 * @author Jackjan
 */
public class SQLiteDB implements SQLDB
{

    @Override
    public void connect(String hostname, int port, String dbname, String username, String pass) {
        
    }

    @Override
    public PreparedStatement getStatement(String sql) {
       PreparedStatement result = null;
       
       return result;
    }
    
}
