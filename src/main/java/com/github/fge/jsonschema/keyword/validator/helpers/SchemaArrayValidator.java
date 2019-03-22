// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator.helpers;

import com.github.fge.jackson.JacksonUtils;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.github.fge.jsonschema.keyword.validator.AbstractKeywordValidator;

public abstract class SchemaArrayValidator extends AbstractKeywordValidator
{
    protected static final JsonNodeFactory FACTORY;
    
    protected SchemaArrayValidator(final String keyword) {
        super(keyword);
    }
    
    @Override
    public final String toString() {
        return this.keyword;
    }
    
    static {
        FACTORY = JacksonUtils.nodeFactory();
    }
}
