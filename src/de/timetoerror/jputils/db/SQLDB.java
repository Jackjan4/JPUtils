/*
 * Copyright 2016, Jan-Philipp Roslan, Alle Rechte vorbehalten
 */
package de.timetoerror.jputils.db;

import java.sql.PreparedStatement;

/**
 *
 * @author Jackjan
 */
public interface SQLDB
{
    public void connect(String hostname,int port, String dbname, String username, String pass);
    
    public PreparedStatement getStatement(String sql);
}
