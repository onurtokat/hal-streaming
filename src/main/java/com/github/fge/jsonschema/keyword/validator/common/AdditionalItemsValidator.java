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
import com.github.fge.jsonschema.keyword.validator.AbstractKeywordValidator;

public final class AdditionalItemsValidator extends AbstractKeywordValidator
{
    private final boolean additionalOK;
    private final int itemsSize;
    
    public AdditionalItemsValidator(final JsonNode digest) {
        super("additionalItems");
        this.additionalOK = digest.get(this.keyword).booleanValue();
        this.itemsSize = digest.get("itemsSize").intValue();
    }
    
    @Override
    public void validate(final Processor<FullData, FullData> processor, final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        if (this.additionalOK) {
            return;
        }
        final int size = data.getInstance().getNode().size();
        if (size > this.itemsSize) {
            report.error(this.newMsg(data, bundle, "err.common.additionalItems.notAllowed").putArgument("allowed", this.itemsSize).putArgument("found", size));
        }
    }
    
    @Override
    public String toString() {
        return this.keyword + ": " + (this.additionalOK ? "allowed" : (this.itemsSize + " max"));
    }
}
