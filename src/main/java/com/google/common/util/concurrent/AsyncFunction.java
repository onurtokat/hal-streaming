// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

public interface AsyncFunction<I, O>
{
    ListenableFuture<O> apply(final I p0) throws Exception;
}
