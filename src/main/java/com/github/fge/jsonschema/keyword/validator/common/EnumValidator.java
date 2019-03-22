// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator.common;

import com.github.fge.jackson.JsonNumEquals;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.util.Iterator;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.core.processing.Processor;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Equivalence;
import com.github.fge.jsonschema.keyword.validator.AbstractKeywordValidator;

public final class EnumValidator extends AbstractKeywordValidator
{
    private static final Equivalence<JsonNode> EQUIVALENCE;
    private final JsonNode values;
    
    public EnumValidator(final JsonNode digest) {
        super("enum");
        this.values = digest.get(this.keyword);
    }
    
    @Override
    public void validate(final Processor<FullData, FullData> processor, final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final JsonNode node = data.getInstance().getNode();
        for (final JsonNode enumValue : this.values) {
            if (EnumValidator.EQUIVALENCE.equivalent(enumValue, node)) {
                return;
            }
        }
        report.error(this.newMsg(data, bundle, "err.common.enum.notInEnum").putArgument("value", node).putArgument(this.keyword, this.values));
    }
    
    @Override
    public String toString() {
        return this.keyword + '(' + this.values.size() + " possible values)";
    }
    
    static {
        EQUIVALENCE = JsonNumEquals.getInstance();
    }
}
