// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.ref;

final class EmptyJsonRef extends JsonRef
{
    private static final JsonRef INSTANCE;
    
    private EmptyJsonRef() {
        super(EmptyJsonRef.HASHONLY_URI);
    }
    
    static JsonRef getInstance() {
        return EmptyJsonRef.INSTANCE;
    }
    
    @Override
    public boolean isAbsolute() {
        return false;
    }
    
    @Override
    public JsonRef resolve(final JsonRef other) {
        return other;
    }
    
    static {
        INSTANCE = new EmptyJsonRef();
    }
}
