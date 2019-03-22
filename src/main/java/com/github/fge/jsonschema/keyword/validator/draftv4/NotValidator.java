// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator.draftv4;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.report.ListProcessingReport;
import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.core.processing.Processor;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonschema.keyword.validator.AbstractKeywordValidator;

public final class NotValidator extends AbstractKeywordValidator
{
    private static final JsonPointer PTR;
    
    public NotValidator(final JsonNode digest) {
        super("not");
    }
    
    @Override
    public void validate(final Processor<FullData, FullData> processor, final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final SchemaTree tree = data.getSchema();
        final ProcessingReport subReport = new ListProcessingReport(report.getLogLevel(), LogLevel.FATAL);
        processor.process(subReport, data.withSchema(tree.append(NotValidator.PTR)));
        if (subReport.isSuccess()) {
            report.error(this.newMsg(data, bundle, "err.draftv4.not.fail"));
        }
    }
    
    @Override
    public String toString() {
        return "must not match subschema";
    }
    
    static {
        PTR = JsonPointer.of("not", new Object[0]);
    }
}
