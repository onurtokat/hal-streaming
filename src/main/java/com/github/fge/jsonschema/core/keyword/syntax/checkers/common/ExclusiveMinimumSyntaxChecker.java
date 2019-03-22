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
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.AbstractSyntaxChecker;

public final class ExclusiveMinimumSyntaxChecker extends AbstractSyntaxChecker
{
    private static final SyntaxChecker INSTANCE;
    
    public static SyntaxChecker getInstance() {
        return ExclusiveMinimumSyntaxChecker.INSTANCE;
    }
    
    private ExclusiveMinimumSyntaxChecker() {
        super("exclusiveMinimum", NodeType.BOOLEAN, new NodeType[0]);
    }
    
    @Override
    protected void checkValue(final Collection<JsonPointer> pointers, final MessageBundle bundle, final ProcessingReport report, final SchemaTree tree) throws ProcessingException {
        if (!tree.getNode().has("minimum")) {
            report.error(this.newMsg(tree, bundle, "common.exclusiveMinimum"));
        }
    }
    
    static {
        INSTANCE = new ExclusiveMinimumSyntaxChecker();
    }
}
