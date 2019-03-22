// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.draftv4;

import com.github.fge.jackson.JsonNumEquals;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.util.Set;
import java.util.EnumSet;
import com.google.common.collect.Sets;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.util.Collection;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Equivalence;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.AbstractSyntaxChecker;

public final class RequiredSyntaxChecker extends AbstractSyntaxChecker
{
    private static final Equivalence<JsonNode> EQUIVALENCE;
    private static final SyntaxChecker INSTANCE;
    
    public static SyntaxChecker getInstance() {
        return RequiredSyntaxChecker.INSTANCE;
    }
    
    private RequiredSyntaxChecker() {
        super("required", NodeType.ARRAY, new NodeType[0]);
    }
    
    @Override
    protected void checkValue(final Collection<JsonPointer> pointers, final MessageBundle bundle, final ProcessingReport report, final SchemaTree tree) throws ProcessingException {
        final JsonNode node = this.getNode(tree);
        final int size = node.size();
        if (size == 0) {
            report.error(this.newMsg(tree, bundle, "common.array.empty"));
            return;
        }
        final Set<Equivalence.Wrapper<JsonNode>> set = (Set<Equivalence.Wrapper<JsonNode>>)Sets.newHashSet();
        boolean uniqueElements = true;
        for (int index = 0; index < size; ++index) {
            final JsonNode element = node.get(index);
            uniqueElements = set.add(RequiredSyntaxChecker.EQUIVALENCE.wrap(element));
            final NodeType type = NodeType.getNodeType(element);
            if (type != NodeType.STRING) {
                report.error(this.newMsg(tree, bundle, "common.array.element.incorrectType").putArgument("index", index).putArgument("expected", (Iterable<NodeType>)EnumSet.of(NodeType.STRING)).putArgument("found", type));
            }
        }
        if (!uniqueElements) {
            report.error(this.newMsg(tree, bundle, "common.array.duplicateElements"));
        }
    }
    
    static {
        EQUIVALENCE = JsonNumEquals.getInstance();
        INSTANCE = new RequiredSyntaxChecker();
    }
}
