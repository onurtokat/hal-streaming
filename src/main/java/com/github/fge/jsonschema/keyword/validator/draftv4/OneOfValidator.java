// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator.draftv4;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonschema.core.report.ListProcessingReport;
import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.core.processing.Processor;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.keyword.validator.helpers.SchemaArrayValidator;

public final class OneOfValidator extends SchemaArrayValidator
{
    public OneOfValidator(final JsonNode digest) {
        super("oneOf");
    }
    
    @Override
    public void validate(final Processor<FullData, FullData> processor, final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final SchemaTree tree = data.getSchema();
        final JsonPointer schemaPointer = tree.getPointer();
        final JsonNode schemas = tree.getNode().get(this.keyword);
        final int size = schemas.size();
        final ObjectNode fullReport = OneOfValidator.FACTORY.objectNode();
        int nrSuccess = 0;
        for (int index = 0; index < size; ++index) {
            final ListProcessingReport subReport = new ListProcessingReport(report.getLogLevel(), LogLevel.FATAL);
            final JsonPointer ptr = schemaPointer.append(JsonPointer.of(this.keyword, index));
            final FullData newData = data.withSchema(tree.setPointer(ptr));
            processor.process(subReport, newData);
            fullReport.put(ptr.toString(), subReport.asJson());
            if (subReport.isSuccess()) {
                ++nrSuccess;
            }
        }
        if (nrSuccess != 1) {
            report.error(this.newMsg(data, bundle, "err.draftv4.oneOf.fail").putArgument("matched", nrSuccess).putArgument("nrSchemas", size).put("reports", (JsonNode)fullReport));
        }
    }
}
