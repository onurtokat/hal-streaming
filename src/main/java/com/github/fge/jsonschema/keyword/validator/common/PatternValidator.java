// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator.common;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.util.RhinoHelper;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.core.processing.Processor;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.keyword.validator.AbstractKeywordValidator;

public final class PatternValidator extends AbstractKeywordValidator
{
    public PatternValidator(final JsonNode digest) {
        super("pattern");
    }
    
    @Override
    public void validate(final Processor<FullData, FullData> processor, final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final String regex = data.getSchema().getNode().get(this.keyword).textValue();
        final String value = data.getInstance().getNode().textValue();
        if (!RhinoHelper.regMatch(regex, value)) {
            report.error(this.newMsg(data, bundle, "err.common.pattern.noMatch").putArgument("regex", regex).putArgument("string", value));
        }
    }
    
    @Override
    public String toString() {
        return this.keyword;
    }
}
