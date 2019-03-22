// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.digest.helpers;

import java.util.Collection;
import java.util.Iterator;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.EnumSet;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.keyword.digest.AbstractDigester;

public final class DraftV3TypeKeywordDigester extends AbstractDigester
{
    private static final String ANY = "any";
    
    public DraftV3TypeKeywordDigester(final String keyword) {
        super(keyword, NodeType.ARRAY, NodeType.values());
    }
    
    @Override
    public JsonNode digest(final JsonNode schema) {
        final ObjectNode ret = DraftV3TypeKeywordDigester.FACTORY.objectNode();
        final ArrayNode simpleTypes = DraftV3TypeKeywordDigester.FACTORY.arrayNode();
        ret.put(this.keyword, simpleTypes);
        final ArrayNode schemas = DraftV3TypeKeywordDigester.FACTORY.arrayNode();
        ret.put("schemas", schemas);
        final JsonNode node = schema.get(this.keyword);
        final EnumSet<NodeType> set = EnumSet.noneOf(NodeType.class);
        if (node.isTextual()) {
            putType(set, node.textValue());
        }
        else {
            for (int size = node.size(), index = 0; index < size; ++index) {
                final JsonNode element = node.get(index);
                if (element.isTextual()) {
                    putType(set, element.textValue());
                }
                else {
                    schemas.add(index);
                }
            }
        }
        if (EnumSet.complementOf(set).isEmpty()) {
            schemas.removeAll();
        }
        for (final NodeType type : set) {
            simpleTypes.add(type.toString());
        }
        return ret;
    }
    
    private static void putType(final EnumSet<NodeType> types, final String s) {
        if ("any".equals(s)) {
            types.addAll((Collection<?>)EnumSet.allOf(NodeType.class));
            return;
        }
        final NodeType type = NodeType.fromName(s);
        types.add(type);
        if (type == NodeType.NUMBER) {
            types.add(NodeType.INTEGER);
        }
    }
}
