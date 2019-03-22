// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;

public abstract class TypeIdResolverBase implements TypeIdResolver
{
    protected final TypeFactory _typeFactory;
    protected final JavaType _baseType;
    
    protected TypeIdResolverBase() {
        this(null, null);
    }
    
    protected TypeIdResolverBase(final JavaType baseType, final TypeFactory typeFactory) {
        this._baseType = baseType;
        this._typeFactory = typeFactory;
    }
    
    @Override
    public void init(final JavaType bt) {
    }
    
    @Override
    public String idFromBaseType() {
        return this.idFromValueAndType(null, this._baseType.getRawClass());
    }
    
    @Deprecated
    @Override
    public abstract JavaType typeFromId(final String p0);
    
    public JavaType typeFromId(final DatabindContext context, final String id) {
        return this.typeFromId(id);
    }
}
