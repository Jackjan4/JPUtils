/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.janroslan.jputils.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
     * @param outputFolder
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static Path extractZip(Path zip, Path outputFolder) throws FileNotFoundException, IOException {

        // Destination directory
        Path destDir = outputFolder;

        // Create destination if not existent
        if (!Files.exists(destDir)) {
            Files.createDirectories(destDir);
        }



        try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(zip.toFile())))) {

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

    public static Path extractZip(Path zip) throws FileNotFoundException, IOException {
        return extractZip(zip, Paths.get(zip.getParent() + File.separator + "zip-output"));
    }

}
