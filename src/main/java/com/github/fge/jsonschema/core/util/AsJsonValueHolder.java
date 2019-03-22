// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.util;

import com.fasterxml.jackson.databind.JsonNode;
import javax.annotation.concurrent.Immutable;

@Immutable
final class AsJsonValueHolder<T extends AsJson> extends ValueHolder<T>
{
    AsJsonValueHolder(final String name, final T value) {
        super(name, value);
    }
    
    @Override
    protected JsonNode valueAsJson() {
        return this.value.asJson();
    }
}
