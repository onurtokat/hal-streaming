// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.processors.validation;

import java.net.URISyntaxException;
import java.net.URI;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.ref.JsonRef;
import javax.annotation.Nullable;
import java.util.Iterator;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JacksonUtils;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.processors.data.FullData;
import com.google.common.collect.Queues;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.util.Deque;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
final class ValidationStack
{
    private static final Element NULL_ELEMENT;
    private final Deque<Element> validationQueue;
    private final String errmsg;
    private JsonPointer pointer;
    private Deque<SchemaURI> schemaURIs;
    
    ValidationStack(final String errmsg) {
        this.validationQueue = (Deque<Element>)Queues.newArrayDeque();
        this.pointer = null;
        this.schemaURIs = null;
        this.errmsg = errmsg;
    }
    
    void push(final FullData data) throws ProcessingException {
        final JsonPointer ptr = data.getInstance().getPointer();
        final SchemaURI schemaURI = new SchemaURI(data.getSchema());
        if (!ptr.equals(this.pointer)) {
            this.validationQueue.addLast(new Element(this.pointer, (Deque)this.schemaURIs));
            this.pointer = ptr;
            (this.schemaURIs = (Deque<SchemaURI>)Queues.newArrayDeque()).addLast(schemaURI);
            return;
        }
        if (this.schemaURIs.contains(schemaURI)) {
            throw new ProcessingException(this.validationLoopMessage(data));
        }
        this.schemaURIs.addLast(schemaURI);
    }
    
    void pop() {
        this.schemaURIs.removeLast();
        if (!this.schemaURIs.isEmpty()) {
            return;
        }
        final Element element = this.validationQueue.removeLast();
        this.pointer = element.pointer;
        this.schemaURIs = element.schemaURIs;
    }
    
    private ProcessingMessage validationLoopMessage(final FullData input) {
        final ArrayNode node = JacksonUtils.nodeFactory().arrayNode();
        for (final SchemaURI uri : this.schemaURIs) {
            node.add(uri.toString());
        }
        return input.newMessage().put("domain", "validation").setMessage(this.errmsg).putArgument("alreadyVisited", new SchemaURI(input.getSchema())).putArgument("instancePointer", this.pointer.toString()).put("validationPath", (JsonNode)node);
    }
    
    static {
        NULL_ELEMENT = new Element((JsonPointer)null, (Deque)null);
    }
    
    private static final class Element
    {
        private final JsonPointer pointer;
        private final Deque<SchemaURI> schemaURIs;
        
        private Element(@Nullable final JsonPointer pointer, @Nullable final Deque<SchemaURI> schemaURIs) {
            this.pointer = pointer;
            this.schemaURIs = schemaURIs;
        }
    }
    
    private static final class SchemaURI
    {
        private final JsonRef locator;
        private final JsonPointer pointer;
        
        private SchemaURI(final SchemaTree tree) {
            this.locator = tree.getContext();
            this.pointer = tree.getPointer();
        }
        
        @Override
        public int hashCode() {
            return this.locator.hashCode() ^ this.pointer.hashCode();
        }
        
        @Override
        public boolean equals(@Nullable final Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            final SchemaURI other = (SchemaURI)obj;
            return this.locator.equals(other.locator) && this.pointer.equals(other.pointer);
        }
        
        @Override
        public String toString() {
            URI tmp;
            try {
                tmp = new URI(null, null, this.pointer.toString());
            }
            catch (URISyntaxException e) {
                throw new RuntimeException("How did I get there??", e);
            }
            return this.locator.toURI().resolve(tmp).toString();
        }
    }
}
