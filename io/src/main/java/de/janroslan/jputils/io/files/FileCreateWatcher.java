/*
 * Copyright 2016, Jan-Philipp Roslan, Alle Rechte vorbehalten
 */
package de.janroslan.jputils.io.files;

import java.io.File;

/**
 *
 * @author Jackjan
 */
public class FileCreateWatcher extends FileWatcher
{

    boolean exists = true;

    public FileCreateWatcher(File f) {
        super(f, FileStatus.CREATED);

    }

    @Override
    protected void call() {

        
            if (!file.exists()) {
                exists = false;
            }
            
            if (!exists && file.exists()) {
                fireChangeEvent(FileStatus.CREATED);
                exists = true;
            }
        
    }
}