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

public final class MinLengthValidator extends PositiveIntegerValidator
{
    public MinLengthValidator(final JsonNode digested) {
        super("minLength", digested);
    }
    
    @Override
    public void validate(final Processor<FullData, FullData> processor, final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final String value = data.getInstance().getNode().textValue();
        final int size = value.codePointCount(0, value.length());
        if (size < this.intValue) {
            report.error(this.newMsg(data, bundle, "err.common.minLength.tooShort").putArgument("value", value).putArgument("found", size).putArgument(this.keyword, this.intValue));
        }
    }
}
