// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.deser.std;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.UUID;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.util.HashSet;

public class JdkDeserializers
{
    private static final HashSet<String> _classNames;
    
    public static JsonDeserializer<?> find(final Class<?> rawType, final String clsName) {
        if (JdkDeserializers._classNames.contains(clsName)) {
            final JsonDeserializer<?> d = FromStringDeserializer.findDeserializer(rawType);
            if (d != null) {
                return d;
            }
            if (rawType == UUID.class) {
                return new UUIDDeserializer();
            }
            if (rawType == StackTraceElement.class) {
                return new StackTraceElementDeserializer();
            }
            if (rawType == AtomicBoolean.class) {
                return new AtomicBooleanDeserializer();
            }
            if (rawType == ByteBuffer.class) {
                return new ByteBufferDeserializer();
            }
        }
        return null;
    }
    
    static {
        _classNames = new HashSet<String>();
        Class[] arr$;
        final Class<?>[] types = (Class<?>[])(arr$ = new Class[] { UUID.class, AtomicBoolean.class, StackTraceElement.class, ByteBuffer.class });
        for (final Class<?> cls : arr$) {
            JdkDeserializers._classNames.add(cls.getName());
        }
        arr$ = FromStringDeserializer.types();
        for (final Class<?> cls : arr$) {
            JdkDeserializers._classNames.add(cls.getName());
        }
    }
}
