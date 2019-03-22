// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.tree.key;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.google.common.primitives.Longs;
import com.github.fge.jsonschema.core.ref.JsonRef;
import java.util.concurrent.atomic.AtomicLong;

public final class AnonymousSchemaKey extends SchemaKey
{
    private static final AtomicLong ID_GEN;
    private final long id;
    
    AnonymousSchemaKey() {
        super(JsonRef.emptyRef());
        this.id = AnonymousSchemaKey.ID_GEN.getAndIncrement();
    }
    
    @Override
    public long getId() {
        return this.id;
    }
    
    @Override
    public int hashCode() {
        return Longs.hashCode(this.id);
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
        final AnonymousSchemaKey other = (AnonymousSchemaKey)obj;
        return this.id == other.id;
    }
    
    @Nonnull
    @Override
    public String toString() {
        return "anonymous; id = " + this.id;
    }
    
    static {
        ID_GEN = new AtomicLong(0L);
    }
}
