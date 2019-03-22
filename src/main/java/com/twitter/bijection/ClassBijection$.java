// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

import scala.Serializable;

public final class ClassBijection$ implements Serializable
{
    public static final ClassBijection$ MODULE$;
    
    static {
        new ClassBijection$();
    }
    
    public <T> Bijection<Class<T>, String> apply() {
        return new ClassBijection<T>();
    }
    
    private Object readResolve() {
        return ClassBijection$.MODULE$;
    }
    
    private ClassBijection$() {
        MODULE$ = this;
    }
}
