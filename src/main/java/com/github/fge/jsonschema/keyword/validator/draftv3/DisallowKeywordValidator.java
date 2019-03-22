// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator.draftv3;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.util.Iterator;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonschema.core.report.ListProcessingReport;
import com.github.fge.jsonschema.core.report.LogLevel;
import java.util.Collection;
import com.github.fge.jsonschema.keyword.validator.AbstractKeywordValidator;
import com.github.fge.jackson.NodeType;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.core.processing.Processor;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.keyword.validator.helpers.DraftV3TypeKeywordValidator;

public final class DisallowKeywordValidator extends DraftV3TypeKeywordValidator
{
    public DisallowKeywordValidator(final JsonNode digested) {
        super("disallow", digested);
    }
    
    @Override
    public void validate(final Processor<FullData, FullData> processor, final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final JsonNode instance = data.getInstance().getNode();
        final NodeType type = NodeType.getNodeType(instance);
        if (this.types.contains(type)) {
            report.error(this.newMsg(data, bundle, "err.draftv3.disallow.type").putArgument("found", type).putArgument("disallowed", AbstractKeywordValidator.toArrayNode(this.types)));
            return;
        }
        final SchemaTree tree = data.getSchema();
        final JsonPointer schemaPointer = tree.getPointer();
        final ObjectNode fullReport = DisallowKeywordValidator.FACTORY.objectNode();
        int nrSuccess = 0;
        for (final int index : this.schemas) {
            final ListProcessingReport subReport = new ListProcessingReport(report.getLogLevel(), LogLevel.FATAL);
            final JsonPointer ptr = schemaPointer.append(JsonPointer.of(this.keyword, index));
            final FullData newData = data.withSchema(tree.setPointer(ptr));
            processor.process(subReport, newData);
            fullReport.put(ptr.toString(), subReport.asJson());
            if (subReport.isSuccess()) {
                ++nrSuccess;
            }
        }
        if (nrSuccess != 0) {
            report.error(this.newMsg(data, bundle, "err.draftv3.disallow.schema").putArgument("matched", nrSuccess).putArgument("nrSchemas", this.schemas.size()).put("reports", (JsonNode)fullReport));
        }
    }
}
