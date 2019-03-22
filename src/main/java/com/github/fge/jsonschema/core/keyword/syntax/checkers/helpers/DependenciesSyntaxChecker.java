// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers;

import com.github.fge.jackson.JsonNumEquals;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.util.Iterator;
import java.util.Map;
import com.github.fge.jackson.JacksonUtils;
import com.google.common.collect.Maps;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.util.Collection;
import com.github.fge.jackson.NodeType;
import java.util.EnumSet;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Equivalence;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.AbstractSyntaxChecker;

public abstract class DependenciesSyntaxChecker extends AbstractSyntaxChecker
{
    protected static final Equivalence<JsonNode> EQUIVALENCE;
    protected final EnumSet<NodeType> dependencyTypes;
    
    protected DependenciesSyntaxChecker(final NodeType... depTypes) {
        super("dependencies", NodeType.OBJECT, new NodeType[0]);
        this.dependencyTypes = EnumSet.of(NodeType.OBJECT, depTypes);
    }
    
    @Override
    protected final void checkValue(final Collection<JsonPointer> pointers, final MessageBundle bundle, final ProcessingReport report, final SchemaTree tree) throws ProcessingException {
        final JsonNode node = this.getNode(tree);
        final Map<String, JsonNode> map = (Map<String, JsonNode>)Maps.newTreeMap();
        map.putAll(JacksonUtils.asMap(node));
        for (final Map.Entry<String, JsonNode> entry : map.entrySet()) {
            final String key = entry.getKey();
            final JsonNode value = entry.getValue();
            if (value.isObject()) {
                pointers.add(JsonPointer.of(this.keyword, key));
            }
            else {
                this.checkDependency(report, bundle, entry.getKey(), tree);
            }
        }
    }
    
    protected abstract void checkDependency(final ProcessingReport p0, final MessageBundle p1, final String p2, final SchemaTree p3) throws ProcessingException;
    
    static {
        EQUIVALENCE = JsonNumEquals.getInstance();
    }
}
