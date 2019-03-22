// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator;

import com.github.fge.jsonschema.exceptions.InvalidInstanceException;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.util.Iterator;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.fge.jackson.JacksonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Collection;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.core.exceptions.ExceptionProvider;

public abstract class AbstractKeywordValidator implements KeywordValidator
{
    private static final ExceptionProvider EXCEPTION_PROVIDER;
    protected final String keyword;
    
    protected AbstractKeywordValidator(final String keyword) {
        this.keyword = keyword;
    }
    
    protected final ProcessingMessage newMsg(final FullData data) {
        return data.newMessage().put("domain", "validation").put("keyword", this.keyword).setExceptionProvider(AbstractKeywordValidator.EXCEPTION_PROVIDER);
    }
    
    protected final ProcessingMessage newMsg(final FullData data, final MessageBundle bundle, final String key) {
        return data.newMessage().put("domain", "validation").put("keyword", this.keyword).setMessage(bundle.getMessage(key)).setExceptionProvider(AbstractKeywordValidator.EXCEPTION_PROVIDER);
    }
    
    protected static <T> JsonNode toArrayNode(final Collection<T> collection) {
        final ArrayNode node = JacksonUtils.nodeFactory().arrayNode();
        for (final T element : collection) {
            node.add(element.toString());
        }
        return node;
    }
    
    @Override
    public abstract String toString();
    
    static {
        EXCEPTION_PROVIDER = new ExceptionProvider() {
            @Override
            public ProcessingException doException(final ProcessingMessage message) {
                return new InvalidInstanceException(message);
            }
        };
    }
}
