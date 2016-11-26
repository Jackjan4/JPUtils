/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.timetoerror.jputils.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author jackjan
 */
public class ZipUtils {

    /**
     * Extracts a zip into a "zip-output"-folder in the same folder the folder
     * where the ZIP relies
     *
     * @param zip
     * @return 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static File extractZip(File zip) throws FileNotFoundException, IOException {

        // Destination directory
        File destDir = new File(zip.getParent() + File.separator + "zip-output");

        // Create destination if not existent
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(zip)))) {

            // Get all zip entries (folders and files)
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {

                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);

                // Create folder or write file for the given entry
                if (fileName.endsWith("/")) {
                    newFile.mkdirs();
                } else {
                    newFile.getParentFile().mkdirs();

                    try (BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(newFile))) {

                        int i;
                        while ((i = zis.read()) != -1) {
                            fos.write(i);
                        }
                    }

                }

                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();
        }
        
        return destDir;
    }
}
