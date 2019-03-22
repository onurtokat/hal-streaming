// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.processing;

import com.github.fge.jsonschema.core.report.MessageProvider;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.core.util.ValueHolder;

public abstract class RawProcessor<IN, OUT> implements Processor<ValueHolder<IN>, ValueHolder<OUT>>
{
    private final String inputName;
    private final String outputName;
    
    protected RawProcessor(final String inputName, final String outputName) {
        this.inputName = inputName;
        this.outputName = outputName;
    }
    
    protected abstract OUT rawProcess(final ProcessingReport p0, final IN p1) throws ProcessingException;
    
    @Override
    public final ValueHolder<OUT> process(final ProcessingReport report, final ValueHolder<IN> input) throws ProcessingException {
        final IN rawInput = input.getValue();
        final OUT rawOutput = this.rawProcess(report, rawInput);
        return ValueHolder.hold(this.outputName, rawOutput);
    }
    
    protected final ProcessingMessage newMessage(final IN rawInput) {
        return new ProcessingMessage().put(this.inputName, rawInput);
    }
}
