// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.processors.data;

import com.github.fge.jsonschema.core.report.ProcessingMessage;
import java.util.Iterator;
import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import com.github.fge.jsonschema.core.report.MessageProvider;
import com.github.fge.jsonschema.keyword.validator.KeywordValidator;

public final class ValidatorList implements Iterable<KeywordValidator>, MessageProvider
{
    private final List<KeywordValidator> validators;
    private final SchemaContext context;
    
    public ValidatorList(final SchemaContext context, final Collection<KeywordValidator> validators) {
        this.context = context;
        this.validators = (List<KeywordValidator>)ImmutableList.copyOf((Collection<?>)validators);
    }
    
    public SchemaContext getContext() {
        return this.context;
    }
    
    @Override
    public Iterator<KeywordValidator> iterator() {
        return this.validators.iterator();
    }
    
    @Override
    public ProcessingMessage newMessage() {
        return this.context.newMessage();
    }
}
