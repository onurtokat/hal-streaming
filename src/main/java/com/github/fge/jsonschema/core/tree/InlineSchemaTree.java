// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.tree;

import com.github.fge.jackson.JacksonUtils;
import java.util.Iterator;
import javax.annotation.Nullable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.tree.key.SchemaKey;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonschema.core.ref.JsonRef;
import java.util.Map;
import javax.annotation.concurrent.Immutable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@Immutable
public final class InlineSchemaTree extends BaseSchemaTree
{
    private final Map<JsonRef, JsonPointer> absRefs;
    private final Map<JsonRef, JsonPointer> otherRefs;
    
    public InlineSchemaTree(final SchemaKey key, final JsonNode baseNode) {
        super(key, baseNode, JsonPointer.empty());
        final Map<JsonRef, JsonPointer> abs = (Map<JsonRef, JsonPointer>)Maps.newHashMap();
        final Map<JsonRef, JsonPointer> other = (Map<JsonRef, JsonPointer>)Maps.newHashMap();
        final JsonRef loadingRef = key.getLoadingRef();
        walk(loadingRef, baseNode, JsonPointer.empty(), abs, other);
        this.absRefs = (Map<JsonRef, JsonPointer>)ImmutableMap.copyOf((Map<?, ?>)abs);
        this.otherRefs = (Map<JsonRef, JsonPointer>)ImmutableMap.copyOf((Map<?, ?>)other);
    }
    
    public InlineSchemaTree(final JsonNode baseNode) {
        this(SchemaKey.anonymousKey(), baseNode);
    }
    
    public InlineSchemaTree(final JsonRef loadingRef, final JsonNode baseNode) {
        this(SchemaKey.forJsonRef(loadingRef), baseNode);
    }
    
    private InlineSchemaTree(final InlineSchemaTree other, final JsonPointer newPointer) {
        super(other, newPointer);
        this.absRefs = other.absRefs;
        this.otherRefs = other.otherRefs;
    }
    
    @Override
    public SchemaTree append(final JsonPointer pointer) {
        final JsonPointer newPointer = this.pointer.append(pointer);
        return new InlineSchemaTree(this, newPointer);
    }
    
    @Override
    public SchemaTree setPointer(final JsonPointer pointer) {
        return new InlineSchemaTree(this, pointer);
    }
    
    @Override
    public boolean containsRef(final JsonRef ref) {
        return this.getMatchingPointer(ref) != null;
    }
    
    @Override
    public JsonPointer matchingPointer(final JsonRef ref) {
        final JsonPointer ret = this.getMatchingPointer(ref);
        if (ret == null) {
            return null;
        }
        return ret.path(this.baseNode).isMissingNode() ? null : ret;
    }
    
    @Nullable
    private JsonPointer getMatchingPointer(final JsonRef ref) {
        if (this.otherRefs.containsKey(ref)) {
            return this.otherRefs.get(ref);
        }
        if (!ref.isLegal()) {
            return null;
        }
        return this.refMatchingPointer(ref);
    }
    
    @Nullable
    private JsonPointer refMatchingPointer(final JsonRef ref) {
        final JsonPointer refPtr = ref.getPointer();
        for (final Map.Entry<JsonRef, JsonPointer> entry : this.absRefs.entrySet()) {
            if (entry.getKey().contains(ref)) {
                return entry.getValue().append(refPtr);
            }
        }
        return this.key.getLoadingRef().contains(ref) ? refPtr : null;
    }
    
    private static void walk(final JsonRef baseRef, final JsonNode node, final JsonPointer ptr, final Map<JsonRef, JsonPointer> absMap, final Map<JsonRef, JsonPointer> otherMap) {
        if (!node.isObject()) {
            return;
        }
        final JsonRef ref = BaseSchemaTree.idFromNode(node);
        JsonRef nextRef = baseRef;
        if (ref != null) {
            nextRef = baseRef.resolve(ref);
            final Map<JsonRef, JsonPointer> targetMap = nextRef.isAbsolute() ? absMap : otherMap;
            targetMap.put(nextRef, ptr);
        }
        final Map<String, JsonNode> tmp = JacksonUtils.asMap(node);
        for (final Map.Entry<String, JsonNode> entry : tmp.entrySet()) {
            walk(nextRef, entry.getValue(), ptr.append(entry.getKey()), absMap, otherMap);
        }
    }
}
