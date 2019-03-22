// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers;

import com.github.fge.jsonschema.core.exceptions.InvalidSchemaException;
import com.github.fge.jsonschema.core.util.AsJson;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.util.Collection;
import com.github.fge.jackson.NodeType;
import java.util.EnumSet;
import com.github.fge.jsonschema.core.exceptions.ExceptionProvider;

public abstract class AbstractSyntaxChecker implements SyntaxChecker
{
    private static final ExceptionProvider EXCEPTION_PROVIDER;
    protected final String keyword;
    private final EnumSet<NodeType> types;
    
    protected AbstractSyntaxChecker(final String keyword, final NodeType first, final NodeType... other) {
        this.keyword = keyword;
        this.types = EnumSet.of(first, other);
    }
    
    @Override
    public final EnumSet<NodeType> getValidTypes() {
        return EnumSet.copyOf(this.types);
    }
    
    @Override
    public final void checkSyntax(final Collection<JsonPointer> pointers, final MessageBundle bundle, final ProcessingReport report, final SchemaTree tree) throws ProcessingException {
        final JsonNode node = this.getNode(tree);
        final NodeType type = NodeType.getNodeType(node);
        if (!this.types.contains(type)) {
            report.error(this.newMsg(tree, bundle, "common.incorrectType").putArgument("found", type).putArgument("expected", (Iterable<NodeType>)this.types));
            return;
        }
        this.checkValue(pointers, bundle, report, tree);
    }
    
    protected abstract void checkValue(final Collection<JsonPointer> p0, final MessageBundle p1, final ProcessingReport p2, final SchemaTree p3) throws ProcessingException;
    
    protected final ProcessingMessage newMsg(final SchemaTree tree, final MessageBundle bundle, final String key) {
        return new ProcessingMessage().setMessage(bundle.getMessage(key)).put("domain", "syntax").put("schema", (AsJson)tree).put("keyword", this.keyword).setExceptionProvider(AbstractSyntaxChecker.EXCEPTION_PROVIDER);
    }
    
    protected final JsonNode getNode(final SchemaTree tree) {
        return tree.getNode().get(this.keyword);
    }
    
    static {
        EXCEPTION_PROVIDER = new ExceptionProvider() {
            @Override
            public ProcessingException doException(final ProcessingMessage message) {
                return new InvalidSchemaException(message);
            }
        };
    }
}
