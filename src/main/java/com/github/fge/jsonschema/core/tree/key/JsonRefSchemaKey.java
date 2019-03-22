// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.tree.key;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.github.fge.jsonschema.core.ref.JsonRef;

public final class JsonRefSchemaKey extends SchemaKey
{
    JsonRefSchemaKey(final JsonRef ref) {
        super(ref);
    }
    
    @Override
    public long getId() {
        return 0L;
    }
    
    @Override
    public int hashCode() {
        return this.loadingRef.hashCode();
    }
    
    @Override
    public boolean equals(@Nullable final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final JsonRefSchemaKey other = (JsonRefSchemaKey)obj;
        return this.loadingRef.equals(other.loadingRef);
    }
    
    @Nonnull
    @Override
    public String toString() {
        return "loaded from JSON ref " + this.loadingRef;
    }
}
