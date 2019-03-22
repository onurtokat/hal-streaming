// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.library.validator;

import com.github.fge.jsonschema.core.util.DictionaryBuilder;
import com.github.fge.jsonschema.keyword.validator.draftv4.DraftV4TypeValidator;
import com.github.fge.jsonschema.keyword.validator.draftv4.NotValidator;
import com.github.fge.jsonschema.keyword.validator.draftv4.OneOfValidator;
import com.github.fge.jsonschema.keyword.validator.draftv4.AllOfValidator;
import com.github.fge.jsonschema.keyword.validator.draftv4.AnyOfValidator;
import com.github.fge.jsonschema.keyword.validator.common.DependenciesValidator;
import com.github.fge.jsonschema.keyword.validator.draftv4.RequiredKeywordValidator;
import com.github.fge.jsonschema.keyword.validator.draftv4.MaxPropertiesValidator;
import com.github.fge.jsonschema.keyword.validator.draftv4.MinPropertiesValidator;
import com.github.fge.jsonschema.keyword.validator.draftv4.MultipleOfValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.keyword.validator.KeywordValidator;
import java.lang.reflect.Constructor;
import com.github.fge.jsonschema.core.util.Dictionary;

public final class DraftV4ValidatorDictionary
{
    private static final Dictionary<Constructor<? extends KeywordValidator>> DICTIONARY;
    
    public static Dictionary<Constructor<? extends KeywordValidator>> get() {
        return DraftV4ValidatorDictionary.DICTIONARY;
    }
    
    private static Constructor<? extends KeywordValidator> constructor(final Class<? extends KeywordValidator> c) {
        try {
            return c.getConstructor(JsonNode.class);
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException("No appropriate constructor found", e);
        }
    }
    
    static {
        final DictionaryBuilder<Constructor<? extends KeywordValidator>> builder = Dictionary.newBuilder();
        builder.addAll(CommonValidatorDictionary.get());
        String keyword = "multipleOf";
        Class<? extends KeywordValidator> c = MultipleOfValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "minProperties";
        c = MinPropertiesValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "maxProperties";
        c = MaxPropertiesValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "required";
        c = RequiredKeywordValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "dependencies";
        c = DependenciesValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "anyOf";
        c = AnyOfValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "allOf";
        c = AllOfValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "oneOf";
        c = OneOfValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "not";
        c = NotValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "type";
        c = DraftV4TypeValidator.class;
        builder.addEntry(keyword, constructor(c));
        DICTIONARY = builder.freeze();
    }
}
