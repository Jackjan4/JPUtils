/*
 * Copyright 2016, Jan-Philipp Roslan, Alle Rechte vorbehalten
 */
package de.timetoerror.jputils.files;

import java.io.File;

/**
 *
 * @author Jackjan
 */
public class FileModifyWatcher extends FileWatcher
{

    private long timestamp;

    public FileModifyWatcher(File f) {
        super(f, FileStatus.MODIFIED);
        timestamp = file.lastModified();
    }

    @Override
    protected void call() {

        long current = file.lastModified();

        if (current > timestamp) {
            fireChangeEvent(FileStatus.MODIFIED);
            timestamp = current;
        }
    }

}
