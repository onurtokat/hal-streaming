// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge;

import javax.annotation.concurrent.Immutable;

@Immutable
public interface Frozen<T extends Thawed<? extends Frozen<T>>>
{
    T thaw();
}
