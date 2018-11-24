/*
 * Copyright 2016, Jan-Philipp Roslan, Alle Rechte vorbehalten
 */
package de.janroslan.jputils.io.files;

import java.io.File;

/**
 *
 * @author Jackjan
 */
public class FileDeleteWatcher extends FileWatcher
{

    boolean exists = false;

    public FileDeleteWatcher(File f) {
        super(f, FileStatus.CREATED);

    }

    @Override
    protected void call() {

        if (file.exists()) {
            exists = true;
        }

        if (exists && !file.exists()) {
            fireChangeEvent(FileStatus.DELETED);
            exists = false;
        }

    }

}
