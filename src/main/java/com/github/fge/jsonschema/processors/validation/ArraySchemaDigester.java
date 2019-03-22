// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.processors.validation;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.keyword.digest.Digester;
import com.github.fge.jsonschema.keyword.digest.AbstractDigester;

public final class ArraySchemaDigester extends AbstractDigester
{
    private static final Digester INSTANCE;
    
    public static Digester getInstance() {
        return ArraySchemaDigester.INSTANCE;
    }
    
    private ArraySchemaDigester() {
        super("", NodeType.ARRAY, new NodeType[0]);
    }
    
    @Override
    public JsonNode digest(final JsonNode schema) {
        final ObjectNode ret = ArraySchemaDigester.FACTORY.objectNode();
        ret.put("itemsSize", 0);
        ret.put("itemsIsArray", false);
        final JsonNode itemsNode = schema.path("items");
        final JsonNode additionalNode = schema.path("additionalItems");
        final boolean hasItems = !itemsNode.isMissingNode();
        final boolean hasAdditional = additionalNode.isObject();
        ret.put("hasItems", hasItems);
        ret.put("hasAdditional", hasAdditional);
        if (itemsNode.isArray()) {
            ret.put("itemsIsArray", true);
            ret.put("itemsSize", itemsNode.size());
        }
        return ret;
    }
    
    static {
        INSTANCE = new ArraySchemaDigester();
    }
}
