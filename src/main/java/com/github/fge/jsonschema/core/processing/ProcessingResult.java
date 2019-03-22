// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.processing;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.core.messages.JsonSchemaCoreMessageBundle;
import com.github.fge.jsonschema.core.report.ListProcessingReport;
import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.MessageProvider;

public final class ProcessingResult<R extends MessageProvider>
{
    private static final MessageBundle BUNDLE;
    private final ProcessingReport report;
    private final R result;
    
    private ProcessingResult(final ProcessingReport report, final R result) {
        ProcessingResult.BUNDLE.checkNotNull(report, "processing.nullReport");
        this.report = report;
        this.result = result;
    }
    
    public static <IN extends MessageProvider, OUT extends MessageProvider> ProcessingResult<OUT> of(final Processor<IN, OUT> processor, final ProcessingReport report, final IN input) throws ProcessingException {
        ProcessingResult.BUNDLE.checkNotNull(processor, "processing.nullProcessor");
        final OUT out = processor.process(report, input);
        return new ProcessingResult<OUT>(report, out);
    }
    
    public static <IN extends MessageProvider, OUT extends MessageProvider> ProcessingResult<OUT> uncheckedResult(final Processor<IN, OUT> processor, final ProcessingReport report, final IN input) {
        try {
            return (ProcessingResult<OUT>)of((Processor<MessageProvider, MessageProvider>)processor, report, input);
        }
        catch (ProcessingException e) {
            return new ProcessingResult<OUT>(buildReport(report, e), null);
        }
    }
    
    public ProcessingReport getReport() {
        return this.report;
    }
    
    public R getResult() {
        return this.result;
    }
    
    public boolean isSuccess() {
        return this.report.isSuccess();
    }
    
    private static ProcessingReport buildReport(final ProcessingReport report, final ProcessingException e) {
        final ListProcessingReport ret = new ListProcessingReport(LogLevel.DEBUG, LogLevel.NONE);
        try {
            ret.fatal(e.getProcessingMessage().put("info", ProcessingResult.BUNDLE.getMessage("processing.moreMessages")));
            ret.mergeWith(report);
        }
        catch (ProcessingException ex) {}
        return ret;
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);
    }
}
