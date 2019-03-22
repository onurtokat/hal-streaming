// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.processors.validation;

import java.util.Collections;
import com.github.fge.jsonschema.core.util.RhinoHelper;
import java.util.Iterator;
import java.util.Collection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import com.github.fge.jackson.jsonpointer.JsonPointer;

public final class ObjectSchemaSelector
{
    private static final JsonPointer PROPERTIES;
    private static final JsonPointer PATTERNPROPERTIES;
    private static final JsonPointer ADDITIONALPROPERTIES;
    private final List<String> properties;
    private final List<String> patternProperties;
    private final boolean hasAdditional;
    
    public ObjectSchemaSelector(final JsonNode digest) {
        this.hasAdditional = digest.get("hasAdditional").booleanValue();
        List<String> list = (List<String>)Lists.newArrayList();
        for (final JsonNode node : digest.get("properties")) {
            list.add(node.textValue());
        }
        this.properties = (List<String>)ImmutableList.copyOf((Collection<?>)list);
        list = (List<String>)Lists.newArrayList();
        for (final JsonNode node : digest.get("patternProperties")) {
            list.add(node.textValue());
        }
        this.patternProperties = (List<String>)ImmutableList.copyOf((Collection<?>)list);
    }
    
    public Iterable<JsonPointer> selectSchemas(final String memberName) {
        final List<JsonPointer> list = (List<JsonPointer>)Lists.newArrayList();
        if (this.properties.contains(memberName)) {
            list.add(ObjectSchemaSelector.PROPERTIES.append(memberName));
        }
        for (final String regex : this.patternProperties) {
            if (RhinoHelper.regMatch(regex, memberName)) {
                list.add(ObjectSchemaSelector.PATTERNPROPERTIES.append(regex));
            }
        }
        if (!list.isEmpty()) {
            return (Iterable<JsonPointer>)ImmutableList.copyOf((Collection<?>)list);
        }
        return (Iterable<JsonPointer>)(this.hasAdditional ? ImmutableList.of(ObjectSchemaSelector.ADDITIONALPROPERTIES) : Collections.emptyList());
    }
    
    static {
        PROPERTIES = JsonPointer.of("properties", new Object[0]);
        PATTERNPROPERTIES = JsonPointer.of("patternProperties", new Object[0]);
        ADDITIONALPROPERTIES = JsonPointer.of("additionalProperties", new Object[0]);
    }
}
