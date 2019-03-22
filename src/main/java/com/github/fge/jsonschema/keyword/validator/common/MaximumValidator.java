// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator.common;

import java.math.BigDecimal;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.keyword.validator.helpers.NumericValidator;

public final class MaximumValidator extends NumericValidator
{
    private final boolean exclusive;
    
    public MaximumValidator(final JsonNode digest) {
        super("maximum", digest);
        this.exclusive = digest.path("exclusive").booleanValue();
    }
    
    @Override
    protected void validateLong(final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final JsonNode instance = data.getInstance().getNode();
        final long instanceValue = instance.longValue();
        final long longValue = this.number.longValue();
        if (instanceValue < longValue) {
            return;
        }
        if (instanceValue > longValue) {
            report.error(this.newMsg(data, bundle, "err.common.maximum.tooLarge").putArgument(this.keyword, this.number).putArgument("found", instance));
            return;
        }
        if (!this.exclusive) {
            return;
        }
        report.error(this.newMsg(data, bundle, "err.common.maximum.notExclusive").putArgument(this.keyword, this.number).put("exclusiveMaximum", (JsonNode)BooleanNode.TRUE));
    }
    
    @Override
    protected void validateDecimal(final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final JsonNode instance = data.getInstance().getNode();
        final BigDecimal instanceValue = instance.decimalValue();
        final BigDecimal decimalValue = this.number.decimalValue();
        final int cmp = instanceValue.compareTo(decimalValue);
        if (cmp < 0) {
            return;
        }
        if (cmp > 0) {
            report.error(this.newMsg(data, bundle, "err.common.maximum.tooLarge").putArgument(this.keyword, this.number).putArgument("found", instance));
            return;
        }
        if (!this.exclusive) {
            return;
        }
        report.error(this.newMsg(data, bundle, "err.common.maximum.notExclusive").putArgument(this.keyword, this.number).put("exclusiveMaximum", (JsonNode)BooleanNode.TRUE));
    }
}
