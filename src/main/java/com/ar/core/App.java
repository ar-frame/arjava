package com.ar.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class App {

    public static void run() {
        if (Ar.getConfig("START_MODE").equals("AR_RUN_AS_SERVICE_HTTP")) {
            Class<?> c = (Class<?>) _createWebApplication("com.ar.core.ApplicationServiceHttp");

            Object obj= null;
            try {
                obj = c.getDeclaredConstructor().newInstance();

                Method method = c.getMethod ("start");
                method.invoke (obj);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }
    }

    public static void close() {


    }

    static private Object  _createWebApplication(String className)
    {
        String cName = className;
        Class<?> c = null;

        if (Ar.a(className) != null) {
            return Ar.a(className);
        }

        try {
            c = Class.forName(cName);
            Ar.setA(className, c);
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException");
        }

        return Ar.a(className);

    }

}
