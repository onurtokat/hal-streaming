// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.math.BigDecimal;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.util.Collection;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.AbstractSyntaxChecker;

public final class DivisorSyntaxChecker extends AbstractSyntaxChecker
{
    public DivisorSyntaxChecker(final String keyword) {
        super(keyword, NodeType.INTEGER, new NodeType[] { NodeType.NUMBER });
    }
    
    @Override
    protected void checkValue(final Collection<JsonPointer> pointers, final MessageBundle bundle, final ProcessingReport report, final SchemaTree tree) throws ProcessingException {
        final JsonNode node = this.getNode(tree);
        final BigDecimal divisor = node.decimalValue();
        if (divisor.compareTo(BigDecimal.ZERO) <= 0) {
            report.error(this.newMsg(tree, bundle, "common.divisor.notPositive").put("found", node));
        }
    }
}
