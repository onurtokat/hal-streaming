// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator.helpers;

import java.math.BigDecimal;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.fasterxml.jackson.databind.JsonNode;

public abstract class DivisorValidator extends NumericValidator
{
    protected DivisorValidator(final String keyword, final JsonNode digest) {
        super(keyword, digest);
    }
    
    @Override
    protected final void validateLong(final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final JsonNode node = data.getInstance().getNode();
        final long instanceValue = node.longValue();
        final long longValue = this.number.longValue();
        final long remainder = instanceValue % longValue;
        if (remainder == 0L) {
            return;
        }
        report.error(this.newMsg(data, bundle, "err.common.divisor.nonZeroRemainder").putArgument("value", node).putArgument("divisor", this.number));
    }
    
    @Override
    protected final void validateDecimal(final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final JsonNode node = data.getInstance().getNode();
        final BigDecimal instanceValue = node.decimalValue();
        final BigDecimal decimalValue = this.number.decimalValue();
        final BigDecimal remainder = instanceValue.remainder(decimalValue);
        if (remainder.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        report.error(this.newMsg(data, bundle, "err.common.divisor.nonZeroRemainder").putArgument("value", node).putArgument("divisor", this.number));
    }
}
