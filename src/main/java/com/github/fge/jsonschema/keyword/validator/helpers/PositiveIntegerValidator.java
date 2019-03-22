// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.keyword.validator.AbstractKeywordValidator;

public abstract class PositiveIntegerValidator extends AbstractKeywordValidator
{
    protected final int intValue;
    
    protected PositiveIntegerValidator(final String keyword, final JsonNode digest) {
        super(keyword);
        this.intValue = digest.get(keyword).intValue();
    }
    
    @Override
    public final String toString() {
        return this.keyword + ": " + this.intValue;
    }
}
