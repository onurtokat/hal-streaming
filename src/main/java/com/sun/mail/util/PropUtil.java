// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.mail.util;

import java.util.Hashtable;
import javax.mail.Session;
import java.util.Properties;

public class PropUtil
{
    public static int getIntProperty(final Properties props, final String name, final int def) {
        return getInt(((Hashtable<K, Object>)props).get(name), def);
    }
    
    public static boolean getBooleanProperty(final Properties props, final String name, final boolean def) {
        return getBoolean(((Hashtable<K, Object>)props).get(name), def);
    }
    
    public static int getIntSessionProperty(final Session session, final String name, final int def) {
        return getInt(((Hashtable<K, Object>)session.getProperties()).get(name), def);
    }
    
    public static boolean getBooleanSessionProperty(final Session session, final String name, final boolean def) {
        return getBoolean(((Hashtable<K, Object>)session.getProperties()).get(name), def);
    }
    
    public static boolean getBooleanSystemProperty(final String name, final boolean def) {
        try {
            return getBoolean(((Hashtable<K, Object>)System.getProperties()).get(name), def);
        }
        catch (SecurityException sex) {
            try {
                final String value = System.getProperty(name);
                if (value == null) {
                    return def;
                }
                if (def) {
                    return !value.equalsIgnoreCase("false");
                }
                return value.equalsIgnoreCase("true");
            }
            catch (SecurityException sex) {
                return def;
            }
        }
    }
    
    private static int getInt(final Object value, final int def) {
        if (value == null) {
            return def;
        }
        if (value instanceof String) {
            try {
                return Integer.parseInt((String)value);
            }
            catch (NumberFormatException ex) {}
        }
        if (value instanceof Integer) {
            return (int)value;
        }
        return def;
    }
    
    private static boolean getBoolean(final Object value, final boolean def) {
        if (value == null) {
            return def;
        }
        if (value instanceof String) {
            if (def) {
                return !((String)value).equalsIgnoreCase("false");
            }
            return ((String)value).equalsIgnoreCase("true");
        }
        else {
            if (value instanceof Boolean) {
                return (boolean)value;
            }
            return def;
        }
    }
}
