// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.util;

import com.esotericsoftware.minlog.Log;

public class Util
{
    public static boolean isAndroid;
    
    public static Class getWrapperClass(final Class type) {
        if (type == Integer.TYPE) {
            return Integer.class;
        }
        if (type == Float.TYPE) {
            return Float.class;
        }
        if (type == Boolean.TYPE) {
            return Boolean.class;
        }
        if (type == Long.TYPE) {
            return Long.class;
        }
        if (type == Byte.TYPE) {
            return Byte.class;
        }
        if (type == Character.TYPE) {
            return Character.class;
        }
        if (type == Short.TYPE) {
            return Short.class;
        }
        return Double.class;
    }
    
    public static boolean isWrapperClass(final Class type) {
        return type == Integer.class || type == Float.class || type == Boolean.class || type == Long.class || type == Byte.class || type == Character.class || type == Short.class || type == Double.class;
    }
    
    public static void log(final String message, final Object object) {
        if (object == null) {
            if (Log.TRACE) {
                Log.trace("kryo", message + ": null");
            }
            return;
        }
        final Class type = object.getClass();
        if (type.isPrimitive() || type == Boolean.class || type == Byte.class || type == Character.class || type == Short.class || type == Integer.class || type == Long.class || type == Float.class || type == Double.class || type == String.class) {
            if (Log.TRACE) {
                Log.trace("kryo", message + ": " + string(object));
            }
        }
        else {
            Log.debug("kryo", message + ": " + string(object));
        }
    }
    
    public static String string(final Object object) {
        if (object == null) {
            return "null";
        }
        final Class type = object.getClass();
        if (type.isArray()) {
            return className(type);
        }
        try {
            if (type.getMethod("toString", (Class[])new Class[0]).getDeclaringClass() == Object.class) {
                return Log.TRACE ? className(type) : type.getSimpleName();
            }
        }
        catch (Exception ex) {}
        return String.valueOf(object);
    }
    
    public static String className(final Class type) {
        if (type.isArray()) {
            final Class elementClass = getElementClass(type);
            final StringBuilder buffer = new StringBuilder(16);
            for (int i = 0, n = getDimensionCount(type); i < n; ++i) {
                buffer.append("[]");
            }
            return className(elementClass) + (Object)buffer;
        }
        if (type.isPrimitive() || type == Object.class || type == Boolean.class || type == Byte.class || type == Character.class || type == Short.class || type == Integer.class || type == Long.class || type == Float.class || type == Double.class || type == String.class) {
            return type.getSimpleName();
        }
        return type.getName();
    }
    
    public static int getDimensionCount(final Class arrayClass) {
        int depth = 0;
        for (Class nextClass = arrayClass.getComponentType(); nextClass != null; nextClass = nextClass.getComponentType()) {
            ++depth;
        }
        return depth;
    }
    
    public static Class getElementClass(final Class arrayClass) {
        Class elementClass;
        for (elementClass = arrayClass; elementClass.getComponentType() != null; elementClass = elementClass.getComponentType()) {}
        return elementClass;
    }
    
    static {
        try {
            Class.forName("android.os.Process");
            Util.isAndroid = true;
        }
        catch (Exception ex) {}
    }
}
