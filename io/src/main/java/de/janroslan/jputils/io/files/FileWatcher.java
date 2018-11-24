/*
 * Copyright 2016, Jan-Philipp Roslan, Alle Rechte vorbehalten
 */
package de.janroslan.jputils.io.files;


import java.io.File;
import java.util.ArrayList;

/**
 * A FileWatcher can observe a file/directory on the hard disk of the system and
 * notifies
 * registered listeners
 * about changes in the file. For example deletion/modify/creation
 *
 * @author Jackjan
 */
public abstract class FileWatcher implements Runnable
{

    private FileChangeListener listener;
    private ArrayList<FileStatus> status;
    protected File file;
    protected boolean running;
    private int delay;

    public FileWatcher(File f, FileStatus... s) {
        running = true;
        file = f;
        delay = 500;
    }

    /**
     * Gets called while the thread is running in the setted delay
     */
    public void start() {
        
        Thread t = new Thread(this);
        t.start();
    }

    protected abstract void call();

    public void run() {
        while (running) {
            call();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
            }
        }
    }

    public void stop() {
        running = false;
    }

    protected void fireChangeEvent(FileStatus status) {
        FileChangedEvent evt = new FileChangedEvent(file, status);

        if (listener != null) {
            listener.changed(evt);
        }

    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void addFileChangeListener(FileChangeListener listener) {
        this.listener = listener;
    }


}
