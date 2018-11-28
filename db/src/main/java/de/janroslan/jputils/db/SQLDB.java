/*
 * Copyright 2016, Jan-Philipp Roslan, Alle Rechte vorbehalten
 */
package de.janroslan.jputils.db;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Jan-Philipp Roslan
 */
public abstract class SQLDB {



    private enum DBMode {
        SQLite, MySQL
    };



    private String hostName;
    private String dbName;
    private int port;
    private String username;
    private String pass;
    private DBMode mode;
    private boolean init;



    /**
     *
     * @param hostName
     * @param port
     * @param dbName
     * @param username
     * @param pass
     */
    protected SQLDB(String hostName, int port, String dbName, String username, String pass) {
        this.hostName = hostName;
        this.dbName = dbName;
        this.port = port;
        this.username = username;
        this.pass = pass;
        mode = DBMode.MySQL;
        init = false;
    }


    /**
     *
     * @param hostName
     * @param dbName
     */
    protected SQLDB(String hostName, String dbName) {
        this.hostName = hostName;

        this.dbName = dbName;
        mode = DBMode.SQLite;
        init = false;
    }



    /**
     * Initializes the given JDBC driver and creates a test connection to the
     * given database to check if the given details are correct
     *
     * @param driverPath
     * @return
     * @throws ClassNotFoundException
     */
    public final boolean init(String driverPath) throws ClassNotFoundException {
        // Init JDBC
        Class.forName(driverPath);

        // Create hostName-Path if not existent
        if (!hostName.equals("") && !new File(hostName).exists()) {
            new File(hostName).mkdirs();
        }

        // Test connection
        Connection c = getSeparateConnection();
        if (c != null) {
            try {
                c.close();
            } catch (SQLException ex) {
                return false;
            }
            return true;
        }

        return false;
    }



    /**
     *
     * @return
     */
    public abstract Connection getSeparateConnection();



    /**
     * Executes an Update in a seperate connection
     *
     * @param sql
     */
    public int execUnpreparedUpdateSeparate(String sql) {
        try {
            Connection c = getSeparateConnection();

            Statement stmt = c.createStatement();
            int i = stmt.executeUpdate(sql);
            stmt.close();
            c.close();
            return i;
        } catch (SQLException ex) {
            return -1;
        }
    }



    /**
     * Executes a Query in a seperate connection The returned ResultSet MUST be
     * closed and with it
     *
     * @param sql
     * @return
     */
    public ArrayList<ArrayList<Object>> execUnpreparedQuerySeperate(String sql) {
        ArrayList<ArrayList<Object>> result = new ArrayList<>();

        try {
            Connection c = getSeparateConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData meta = rs.getMetaData();
            while (rs.next()) {
                ArrayList<Object> current = new ArrayList<>();

                for (int i = 0; i < meta.getColumnCount(); i++) {
                    current.add(rs.getObject(i + 1));
                }
                result.add(current);
            }
            rs.close();
            stmt.close();
            c.close();
            return result;
        } catch (SQLException ex) {
            return null;
        }
    }



    /**
     *
     * @param sql
     * @param objs
     * @param types
     * @return
     */
    public PreparedStatement prepareUpdateSeperate(String sql, Object[] objs, int... types) {

        try {
            Connection c = getSeparateConnection();
            return c.prepareStatement(sql);
        } catch (SQLException ex) {
            return null;
        }
    }



    /**
     *
     * @param stmt
     * @param objs
     * @param types
     * @return
     */
    public void execPreparedUpdate(PreparedStatement stmt, Object[] objs, int... types) {
        try {
            for (int i = 0; i < objs.length; i++) {
                stmt.setObject(i + 1, objs[i], types[i]);
            }

            stmt.executeUpdate();
            stmt.close();
            stmt.getConnection().close();

        } catch (SQLException ex) {
        }
    }



    /**
     *
     * @param sql
     * @param objs
     * @param types
     */
    public void execPreparedUpdateSeperate(String sql, Object[] objs, int... types) {

        try {
            Connection c = getSeparateConnection();
            PreparedStatement stmt = c.prepareStatement(sql);

            for (int i = 0; i < objs.length; i++) {
                stmt.setObject(i + 1, objs[i], types[i]);
            }

            stmt.executeUpdate();
            stmt.close();
            stmt.getConnection().close();

        } catch (SQLException ex) {
        }
    }



    /**
     *
     * @param sql
     * @param objs
     * @param types
     * @return 
     */
    public ArrayList<ArrayList<Object>> execPreparedQuerySeperate(String sql, Object[] objs, int... types) {
        ArrayList<ArrayList<Object>> result = new ArrayList<>();
        try {
        Connection c = getSeparateConnection();
            PreparedStatement stmt = c.prepareStatement(sql);

            
            // Add all parameters
            for (int i = 0; i < objs.length; i++) {
                stmt.setObject(i + 1, objs[i], types[i]);
            }

            ResultSet rs = stmt.executeQuery();
            
            // Collect results for return
            ResultSetMetaData meta = rs.getMetaData();
            while (rs.next()) {
                ArrayList<Object> current = new ArrayList<>();

                for (int i = 0; i < meta.getColumnCount(); i++) {
                    current.add(rs.getObject(i + 1));
                }
                result.add(current);
            }
            
            // Close all connections
            rs.close();
            stmt.close();
            c.close();

            return result;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void connectionPool() {

    }



    // ###  Getters & Setters ###
    public String getHostName() {
        return hostName;
    }

    public String getDbName() {
        return dbName;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }

    public DBMode getMode() {
        return mode;
    }

}
