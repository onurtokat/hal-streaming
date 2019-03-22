// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.processing;

import java.util.Iterator;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.google.common.collect.ImmutableMap;
import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.core.messages.JsonSchemaCoreMessageBundle;
import com.google.common.collect.Maps;
import com.google.common.base.Predicate;
import java.util.Map;
import com.github.fge.msgsimple.bundle.MessageBundle;
import javax.annotation.concurrent.Immutable;
import com.github.fge.jsonschema.core.report.MessageProvider;

@Immutable
public final class ProcessorSelector<IN extends MessageProvider, OUT extends MessageProvider>
{
    private static final MessageBundle BUNDLE;
    final Map<Predicate<IN>, Processor<IN, OUT>> choices;
    final Processor<IN, OUT> byDefault;
    
    public ProcessorSelector() {
        this.choices = (Map<Predicate<IN>, Processor<IN, OUT>>)Maps.newLinkedHashMap();
        this.byDefault = null;
    }
    
    private ProcessorSelector(final Map<Predicate<IN>, Processor<IN, OUT>> choices, final Processor<IN, OUT> byDefault) {
        this.choices = (Map<Predicate<IN>, Processor<IN, OUT>>)Maps.newLinkedHashMap((Map<?, ?>)choices);
        this.byDefault = byDefault;
    }
    
    ProcessorSelector(final ProcessorSelectorPredicate<IN, OUT> selector) {
        this(selector.choices, selector.byDefault);
    }
    
    public ProcessorSelectorPredicate<IN, OUT> when(final Predicate<IN> predicate) {
        ProcessorSelector.BUNDLE.checkNotNull(predicate, "processing.nullPredicate");
        return new ProcessorSelectorPredicate<IN, OUT>(this, predicate);
    }
    
    public ProcessorSelector<IN, OUT> otherwise(final Processor<IN, OUT> byDefault) {
        ProcessorSelector.BUNDLE.checkNotNull(byDefault, "processing.nullProcessor");
        return new ProcessorSelector<IN, OUT>(this.choices, byDefault);
    }
    
    public Processor<IN, OUT> getProcessor() {
        return new Chooser<IN, OUT>((Map)this.choices, (Processor)this.byDefault);
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);
    }
    
    private static final class Chooser<X extends MessageProvider, Y extends MessageProvider> implements Processor<X, Y>
    {
        private final Map<Predicate<X>, Processor<X, Y>> map;
        private final Processor<X, Y> byDefault;
        
        private Chooser(final Map<Predicate<X>, Processor<X, Y>> map, final Processor<X, Y> byDefault) {
            this.map = (Map<Predicate<X>, Processor<X, Y>>)ImmutableMap.copyOf((Map<?, ?>)map);
            this.byDefault = byDefault;
        }
        
        @Override
        public Y process(final ProcessingReport report, final X input) throws ProcessingException {
            for (final Map.Entry<Predicate<X>, Processor<X, Y>> entry : this.map.entrySet()) {
                final Predicate<X> predicate = entry.getKey();
                final Processor<X, Y> processor = entry.getValue();
                if (predicate.apply(input)) {
                    return processor.process(report, input);
                }
            }
            if (this.byDefault != null) {
                return this.byDefault.process(report, input);
            }
            throw new ProcessingException(ProcessorSelector.BUNDLE.getMessage("processing.noProcessor"));
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("selector[").append(this.map.size()).append(" choices with ");
            if (this.byDefault == null) {
                sb.append("no ");
            }
            return sb.append("default]").toString();
        }
    }
}
