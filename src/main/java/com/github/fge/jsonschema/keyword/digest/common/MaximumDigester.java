// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.digest.common;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.keyword.digest.Digester;
import com.github.fge.jsonschema.keyword.digest.helpers.NumericDigester;

public final class MaximumDigester extends NumericDigester
{
    private static final Digester INSTANCE;
    
    public static Digester getInstance() {
        return MaximumDigester.INSTANCE;
    }
    
    private MaximumDigester() {
        super("maximum");
    }
    
    @Override
    public JsonNode digest(final JsonNode schema) {
        final ObjectNode ret = this.digestedNumberNode(schema);
        ret.put("exclusive", schema.path("exclusiveMaximum").asBoolean(false));
        return ret;
    }
    
    static {
        INSTANCE = new MaximumDigester();
    }
}
