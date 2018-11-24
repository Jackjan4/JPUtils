/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.janroslan.jputils.benchmarking;

/**
 *
 * @author Jackjan
 */
public class BenchmarkStamp {
 
    private String title;
    private long time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BenchmarkStamp(String title, long time) {
        this.title = title;
        this.time = time; 
      
    }

    public long getTime() {
        return time;
    }
    
    
    
}
