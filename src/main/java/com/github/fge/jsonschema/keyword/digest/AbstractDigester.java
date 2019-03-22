// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.digest;

import com.github.fge.jackson.JacksonUtils;
import com.github.fge.jackson.NodeType;
import java.util.EnumSet;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

public abstract class AbstractDigester implements Digester
{
    protected static final JsonNodeFactory FACTORY;
    private final EnumSet<NodeType> types;
    protected final String keyword;
    
    protected AbstractDigester(final String keyword, final NodeType first, final NodeType... other) {
        this.keyword = keyword;
        this.types = EnumSet.of(first, other);
    }
    
    @Override
    public final EnumSet<NodeType> supportedTypes() {
        return EnumSet.copyOf(this.types);
    }
    
    @Override
    public final String toString() {
        return "digester for keyword \"" + this.keyword + '\"';
    }
    
    static {
        FACTORY = JacksonUtils.nodeFactory();
    }
}
