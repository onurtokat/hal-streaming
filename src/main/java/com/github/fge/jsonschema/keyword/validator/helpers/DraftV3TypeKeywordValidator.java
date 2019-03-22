// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator.helpers;

import com.github.fge.jackson.JacksonUtils;
import java.util.Iterator;
import com.google.common.collect.Lists;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import com.github.fge.jackson.NodeType;
import java.util.EnumSet;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.github.fge.jsonschema.keyword.validator.AbstractKeywordValidator;

public abstract class DraftV3TypeKeywordValidator extends AbstractKeywordValidator
{
    protected static final JsonNodeFactory FACTORY;
    protected final EnumSet<NodeType> types;
    protected final List<Integer> schemas;
    
    protected DraftV3TypeKeywordValidator(final String keyword, final JsonNode digested) {
        super(keyword);
        this.types = EnumSet.noneOf(NodeType.class);
        this.schemas = (List<Integer>)Lists.newArrayList();
        for (final JsonNode element : digested.get(keyword)) {
            this.types.add(NodeType.fromName(element.textValue()));
        }
        for (final JsonNode element : digested.get("schemas")) {
            this.schemas.add(element.intValue());
        }
    }
    
    @Override
    public final String toString() {
        return this.keyword + ": " + this.types + "; " + this.schemas.size() + " schemas";
    }
    
    static {
        FACTORY = JacksonUtils.nodeFactory();
    }
}
