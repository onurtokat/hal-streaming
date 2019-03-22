// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jackson;

import java.util.Set;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Equivalence;

public final class JsonNumEquals extends Equivalence<JsonNode>
{
    private static final Equivalence<JsonNode> INSTANCE;
    
    public static Equivalence<JsonNode> getInstance() {
        return JsonNumEquals.INSTANCE;
    }
    
    @Override
    protected boolean doEquivalent(final JsonNode a, final JsonNode b) {
        if (a.isNumber() && b.isNumber()) {
            return numEquals(a, b);
        }
        final NodeType typeA = NodeType.getNodeType(a);
        final NodeType typeB = NodeType.getNodeType(b);
        if (typeA != typeB) {
            return false;
        }
        if (!a.isContainerNode()) {
            return a.equals(b);
        }
        return a.size() == b.size() && ((typeA == NodeType.ARRAY) ? this.arrayEquals(a, b) : this.objectEquals(a, b));
    }
    
    @Override
    protected int doHash(final JsonNode t) {
        if (t.isNumber()) {
            return Double.valueOf(t.doubleValue()).hashCode();
        }
        if (!t.isContainerNode()) {
            return t.hashCode();
        }
        int ret = 0;
        if (t.size() == 0) {
            return ret;
        }
        if (t.isArray()) {
            for (final JsonNode element : t) {
                ret = 31 * ret + this.doHash(element);
            }
            return ret;
        }
        final Iterator<Map.Entry<String, JsonNode>> iterator = t.fields();
        while (iterator.hasNext()) {
            final Map.Entry<String, JsonNode> entry = iterator.next();
            ret = 31 * ret + (entry.getKey().hashCode() ^ this.doHash(entry.getValue()));
        }
        return ret;
    }
    
    private static boolean numEquals(final JsonNode a, final JsonNode b) {
        if (a.isIntegralNumber() && b.isIntegralNumber()) {
            return a.equals(b);
        }
        return a.decimalValue().compareTo(b.decimalValue()) == 0;
    }
    
    private boolean arrayEquals(final JsonNode a, final JsonNode b) {
        for (int size = a.size(), i = 0; i < size; ++i) {
            if (!this.doEquivalent(a.get(i), b.get(i))) {
                return false;
            }
        }
        return true;
    }
    
    private boolean objectEquals(final JsonNode a, final JsonNode b) {
        final Set<String> keys = (Set<String>)Sets.newHashSet((Iterator<?>)a.fieldNames());
        final Set<String> set = (Set<String>)Sets.newHashSet((Iterator<?>)b.fieldNames());
        if (!set.equals(keys)) {
            return false;
        }
        for (final String key : keys) {
            if (!this.doEquivalent(a.get(key), b.get(key))) {
                return false;
            }
        }
        return true;
    }
    
    static {
        INSTANCE = new JsonNumEquals();
    }
}
