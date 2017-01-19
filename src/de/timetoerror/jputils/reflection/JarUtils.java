/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.timetoerror.jputils.reflection;

import de.timetoerror.jputils.io.FileUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jackjan
 */
public class JarUtils {

    /**
     * Gets a list of classes inside a JarFile
     *
     * @param jar
     * @return
     */
    public static ArrayList<String> getClasses(String jar) {
        ArrayList<String> result = new ArrayList<>();
        
        try {
            JarFile jarF = new JarFile(jar);
            Enumeration<JarEntry> entries = jarF.entries();
            
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                
                if (entry.getName().endsWith(".class")) {
                    result.add(entry.getName().replaceAll("/", ".").substring(0, entry.getName().length() - 6));
                }
            }
        } catch (IOException ex) {
            System.out.println("Error: ");
            return null;
        }
        return result;
    }
    
    public static void copyOutOfJar(Class c, String content, File dest) {
        BufferedInputStream iStream = new BufferedInputStream(c.getResourceAsStream(content));
        
        if (!dest.exists()) {
            dest.getParentFile().mkdirs();
            try {
                dest.createNewFile();
            } catch (IOException ex) {
                
            }
        }
        
        
        try (BufferedOutputStream bfos = new BufferedOutputStream(new FileOutputStream((dest)))) {
            
            int i;
            while ((i = iStream.read()) != -1) {
                bfos.write(i);
            }
            
            iStream.close();
            
            
        } catch (FileNotFoundException ex) {
            System.out.println("lul file not found");
        } catch (IOException ex) {
            System.out.println("lul - io");
        }
    }
    
}
