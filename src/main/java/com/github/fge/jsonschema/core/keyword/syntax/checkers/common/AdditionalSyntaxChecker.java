// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.common;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.util.Collection;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.AbstractSyntaxChecker;

public final class AdditionalSyntaxChecker extends AbstractSyntaxChecker
{
    public AdditionalSyntaxChecker(final String keyword) {
        super(keyword, NodeType.BOOLEAN, new NodeType[] { NodeType.OBJECT });
    }
    
    @Override
    protected void checkValue(final Collection<JsonPointer> pointers, final MessageBundle bundle, final ProcessingReport report, final SchemaTree tree) throws ProcessingException {
        if (this.getNode(tree).isObject()) {
            pointers.add(JsonPointer.of(this.keyword, new Object[0]));
        }
    }
}
