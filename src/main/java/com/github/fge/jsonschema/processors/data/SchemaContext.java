// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.processors.data;

import com.github.fge.jsonschema.core.util.AsJson;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.tree.JsonTree;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.report.MessageProvider;

public final class SchemaContext implements MessageProvider
{
    private final SchemaTree schema;
    private final NodeType instanceType;
    
    public SchemaContext(final FullData data) {
        this.schema = data.getSchema();
        final JsonTree tree = data.getInstance();
        this.instanceType = ((tree != null) ? NodeType.getNodeType(tree.getNode()) : null);
    }
    
    public SchemaContext(final SchemaTree schema, final NodeType instanceType) {
        this.schema = schema;
        this.instanceType = instanceType;
    }
    
    public SchemaTree getSchema() {
        return this.schema;
    }
    
    public NodeType getInstanceType() {
        return this.instanceType;
    }
    
    @Override
    public ProcessingMessage newMessage() {
        return new ProcessingMessage().put("schema", (AsJson)this.schema);
    }
}
