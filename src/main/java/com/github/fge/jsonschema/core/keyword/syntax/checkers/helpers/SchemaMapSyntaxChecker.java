// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers;

import java.util.Set;
import com.google.common.collect.Ordering;
import java.util.Iterator;
import com.google.common.collect.Sets;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.util.Collection;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.AbstractSyntaxChecker;

public abstract class SchemaMapSyntaxChecker extends AbstractSyntaxChecker
{
    protected SchemaMapSyntaxChecker(final String keyword) {
        super(keyword, NodeType.OBJECT, new NodeType[0]);
    }
    
    @Override
    protected final void checkValue(final Collection<JsonPointer> pointers, final MessageBundle bundle, final ProcessingReport report, final SchemaTree tree) throws ProcessingException {
        this.collectPointers(pointers, this.getNode(tree));
        this.extraChecks(report, bundle, tree);
    }
    
    protected abstract void extraChecks(final ProcessingReport p0, final MessageBundle p1, final SchemaTree p2) throws ProcessingException;
    
    private void collectPointers(final Collection<JsonPointer> pointers, final JsonNode node) {
        final Set<String> set = (Set<String>)Sets.newHashSet((Iterator<?>)node.fieldNames());
        for (final String s : Ordering.natural().sortedCopy(set)) {
            pointers.add(JsonPointer.of(this.keyword, s));
        }
    }
}
