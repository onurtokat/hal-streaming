// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.databind.cfg.ConfigFeature;

public enum DeserializationFeature implements ConfigFeature
{
    USE_BIG_DECIMAL_FOR_FLOATS(false), 
    USE_BIG_INTEGER_FOR_INTS(false), 
    USE_JAVA_ARRAY_FOR_JSON_ARRAY(false), 
    READ_ENUMS_USING_TO_STRING(false), 
    FAIL_ON_UNKNOWN_PROPERTIES(true), 
    FAIL_ON_NULL_FOR_PRIMITIVES(false), 
    FAIL_ON_NUMBERS_FOR_ENUMS(false), 
    FAIL_ON_INVALID_SUBTYPE(true), 
    FAIL_ON_READING_DUP_TREE_KEY(false), 
    FAIL_ON_IGNORED_PROPERTIES(false), 
    WRAP_EXCEPTIONS(true), 
    ACCEPT_SINGLE_VALUE_AS_ARRAY(false), 
    UNWRAP_SINGLE_VALUE_ARRAYS(false), 
    UNWRAP_ROOT_VALUE(false), 
    ACCEPT_EMPTY_STRING_AS_NULL_OBJECT(false), 
    READ_UNKNOWN_ENUM_VALUES_AS_NULL(false), 
    READ_DATE_TIMESTAMPS_AS_NANOSECONDS(true), 
    ADJUST_DATES_TO_CONTEXT_TIME_ZONE(true), 
    EAGER_DESERIALIZER_FETCH(true);
    
    private final boolean _defaultState;
    
    private DeserializationFeature(final boolean defaultState) {
        this._defaultState = defaultState;
    }
    
    @Override
    public boolean enabledByDefault() {
        return this._defaultState;
    }
    
    @Override
    public int getMask() {
        return 1 << this.ordinal();
    }
}
