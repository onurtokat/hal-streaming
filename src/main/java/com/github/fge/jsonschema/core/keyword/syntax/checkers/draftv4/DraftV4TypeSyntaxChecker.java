// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.draftv4;

import com.github.fge.jackson.JsonNumEquals;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.util.Set;
import com.google.common.collect.Sets;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.util.Collection;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Equivalence;
import com.github.fge.jackson.NodeType;
import java.util.EnumSet;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.AbstractSyntaxChecker;

public final class DraftV4TypeSyntaxChecker extends AbstractSyntaxChecker
{
    private static final EnumSet<NodeType> ALL_TYPES;
    private static final Equivalence<JsonNode> EQUIVALENCE;
    private static final SyntaxChecker INSTANCE;
    
    public static SyntaxChecker getInstance() {
        return DraftV4TypeSyntaxChecker.INSTANCE;
    }
    
    private DraftV4TypeSyntaxChecker() {
        super("type", NodeType.ARRAY, new NodeType[] { NodeType.STRING });
    }
    
    @Override
    protected void checkValue(final Collection<JsonPointer> pointers, final MessageBundle bundle, final ProcessingReport report, final SchemaTree tree) throws ProcessingException {
        final JsonNode node = this.getNode(tree);
        if (node.isTextual()) {
            final String s = node.textValue();
            if (NodeType.fromName(s) == null) {
                report.error(this.newMsg(tree, bundle, "common.typeDisallow.primitiveType.unknown").putArgument("found", s).putArgument("valid", (Iterable<NodeType>)DraftV4TypeSyntaxChecker.ALL_TYPES));
            }
            return;
        }
        final int size = node.size();
        if (size == 0) {
            report.error(this.newMsg(tree, bundle, "common.array.empty"));
            return;
        }
        final Set<Equivalence.Wrapper<JsonNode>> set = (Set<Equivalence.Wrapper<JsonNode>>)Sets.newHashSet();
        boolean uniqueElements = true;
        for (int index = 0; index < size; ++index) {
            final JsonNode element = node.get(index);
            final NodeType type = NodeType.getNodeType(element);
            uniqueElements = set.add(DraftV4TypeSyntaxChecker.EQUIVALENCE.wrap(element));
            if (type != NodeType.STRING) {
                report.error(this.newMsg(tree, bundle, "common.array.element.incorrectType").putArgument("index", index).putArgument("expected", NodeType.STRING).putArgument("found", type));
            }
            else {
                final String found = element.textValue();
                if (NodeType.fromName(found) == null) {
                    report.error(this.newMsg(tree, bundle, "common.typeDisallow.primitiveType.unknown").put("index", index).putArgument("found", found).putArgument("valid", (Iterable<NodeType>)DraftV4TypeSyntaxChecker.ALL_TYPES));
                }
            }
        }
        if (!uniqueElements) {
            report.error(this.newMsg(tree, bundle, "common.array.duplicateElements"));
        }
    }
    
    static {
        ALL_TYPES = EnumSet.allOf(NodeType.class);
        EQUIVALENCE = JsonNumEquals.getInstance();
        INSTANCE = new DraftV4TypeSyntaxChecker();
    }
}
