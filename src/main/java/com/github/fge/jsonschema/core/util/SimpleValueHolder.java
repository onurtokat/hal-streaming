// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.util;

import com.fasterxml.jackson.databind.JsonNode;

final class SimpleValueHolder<T> extends ValueHolder<T>
{
    SimpleValueHolder(final String name, final T value) {
        super(name, value);
    }
    
    @Override
    protected JsonNode valueAsJson() {
        return SimpleValueHolder.FACTORY.textNode(this.value.getClass().getCanonicalName());
    }
}
