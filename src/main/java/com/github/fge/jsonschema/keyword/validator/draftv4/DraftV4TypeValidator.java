// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.keyword.validator.draftv4;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.util.Collection;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.core.processing.Processor;
import java.util.Iterator;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.NodeType;
import java.util.EnumSet;
import com.github.fge.jsonschema.keyword.validator.AbstractKeywordValidator;

public final class DraftV4TypeValidator extends AbstractKeywordValidator
{
    private final EnumSet<NodeType> types;
    
    public DraftV4TypeValidator(final JsonNode digest) {
        super("type");
        this.types = EnumSet.noneOf(NodeType.class);
        for (final JsonNode node : digest.get(this.keyword)) {
            this.types.add(NodeType.fromName(node.textValue()));
        }
    }
    
    @Override
    public void validate(final Processor<FullData, FullData> processor, final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
        final NodeType type = NodeType.getNodeType(data.getInstance().getNode());
        if (!this.types.contains(type)) {
            report.error(this.newMsg(data, bundle, "err.common.typeNoMatch").putArgument("found", type).putArgument("expected", AbstractKeywordValidator.toArrayNode(this.types)));
        }
    }
    
    @Override
    public String toString() {
        return this.keyword + ": " + this.types;
    }
}
