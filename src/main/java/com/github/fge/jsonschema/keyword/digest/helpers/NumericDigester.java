// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.digest.helpers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.math.BigDecimal;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.keyword.digest.AbstractDigester;

public abstract class NumericDigester extends AbstractDigester
{
    protected NumericDigester(final String keyword) {
        super(keyword, NodeType.INTEGER, new NodeType[] { NodeType.NUMBER });
    }
    
    private static boolean valueIsLong(final JsonNode node) {
        return node.canConvertToLong() && (NodeType.getNodeType(node) == NodeType.INTEGER || node.decimalValue().remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0);
    }
    
    protected final ObjectNode digestedNumberNode(final JsonNode schema) {
        final ObjectNode ret = NumericDigester.FACTORY.objectNode();
        final JsonNode node = schema.get(this.keyword);
        final boolean isLong = valueIsLong(node);
        ret.put("valueIsLong", isLong);
        if (isLong) {
            ret.put(this.keyword, node.canConvertToInt() ? NumericDigester.FACTORY.numberNode(node.intValue()) : NumericDigester.FACTORY.numberNode(node.longValue()));
            return ret;
        }
        final BigDecimal decimal = node.decimalValue();
        ret.put(this.keyword, (decimal.scale() == 0) ? NumericDigester.FACTORY.numberNode(decimal.toBigIntegerExact()) : node);
        return ret;
    }
}
