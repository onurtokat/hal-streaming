// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public interface ClassResolver
{
    void setKryo(final Kryo p0);
    
    Registration register(final Registration p0);
    
    Registration registerImplicit(final Class p0);
    
    Registration getRegistration(final Class p0);
    
    Registration getRegistration(final int p0);
    
    Registration writeClass(final Output p0, final Class p1);
    
    Registration readClass(final Input p0);
    
    void reset();
}
