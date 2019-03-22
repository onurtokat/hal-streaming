// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.processing;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.google.common.collect.ImmutableMap;
import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.core.messages.JsonSchemaCoreMessageBundle;
import com.google.common.collect.Maps;
import java.util.Map;
import com.google.common.base.Function;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.MessageProvider;

public final class ProcessorMap<K, IN extends MessageProvider, OUT extends MessageProvider>
{
    private static final MessageBundle BUNDLE;
    private final Function<IN, K> keyFunction;
    private final Map<K, Processor<IN, OUT>> processors;
    private Processor<IN, OUT> defaultProcessor;
    
    public ProcessorMap(final Function<IN, K> keyFunction) {
        this.processors = (Map<K, Processor<IN, OUT>>)Maps.newHashMap();
        this.defaultProcessor = null;
        ProcessorMap.BUNDLE.checkNotNull(keyFunction, "processing.nullFunction");
        this.keyFunction = keyFunction;
    }
    
    public ProcessorMap<K, IN, OUT> addEntry(final K key, final Processor<IN, OUT> processor) {
        ProcessorMap.BUNDLE.checkNotNull(key, "processing.nullKey");
        ProcessorMap.BUNDLE.checkNotNull(processor, "processing.nullProcessor");
        this.processors.put(key, processor);
        return this;
    }
    
    public ProcessorMap<K, IN, OUT> setDefaultProcessor(final Processor<IN, OUT> defaultProcessor) {
        ProcessorMap.BUNDLE.checkNotNull(defaultProcessor, "processing.nullProcessor");
        this.defaultProcessor = defaultProcessor;
        return this;
    }
    
    public Processor<IN, OUT> getProcessor() {
        return new Mapper<Object, IN, OUT>((Map)this.processors, (Function)this.keyFunction, (Processor)this.defaultProcessor);
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);
    }
    
    private static final class Mapper<K, IN extends MessageProvider, OUT extends MessageProvider> implements Processor<IN, OUT>
    {
        private final Map<K, Processor<IN, OUT>> processors;
        private final Function<IN, K> f;
        private final Processor<IN, OUT> defaultProcessor;
        
        private Mapper(final Map<K, Processor<IN, OUT>> processors, final Function<IN, K> f, final Processor<IN, OUT> defaultProcessor) {
            this.processors = (Map<K, Processor<IN, OUT>>)ImmutableMap.copyOf((Map<?, ?>)processors);
            this.f = f;
            this.defaultProcessor = defaultProcessor;
        }
        
        @Override
        public OUT process(final ProcessingReport report, final IN input) throws ProcessingException {
            final K key = this.f.apply(input);
            Processor<IN, OUT> processor = this.processors.get(key);
            if (processor == null) {
                processor = this.defaultProcessor;
            }
            if (processor == null) {
                throw new ProcessingException(new ProcessingMessage().setMessage(ProcessorMap.BUNDLE.getMessage("processing.noProcessor")).put("key", key));
            }
            return processor.process(report, input);
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("map[").append(this.processors.size()).append(" entries with ");
            if (this.defaultProcessor == null) {
                sb.append("no ");
            }
            return sb.append("default processor]").toString();
        }
    }
}
