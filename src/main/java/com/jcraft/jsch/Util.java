// 
// Decompiled by Procyon v0.5.30
// 

package com.jcraft.jsch;

import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Vector;

class Util
{
    private static final byte[] b64;
    private static String[] chars;
    
    private static byte val(final byte b) {
        if (b == 61) {
            return 0;
        }
        for (int i = 0; i < Util.b64.length; ++i) {
            if (b == Util.b64[i]) {
                return (byte)i;
            }
        }
        return 0;
    }
    
    static byte[] fromBase64(final byte[] array, final int n, final int n2) {
        final byte[] array2 = new byte[n2];
        int n3 = 0;
        for (int i = n; i < n + n2; i += 4) {
            array2[n3] = (byte)(val(array[i]) << 2 | (val(array[i + 1]) & 0x30) >>> 4);
            if (array[i + 2] == 61) {
                ++n3;
                break;
            }
            array2[n3 + 1] = (byte)((val(array[i + 1]) & 0xF) << 4 | (val(array[i + 2]) & 0x3C) >>> 2);
            if (array[i + 3] == 61) {
                n3 += 2;
                break;
            }
            array2[n3 + 2] = (byte)((val(array[i + 2]) & 0x3) << 6 | (val(array[i + 3]) & 0x3F));
            n3 += 3;
        }
        final byte[] array3 = new byte[n3];
        System.arraycopy(array2, 0, array3, 0, n3);
        return array3;
    }
    
    static byte[] toBase64(final byte[] array, final int n, final int n2) {
        final byte[] array2 = new byte[n2 * 2];
        final int n3 = n2 / 3 * 3 + n;
        int n4 = 0;
        int i;
        for (i = n; i < n3; i += 3) {
            array2[n4++] = Util.b64[array[i] >>> 2 & 0x3F];
            array2[n4++] = Util.b64[(array[i] & 0x3) << 4 | (array[i + 1] >>> 4 & 0xF)];
            array2[n4++] = Util.b64[(array[i + 1] & 0xF) << 2 | (array[i + 2] >>> 6 & 0x3)];
            array2[n4++] = Util.b64[array[i + 2] & 0x3F];
        }
        final int n5 = n + n2 - n3;
        if (n5 == 1) {
            array2[n4++] = Util.b64[array[i] >>> 2 & 0x3F];
            array2[n4++] = Util.b64[(array[i] & 0x3) << 4 & 0x3F];
            array2[n4++] = 61;
            array2[n4++] = 61;
        }
        else if (n5 == 2) {
            array2[n4++] = Util.b64[array[i] >>> 2 & 0x3F];
            array2[n4++] = Util.b64[(array[i] & 0x3) << 4 | (array[i + 1] >>> 4 & 0xF)];
            array2[n4++] = Util.b64[(array[i + 1] & 0xF) << 2 & 0x3F];
            array2[n4++] = 61;
        }
        final byte[] array3 = new byte[n4];
        System.arraycopy(array2, 0, array3, 0, n4);
        return array3;
    }
    
    static String[] split(final String s, final String s2) {
        if (s == null) {
            return null;
        }
        final byte[] bytes = s.getBytes();
        final Vector vector = new Vector<String>();
        int n = 0;
        while (true) {
            final int index = s.indexOf(s2, n);
            if (index < 0) {
                break;
            }
            vector.addElement(new String(bytes, n, index - n));
            n = index + 1;
        }
        vector.addElement(new String(bytes, n, bytes.length - n));
        final String[] array = new String[vector.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = vector.elementAt(i);
        }
        return array;
    }
    
    static boolean glob(final byte[] array, final byte[] array2) {
        return glob0(array, 0, array2, 0);
    }
    
    private static boolean glob0(final byte[] array, final int n, final byte[] array2, final int n2) {
        if (array2.length > 0 && array2[0] == 46) {
            return array.length > 0 && array[0] == 46 && ((array.length == 2 && array[1] == 42) || glob(array, n + 1, array2, n2 + 1));
        }
        return glob(array, n, array2, n2);
    }
    
    private static boolean glob(final byte[] array, final int n, final byte[] array2, final int n2) {
        final int length = array.length;
        if (length == 0) {
            return false;
        }
        final int length2 = array2.length;
        int i = n;
        int j = n2;
        while (i < length && j < length2) {
            if (array[i] == 92) {
                if (i + 1 == length) {
                    return false;
                }
                ++i;
                if (array[i] != array2[j]) {
                    return false;
                }
                i += skipUTF8Char(array[i]);
                j += skipUTF8Char(array2[j]);
            }
            else if (array[i] == 42) {
                while (i < length && array[i] == 42) {
                    ++i;
                }
                if (length == i) {
                    return true;
                }
                final byte b = array[i];
                if (b == 63) {
                    while (j < length2) {
                        if (glob(array, i, array2, j)) {
                            return true;
                        }
                        j += skipUTF8Char(array2[j]);
                    }
                    return false;
                }
                if (b != 92) {
                    while (j < length2) {
                        if (b == array2[j] && glob(array, i, array2, j)) {
                            return true;
                        }
                        j += skipUTF8Char(array2[j]);
                    }
                    return false;
                }
                if (i + 1 == length) {
                    return false;
                }
                ++i;
                final byte b2 = array[i];
                while (j < length2) {
                    if (b2 == array2[j] && glob(array, i + skipUTF8Char(b2), array2, j + skipUTF8Char(array2[j]))) {
                        return true;
                    }
                    j += skipUTF8Char(array2[j]);
                }
                return false;
            }
            else if (array[i] == 63) {
                ++i;
                j += skipUTF8Char(array2[j]);
            }
            else {
                if (array[i] != array2[j]) {
                    return false;
                }
                i += skipUTF8Char(array[i]);
                j += skipUTF8Char(array2[j]);
                if (j < length2) {
                    continue;
                }
                if (i >= length) {
                    return true;
                }
                if (array[i] == 42) {
                    break;
                }
                continue;
            }
        }
        if (i == length && j == length2) {
            return true;
        }
        if (j >= length2 && array[i] == 42) {
            boolean b3 = true;
            while (i < length) {
                if (array[i++] != 42) {
                    b3 = false;
                    break;
                }
            }
            return b3;
        }
        return false;
    }
    
