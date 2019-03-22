// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.processing;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.core.messages.JsonSchemaCoreMessageBundle;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.msgsimple.bundle.MessageBundle;
import javax.annotation.concurrent.Immutable;
import com.github.fge.jsonschema.core.report.MessageProvider;

@Immutable
public final class ProcessorChain<IN extends MessageProvider, OUT extends MessageProvider>
{
    private static final MessageBundle BUNDLE;
    private final Processor<IN, OUT> processor;
    
    public static <X extends MessageProvider, Y extends MessageProvider> ProcessorChain<X, Y> startWith(final Processor<X, Y> p) {
        ProcessorChain.BUNDLE.checkNotNull(p, "processing.nullProcessor");
        return new ProcessorChain<X, Y>(p);
    }
    
    private ProcessorChain(final Processor<IN, OUT> processor) {
        this.processor = processor;
    }
    
    public ProcessorChain<IN, OUT> failOnError() {
        return this.failOnError(new ProcessingMessage().setMessage(ProcessorChain.BUNDLE.getMessage("processing.chainStopped")));
    }
    
    public ProcessorChain<IN, OUT> failOnError(final ProcessingMessage message) {
        final Processor<OUT, OUT> fail = new Processor<OUT, OUT>() {
            @Override
            public OUT process(final ProcessingReport report, final OUT input) throws ProcessingException {
                if (!report.isSuccess()) {
                    throw message.asException();
                }
                return input;
            }
        };
        final ProcessorMerger<IN, OUT, OUT> merger = new ProcessorMerger<IN, OUT, OUT>((Processor)this.processor, (Processor)fail);
        return new ProcessorChain<IN, OUT>(merger);
    }
    
    public <NEWOUT extends MessageProvider> ProcessorChain<IN, NEWOUT> chainWith(final Processor<OUT, NEWOUT> p) {
        ProcessorChain.BUNDLE.checkNotNull(p, "processing.nullProcessor");
        final Processor<IN, NEWOUT> merger = new ProcessorMerger<IN, Object, NEWOUT>((Processor)this.processor, (Processor)p);
        return new ProcessorChain<IN, NEWOUT>(merger);
    }
    
    public Processor<IN, OUT> getProcessor() {
        return this.processor;
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);
    }
    
    private static final class ProcessorMerger<X extends MessageProvider, Y extends MessageProvider, Z extends MessageProvider> implements Processor<X, Z>
    {
        private final Processor<X, Y> p1;
        private final Processor<Y, Z> p2;
        
        private ProcessorMerger(final Processor<X, Y> p1, final Processor<Y, Z> p2) {
            this.p1 = p1;
            this.p2 = p2;
        }
        
        @Override
        public Z process(final ProcessingReport report, final X input) throws ProcessingException {
            final Y intermediate = this.p1.process(report, input);
            return this.p2.process(report, intermediate);
        }
        
        @Override
        public String toString() {
            return this.p1 + " -> " + this.p2;
        }
    }
}
