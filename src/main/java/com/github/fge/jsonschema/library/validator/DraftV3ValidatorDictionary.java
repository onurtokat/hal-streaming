// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.library.validator;

import com.github.fge.jsonschema.core.util.DictionaryBuilder;
import com.github.fge.jsonschema.keyword.validator.draftv3.ExtendsValidator;
import com.github.fge.jsonschema.keyword.validator.draftv3.DisallowKeywordValidator;
import com.github.fge.jsonschema.keyword.validator.draftv3.DraftV3TypeValidator;
import com.github.fge.jsonschema.keyword.validator.common.DependenciesValidator;
import com.github.fge.jsonschema.keyword.validator.draftv3.PropertiesValidator;
import com.github.fge.jsonschema.keyword.validator.draftv3.DivisibleByValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.keyword.validator.KeywordValidator;
import java.lang.reflect.Constructor;
import com.github.fge.jsonschema.core.util.Dictionary;

public final class DraftV3ValidatorDictionary
{
    private static final Dictionary<Constructor<? extends KeywordValidator>> DICTIONARY;
    
    public static Dictionary<Constructor<? extends KeywordValidator>> get() {
        return DraftV3ValidatorDictionary.DICTIONARY;
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
        String keyword = "divisibleBy";
        Class<? extends KeywordValidator> c = DivisibleByValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "properties";
        c = PropertiesValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "dependencies";
        c = DependenciesValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "type";
        c = DraftV3TypeValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "disallow";
        c = DisallowKeywordValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "extends";
        c = ExtendsValidator.class;
        builder.addEntry(keyword, constructor(c));
        DICTIONARY = builder.freeze();
    }
}
