// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.load;

import com.github.fge.jsonschema.core.tree.InlineSchemaTree;
import com.github.fge.jsonschema.core.tree.CanonicalSchemaTree;
import com.github.fge.jsonschema.core.tree.key.SchemaKey;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.ref.JsonRef;

public enum Dereferencing
{
    CANONICAL("canonical") {
        @Override
        protected SchemaTree newTree(final SchemaKey key, final JsonNode node) {
            return new CanonicalSchemaTree(key, node);
        }
    }, 
    INLINE("inline") {
        @Override
        protected SchemaTree newTree(final SchemaKey key, final JsonNode node) {
            return new InlineSchemaTree(key, node);
        }
    };
    
    private final String name;
    
    public SchemaTree newTree(final JsonRef ref, final JsonNode node) {
        return this.newTree(SchemaKey.forJsonRef(ref), node);
    }
    
    public SchemaTree newTree(final JsonNode node) {
        return this.newTree(SchemaKey.anonymousKey(), node);
    }
    
    protected abstract SchemaTree newTree(final SchemaKey p0, final JsonNode p1);
    
    private Dereferencing(final String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
