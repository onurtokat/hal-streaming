// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.core;

public abstract class JsonStreamContext
{
    protected static final int TYPE_ROOT = 0;
    protected static final int TYPE_ARRAY = 1;
    protected static final int TYPE_OBJECT = 2;
    protected int _type;
    protected int _index;
    
    public abstract JsonStreamContext getParent();
    
    public final boolean inArray() {
        return this._type == 1;
    }
    
    public final boolean inRoot() {
        return this._type == 0;
    }
    
    public final boolean inObject() {
        return this._type == 2;
    }
    
    public final String getTypeDesc() {
        switch (this._type) {
            case 0: {
                return "ROOT";
            }
            case 1: {
                return "ARRAY";
            }
            case 2: {
                return "OBJECT";
            }
            default: {
                return "?";
            }
        }
    }
    
    public final int getEntryCount() {
        return this._index + 1;
    }
    
    public final int getCurrentIndex() {
        return (this._index < 0) ? 0 : this._index;
    }
    
    public abstract String getCurrentName();
}
