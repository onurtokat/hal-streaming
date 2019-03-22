// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.processors.validation;

import java.util.Collections;
import com.google.common.collect.ImmutableList;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;

public final class ArraySchemaSelector
{
    private static final JsonPointer ITEMS;
    private static final JsonPointer ADDITIONAL_ITEMS;
    private final boolean hasItems;
    private final boolean itemsIsArray;
    private final int itemsSize;
    private final boolean hasAdditional;
    
    public ArraySchemaSelector(final JsonNode digest) {
        this.hasItems = digest.get("hasItems").booleanValue();
        this.itemsIsArray = digest.get("itemsIsArray").booleanValue();
        this.itemsSize = digest.get("itemsSize").intValue();
        this.hasAdditional = digest.get("hasAdditional").booleanValue();
    }
    
    public Iterable<JsonPointer> selectSchemas(final int index) {
        if (!this.hasItems) {
            return (Iterable<JsonPointer>)(this.hasAdditional ? ImmutableList.of(ArraySchemaSelector.ADDITIONAL_ITEMS) : Collections.emptyList());
        }
        if (!this.itemsIsArray) {
            return ImmutableList.of(ArraySchemaSelector.ITEMS);
        }
        if (index < this.itemsSize) {
            return ImmutableList.of(ArraySchemaSelector.ITEMS.append(index));
        }
        return (Iterable<JsonPointer>)(this.hasAdditional ? ImmutableList.of(ArraySchemaSelector.ADDITIONAL_ITEMS) : Collections.emptyList());
    }
    
    static {
        ITEMS = JsonPointer.of("items", new Object[0]);
        ADDITIONAL_ITEMS = JsonPointer.of("additionalItems", new Object[0]);
    }
}
