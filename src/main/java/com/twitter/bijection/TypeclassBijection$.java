// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

public final class TypeclassBijection$
{
    public static final TypeclassBijection$ MODULE$;
    
    static {
        new TypeclassBijection$();
    }
    
    public <T, A> TypeclassBijection.RichTypeclass<T, A> RichTypeclass(final T t) {
        return new TypeclassBijection.RichTypeclass<T, A>(t);
    }
    
    public <T, A, B> T typeclassBijection(final TypeclassBijection<T> tcBij, final T typeclass, final ImplicitBijection<A, B> bij) {
        return tcBij.apply(typeclass, bij.bijection());
    }
    
    public <T, To> T deriveFor(final TypeclassBijection<T> tcBij, final TypeclassBijection.BijectionAndTypeclass<T, ?, To> batc) {
        return batc.apply(tcBij);
    }
    
    private TypeclassBijection$() {
        MODULE$ = this;
    }
}
