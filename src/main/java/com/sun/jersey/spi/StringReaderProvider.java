// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.spi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public interface StringReaderProvider<T>
{
    StringReader<T> getStringReader(final Class<?> p0, final Type p1, final Annotation[] p2);
}