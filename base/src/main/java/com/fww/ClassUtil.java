package com.fww;

/**
 * @author Administrator
 * @date 2018/04/29 20:42
 */
public class ClassUtil {
    public ClassUtil() {
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static boolean exist(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException var2) {
            return false;
        }
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception var2) {
            throw new RuntimeException(var2.getMessage(), var2);
        }
    }
}
