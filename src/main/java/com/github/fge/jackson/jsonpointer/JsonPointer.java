// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jackson.jsonpointer;

import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import java.util.Collection;
import com.fasterxml.jackson.databind.node.MissingNode;
import java.util.List;
import com.google.common.collect.Lists;
import javax.annotation.concurrent.Immutable;
import com.fasterxml.jackson.databind.JsonNode;

@Immutable
public final class JsonPointer extends TreePointer<JsonNode>
{
    private static final JsonPointer EMPTY;
    
    public static JsonPointer empty() {
        return JsonPointer.EMPTY;
    }
    
    public static JsonPointer of(final Object first, final Object... other) {
        final List<ReferenceToken> tokens = (List<ReferenceToken>)Lists.newArrayList();
        tokens.add(ReferenceToken.fromRaw(first.toString()));
        for (final Object o : other) {
            tokens.add(ReferenceToken.fromRaw(o.toString()));
        }
        return new JsonPointer(fromTokens(tokens));
    }
    
    public JsonPointer(final String input) throws JsonPointerException {
        this(fromTokens(TreePointer.tokensFromInput(input)));
    }
    
    public JsonPointer(final List<TokenResolver<JsonNode>> tokenResolvers) {
        super(MissingNode.getInstance(), tokenResolvers);
    }
    
    public JsonPointer append(final String raw) {
        final ReferenceToken refToken = ReferenceToken.fromRaw(raw);
        final JsonNodeResolver resolver = new JsonNodeResolver(refToken);
        final List<TokenResolver<JsonNode>> list = (List<TokenResolver<JsonNode>>)Lists.newArrayList((Iterable<?>)this.tokenResolvers);
        list.add(resolver);
        return new JsonPointer(list);
    }
    
    public JsonPointer append(final int index) {
        return this.append(Integer.toString(index));
    }
    
    public JsonPointer append(final JsonPointer other) {
        JsonPointer.BUNDLE.checkNotNull(other, "nullInput");
        final List<TokenResolver<JsonNode>> list = (List<TokenResolver<JsonNode>>)Lists.newArrayList((Iterable<?>)this.tokenResolvers);
        list.addAll((Collection<? extends TokenResolver<JsonNode>>)other.tokenResolvers);
        return new JsonPointer(list);
    }
    
    public JsonPointer parent() {
        final int size = this.tokenResolvers.size();
        return (size <= 1) ? JsonPointer.EMPTY : new JsonPointer((List<TokenResolver<JsonNode>>)this.tokenResolvers.subList(0, size - 1));
    }
    
    private static List<TokenResolver<JsonNode>> fromTokens(final List<ReferenceToken> tokens) {
        final List<TokenResolver<JsonNode>> list = (List<TokenResolver<JsonNode>>)Lists.newArrayList();
        for (final ReferenceToken token : tokens) {
            list.add(new JsonNodeResolver(token));
        }
        return list;
    }
    
    static {
        EMPTY = new JsonPointer((List<TokenResolver<JsonNode>>)ImmutableList.of());
    }
}
