// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector;

public class StringUtil
{
    public static boolean containsIgnoreCase(final String[] array, final String value) {
        for (final String str : array) {
            if (value == null && str == null) {
                return true;
            }
            if (value != null && value.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }
    
    public static String join(final String[] array, final String separator) {
        final int len = array.length;
        if (len == 0) {
            return "";
        }
        final StringBuilder out = new StringBuilder();
        out.append(array[0]);
        for (int i = 1; i < len; ++i) {
            out.append(separator).append(array[i]);
        }
        return out.toString();
    }
}
