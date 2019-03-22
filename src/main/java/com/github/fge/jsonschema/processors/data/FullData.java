// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.processors.data;

import com.github.fge.jsonschema.core.util.AsJson;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.tree.JsonTree;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import javax.annotation.concurrent.Immutable;
import com.github.fge.jsonschema.core.report.MessageProvider;

@Immutable
public final class FullData implements MessageProvider
{
    private final SchemaTree schema;
    private final JsonTree instance;
    private final boolean deepCheck;
    
    public FullData(final SchemaTree schema, final JsonTree instance, final boolean deepCheck) {
        this.schema = schema;
        this.instance = instance;
        this.deepCheck = deepCheck;
    }
    
    public FullData(final SchemaTree schema, final JsonTree instance) {
        this(schema, instance, false);
    }
    
    public FullData(final SchemaTree schema) {
        this(schema, null);
    }
    
    public SchemaTree getSchema() {
        return this.schema;
    }
    
    public JsonTree getInstance() {
        return this.instance;
    }
    
    public boolean isDeepCheck() {
        return this.deepCheck;
    }
    
    public FullData withSchema(final SchemaTree schema) {
        return new FullData(schema, this.instance, this.deepCheck);
    }
    
    public FullData withInstance(final JsonTree instance) {
        return new FullData(this.schema, instance, this.deepCheck);
    }
    
    @Override
    public ProcessingMessage newMessage() {
        final ProcessingMessage ret = new ProcessingMessage();
        if (this.schema != null) {
            ret.put("schema", (AsJson)this.schema);
        }
        if (this.instance != null) {
            ret.put("instance", (AsJson)this.instance);
        }
        return ret;
    }
}
