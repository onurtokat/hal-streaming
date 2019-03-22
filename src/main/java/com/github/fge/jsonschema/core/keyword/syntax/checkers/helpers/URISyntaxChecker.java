// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.net.URISyntaxException;
import java.net.URI;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.util.Collection;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.AbstractSyntaxChecker;

public final class URISyntaxChecker extends AbstractSyntaxChecker
{
    public URISyntaxChecker(final String keyword) {
        super(keyword, NodeType.STRING, new NodeType[0]);
    }
    
    @Override
    protected void checkValue(final Collection<JsonPointer> pointers, final MessageBundle bundle, final ProcessingReport report, final SchemaTree tree) throws ProcessingException {
        final String s = this.getNode(tree).textValue();
        try {
            final URI uri = new URI(s);
            if (!uri.equals(uri.normalize())) {
                report.error(this.newMsg(tree, bundle, "common.uri.notNormalized").putArgument("value", s));
            }
        }
        catch (URISyntaxException ignored) {
            report.error(this.newMsg(tree, bundle, "common.uri.invalid").putArgument("value", s));
        }
    }
}
