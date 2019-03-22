// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.tree;

import com.github.fge.jackson.JacksonUtils;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

public abstract class BaseJsonTree implements JsonTree
{
    protected static final JsonNodeFactory FACTORY;
    protected final JsonNode baseNode;
    protected final JsonPointer pointer;
    private final JsonNode node;
    
    protected BaseJsonTree(final JsonNode baseNode) {
        this(baseNode, JsonPointer.empty());
    }
    
    protected BaseJsonTree(final JsonNode baseNode, final JsonPointer pointer) {
        this.baseNode = baseNode;
        this.node = pointer.path(baseNode);
        this.pointer = pointer;
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
    public abstract String toString();
    
    static {
        FACTORY = JacksonUtils.nodeFactory();
    }
}
