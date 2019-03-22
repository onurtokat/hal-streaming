// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.main;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.processing.Processor;
import com.github.fge.jsonschema.core.processing.ProcessingResult;
import com.github.fge.jsonschema.core.tree.JsonTree;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.core.tree.SimpleJsonTree;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.report.ReportProvider;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.processors.validation.ValidationProcessor;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class JsonSchema
{
    private final ValidationProcessor processor;
    private final SchemaTree schema;
    private final ReportProvider reportProvider;
    
    JsonSchema(final ValidationProcessor processor, final SchemaTree schema, final ReportProvider reportProvider) {
        this.processor = processor;
        this.schema = schema;
        this.reportProvider = reportProvider;
    }
    
    private ProcessingReport doValidate(final JsonNode node, final boolean deepCheck) throws ProcessingException {
        final FullData data = new FullData(this.schema, new SimpleJsonTree(node), deepCheck);
        final ProcessingReport report = this.reportProvider.newReport();
        final ProcessingResult<FullData> result = ProcessingResult.of((Processor<FullData, FullData>)this.processor, report, data);
        return result.getReport();
    }
    
    private ProcessingReport doValidateUnchecked(final JsonNode node, final boolean deepCheck) {
        final FullData data = new FullData(this.schema, new SimpleJsonTree(node), deepCheck);
        final ProcessingReport report = this.reportProvider.newReport();
        final ProcessingResult<FullData> result = ProcessingResult.uncheckedResult((Processor<FullData, FullData>)this.processor, report, data);
        return result.getReport();
    }
    
    public ProcessingReport validate(final JsonNode instance, final boolean deepCheck) throws ProcessingException {
        return this.doValidate(instance, deepCheck);
    }
    
    public ProcessingReport validate(final JsonNode instance) throws ProcessingException {
        return this.validate(instance, false);
    }
    
    public ProcessingReport validateUnchecked(final JsonNode instance, final boolean deepCheck) {
        return this.doValidateUnchecked(instance, deepCheck);
    }
    
    public ProcessingReport validateUnchecked(final JsonNode instance) {
        return this.doValidateUnchecked(instance, false);
    }
    
    public boolean validInstance(final JsonNode instance) throws ProcessingException {
        return this.doValidate(instance, false).isSuccess();
    }
    
    public boolean validInstanceUnchecked(final JsonNode instance) {
        return this.doValidateUnchecked(instance, false).isSuccess();
    }
}
