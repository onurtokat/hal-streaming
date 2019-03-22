// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator.helpers;

import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.core.processing.Processor;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.keyword.validator.AbstractKeywordValidator;

public abstract class NumericValidator extends AbstractKeywordValidator
{
    protected final JsonNode number;
    private final boolean isLong;
    
    protected NumericValidator(final String keyword, final JsonNode digest) {
        super(keyword);
        this.number = digest.get(keyword);
        this.isLong = digest.get("valueIsLong").booleanValue();
    }
    
    @Override
    public final void validate(final Processor<FullData, FullData> processor, final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final JsonNode instance = data.getInstance().getNode();
        if (valueIsLong(instance) && this.isLong) {
            this.validateLong(report, bundle, data);
        }
        else {
            this.validateDecimal(report, bundle, data);
        }
    }
    
    protected abstract void validateLong(final ProcessingReport p0, final MessageBundle p1, final FullData p2) throws ProcessingException;
    
    protected abstract void validateDecimal(final ProcessingReport p0, final MessageBundle p1, final FullData p2) throws ProcessingException;
    
    @Override
    public final String toString() {
        return this.keyword + ": " + this.number;
    }
    
    private static boolean valueIsLong(final JsonNode node) {
        return NodeType.getNodeType(node) == NodeType.INTEGER && node.canConvertToLong();
    }
}
