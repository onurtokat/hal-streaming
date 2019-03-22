// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.tree;

import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class SimpleJsonTree extends BaseJsonTree
{
    public SimpleJsonTree(final JsonNode baseNode) {
        super(baseNode);
    }
    
    private SimpleJsonTree(final JsonNode baseNode, final JsonPointer pointer) {
        super(baseNode, pointer);
    }
    
    @Override
    public SimpleJsonTree append(final JsonPointer pointer) {
        return new SimpleJsonTree(this.baseNode, this.pointer.append(pointer));
    }
    
    @Override
    public JsonNode asJson() {
        return SimpleJsonTree.FACTORY.objectNode().set("pointer", SimpleJsonTree.FACTORY.textNode(this.pointer.toString()));
    }
    
    @Override
    public String toString() {
        return "current pointer: \"" + this.pointer + '\"';
    }
}
