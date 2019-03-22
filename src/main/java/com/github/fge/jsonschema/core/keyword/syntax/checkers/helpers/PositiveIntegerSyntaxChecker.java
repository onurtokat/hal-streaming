// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.util.Collection;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.AbstractSyntaxChecker;

public final class PositiveIntegerSyntaxChecker extends AbstractSyntaxChecker
{
    public PositiveIntegerSyntaxChecker(final String keyword) {
        super(keyword, NodeType.INTEGER, new NodeType[0]);
    }
    
    @Override
    protected void checkValue(final Collection<JsonPointer> pointers, final MessageBundle bundle, final ProcessingReport report, final SchemaTree tree) throws ProcessingException {
        final JsonNode node = this.getNode(tree);
        if (!node.canConvertToInt()) {
            report.error(this.newMsg(tree, bundle, "common.integerTooLarge").put("max", Integer.MAX_VALUE));
            return;
        }
        if (node.intValue() < 0) {
            report.error(this.newMsg(tree, bundle, "common.integerIsNegative"));
        }
    }
}
