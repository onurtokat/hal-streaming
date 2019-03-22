// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.util;

import java.util.ArrayList;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ReferenceResolver;

public class MapReferenceResolver implements ReferenceResolver
{
    protected Kryo kryo;
    protected final IdentityObjectIntMap writtenObjects;
    protected final ArrayList readObjects;
    
    public MapReferenceResolver() {
        this.writtenObjects = new IdentityObjectIntMap();
        this.readObjects = new ArrayList();
    }
    
    public void setKryo(final Kryo kryo) {
        this.kryo = kryo;
    }
    
    public int addWrittenObject(final Object object) {
        final int id = this.writtenObjects.size;
        this.writtenObjects.put(object, id);
        return id;
    }
    
    public int getWrittenId(final Object object) {
        return this.writtenObjects.get(object, -1);
    }
    
    public int nextReadId(final Class type) {
        final int id = this.readObjects.size();
        this.readObjects.add(null);
        return id;
    }
    
    public void setReadObject(final int id, final Object object) {
        this.readObjects.set(id, object);
    }
    
    public Object getReadObject(final Class type, final int id) {
        return this.readObjects.get(id);
    }
    
    public void reset() {
        this.readObjects.clear();
        this.writtenObjects.clear();
    }
    
    public boolean useReferences(final Class type) {
        return !Util.isWrapperClass(type);
    }
}
