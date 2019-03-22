// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator.draftv3;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.core.processing.Processor;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.keyword.validator.AbstractKeywordValidator;

public final class ExtendsValidator extends AbstractKeywordValidator
{
    public ExtendsValidator(final JsonNode digest) {
        super("extends");
    }
    
    @Override
    public void validate(final Processor<FullData, FullData> processor, final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final SchemaTree tree = data.getSchema();
        final JsonNode node = tree.getNode().get(this.keyword);
        if (node.isObject()) {
            final FullData newData = data.withSchema(tree.append(JsonPointer.of(this.keyword, new Object[0])));
            processor.process(report, newData);
            return;
        }
        for (int size = node.size(), index = 0; index < size; ++index) {
            final JsonPointer pointer = JsonPointer.of(this.keyword, index);
            final FullData newData = data.withSchema(tree.append(pointer));
            processor.process(report, newData);
        }
    }
    
    @Override
    public String toString() {
        return this.keyword;
    }
}
