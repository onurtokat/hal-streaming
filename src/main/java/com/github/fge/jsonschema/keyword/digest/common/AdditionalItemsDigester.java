// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.digest.common;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.keyword.digest.Digester;
import com.github.fge.jsonschema.keyword.digest.AbstractDigester;

public final class AdditionalItemsDigester extends AbstractDigester
{
    private static final Digester INSTANCE;
    
    public static Digester getInstance() {
        return AdditionalItemsDigester.INSTANCE;
    }
    
    private AdditionalItemsDigester() {
        super("additionalItems", NodeType.ARRAY, new NodeType[0]);
    }
    
    @Override
    public JsonNode digest(final JsonNode schema) {
        final ObjectNode ret = AdditionalItemsDigester.FACTORY.objectNode();
        ret.put(this.keyword, true);
        ret.put("itemsSize", 0);
        if (schema.get(this.keyword).asBoolean(true)) {
            return ret;
        }
        final JsonNode itemsNode = schema.path("items");
        if (!itemsNode.isArray()) {
            return ret;
        }
        ret.put(this.keyword, false);
        ret.put("itemsSize", itemsNode.size());
        return ret;
    }
    
    static {
        INSTANCE = new AdditionalItemsDigester();
    }
}
