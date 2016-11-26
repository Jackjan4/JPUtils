/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.timetoerror.jputils.jfx;

/**
 *
 * @author jackjan
 */
public interface AdvancedController {
    public void onInitFinished();
    
    
    /**
     * Gets called when this controller is about to be unloaded.
     */
    public void unload();
}
