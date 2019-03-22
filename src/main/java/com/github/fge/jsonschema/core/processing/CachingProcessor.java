// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.processing;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.core.messages.JsonSchemaCoreMessageBundle;
import com.github.fge.jsonschema.core.report.ListProcessingReport;
import com.github.fge.jsonschema.core.report.LogLevel;
import java.util.concurrent.ExecutionException;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheBuilder;
import com.github.fge.jsonschema.core.util.equivalence.Equivalences;
import com.google.common.cache.LoadingCache;
import com.google.common.base.Equivalence;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.MessageProvider;

public final class CachingProcessor<IN extends MessageProvider, OUT extends MessageProvider> implements Processor<IN, OUT>
{
    private static final MessageBundle BUNDLE;
    private final Processor<IN, OUT> processor;
    private final Equivalence<IN> equivalence;
    private final LoadingCache<Equivalence.Wrapper<IN>, ProcessingResult<OUT>> cache;
    
    public CachingProcessor(final Processor<IN, OUT> processor) {
        this((Processor<Object, OUT>)processor, Equivalences.equals());
    }
    
    public CachingProcessor(final Processor<IN, OUT> processor, final Equivalence<IN> equivalence) {
        CachingProcessor.BUNDLE.checkNotNull(processor, "processing.nullProcessor");
        CachingProcessor.BUNDLE.checkNotNull(equivalence, "processing.nullEquivalence");
        this.processor = processor;
        this.equivalence = equivalence;
        this.cache = CacheBuilder.newBuilder().build((CacheLoader<? super Equivalence.Wrapper<IN>, ProcessingResult<OUT>>)this.loader());
    }
    
    @Override
    public OUT process(final ProcessingReport report, final IN input) throws ProcessingException {
        ProcessingResult<OUT> result;
        try {
            result = this.cache.get(this.equivalence.wrap(input));
        }
        catch (ExecutionException e) {
            throw (ProcessingException)e.getCause();
        }
        report.mergeWith(result.getReport());
        return result.getResult();
    }
    
    private CacheLoader<Equivalence.Wrapper<IN>, ProcessingResult<OUT>> loader() {
        return new CacheLoader<Equivalence.Wrapper<IN>, ProcessingResult<OUT>>() {
            @Override
            public ProcessingResult<OUT> load(final Equivalence.Wrapper<IN> key) throws ProcessingException {
                final IN input = key.get();
                final ListProcessingReport report = new ListProcessingReport(LogLevel.DEBUG, LogLevel.NONE);
                return ProcessingResult.of(CachingProcessor.this.processor, report, input);
            }
        };
    }
    
    @Override
    public String toString() {
        return "CACHED[" + this.processor + ']';
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);
    }
}
