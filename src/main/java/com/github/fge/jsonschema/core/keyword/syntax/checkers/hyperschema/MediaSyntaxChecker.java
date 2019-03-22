// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.hyperschema;

import com.google.common.collect.ImmutableSet;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.net.MediaType;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.util.Collection;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import java.util.Set;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.AbstractSyntaxChecker;

public final class MediaSyntaxChecker extends AbstractSyntaxChecker
{
    private static final String BINARY_ENCODING_FIELDNAME = "binaryEncoding";
    private static final String TYPE_FIELDNAME = "type";
    private static final Set<String> BINARY_ENCODINGS;
    private static final SyntaxChecker INSTANCE;
    
    private MediaSyntaxChecker() {
        super("media", NodeType.OBJECT, new NodeType[0]);
    }
    
    public static SyntaxChecker getInstance() {
        return MediaSyntaxChecker.INSTANCE;
    }
    
    @Override
    protected void checkValue(final Collection<JsonPointer> pointers, final MessageBundle bundle, final ProcessingReport report, final SchemaTree tree) throws ProcessingException {
        final JsonNode node = this.getNode(tree);
        JsonNode subNode = node.path("binaryEncoding");
        if (!subNode.isMissingNode()) {
            final NodeType type = NodeType.getNodeType(subNode);
            final String value = subNode.textValue();
            if (value == null) {
                report.error(this.newMsg(tree, bundle, "draftv4.media.binaryEncoding.incorrectType").put("expected", NodeType.STRING).putArgument("found", type));
            }
            else if (!MediaSyntaxChecker.BINARY_ENCODINGS.contains(value.toLowerCase())) {
                report.error(this.newMsg(tree, bundle, "draftv4.media.binaryEncoding.invalid").putArgument("value", value).putArgument("valid", (Iterable<String>)MediaSyntaxChecker.BINARY_ENCODINGS));
            }
        }
        subNode = node.path("type");
        if (subNode.isMissingNode()) {
            return;
        }
        final NodeType type = NodeType.getNodeType(subNode);
        if (type != NodeType.STRING) {
            report.error(this.newMsg(tree, bundle, "draftv4.media.type.incorrectType").put("expected", NodeType.STRING).putArgument("found", type));
            return;
        }
        final String value = subNode.textValue();
        try {
            MediaType.parse(value);
        }
        catch (IllegalArgumentException ignored) {
            report.error(this.newMsg(tree, bundle, "draftv4.media.type.notMediaType").putArgument("value", value));
        }
    }
    
    static {
        BINARY_ENCODINGS = ImmutableSet.of("7bit", "8bit", "binary", "quoted-printable", "base64");
        INSTANCE = new MediaSyntaxChecker();
    }
}
