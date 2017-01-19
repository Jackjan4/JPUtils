/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.janroslan.jputils.timer;


import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Jackjan
 */
public class TimerUtils {


    /**
     *
     * @param millis
     * @param task
     */
    public static void start(long millis, Runnable task) {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException ex) {

            } finally {
                task.run();
            }
        });
        t.setDaemon(true);
        t.start();
    }
}
