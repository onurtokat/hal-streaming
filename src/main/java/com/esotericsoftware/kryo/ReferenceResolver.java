// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo;

public interface ReferenceResolver
{
    void setKryo(final Kryo p0);
    
    int getWrittenId(final Object p0);
    
    int addWrittenObject(final Object p0);
    
    int nextReadId(final Class p0);
    
    void setReadObject(final int p0, final Object p1);
    
    Object getReadObject(final Class p0, final int p1);
    
    void reset();
    
    boolean useReferences(final Class p0);
}
