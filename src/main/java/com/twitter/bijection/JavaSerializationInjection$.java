// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.reflect.ClassTag;
import java.io.Serializable;

public final class JavaSerializationInjection$ implements Serializable
{
    public static final JavaSerializationInjection$ MODULE$;
    
    static {
        new JavaSerializationInjection$();
    }
    
    public <T extends Serializable> JavaSerializationInjection<T> apply(final ClassTag<T> ct) {
        final Class cls = ct.runtimeClass();
        return new JavaSerializationInjection<T>(cls);
    }
    
    private Object readResolve() {
        return JavaSerializationInjection$.MODULE$;
    }
    
    private JavaSerializationInjection$() {
        MODULE$ = this;
    }
}
