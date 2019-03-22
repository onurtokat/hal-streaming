// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.tree.key;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.google.common.base.Preconditions;
import javax.annotation.Untainted;
import com.github.fge.jsonschema.core.ref.JsonRef;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class SchemaKey
{
    protected final JsonRef loadingRef;
    
    protected SchemaKey(final JsonRef loadingRef) {
        this.loadingRef = loadingRef;
    }
    
    public static SchemaKey anonymousKey() {
        return new AnonymousSchemaKey();
    }
    
    public static SchemaKey forJsonRef(@Untainted final JsonRef ref) {
        return new JsonRefSchemaKey(Preconditions.checkNotNull(ref));
    }
    
    public abstract long getId();
    
    public final JsonRef getLoadingRef() {
        return this.loadingRef;
    }
    
    @Override
    public abstract int hashCode();
    
    @Override
    public abstract boolean equals(@Nullable final Object p0);
    
    @Nonnull
    @Override
    public abstract String toString();
}
