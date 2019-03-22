// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator.draftv3;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.keyword.validator.helpers.DivisorValidator;

public final class DivisibleByValidator extends DivisorValidator
{
    public DivisibleByValidator(final JsonNode digest) {
        super("divisibleBy", digest);
    }
}
