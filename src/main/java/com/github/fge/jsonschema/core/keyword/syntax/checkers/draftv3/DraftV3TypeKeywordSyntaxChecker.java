// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.draftv3;

import com.github.fge.jackson.JsonNumEquals;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.util.Set;
import com.google.common.collect.Sets;
import java.util.EnumSet;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.util.Collection;
import com.github.fge.jackson.NodeType;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Equivalence;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.AbstractSyntaxChecker;

public final class DraftV3TypeKeywordSyntaxChecker extends AbstractSyntaxChecker
{
    private static final String ANY = "any";
    private static final Equivalence<JsonNode> EQUIVALENCE;
    
    public DraftV3TypeKeywordSyntaxChecker(final String keyword) {
        super(keyword, NodeType.STRING, new NodeType[] { NodeType.ARRAY });
    }
    
    @Override
    protected void checkValue(final Collection<JsonPointer> pointers, final MessageBundle bundle, final ProcessingReport report, final SchemaTree tree) throws ProcessingException {
        final JsonNode node = tree.getNode().get(this.keyword);
        if (node.isTextual()) {
            final String found = node.textValue();
            if (!typeIsValid(found)) {
                report.error(this.newMsg(tree, bundle, "common.typeDisallow.primitiveType.unknown").putArgument("found", found).putArgument("valid", (Iterable<NodeType>)EnumSet.allOf(NodeType.class)));
            }
            return;
        }
        final int size = node.size();
        final Set<Equivalence.Wrapper<JsonNode>> set = (Set<Equivalence.Wrapper<JsonNode>>)Sets.newHashSet();
        boolean uniqueItems = true;
        for (int index = 0; index < size; ++index) {
            final JsonNode element = node.get(index);
            final NodeType type = NodeType.getNodeType(element);
            uniqueItems = set.add(DraftV3TypeKeywordSyntaxChecker.EQUIVALENCE.wrap(element));
            if (type == NodeType.OBJECT) {
                pointers.add(JsonPointer.of(this.keyword, index));
            }
            else if (type != NodeType.STRING) {
                report.error(this.newMsg(tree, bundle, "common.array.element.incorrectType").putArgument("index", index).putArgument("expected", (Iterable<NodeType>)EnumSet.of(NodeType.OBJECT, NodeType.STRING)).putArgument("found", type));
            }
            else if (!typeIsValid(element.textValue())) {
                report.error(this.newMsg(tree, bundle, "common.typeDisallow.primitiveType.unknown").put("index", index).putArgument("found", element.textValue()).putArgument("valid", (Iterable<NodeType>)EnumSet.allOf(NodeType.class)));
            }
        }
        if (!uniqueItems) {
            report.error(this.newMsg(tree, bundle, "common.array.duplicateElements"));
        }
    }
    
    private static boolean typeIsValid(final String s) {
        return "any".equals(s) || NodeType.fromName(s) != null;
    }
    
    static {
        EQUIVALENCE = JsonNumEquals.getInstance();
    }
}
