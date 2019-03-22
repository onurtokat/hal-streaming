// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.util.Collection;
import com.google.common.annotations.VisibleForTesting;
import com.github.fge.jackson.NodeType;
import java.util.EnumSet;

public interface SyntaxChecker
{
    @VisibleForTesting
    EnumSet<NodeType> getValidTypes();
    
    void checkSyntax(final Collection<JsonPointer> p0, final MessageBundle p1, final ProcessingReport p2, final SchemaTree p3) throws ProcessingException;
}
