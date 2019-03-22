// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.main;

import com.github.fge.jsonschema.core.messages.JsonSchemaCoreMessageBundle;
import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.messages.JsonSchemaConfigurationBundle;
import com.github.fge.Thawed;
import java.util.Iterator;
import com.github.fge.jsonschema.core.processing.CachingProcessor;
import com.github.fge.jsonschema.processors.validation.SchemaContextEquivalence;
import com.github.fge.jsonschema.library.Library;
import java.util.Map;
import com.github.fge.jsonschema.core.processing.ProcessorMap;
import com.github.fge.jsonschema.processors.validation.ValidationChain;
import com.github.fge.jsonschema.core.load.RefResolver;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.processors.data.ValidatorList;
import com.github.fge.jsonschema.core.processing.Processor;
import com.github.fge.jsonschema.processors.validation.ValidationProcessor;
import com.github.fge.jsonschema.processors.syntax.SyntaxValidator;
import com.github.fge.jsonschema.core.load.SchemaLoader;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.core.load.configuration.LoadingConfiguration;
import com.github.fge.jsonschema.core.report.ReportProvider;
import com.github.fge.jsonschema.core.ref.JsonRef;
import com.github.fge.jsonschema.processors.data.SchemaContext;
import com.google.common.base.Function;
import com.github.fge.msgsimple.bundle.MessageBundle;
import javax.annotation.concurrent.Immutable;
import com.github.fge.Frozen;

@Immutable
public final class JsonSchemaFactory implements Frozen<JsonSchemaFactoryBuilder>
{
    private static final MessageBundle BUNDLE;
    private static final MessageBundle CORE_BUNDLE;
    private static final Function<SchemaContext, JsonRef> FUNCTION;
    final ReportProvider reportProvider;
    final LoadingConfiguration loadingCfg;
    final ValidationConfiguration validationCfg;
    private final SchemaLoader loader;
    private final JsonValidator validator;
    private final SyntaxValidator syntaxValidator;
    
    public static JsonSchemaFactory byDefault() {
        return newBuilder().freeze();
    }
    
    public static JsonSchemaFactoryBuilder newBuilder() {
        return new JsonSchemaFactoryBuilder();
    }
    
    JsonSchemaFactory(final JsonSchemaFactoryBuilder builder) {
        this.reportProvider = builder.reportProvider;
        this.loadingCfg = builder.loadingCfg;
        this.validationCfg = builder.validationCfg;
        this.loader = new SchemaLoader(this.loadingCfg);
        final Processor<SchemaContext, ValidatorList> processor = this.buildProcessor();
        this.validator = new JsonValidator(this.loader, new ValidationProcessor(this.validationCfg, processor), this.reportProvider);
        this.syntaxValidator = new SyntaxValidator(this.validationCfg);
    }
    
    public JsonValidator getValidator() {
        return this.validator;
    }
    
    public SyntaxValidator getSyntaxValidator() {
        return this.syntaxValidator;
    }
    
    public JsonSchema getJsonSchema(final JsonNode schema) throws ProcessingException {
        JsonSchemaFactory.BUNDLE.checkNotNull(schema, "nullSchema");
        return this.validator.buildJsonSchema(schema, JsonPointer.empty());
    }
    
    public JsonSchema getJsonSchema(final JsonNode schema, final String ptr) throws ProcessingException {
        JsonSchemaFactory.BUNDLE.checkNotNull(schema, "nullSchema");
        JsonSchemaFactory.CORE_BUNDLE.checkNotNull(ptr, "nullPointer");
        try {
            final JsonPointer pointer = new JsonPointer(ptr);
            return this.validator.buildJsonSchema(schema, pointer);
        }
        catch (JsonPointerException ignored) {
            throw new IllegalStateException("How did I get there??");
        }
    }
    
    public JsonSchema getJsonSchema(final String uri) throws ProcessingException {
        JsonSchemaFactory.CORE_BUNDLE.checkNotNull(uri, "nullURI");
        return this.validator.buildJsonSchema(uri);
    }
    
    public Processor<FullData, FullData> getProcessor() {
        return this.validator.getProcessor();
    }
    
    @Override
    public JsonSchemaFactoryBuilder thaw() {
        return new JsonSchemaFactoryBuilder(this);
    }
    
    private Processor<SchemaContext, ValidatorList> buildProcessor() {
        final RefResolver resolver = new RefResolver(this.loader);
        final Map<JsonRef, Library> libraries = this.validationCfg.getLibraries();
        final Library defaultLibrary = this.validationCfg.getDefaultLibrary();
        final ValidationChain defaultChain = new ValidationChain(resolver, defaultLibrary, this.validationCfg);
        final ProcessorMap<JsonRef, SchemaContext, ValidatorList> map = new ProcessorMap<JsonRef, SchemaContext, ValidatorList>(JsonSchemaFactory.FUNCTION);
        map.setDefaultProcessor(defaultChain);
        for (final Map.Entry<JsonRef, Library> entry : libraries.entrySet()) {
            final JsonRef ref = entry.getKey();
            final ValidationChain chain = new ValidationChain(resolver, entry.getValue(), this.validationCfg);
            map.addEntry(ref, chain);
        }
        final Processor<SchemaContext, ValidatorList> processor = map.getProcessor();
        return new CachingProcessor<SchemaContext, ValidatorList>(processor, SchemaContextEquivalence.getInstance());
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaConfigurationBundle.class);
        CORE_BUNDLE = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);
        FUNCTION = new Function<SchemaContext, JsonRef>() {
            @Override
            public JsonRef apply(final SchemaContext input) {
                return input.getSchema().getDollarSchema();
            }
        };
    }
}
