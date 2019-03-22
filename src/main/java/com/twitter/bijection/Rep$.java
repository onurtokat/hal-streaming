// 
// Decompiled by Procyon v0.5.30
// 

package com.twitter.bijection;

public final class Rep$
{
    public static final Rep$ MODULE$;
    
    static {
        new Rep$();
    }
    
    public <A> Rep.ToRepOps<A> toRepOpsEnrichment(final A a) {
        return new Rep.ToRepOps<A>(a);
    }
    
    private Rep$() {
        MODULE$ = this;
    }
}
