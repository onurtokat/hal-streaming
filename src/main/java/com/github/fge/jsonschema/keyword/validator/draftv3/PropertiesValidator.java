// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator.draftv3;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
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
import com.github.fge.jsonschema.keyword.validator.AbstractKeywordValidator;

public final class PropertiesValidator extends AbstractKeywordValidator
{
    private final Set<String> required;
    
    public PropertiesValidator(final JsonNode digest) {
        super("properties");
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (final JsonNode element : digest.get("required")) {
            builder.add(element.textValue());
        }
        this.required = builder.build();
    }
    
    @Override
    public void validate(final Processor<FullData, FullData> processor, final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final Set<String> set = (Set<String>)Sets.newLinkedHashSet((Iterable<?>)this.required);
        set.removeAll(Sets.newHashSet((Iterator<?>)data.getInstance().getNode().fieldNames()));
        if (!set.isEmpty()) {
            report.error(this.newMsg(data, bundle, "err.common.object.missingMembers").put("required", (Iterable<String>)this.required).putArgument("missing", AbstractKeywordValidator.toArrayNode(set)));
        }
    }
    
    @Override
    public String toString() {
        return "required: " + this.required.size() + " properties";
    }
}
