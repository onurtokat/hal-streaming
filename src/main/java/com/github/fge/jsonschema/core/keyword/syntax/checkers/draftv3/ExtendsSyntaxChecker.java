// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.draftv3;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers.SchemaOrSchemaArraySyntaxChecker;

public final class ExtendsSyntaxChecker extends SchemaOrSchemaArraySyntaxChecker
{
    private static final SyntaxChecker INSTANCE;
    
    public static SyntaxChecker getInstance() {
        return ExtendsSyntaxChecker.INSTANCE;
    }
    
    private ExtendsSyntaxChecker() {
        super("extends");
    }
    
    @Override
    protected void extraChecks(final ProcessingReport report, final MessageBundle bundle, final SchemaTree tree) throws ProcessingException {
        final JsonNode node = tree.getNode().get(this.keyword);
        if (node.isArray() && node.size() == 0) {
            report.warn(this.newMsg(tree, bundle, "draftv3.extends.emptyArray"));
        }
    }
    
    static {
        INSTANCE = new ExtendsSyntaxChecker();
    }
}
