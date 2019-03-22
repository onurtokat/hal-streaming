// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax;

import com.github.fge.jsonschema.core.util.AsJson;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import java.util.Collection;
import java.util.Iterator;
import com.google.common.collect.Sets;
import com.google.common.collect.Maps;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.core.util.Dictionary;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import java.util.Map;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.processing.RawProcessor;

public final class SyntaxProcessor extends RawProcessor<SchemaTree, SchemaTree>
{
    private final MessageBundle bundle;
    private final Map<String, SyntaxChecker> checkers;
    
    public SyntaxProcessor(final MessageBundle bundle, final Dictionary<SyntaxChecker> dict) {
        super("schema", "schema");
        this.bundle = bundle;
        this.checkers = dict.entries();
    }
    
    public SchemaTree rawProcess(final ProcessingReport report, final SchemaTree input) throws ProcessingException {
        this.validate(report, input);
        return input;
    }
    
    private void validate(final ProcessingReport report, final SchemaTree tree) throws ProcessingException {
        final JsonNode node = tree.getNode();
        final NodeType type = NodeType.getNodeType(node);
        if (type != NodeType.OBJECT) {
            report.error(this.newMsg(tree, "core.notASchema").putArgument("found", type));
            return;
        }
        final Map<String, SyntaxChecker> map = (Map<String, SyntaxChecker>)Maps.newTreeMap();
        map.putAll(this.checkers);
        final Set<String> fields = (Set<String>)Sets.newHashSet((Iterator<?>)node.fieldNames());
        map.keySet().retainAll(fields);
        fields.removeAll(map.keySet());
        if (!fields.isEmpty()) {
            report.warn(this.newMsg(tree, "core.unknownKeywords").putArgument("ignored", (Iterable<String>)Ordering.natural().sortedCopy(fields)));
        }
        final List<JsonPointer> pointers = (List<JsonPointer>)Lists.newArrayList();
        for (final SyntaxChecker checker : map.values()) {
            checker.checkSyntax(pointers, this.bundle, report, tree);
        }
        for (final JsonPointer pointer : pointers) {
            this.validate(report, tree.append(pointer));
        }
    }
    
    private ProcessingMessage newMsg(final SchemaTree tree, final String key) {
        return new ProcessingMessage().put("schema", (AsJson)tree).put("domain", "syntax").setMessage(this.bundle.getMessage(key));
    }
    
    @Override
    public String toString() {
        return "syntax checker";
    }
}
