// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.main;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.core.messages.JsonSchemaCoreMessageBundle;
import com.github.fge.jsonschema.core.tree.JsonTree;
import com.github.fge.jsonschema.core.tree.SimpleJsonTree;
import com.github.fge.jsonschema.core.ref.JsonRef;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.exceptions.JsonReferenceException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.MessageProvider;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.core.processing.Processor;
import com.github.fge.jsonschema.core.processing.ProcessingResult;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.report.ReportProvider;
import com.github.fge.jsonschema.processors.validation.ValidationProcessor;
import com.github.fge.jsonschema.core.load.SchemaLoader;
import com.github.fge.msgsimple.bundle.MessageBundle;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class JsonValidator
{
    private static final MessageBundle BUNDLE;
    private final SchemaLoader loader;
    private final ValidationProcessor processor;
    private final ReportProvider reportProvider;
    
    JsonValidator(final SchemaLoader loader, final ValidationProcessor processor, final ReportProvider reportProvider) {
        this.loader = loader;
        this.processor = processor;
        this.reportProvider = reportProvider;
    }
    
    public ProcessingReport validate(final JsonNode schema, final JsonNode instance, final boolean deepCheck) throws ProcessingException {
        final ProcessingReport report = this.reportProvider.newReport();
        final FullData data = this.buildData(schema, instance, deepCheck);
        return ProcessingResult.of((Processor<FullData, MessageProvider>)this.processor, report, data).getReport();
    }
    
    public ProcessingReport validate(final JsonNode schema, final JsonNode instance) throws ProcessingException {
        return this.validate(schema, instance, false);
    }
    
    public ProcessingReport validateUnchecked(final JsonNode schema, final JsonNode instance, final boolean deepCheck) {
        final ProcessingReport report = this.reportProvider.newReport();
        final FullData data = this.buildData(schema, instance, deepCheck);
        return ProcessingResult.uncheckedResult((Processor<FullData, MessageProvider>)this.processor, report, data).getReport();
    }
    
    public ProcessingReport validateUnchecked(final JsonNode schema, final JsonNode instance) {
        return this.validateUnchecked(schema, instance, false);
    }
    
    JsonSchema buildJsonSchema(final JsonNode schema, final JsonPointer pointer) throws ProcessingException {
        final SchemaTree tree = this.loader.load(schema).setPointer(pointer);
        if (tree.getNode().isMissingNode()) {
            throw new JsonReferenceException(new ProcessingMessage().setMessage(JsonValidator.BUNDLE.getMessage("danglingRef")));
        }
        return new JsonSchema(this.processor, tree, this.reportProvider);
    }
    
    JsonSchema buildJsonSchema(final String uri) throws ProcessingException {
        final JsonRef ref = JsonRef.fromString(uri);
        if (!ref.isLegal()) {
            throw new JsonReferenceException(new ProcessingMessage().setMessage(JsonValidator.BUNDLE.getMessage("illegalJsonRef")));
        }
        final SchemaTree tree = this.loader.get(ref.getLocator()).setPointer(ref.getPointer());
        if (tree.getNode().isMissingNode()) {
            throw new JsonReferenceException(new ProcessingMessage().setMessage(JsonValidator.BUNDLE.getMessage("danglingRef")));
        }
        return new JsonSchema(this.processor, tree, this.reportProvider);
    }
    
    Processor<FullData, FullData> getProcessor() {
        return this.processor;
    }
    
    private FullData buildData(final JsonNode schema, final JsonNode instance, final boolean deepCheck) {
        JsonValidator.BUNDLE.checkNotNull(schema, "nullSchema");
        JsonValidator.BUNDLE.checkNotNull(instance, "nullInstance");
        final SchemaTree schemaTree = this.loader.load(schema);
        final JsonTree tree = new SimpleJsonTree(instance);
        return new FullData(schemaTree, tree, deepCheck);
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);
    }
}
