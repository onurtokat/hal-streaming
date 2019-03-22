// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.tree;

import com.github.fge.jackson.JacksonUtils;
import java.util.Iterator;
import com.github.fge.jackson.jsonpointer.TokenResolver;
import com.github.fge.jsonschema.core.exceptions.JsonReferenceException;
import javax.annotation.Nullable;
import javax.annotation.Nonnull;
import com.google.common.base.Objects;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Preconditions;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.ref.JsonRef;
import com.github.fge.jsonschema.core.tree.key.SchemaKey;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import javax.annotation.concurrent.Immutable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@Immutable
public abstract class BaseSchemaTree implements SchemaTree
{
    private static final JsonNodeFactory FACTORY;
    protected final SchemaKey key;
    private final JsonRef dollarSchema;
    protected final JsonNode baseNode;
    protected final JsonPointer pointer;
    private final JsonNode node;
    private final JsonRef startingRef;
    private final JsonRef currentRef;
    
    protected BaseSchemaTree(final SchemaKey key, final JsonNode baseNode, final JsonPointer pointer) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(baseNode);
        Preconditions.checkNotNull(pointer);
        this.key = key;
        this.dollarSchema = extractDollarSchema(baseNode);
        this.baseNode = baseNode;
        this.pointer = pointer;
        this.node = pointer.path(baseNode);
        final JsonRef loadingRef = key.getLoadingRef();
        final JsonRef ref = idFromNode(baseNode);
        this.startingRef = ((ref == null) ? loadingRef : loadingRef.resolve(ref));
        this.currentRef = nextRef(this.startingRef, pointer, baseNode);
    }
    
    protected BaseSchemaTree(final JsonRef loadingRef, final JsonNode baseNode, final JsonPointer pointer) {
        this.dollarSchema = extractDollarSchema(baseNode);
        this.key = (loadingRef.equals(JsonRef.emptyRef()) ? SchemaKey.anonymousKey() : SchemaKey.forJsonRef(loadingRef));
        this.baseNode = baseNode;
        this.pointer = pointer;
        this.node = pointer.path(baseNode);
        final JsonRef ref = idFromNode(baseNode);
        this.startingRef = ((ref == null) ? loadingRef : loadingRef.resolve(ref));
        this.currentRef = nextRef(this.startingRef, pointer, baseNode);
    }
    
    protected BaseSchemaTree(final BaseSchemaTree other, final JsonPointer newPointer) {
        this.key = other.key;
        this.dollarSchema = other.dollarSchema;
        this.baseNode = other.baseNode;
        this.pointer = newPointer;
        this.node = newPointer.get(this.baseNode);
        this.startingRef = other.startingRef;
        this.currentRef = nextRef(this.startingRef, newPointer, this.baseNode);
    }
    
    @Deprecated
    @Override
    public final long getId() {
        return this.key.getId();
    }
    
    @Override
    public final JsonNode getBaseNode() {
        return this.baseNode;
    }
    
    @Override
    public final JsonPointer getPointer() {
        return this.pointer;
    }
    
    @Override
    public final JsonNode getNode() {
        return this.node;
    }
    
    @Override
    public final JsonRef resolve(final JsonRef other) {
        return this.currentRef.resolve(other);
    }
    
    @Override
    public final JsonRef getDollarSchema() {
        return this.dollarSchema;
    }
    
    @Override
    public final JsonRef getLoadingRef() {
        return this.key.getLoadingRef();
    }
    
    @Override
    public final JsonRef getContext() {
        return this.currentRef;
    }
    
    @Override
    public final JsonNode asJson() {
        final ObjectNode ret = BaseSchemaTree.FACTORY.objectNode();
        ret.put("loadingURI", BaseSchemaTree.FACTORY.textNode(this.key.getLoadingRef().toString()));
        ret.put("pointer", BaseSchemaTree.FACTORY.textNode(this.pointer.toString()));
        return ret;
    }
    
    @Nonnull
    @Override
    public final String toString() {
        return Objects.toStringHelper(this).add("key", this.key).add("pointer", this.pointer).add("URI context", this.currentRef).toString();
    }
    
    @Override
    public final int hashCode() {
        return this.key.hashCode() ^ this.pointer.hashCode();
    }
    
    @Override
    public final boolean equals(@Nullable final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final BaseSchemaTree other = (BaseSchemaTree)obj;
        return this.key.equals(other.key) && this.pointer.equals(other.pointer);
    }
    
    @Nullable
    protected static JsonRef idFromNode(final JsonNode node) {
        if (!node.path("id").isTextual()) {
            return null;
        }
        try {
            return JsonRef.fromString(node.get("id").textValue());
        }
        catch (JsonReferenceException ignored) {
            return null;
        }
    }
    
    private static JsonRef nextRef(final JsonRef startingRef, final JsonPointer ptr, final JsonNode startingNode) {
        JsonRef ret = startingRef;
        JsonNode node = startingNode;
        for (final TokenResolver<JsonNode> resolver : ptr) {
            node = resolver.get(node);
            if (node == null) {
                break;
            }
            final JsonRef idRef = idFromNode(node);
            if (idRef == null) {
                continue;
            }
            ret = ret.resolve(idRef);
        }
        return ret;
    }
    
    private static JsonRef extractDollarSchema(final JsonNode schema) {
        final JsonNode node = schema.path("$schema");
        if (!node.isTextual()) {
            return JsonRef.emptyRef();
        }
        try {
            final JsonRef ref = JsonRef.fromString(node.textValue());
            return ref.isAbsolute() ? ref : JsonRef.emptyRef();
        }
        catch (JsonReferenceException ignored) {
            return JsonRef.emptyRef();
        }
    }
    
    static {
        FACTORY = JacksonUtils.nodeFactory();
    }
}
