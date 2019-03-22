// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.util.Collection;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.AbstractSyntaxChecker;

public final class SchemaArraySyntaxChecker extends AbstractSyntaxChecker
{
    public SchemaArraySyntaxChecker(final String keyword) {
        super(keyword, NodeType.ARRAY, new NodeType[0]);
    }
    
    @Override
    protected void checkValue(final Collection<JsonPointer> pointers, final MessageBundle bundle, final ProcessingReport report, final SchemaTree tree) throws ProcessingException {
        final int size = this.getNode(tree).size();
        if (size == 0) {
            report.error(this.newMsg(tree, bundle, "common.array.empty"));
            return;
        }
        for (int index = 0; index < size; ++index) {
            pointers.add(JsonPointer.of(this.keyword, index));
        }
    }
}
