// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.draftv3;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers.SchemaOrSchemaArraySyntaxChecker;

public final class DraftV3ItemsSyntaxChecker extends SchemaOrSchemaArraySyntaxChecker
{
    private static final SyntaxChecker INSTANCE;
    
    public static SyntaxChecker getInstance() {
        return DraftV3ItemsSyntaxChecker.INSTANCE;
    }
    
    private DraftV3ItemsSyntaxChecker() {
        super("items");
    }
    
    @Override
    protected void extraChecks(final ProcessingReport report, final MessageBundle bundle, final SchemaTree tree) throws ProcessingException {
    }
    
    static {
        INSTANCE = new DraftV3ItemsSyntaxChecker();
    }
}
