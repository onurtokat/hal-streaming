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

public final class TypeOnlySyntaxChecker extends AbstractSyntaxChecker
{
    public TypeOnlySyntaxChecker(final String keyword, final NodeType first, final NodeType... other) {
        super(keyword, first, other);
    }
    
    public void checkValue(final Collection<JsonPointer> pointers, final MessageBundle bundle, final ProcessingReport report, final SchemaTree tree) throws ProcessingException {
    }
}
