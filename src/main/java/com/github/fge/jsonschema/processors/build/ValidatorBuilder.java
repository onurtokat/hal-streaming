// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.processors.build;

import com.github.fge.jsonschema.core.report.MessageProvider;
import java.lang.reflect.InvocationTargetException;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.util.Iterator;
import java.util.SortedMap;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.core.util.Dictionary;
import com.github.fge.jsonschema.library.Library;
import com.github.fge.jsonschema.keyword.validator.KeywordValidator;
import java.lang.reflect.Constructor;
import java.util.Map;
import com.github.fge.jsonschema.processors.data.ValidatorList;
import com.github.fge.jsonschema.processors.data.SchemaDigest;
import com.github.fge.jsonschema.core.processing.Processor;

public final class ValidatorBuilder implements Processor<SchemaDigest, ValidatorList>
{
    private static final String ERRMSG = "failed to build keyword validator";
    private final Map<String, Constructor<? extends KeywordValidator>> constructors;
    
    public ValidatorBuilder(final Library library) {
        this.constructors = library.getValidators().entries();
    }
    
    public ValidatorBuilder(final Dictionary<Constructor<? extends KeywordValidator>> dict) {
        this.constructors = dict.entries();
    }
    
    @Override
    public ValidatorList process(final ProcessingReport report, final SchemaDigest input) throws ProcessingException {
        final SortedMap<String, KeywordValidator> map = (SortedMap<String, KeywordValidator>)Maps.newTreeMap();
        for (final Map.Entry<String, JsonNode> entry : input.getDigests().entrySet()) {
            final String keyword = entry.getKey();
            final JsonNode digest = entry.getValue();
            final Constructor<? extends KeywordValidator> constructor = this.constructors.get(keyword);
            final KeywordValidator validator = buildKeyword(constructor, digest);
            map.put(keyword, validator);
        }
        return new ValidatorList(input.getContext(), map.values());
    }
    
    private static KeywordValidator buildKeyword(final Constructor<? extends KeywordValidator> constructor, final JsonNode node) throws ProcessingException {
        try {
            return (KeywordValidator)constructor.newInstance(node);
        }
        catch (InstantiationException e) {
            throw new ProcessingException("failed to build keyword validator", e);
        }
        catch (IllegalAccessException e2) {
            throw new ProcessingException("failed to build keyword validator", e2);
        }
        catch (InvocationTargetException e3) {
            throw new ProcessingException("failed to build keyword validator", e3);
        }
    }
    
    @Override
    public String toString() {
        return "validator builder";
    }
}
