// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jackson.jsonpointer;

import com.fasterxml.jackson.core.TreeNode;
import javax.annotation.concurrent.Immutable;
import com.fasterxml.jackson.databind.JsonNode;

@Immutable
public final class JsonNodeResolver extends TokenResolver<JsonNode>
{
    private static final char ZERO = '0';
    
    public JsonNodeResolver(final ReferenceToken token) {
        super(token);
    }
    
    @Override
    public JsonNode get(final JsonNode node) {
        if (node == null || !node.isContainerNode()) {
            return null;
        }
        final String raw = this.token.getRaw();
        return node.isObject() ? node.get(raw) : node.get(arrayIndexFor(raw));
    }
    
    private static int arrayIndexFor(final String raw) {
        if (raw.isEmpty()) {
            return -1;
        }
        if (raw.charAt(0) == '0') {
            return (raw.length() == 1) ? 0 : -1;
        }
        try {
            return Integer.parseInt(raw);
        }
        catch (NumberFormatException ignored) {
            return -1;
        }
    }
}
