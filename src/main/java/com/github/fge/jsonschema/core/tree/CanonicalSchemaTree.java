// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.tree;

import javax.annotation.Nullable;
import com.github.fge.jsonschema.core.ref.JsonRef;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.tree.key.SchemaKey;
import javax.annotation.concurrent.Immutable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@Immutable
public final class CanonicalSchemaTree extends BaseSchemaTree
{
    public CanonicalSchemaTree(final SchemaKey key, final JsonNode baseNode) {
        super(key, baseNode, JsonPointer.empty());
    }
    
    public CanonicalSchemaTree(final JsonNode baseNode) {
        this(SchemaKey.anonymousKey(), baseNode);
    }
    
    public CanonicalSchemaTree(final JsonRef loadingRef, final JsonNode baseNode) {
        this(SchemaKey.forJsonRef(loadingRef), baseNode);
    }
    
    private CanonicalSchemaTree(final CanonicalSchemaTree other, final JsonPointer newPointer) {
        super(other, newPointer);
    }
    
    @Override
    public SchemaTree append(final JsonPointer pointer) {
        final JsonPointer newPointer = this.pointer.append(pointer);
        return new CanonicalSchemaTree(this, newPointer);
    }
    
    @Override
    public SchemaTree setPointer(final JsonPointer pointer) {
        return new CanonicalSchemaTree(this, pointer);
    }
    
    @Override
    public boolean containsRef(final JsonRef ref) {
        return this.key.getLoadingRef().contains(ref);
    }
    
    @Nullable
    @Override
    public JsonPointer matchingPointer(final JsonRef ref) {
        if (!ref.isLegal()) {
            return null;
        }
        final JsonPointer ptr = ref.getPointer();
        return ptr.path(this.baseNode).isMissingNode() ? null : ptr;
    }
}
