// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.common;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.util.RhinoHelper;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.util.Collection;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.AbstractSyntaxChecker;

public final class PatternSyntaxChecker extends AbstractSyntaxChecker
{
    private static final SyntaxChecker INSTANCE;
    
    public static SyntaxChecker getInstance() {
        return PatternSyntaxChecker.INSTANCE;
    }
    
    private PatternSyntaxChecker() {
        super("pattern", NodeType.STRING, new NodeType[0]);
    }
    
    @Override
    protected void checkValue(final Collection<JsonPointer> pointers, final MessageBundle bundle, final ProcessingReport report, final SchemaTree tree) throws ProcessingException {
        final String value = this.getNode(tree).textValue();
        if (!RhinoHelper.regexIsValid(value)) {
            report.error(this.newMsg(tree, bundle, "common.invalidRegex").putArgument("value", value));
        }
    }
    
    static {
        INSTANCE = new PatternSyntaxChecker();
    }
}
