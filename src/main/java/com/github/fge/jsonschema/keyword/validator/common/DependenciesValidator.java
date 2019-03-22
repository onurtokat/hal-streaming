// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator.common;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.util.Collection;
import com.google.common.collect.Sets;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.core.processing.Processor;
import java.util.Iterator;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import com.github.fge.jackson.JacksonUtils;
import com.google.common.collect.ImmutableMultimap;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Set;
import com.google.common.collect.Multimap;
import com.github.fge.jsonschema.keyword.validator.AbstractKeywordValidator;

public final class DependenciesValidator extends AbstractKeywordValidator
{
    private final Multimap<String, String> propertyDeps;
    private final Set<String> schemaDeps;
    
    public DependenciesValidator(final JsonNode digest) {
        super("dependencies");
        final ImmutableMultimap.Builder<String, String> mapBuilder = ImmutableMultimap.builder();
        final Map<String, JsonNode> map = JacksonUtils.asMap(digest.get("propertyDeps"));
        for (final Map.Entry<String, JsonNode> entry : map.entrySet()) {
            final String key = entry.getKey();
            for (final JsonNode element : entry.getValue()) {
                mapBuilder.put(key, element.textValue());
            }
        }
        this.propertyDeps = mapBuilder.build();
        final ImmutableSet.Builder<String> setBuilder = ImmutableSet.builder();
        for (final JsonNode node : digest.get("schemaDeps")) {
            setBuilder.add(node.textValue());
        }
        this.schemaDeps = setBuilder.build();
    }
    
    @Override
    public void validate(final Processor<FullData, FullData> processor, final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final JsonNode instance = data.getInstance().getNode();
        final Set<String> fields = (Set<String>)Sets.newHashSet((Iterator<?>)instance.fieldNames());
        for (final String field : this.propertyDeps.keySet()) {
            if (!fields.contains(field)) {
                continue;
            }
            final Collection<String> collection = this.propertyDeps.get(field);
            final Set<String> set = (Set<String>)Sets.newLinkedHashSet((Iterable<?>)collection);
            set.removeAll(fields);
            if (set.isEmpty()) {
                continue;
            }
            report.error(this.newMsg(data, bundle, "err.common.dependencies.missingPropertyDeps").putArgument("property", field).putArgument("required", AbstractKeywordValidator.toArrayNode(collection)).putArgument("missing", AbstractKeywordValidator.toArrayNode(set)));
        }
        if (this.schemaDeps.isEmpty()) {
            return;
        }
        final SchemaTree tree = data.getSchema();
        for (final String field2 : this.schemaDeps) {
            if (!fields.contains(field2)) {
                continue;
            }
            final JsonPointer pointer = JsonPointer.of(this.keyword, field2);
            final FullData newData = data.withSchema(tree.append(pointer));
            processor.process(report, newData);
        }
    }
    
    @Override
    public String toString() {
        return this.keyword + ": " + this.propertyDeps.size() + " property dependencies, " + this.schemaDeps.size() + " schema dependencies";
    }
}
