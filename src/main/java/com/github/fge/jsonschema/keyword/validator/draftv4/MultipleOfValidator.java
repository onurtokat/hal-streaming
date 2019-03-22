// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator.draftv4;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.keyword.validator.helpers.DivisorValidator;

public final class MultipleOfValidator extends DivisorValidator
{
    public MultipleOfValidator(final JsonNode digest) {
        super("multipleOf", digest);
    }
}
