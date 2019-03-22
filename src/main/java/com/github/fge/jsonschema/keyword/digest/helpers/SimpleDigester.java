// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.digest.helpers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.keyword.digest.AbstractDigester;

public final class SimpleDigester extends AbstractDigester
{
    public SimpleDigester(final String keyword, final NodeType first, final NodeType... other) {
        super(keyword, first, other);
    }
    
    @Override
    public JsonNode digest(final JsonNode schema) {
        final ObjectNode ret = SimpleDigester.FACTORY.objectNode();
        ret.put(this.keyword, schema.get(this.keyword));
        return ret;
    }
}
