// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.draftv4;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers.SchemaOrSchemaArraySyntaxChecker;

public final class DraftV4ItemsSyntaxChecker extends SchemaOrSchemaArraySyntaxChecker
{
    private static final SyntaxChecker INSTANCE;
    
    public static SyntaxChecker getInstance() {
        return DraftV4ItemsSyntaxChecker.INSTANCE;
    }
    
    private DraftV4ItemsSyntaxChecker() {
        super("items");
    }
    
    @Override
    protected void extraChecks(final ProcessingReport report, final MessageBundle bundle, final SchemaTree tree) throws ProcessingException {
        final JsonNode node = this.getNode(tree);
        if (node.isArray() && node.size() == 0) {
            report.error(this.newMsg(tree, bundle, "common.array.empty"));
        }
    }
    
    static {
        INSTANCE = new DraftV4ItemsSyntaxChecker();
    }
}
