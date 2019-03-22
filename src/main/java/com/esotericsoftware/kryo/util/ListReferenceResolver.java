// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.util;

import java.util.ArrayList;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ReferenceResolver;

public class ListReferenceResolver implements ReferenceResolver
{
    protected Kryo kryo;
    protected final ArrayList seenObjects;
    
    public ListReferenceResolver() {
        this.seenObjects = new ArrayList();
    }
    
    public void setKryo(final Kryo kryo) {
        this.kryo = kryo;
    }
    
    public int addWrittenObject(final Object object) {
        final int id = this.seenObjects.size();
        this.seenObjects.add(object);
        return id;
    }
    
    public int getWrittenId(final Object object) {
        for (int i = 0, n = this.seenObjects.size(); i < n; ++i) {
            if (this.seenObjects.get(i) == object) {
                return i;
            }
        }
        return -1;
    }
    
    public int nextReadId(final Class type) {
        final int id = this.seenObjects.size();
        this.seenObjects.add(null);
        return id;
    }
    
    public void setReadObject(final int id, final Object object) {
        this.seenObjects.set(id, object);
    }
    
    public Object getReadObject(final Class type, final int id) {
        return this.seenObjects.get(id);
    }
    
    public void reset() {
        this.seenObjects.clear();
    }
    
    public boolean useReferences(final Class type) {
        return !Util.isWrapperClass(type);
    }
}
