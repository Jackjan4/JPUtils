/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.janroslan.jputils.jfx;


import javafx.scene.Parent;



/**
 *
 * @author Jackjan
 */
public class FXMLPackage {
    private Parent viewParent;
    private Object controller;


    public FXMLPackage(Parent viewParent, Object controller) {
        this.viewParent = viewParent;
        this.controller = controller;
    }


    public Parent getViewParent() {
        return viewParent;
    }


    public Object getController() {
        return controller;
    }
    
    
}
