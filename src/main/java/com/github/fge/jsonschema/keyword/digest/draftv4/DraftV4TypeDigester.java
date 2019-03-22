// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.digest.draftv4;

import java.util.Iterator;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.EnumSet;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.keyword.digest.Digester;
import com.github.fge.jsonschema.keyword.digest.AbstractDigester;

public final class DraftV4TypeDigester extends AbstractDigester
{
    private static final Digester INSTANCE;
    
    public static Digester getInstance() {
        return DraftV4TypeDigester.INSTANCE;
    }
    
    private DraftV4TypeDigester() {
        super("type", NodeType.ARRAY, NodeType.values());
    }
    
    @Override
    public JsonNode digest(final JsonNode schema) {
        final ObjectNode ret = DraftV4TypeDigester.FACTORY.objectNode();
        final ArrayNode allowedTypes = DraftV4TypeDigester.FACTORY.arrayNode();
        ret.put(this.keyword, allowedTypes);
        final JsonNode node = schema.get(this.keyword);
        final EnumSet<NodeType> typeSet = EnumSet.noneOf(NodeType.class);
        if (node.isTextual()) {
            typeSet.add(NodeType.fromName(node.textValue()));
        }
        else {
            for (final JsonNode element : node) {
                typeSet.add(NodeType.fromName(element.textValue()));
            }
        }
        if (typeSet.contains(NodeType.NUMBER)) {
            typeSet.add(NodeType.INTEGER);
        }
        for (final NodeType type : typeSet) {
            allowedTypes.add(type.toString());
        }
        return ret;
    }
    
    static {
        INSTANCE = new DraftV4TypeDigester();
    }
}
