// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.SimpleObjectIdResolver;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.databind.PropertyName;

public class ObjectIdInfo
{
    protected final PropertyName _propertyName;
    protected final Class<? extends ObjectIdGenerator<?>> _generator;
    protected final Class<? extends ObjectIdResolver> _resolver;
    protected final Class<?> _scope;
    protected final boolean _alwaysAsId;
    
    public ObjectIdInfo(final PropertyName name, final Class<?> scope, final Class<? extends ObjectIdGenerator<?>> gen, final Class<? extends ObjectIdResolver> resolver) {
        this(name, scope, gen, false, resolver);
    }
    
    public ObjectIdInfo(final PropertyName name, final Class<?> scope, final Class<? extends ObjectIdGenerator<?>> gen) {
        this(name, scope, gen, false);
    }
    
    public ObjectIdInfo(final String name, final Class<?> scope, final Class<? extends ObjectIdGenerator<?>> gen) {
        this(new PropertyName(name), scope, gen, false);
    }
    
    protected ObjectIdInfo(final PropertyName prop, final Class<?> scope, final Class<? extends ObjectIdGenerator<?>> gen, final boolean alwaysAsId) {
        this(prop, scope, gen, alwaysAsId, SimpleObjectIdResolver.class);
    }
    
    protected ObjectIdInfo(final PropertyName prop, final Class<?> scope, final Class<? extends ObjectIdGenerator<?>> gen, final boolean alwaysAsId, Class<? extends ObjectIdResolver> resolver) {
        this._propertyName = prop;
        this._scope = scope;
        this._generator = gen;
        this._alwaysAsId = alwaysAsId;
        if (resolver == null) {
            resolver = SimpleObjectIdResolver.class;
        }
        this._resolver = resolver;
    }
    
    public ObjectIdInfo withAlwaysAsId(final boolean state) {
        if (this._alwaysAsId == state) {
            return this;
        }
        return new ObjectIdInfo(this._propertyName, this._scope, this._generator, state, this._resolver);
    }
    
    public PropertyName getPropertyName() {
        return this._propertyName;
    }
    
    public Class<?> getScope() {
        return this._scope;
    }
    
    public Class<? extends ObjectIdGenerator<?>> getGeneratorType() {
        return this._generator;
    }
    
    public Class<? extends ObjectIdResolver> getResolverType() {
        return this._resolver;
    }
    
    public boolean getAlwaysAsId() {
        return this._alwaysAsId;
    }
    
    @Override
    public String toString() {
        return "ObjectIdInfo: propName=" + this._propertyName + ", scope=" + ((this._scope == null) ? "null" : this._scope.getName()) + ", generatorType=" + ((this._generator == null) ? "null" : this._generator.getName()) + ", alwaysAsId=" + this._alwaysAsId;
    }
}
