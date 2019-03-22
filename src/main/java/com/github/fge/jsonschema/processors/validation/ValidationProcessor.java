// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.processors.validation;

import com.github.fge.jsonschema.core.report.MessageProvider;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.processors.data.ValidatorList;
import com.github.fge.jsonschema.processors.data.SchemaContext;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.core.processing.Processor;

public final class ValidationProcessor implements Processor<FullData, FullData>
{
    private final MessageBundle syntaxMessages;
    private final MessageBundle validationMessages;
    private final Processor<SchemaContext, ValidatorList> processor;
    
    public ValidationProcessor(final ValidationConfiguration cfg, final Processor<SchemaContext, ValidatorList> processor) {
        this.syntaxMessages = cfg.getSyntaxMessages();
        this.validationMessages = cfg.getValidationMessages();
        this.processor = processor;
    }
    
    @Override
    public FullData process(final ProcessingReport report, final FullData input) throws ProcessingException {
        final InstanceValidator validator = new InstanceValidator(this.syntaxMessages, this.validationMessages, this.processor);
        return validator.process(report, input);
    }
    
    @Override
    public String toString() {
        return "validation processor";
    }
}
