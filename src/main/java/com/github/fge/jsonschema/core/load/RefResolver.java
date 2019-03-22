// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.load;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.core.messages.JsonSchemaCoreMessageBundle;
import com.github.fge.jsonschema.core.exceptions.JsonReferenceException;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Set;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.ref.JsonRef;
import com.github.fge.jsonschema.core.util.AsJson;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.google.common.collect.Sets;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.processing.RawProcessor;

public final class RefResolver extends RawProcessor<SchemaTree, SchemaTree>
{
    private static final MessageBundle BUNDLE;
    private final SchemaLoader loader;
    
    public RefResolver(final SchemaLoader loader) {
        super("schema", "schema");
        this.loader = loader;
    }
    
    public SchemaTree rawProcess(final ProcessingReport report, final SchemaTree input) throws ProcessingException {
        final Set<JsonRef> refs = (Set<JsonRef>)Sets.newLinkedHashSet();
        SchemaTree tree = input;
        while (true) {
            final JsonNode node = tree.getNode();
            JsonRef ref = nodeAsRef(node);
            if (ref == null) {
                return tree;
            }
            ref = tree.resolve(ref);
            if (!refs.add(ref)) {
                throw new ProcessingException(new ProcessingMessage().setMessage(RefResolver.BUNDLE.getMessage("refProcessing.refLoop")).put("schema", (AsJson)tree).putArgument("ref", ref).put("path", (Iterable<JsonRef>)refs));
            }
            if (!tree.containsRef(ref)) {
                tree = this.loader.get(ref.getLocator());
            }
            final JsonPointer ptr = tree.matchingPointer(ref);
            if (ptr == null) {
                throw new ProcessingException(new ProcessingMessage().setMessage(RefResolver.BUNDLE.getMessage("refProcessing.danglingRef")).put("schema", (AsJson)tree).putArgument("ref", ref));
            }
            tree = tree.setPointer(ptr);
        }
    }
    
    private static JsonRef nodeAsRef(final JsonNode node) {
        final JsonNode refNode = node.path("$ref");
        if (!refNode.isTextual()) {
            return null;
        }
        try {
            return JsonRef.fromString(refNode.textValue());
        }
        catch (JsonReferenceException ignored) {
            return null;
        }
    }
    
    @Override
    public String toString() {
        return "ref resolver";
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);
    }
}
