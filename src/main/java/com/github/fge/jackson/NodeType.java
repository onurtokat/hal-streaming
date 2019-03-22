// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jackson;

import com.google.common.collect.ImmutableMap;
import java.util.EnumMap;
import com.google.common.base.Preconditions;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonToken;
import java.util.Map;

public enum NodeType
{
    ARRAY("array"), 
    BOOLEAN("boolean"), 
    INTEGER("integer"), 
    NULL("null"), 
    NUMBER("number"), 
    OBJECT("object"), 
    STRING("string");
    
    private final String name;
    private static final Map<String, NodeType> NAME_MAP;
    private static final Map<JsonToken, NodeType> TOKEN_MAP;
    
    private NodeType(final String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    public static NodeType fromName(final String name) {
        return NodeType.NAME_MAP.get(name);
    }
    
    public static NodeType getNodeType(final JsonNode node) {
        final JsonToken token = node.asToken();
        final NodeType ret = NodeType.TOKEN_MAP.get(token);
        Preconditions.checkNotNull(ret, (Object)("unhandled token type " + token));
        return ret;
    }
    
    static {
        (TOKEN_MAP = new EnumMap<JsonToken, NodeType>(JsonToken.class)).put(JsonToken.START_ARRAY, NodeType.ARRAY);
        NodeType.TOKEN_MAP.put(JsonToken.VALUE_TRUE, NodeType.BOOLEAN);
        NodeType.TOKEN_MAP.put(JsonToken.VALUE_FALSE, NodeType.BOOLEAN);
        NodeType.TOKEN_MAP.put(JsonToken.VALUE_NUMBER_INT, NodeType.INTEGER);
        NodeType.TOKEN_MAP.put(JsonToken.VALUE_NUMBER_FLOAT, NodeType.NUMBER);
        NodeType.TOKEN_MAP.put(JsonToken.VALUE_NULL, NodeType.NULL);
        NodeType.TOKEN_MAP.put(JsonToken.START_OBJECT, NodeType.OBJECT);
        NodeType.TOKEN_MAP.put(JsonToken.VALUE_STRING, NodeType.STRING);
        final ImmutableMap.Builder<String, NodeType> builder = ImmutableMap.builder();
        for (final NodeType type : values()) {
            builder.put(type.name, type);
        }
        NAME_MAP = builder.build();
    }
}
