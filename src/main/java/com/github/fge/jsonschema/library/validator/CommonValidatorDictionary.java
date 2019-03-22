// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.library.validator;

import com.github.fge.jsonschema.core.util.DictionaryBuilder;
import com.github.fge.jsonschema.keyword.validator.common.EnumValidator;
import com.github.fge.jsonschema.keyword.validator.common.PatternValidator;
import com.github.fge.jsonschema.keyword.validator.common.MaxLengthValidator;
import com.github.fge.jsonschema.keyword.validator.common.MinLengthValidator;
import com.github.fge.jsonschema.keyword.validator.common.AdditionalPropertiesValidator;
import com.github.fge.jsonschema.keyword.validator.common.MaximumValidator;
import com.github.fge.jsonschema.keyword.validator.common.MinimumValidator;
import com.github.fge.jsonschema.keyword.validator.common.UniqueItemsValidator;
import com.github.fge.jsonschema.keyword.validator.common.MaxItemsValidator;
import com.github.fge.jsonschema.keyword.validator.common.MinItemsValidator;
import com.github.fge.jsonschema.keyword.validator.common.AdditionalItemsValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.keyword.validator.KeywordValidator;
import java.lang.reflect.Constructor;
import com.github.fge.jsonschema.core.util.Dictionary;

public final class CommonValidatorDictionary
{
    private static final Dictionary<Constructor<? extends KeywordValidator>> DICTIONARY;
    
    public static Dictionary<Constructor<? extends KeywordValidator>> get() {
        return CommonValidatorDictionary.DICTIONARY;
    }
    
    private static Constructor<? extends KeywordValidator> constructor(final Class<? extends KeywordValidator> c) {
        try {
            return c.getConstructor(JsonNode.class);
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException("No appropriate constructor", e);
        }
    }
    
    static {
        final DictionaryBuilder<Constructor<? extends KeywordValidator>> builder = Dictionary.newBuilder();
        String keyword = "additionalItems";
        Class<? extends KeywordValidator> c = AdditionalItemsValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "minItems";
        c = MinItemsValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "maxItems";
        c = MaxItemsValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "uniqueItems";
        c = UniqueItemsValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "minimum";
        c = MinimumValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "maximum";
        c = MaximumValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "additionalProperties";
        c = AdditionalPropertiesValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "minLength";
        c = MinLengthValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "maxLength";
        c = MaxLengthValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "pattern";
        c = PatternValidator.class;
        builder.addEntry(keyword, constructor(c));
        keyword = "enum";
        c = EnumValidator.class;
        builder.addEntry(keyword, constructor(c));
        DICTIONARY = builder.freeze();
    }
}
