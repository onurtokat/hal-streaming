// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public interface KryoSerializable
{
    void write(final Kryo p0, final Output p1);
    
    void read(final Kryo p0, final Input p1);
}
