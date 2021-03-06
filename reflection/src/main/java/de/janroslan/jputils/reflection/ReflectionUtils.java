/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.janroslan.jputils.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

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
     * @return - The return object of ormally, null if the method does not have
     * a return value
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object callMethod(Class c, String method, Object instance, Class[] types, Object... args) throws Exception {
        Object result = null;

        try {
            Method m = c.getDeclaredMethod(method, types);
            m.setAccessible(true);

            result = m.invoke(instance, args);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
            throw ex;
        }
        return result;
    }

    /**
     * Injects a value into a field
     *
     * @param name The name of the field
     * @param instance - The instance of the class
     * @param value - The value of the field
     * @return - Returns true if everything was sucessfull, false if not
     */
    public static boolean injectField(Object instance, String name,  Object value) {
        try {
            Field f = instance.getClass().getDeclaredField(name);
            f.setAccessible(true);
            f.set(instance, value);
            return true;
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {

        }
        return false;
    }

    public static boolean hasAnnotation(Field f, String annotationName) {

        Annotation[] annots = f.getDeclaredAnnotations();
        for(Annotation a : annots) {
            String aName = a.annotationType().getSimpleName();
            if (aName.equals(annotationName)) {
                return true;
            }
        }


        return false;
    }

    public static boolean isPrimiteOrWrapper(Object obj) {
        Class c = obj.getClass();

        if (c == int.class || c == double.class || c == float.class || c == byte.class
        || c == Integer.class || c == Double.class || c == Float.class) {
            return true;
        }

        return false;
    }

}
