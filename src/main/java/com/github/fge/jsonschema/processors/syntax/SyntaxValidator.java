// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.processors.syntax;

import com.github.fge.jsonschema.core.tree.CanonicalSchemaTree;
import com.github.fge.jsonschema.core.tree.key.SchemaKey;
import com.github.fge.jsonschema.core.processing.ProcessingResult;
import com.github.fge.jsonschema.core.report.ListProcessingReport;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.core.report.DevNullProcessingReport;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Iterator;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.github.fge.jsonschema.core.util.Dictionary;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.library.Library;
import java.util.Map;
import com.github.fge.jsonschema.core.keyword.syntax.SyntaxProcessor;
import com.github.fge.jsonschema.core.processing.ProcessorMap;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.core.processing.Processor;
import com.github.fge.jsonschema.core.ref.JsonRef;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.util.ValueHolder;
import com.google.common.base.Function;

public final class SyntaxValidator
{
    private static final Function<ValueHolder<SchemaTree>, JsonRef> FUNCTION;
    private final Processor<ValueHolder<SchemaTree>, ValueHolder<SchemaTree>> processor;
    
    public SyntaxValidator(final ValidationConfiguration cfg) {
        final MessageBundle syntaxMessages = cfg.getSyntaxMessages();
        final ProcessorMap<JsonRef, ValueHolder<SchemaTree>, ValueHolder<SchemaTree>> map = new ProcessorMap<JsonRef, ValueHolder<SchemaTree>, ValueHolder<SchemaTree>>(SyntaxValidator.FUNCTION);
        Dictionary<SyntaxChecker> dict = cfg.getDefaultLibrary().getSyntaxCheckers();
        final SyntaxProcessor byDefault = new SyntaxProcessor(cfg.getSyntaxMessages(), dict);
        map.setDefaultProcessor(byDefault);
        final Map<JsonRef, Library> libraries = cfg.getLibraries();
        for (final Map.Entry<JsonRef, Library> entry : libraries.entrySet()) {
            final JsonRef ref = entry.getKey();
            dict = entry.getValue().getSyntaxCheckers();
            final SyntaxProcessor syntaxProcessor = new SyntaxProcessor(syntaxMessages, dict);
            map.addEntry(ref, syntaxProcessor);
        }
        this.processor = map.getProcessor();
    }
    
    public boolean schemaIsValid(final JsonNode schema) {
        final ProcessingReport report = new DevNullProcessingReport();
        return this.getResult(schema, report).isSuccess();
    }
    
    public ProcessingReport validateSchema(final JsonNode schema) {
        final ProcessingReport report = new ListProcessingReport();
        return this.getResult(schema, report).getReport();
    }
    
    public Processor<ValueHolder<SchemaTree>, ValueHolder<SchemaTree>> getProcessor() {
        return this.processor;
    }
    
    private ProcessingResult<ValueHolder<SchemaTree>> getResult(final JsonNode schema, final ProcessingReport report) {
        final ValueHolder<SchemaTree> holder = holder(schema);
        return ProcessingResult.uncheckedResult(this.processor, report, holder);
    }
    
    private static ValueHolder<SchemaTree> holder(final JsonNode node) {
        return (ValueHolder<SchemaTree>)ValueHolder.hold("schema", new CanonicalSchemaTree(SchemaKey.anonymousKey(), node));
    }
    
    static {
        FUNCTION = new Function<ValueHolder<SchemaTree>, JsonRef>() {
            @Override
            public JsonRef apply(final ValueHolder<SchemaTree> input) {
                return input.getValue().getDollarSchema();
            }
        };
    }
}
