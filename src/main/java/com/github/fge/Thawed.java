// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public interface Thawed<F extends Frozen<? extends Thawed<F>>>
{
    F freeze();
}