    static String quote(final String s) {
        final byte[] str2byte = str2byte(s);
        int n = 0;
        for (int i = 0; i < str2byte.length; ++i) {
            final byte b = str2byte[i];
            if (b == 92 || b == 63 || b == 42) {
                ++n;
            }
        }
        if (n == 0) {
            return s;
        }
        final byte[] array = new byte[str2byte.length + n];
        int j = 0;
        int n2 = 0;
        while (j < str2byte.length) {
            final byte b2 = str2byte[j];
            if (b2 == 92 || b2 == 63 || b2 == 42) {
                array[n2++] = 92;
            }
            array[n2++] = b2;
            ++j;
        }
        return byte2str(array);
    }
    
    static String unquote(final String s) {
        final byte[] str2byte = str2byte(s);
        final byte[] unquote = unquote(str2byte);
        if (str2byte.length == unquote.length) {
            return s;
        }
        return byte2str(unquote);
    }
    
    static byte[] unquote(final byte[] array) {
        int length = array.length;
        for (int i = 0; i < length; ++i) {
            if (array[i] == 92) {
                if (i + 1 == length) {
                    break;
                }
                System.arraycopy(array, i + 1, array, i, array.length - (i + 1));
                --length;
            }
            else {}
        }
        if (length == array.length) {
            return array;
        }
        final byte[] array2 = new byte[length];
        System.arraycopy(array, 0, array2, 0, length);
        return array2;
    }
    
    static String getFingerPrint(final HASH hash, final byte[] array) {
        try {
            hash.init();
            hash.update(array, 0, array.length);
            final byte[] digest = hash.digest();
            final StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; ++i) {
                final int n = digest[i] & 0xFF;
                sb.append(Util.chars[n >>> 4 & 0xF]);
                sb.append(Util.chars[n & 0xF]);
                if (i + 1 < digest.length) {
                    sb.append(":");
                }
            }
            return sb.toString();
        }
        catch (Exception ex) {
            return "???";
        }
    }
    
    static boolean array_equals(final byte[] array, final byte[] array2) {
        final int length = array.length;
        if (length != array2.length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (array[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }
    
    static Socket createSocket(final String s, final int n, final int n2) throws JSchException {
        if (n2 == 0) {
            try {
                return new Socket(s, n);
            }
            catch (Exception ex) {
                final String string = ex.toString();
                if (ex instanceof Throwable) {
                    throw new JSchException(string, ex);
                }
                throw new JSchException(string);
            }
        }
        final Socket[] array = { null };
        final Exception[] array2 = { null };
        String s2 = "";
        final Thread thread = new Thread(new Runnable() {
            public void run() {
                array[0] = null;
                try {
                    array[0] = new Socket(s, n);
                }
                catch (Exception ex) {
                    array2[0] = ex;
                    if (array[0] != null && array[0].isConnected()) {
                        try {
                            array[0].close();
                        }
                        catch (Exception ex2) {}
                    }
                    array[0] = null;
                }
            }
        });
        thread.setName("Opening Socket " + s);
        thread.start();
        try {
            thread.join(n2);
            s2 = "timeout: ";
        }
        catch (InterruptedException ex2) {}
        if (array[0] != null && array[0].isConnected()) {
            return array[0];
        }
        String s3 = s2 + "socket is not established";
        if (array2[0] != null) {
            s3 = array2[0].toString();
        }
        thread.interrupt();
        throw new JSchException(s3);
    }
    
    static byte[] str2byte(final String s, final String s2) {
        if (s == null) {
            return null;
        }
        try {
            return s.getBytes(s2);
        }
        catch (UnsupportedEncodingException ex) {
            return s.getBytes();
        }
    }
    
    static byte[] str2byte(final String s) {
        return str2byte(s, "UTF-8");
    }
    
    static String byte2str(final byte[] array, final String s) {
        try {
            return new String(array, s);
        }
        catch (UnsupportedEncodingException ex) {
            return new String(array);
        }
    }
    
    static String byte2str(final byte[] array) {
        return byte2str(array, "UTF-8");
    }
    
    static void bzero(final byte[] array) {
        if (array == null) {
            return;
        }
        for (int i = 0; i < array.length; ++i) {
            array[i] = 0;
        }
    }
    
    static String diffString(final String s, final String[] array) {
        final String[] split = split(s, ",");
        String string = null;
        int i = 0;
    Label_0090:
        while (i < split.length) {
            while (true) {
                for (int j = 0; j < array.length; ++j) {
                    if (split[i].equals(array[j])) {
                        ++i;
                        continue Label_0090;
                    }
                }
                if (string == null) {
                    string = split[i];
                    continue;
                }
                string = string + "," + split[i];
                continue;
            }
        }
        return string;
    }
    
    private static int skipUTF8Char(final byte b) {
        if ((byte)(b & 0x80) == 0) {
            return 1;
        }
        if ((byte)(b & 0xE0) == -64) {
            return 2;
        }
        if ((byte)(b & 0xF0) == -32) {
            return 3;
        }
        return 1;
    }
    
    static {
        b64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".getBytes();
        Util.chars = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    }
}
