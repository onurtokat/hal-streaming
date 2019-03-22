// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.commons.message.util;

import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import org.slf4j.Logger;

public class JavaSerializationUtils
{
    private static final Logger LOG;
    
    public static Object deserialize(final byte[] message) {
        try (final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(message))) {
            final Object obj = ois.readObject();
            return obj;
        }
        catch (Exception e) {
            JavaSerializationUtils.LOG.error("Error during deserialize object: ", e);
            return null;
        }
    }
    
    public static byte[] serialize(final Object obj) {
        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream();
             final ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(obj);
            return bos.toByteArray();
        }
        catch (IOException e) {
            JavaSerializationUtils.LOG.error("Error during serialize object: ", e);
            return new byte[0];
        }
    }
    
    static {
        LOG = LoggerFactory.getLogger(JavaSerializationUtils.class);
    }
}
