/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.timetoerror.jputils.refl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 * @author Jackjan
 */
public class ReflectionUtils
{

    /**
     * Loads a class inside a JAR with its fully qualified class name
     *
     * @param jar
     * @param className
     * @return
     */
    public static Class loadClass(String jar, String className) {
        try {
            ClassLoader loader = new URLClassLoader(new URL[]{new URL("file:" + jar)});
            return loader.loadClass(className);
        } catch (MalformedURLException | ClassNotFoundException ex) {
            System.out.println("Error:");
        }
        return null;
    }

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

    /**
     *
     * @param c
     * @param method
     * @param instance
     * @param types
     * @param args
     * @return - The object of executed normally, null if an error occured or
     * the method does not have a return value
     */
    public static Object callMethod(Class c, String method, Object instance, Class[] types, Object... args) {
        Object result = null;
        try {
            Method m = c.getDeclaredMethod(method,types);
            m.setAccessible(true);
            result = m.invoke(instance,args);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            ex.getCause().printStackTrace();
            ex.printStackTrace();
            return null;
        }
        return result;
    }

    /**
     *
     * @param c
     * @param name
     * @param instance
     * @param value
     * @return
     */
    public static boolean injectField(Class c, String name, Object instance, Object value) {
        try {
            Field f = c.getDeclaredField(name);
            f.setAccessible(true);
            f.set(instance, value);
            return true;
        } catch (Exception ex) {

        }
        return false;
    }

}
