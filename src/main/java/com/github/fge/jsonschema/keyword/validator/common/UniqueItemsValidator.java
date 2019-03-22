// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator.common;

import com.github.fge.jackson.JsonNumEquals;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.util.Iterator;
import java.util.Set;
import com.google.common.collect.Sets;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.core.processing.Processor;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Equivalence;
import com.github.fge.jsonschema.keyword.validator.AbstractKeywordValidator;

public final class UniqueItemsValidator extends AbstractKeywordValidator
{
    private static final Equivalence<JsonNode> EQUIVALENCE;
    private final boolean uniqueItems;
    
    public UniqueItemsValidator(final JsonNode digest) {
        super("uniqueItems");
        this.uniqueItems = digest.get(this.keyword).booleanValue();
    }
    
    @Override
    public void validate(final Processor<FullData, FullData> processor, final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        if (!this.uniqueItems) {
            return;
        }
        final Set<Equivalence.Wrapper<JsonNode>> set = (Set<Equivalence.Wrapper<JsonNode>>)Sets.newHashSet();
        final JsonNode node = data.getInstance().getNode();
        for (final JsonNode element : node) {
            if (!set.add(UniqueItemsValidator.EQUIVALENCE.wrap(element))) {
                report.error(this.newMsg(data, bundle, "err.common.uniqueItems.duplicateElements"));
            }
        }
    }
    
    @Override
    public String toString() {
        return this.keyword + ": " + this.uniqueItems;
    }
    
    static {
        EQUIVALENCE = JsonNumEquals.getInstance();
    }
}
