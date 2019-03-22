// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.draftv4;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.google.common.base.Equivalence;
import java.util.Set;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.EnumSet;
import com.google.common.collect.Sets;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers.DependenciesSyntaxChecker;

public final class DraftV4DependenciesSyntaxChecker extends DependenciesSyntaxChecker
{
    private static final SyntaxChecker INSTANCE;
    
    public static SyntaxChecker getInstance() {
        return DraftV4DependenciesSyntaxChecker.INSTANCE;
    }
    
    private DraftV4DependenciesSyntaxChecker() {
        super(new NodeType[] { NodeType.ARRAY });
    }
    
    @Override
    protected void checkDependency(final ProcessingReport report, final MessageBundle bundle, final String name, final SchemaTree tree) throws ProcessingException {
        final JsonNode node = this.getNode(tree).get(name);
        NodeType type = NodeType.getNodeType(node);
        if (type != NodeType.ARRAY) {
            report.error(this.newMsg(tree, bundle, "common.dependencies.value.incorrectType").putArgument("property", name).putArgument("expected", (Iterable<NodeType>)this.dependencyTypes).putArgument("found", type));
            return;
        }
        final int size = node.size();
        if (size == 0) {
            report.error(this.newMsg(tree, bundle, "common.array.empty").put("property", name));
            return;
        }
        final Set<Equivalence.Wrapper<JsonNode>> set = (Set<Equivalence.Wrapper<JsonNode>>)Sets.newHashSet();
        boolean uniqueElements = true;
        for (int index = 0; index < size; ++index) {
            final JsonNode element = node.get(index);
            type = NodeType.getNodeType(element);
            uniqueElements = set.add(DraftV4DependenciesSyntaxChecker.EQUIVALENCE.wrap(element));
            if (type != NodeType.STRING) {
                report.error(this.newMsg(tree, bundle, "common.array.element.incorrectType").put("property", name).putArgument("index", index).putArgument("expected", (Iterable<NodeType>)EnumSet.of(NodeType.STRING)).putArgument("found", type));
            }
        }
        if (!uniqueElements) {
            report.error(this.newMsg(tree, bundle, "common.array.duplicateElements").put("property", name));
        }
    }
    
    static {
        INSTANCE = new DraftV4DependenciesSyntaxChecker();
    }
}
