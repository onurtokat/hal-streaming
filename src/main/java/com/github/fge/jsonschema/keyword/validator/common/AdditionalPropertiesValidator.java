// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator.common;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.Ordering;
import com.github.fge.jackson.JacksonUtils;
import com.github.fge.jsonschema.core.util.RhinoHelper;
import java.util.Collection;
import com.google.common.collect.Sets;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.core.processing.Processor;
import java.util.Iterator;
import com.google.common.collect.ImmutableSet;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Set;
import com.google.common.base.Joiner;
import com.github.fge.jsonschema.keyword.validator.AbstractKeywordValidator;

public final class AdditionalPropertiesValidator extends AbstractKeywordValidator
{
    private static final Joiner TOSTRING_JOINER;
    private final boolean additionalOK;
    private final Set<String> properties;
    private final Set<String> patternProperties;
    
    public AdditionalPropertiesValidator(final JsonNode digest) {
        super("additionalProperties");
        this.additionalOK = digest.get(this.keyword).booleanValue();
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (final JsonNode node : digest.get("properties")) {
            builder.add(node.textValue());
        }
        this.properties = builder.build();
        builder = ImmutableSet.builder();
        for (final JsonNode node : digest.get("patternProperties")) {
            builder.add(node.textValue());
        }
        this.patternProperties = builder.build();
    }
    
    @Override
    public void validate(final Processor<FullData, FullData> processor, final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        if (this.additionalOK) {
            return;
        }
        final JsonNode instance = data.getInstance().getNode();
        final Set<String> fields = (Set<String>)Sets.newHashSet((Iterator<?>)instance.fieldNames());
        fields.removeAll(this.properties);
        final Set<String> tmp = (Set<String>)Sets.newHashSet();
        for (final String field : fields) {
            for (final String regex : this.patternProperties) {
                if (RhinoHelper.regMatch(regex, field)) {
                    tmp.add(field);
                }
            }
        }
        fields.removeAll(tmp);
        if (fields.isEmpty()) {
            return;
        }
        final ArrayNode node = JacksonUtils.nodeFactory().arrayNode();
        for (final String field2 : Ordering.natural().sortedCopy(fields)) {
            node.add(field2);
        }
        report.error(this.newMsg(data, bundle, "err.common.additionalProperties.notAllowed").putArgument("unwanted", (JsonNode)node));
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.keyword + ": ");
        if (this.additionalOK) {
            return sb.append("allowed").toString();
        }
        sb.append("none");
        if (this.properties.isEmpty() && this.patternProperties.isEmpty()) {
            return sb.toString();
        }
        sb.append(", unless: ");
        final Set<String> further = (Set<String>)Sets.newLinkedHashSet();
        if (!this.properties.isEmpty()) {
            further.add("one property is any of: " + this.properties);
        }
        if (!this.patternProperties.isEmpty()) {
            further.add("a property matches any regex among: " + this.patternProperties);
        }
        sb.append(AdditionalPropertiesValidator.TOSTRING_JOINER.join(further));
        return sb.toString();
    }
    
    static {
        TOSTRING_JOINER = Joiner.on("; or ");
    }
}
