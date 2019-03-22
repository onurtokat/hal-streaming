// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator.common;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.core.processing.Processor;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.keyword.validator.helpers.PositiveIntegerValidator;

public final class MinItemsValidator extends PositiveIntegerValidator
{
    public MinItemsValidator(final JsonNode digest) {
        super("minItems", digest);
    }
    
    @Override
    public void validate(final Processor<FullData, FullData> processor, final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final int size = data.getInstance().getNode().size();
        if (size < this.intValue) {
            report.error(this.newMsg(data, bundle, "err.common.minItems.arrayTooShort").putArgument(this.keyword, this.intValue).putArgument("found", size));
        }
    }
}
