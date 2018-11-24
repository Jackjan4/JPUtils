/*
 * Copyright 2016, Jan-Philipp Roslan, Alle Rechte vorbehalten
 */
package de.janroslan.jputils.io.files;

import java.io.File;

/**
 *
 * @author Jackjan
 */
public class FileChangedEvent
{
    private final File file;
    private final FileStatus status;
    
    public FileChangedEvent(File f, FileStatus s)
    {
        
        file = f;
        status = s;
    }

    public File getFile() {
        return file;
    }

    public FileStatus getStatus() {
        return status;
    }
    
    
}
