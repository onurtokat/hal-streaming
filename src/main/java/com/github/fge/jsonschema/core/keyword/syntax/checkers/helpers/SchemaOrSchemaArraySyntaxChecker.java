// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.util.Collection;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.AbstractSyntaxChecker;

public abstract class SchemaOrSchemaArraySyntaxChecker extends AbstractSyntaxChecker
{
    protected SchemaOrSchemaArraySyntaxChecker(final String keyword) {
        super(keyword, NodeType.ARRAY, new NodeType[] { NodeType.OBJECT });
    }
    
    @Override
    protected final void checkValue(final Collection<JsonPointer> pointers, final MessageBundle bundle, final ProcessingReport report, final SchemaTree tree) throws ProcessingException {
        this.collectPointers(pointers, tree);
        this.extraChecks(report, bundle, tree);
    }
    
    protected abstract void extraChecks(final ProcessingReport p0, final MessageBundle p1, final SchemaTree p2) throws ProcessingException;
    
    private void collectPointers(final Collection<JsonPointer> pointers, final SchemaTree tree) {
        final JsonNode node = this.getNode(tree);
        if (node.isObject()) {
            pointers.add(JsonPointer.of(this.keyword, new Object[0]));
            return;
        }
        for (int index = 0; index < node.size(); ++index) {
            pointers.add(JsonPointer.of(this.keyword, index));
        }
    }
}
