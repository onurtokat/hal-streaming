// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.processing;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.core.messages.JsonSchemaCoreMessageBundle;
import com.google.common.collect.Maps;
import java.util.Map;
import com.google.common.base.Predicate;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.MessageProvider;

public final class ProcessorSelectorPredicate<IN extends MessageProvider, OUT extends MessageProvider>
{
    private static final MessageBundle BUNDLE;
    private final Predicate<IN> predicate;
    final Map<Predicate<IN>, Processor<IN, OUT>> choices;
    final Processor<IN, OUT> byDefault;
    
    ProcessorSelectorPredicate(final ProcessorSelector<IN, OUT> selector, final Predicate<IN> predicate) {
        this.predicate = predicate;
        this.choices = (Map<Predicate<IN>, Processor<IN, OUT>>)Maps.newLinkedHashMap((Map<?, ?>)selector.choices);
        this.byDefault = selector.byDefault;
    }
    
    public ProcessorSelector<IN, OUT> then(final Processor<IN, OUT> processor) {
        ProcessorSelectorPredicate.BUNDLE.checkNotNull(processor, "processing.nullProcessor");
        this.choices.put(this.predicate, processor);
        return new ProcessorSelector<IN, OUT>(this);
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);
    }
}
