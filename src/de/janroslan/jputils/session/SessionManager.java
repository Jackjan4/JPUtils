/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.janroslan.jputils.session;

/**
 *
 * @author jackjan
 */
public  class SessionManager {
    
    private static Session current;

    public static Session getCurrentSession() {
        return current;
    }

    public static void setCurrentSession(Session current) {
        SessionManager.current = current;
    }
    
    
    
    
}
