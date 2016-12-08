/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.timetoerror.jputils.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.MalformedURLException;

/**
 *
 * @author Jackjan
 */
public class ReflectionUtils {

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

    
    

    /**
     * Calls a method inside the specified class
     * 
     * @param c
     * @param method
     * @param instance
     * @param types
     * @param args
     * @return - The return object of ormally, null if the method does not have a return value
     * @throws java.lang.NoSuchMethodException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     */
    public static Object callMethod(Class c, String method, Object instance, Class[] types, Object... args) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object result = null;

        Method m = c.getDeclaredMethod(method, types);
        m.setAccessible(true);
        result = m.invoke(instance, args);

        return result;
    }

    /**
     * Injects a value into a field 
     * @param c - The class
     * @param name The name of the field
     * @param instance - The instance of the class
     * @param value - The value of the field
     * @return - Returns true if everything was sucessfull, false if not
     */
    public static boolean injectField(Class c, String name, Object instance, Object value) {
        try {
            Field f = c.getDeclaredField(name);
            f.setAccessible(true);
            f.set(instance, value);
            return true;
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {

        }
        return false;
    }

}
