// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.common;

import com.github.fge.jackson.JsonNumEquals;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.util.Iterator;
import java.util.Set;
import com.google.common.collect.Sets;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.util.Collection;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Equivalence;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.AbstractSyntaxChecker;

public final class EnumSyntaxChecker extends AbstractSyntaxChecker
{
    private static final Equivalence<JsonNode> EQUIVALENCE;
    private static final SyntaxChecker INSTANCE;
    
    public static SyntaxChecker getInstance() {
        return EnumSyntaxChecker.INSTANCE;
    }
    
    private EnumSyntaxChecker() {
        super("enum", NodeType.ARRAY, new NodeType[0]);
    }
    
    @Override
    protected void checkValue(final Collection<JsonPointer> pointers, final MessageBundle bundle, final ProcessingReport report, final SchemaTree tree) throws ProcessingException {
        final Set<Equivalence.Wrapper<JsonNode>> set = (Set<Equivalence.Wrapper<JsonNode>>)Sets.newHashSet();
        for (final JsonNode element : this.getNode(tree)) {
            if (!set.add(EnumSyntaxChecker.EQUIVALENCE.wrap(element))) {
                report.error(this.newMsg(tree, bundle, "common.array.duplicateElements"));
            }
        }
    }
    
    static {
        EQUIVALENCE = JsonNumEquals.getInstance();
        INSTANCE = new EnumSyntaxChecker();
    }
}
