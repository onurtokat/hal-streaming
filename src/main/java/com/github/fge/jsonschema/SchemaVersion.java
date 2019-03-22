// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema;

import java.io.IOException;
import com.github.fge.jackson.JsonLoader;
import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;

public enum SchemaVersion
{
    DRAFTV4("http://json-schema.org/draft-04/schema#", "/draftv4/schema"), 
    DRAFTV3("http://json-schema.org/draft-03/schema#", "/draftv3/schema"), 
    DRAFTV4_HYPERSCHEMA("http://json-schema.org/draft-04/hyper-schema#", "/draftv4/hyper-schema");
    
    private final URI location;
    private final JsonNode schema;
    
    private SchemaVersion(final String uri, final String resource) {
        try {
            this.location = URI.create(uri);
            this.schema = JsonLoader.fromResource(resource);
        }
        catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public URI getLocation() {
        return this.location;
    }
    
    public JsonNode getSchema() {
        return this.schema.deepCopy();
    }
}
