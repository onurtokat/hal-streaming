// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.cfg;

import com.github.fge.jsonschema.library.DraftV4HyperSchemaLibrary;
import com.github.fge.jsonschema.library.DraftV4Library;
import com.github.fge.jsonschema.library.DraftV3Library;
import com.github.fge.jsonschema.messages.JsonSchemaConfigurationBundle;
import com.github.fge.Frozen;
import com.github.fge.jsonschema.core.exceptions.JsonReferenceException;
import java.util.Iterator;
import com.github.fge.jsonschema.messages.JsonSchemaValidationBundle;
import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.core.messages.JsonSchemaSyntaxMessageBundle;
import com.google.common.collect.Maps;
import com.github.fge.jsonschema.core.ref.JsonRef;
import com.github.fge.jsonschema.library.Library;
import com.github.fge.jsonschema.SchemaVersion;
import java.util.Map;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.Thawed;

public final class ValidationConfigurationBuilder implements Thawed<ValidationConfiguration>
{
    private static final MessageBundle BUNDLE;
    private static final Map<SchemaVersion, Library> DEFAULT_LIBRARIES;
    final Map<JsonRef, Library> libraries;
    Library defaultLibrary;
    boolean useFormat;
    MessageBundle syntaxMessages;
    MessageBundle validationMessages;
    
    ValidationConfigurationBuilder() {
        this.defaultLibrary = ValidationConfigurationBuilder.DEFAULT_LIBRARIES.get(SchemaVersion.DRAFTV4);
        this.useFormat = true;
        this.libraries = (Map<JsonRef, Library>)Maps.newHashMap();
        for (final Map.Entry<SchemaVersion, Library> entry : ValidationConfigurationBuilder.DEFAULT_LIBRARIES.entrySet()) {
            final JsonRef ref = JsonRef.fromURI(entry.getKey().getLocation());
            final Library library = entry.getValue();
            this.libraries.put(ref, library);
        }
        this.syntaxMessages = MessageBundles.getBundle(JsonSchemaSyntaxMessageBundle.class);
        this.validationMessages = MessageBundles.getBundle(JsonSchemaValidationBundle.class);
    }
    
    ValidationConfigurationBuilder(final ValidationConfiguration cfg) {
        this.defaultLibrary = ValidationConfigurationBuilder.DEFAULT_LIBRARIES.get(SchemaVersion.DRAFTV4);
        this.useFormat = true;
        this.libraries = (Map<JsonRef, Library>)Maps.newHashMap((Map<?, ?>)cfg.libraries);
        this.defaultLibrary = cfg.defaultLibrary;
        this.useFormat = cfg.useFormat;
        this.syntaxMessages = cfg.syntaxMessages;
        this.validationMessages = cfg.validationMessages;
    }
    
    public ValidationConfigurationBuilder addLibrary(final String uri, final Library library) {
        JsonRef ref;
        try {
            ref = JsonRef.fromString(uri);
        }
        catch (JsonReferenceException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        ValidationConfigurationBuilder.BUNDLE.checkArgumentPrintf(ref.isAbsolute(), "refProcessing.uriNotAbsolute", ref);
        ValidationConfigurationBuilder.BUNDLE.checkNotNull(library, "nullLibrary");
        ValidationConfigurationBuilder.BUNDLE.checkArgumentPrintf(this.libraries.put(ref, library) == null, "dupLibrary", ref);
        return this;
    }
    
    public ValidationConfigurationBuilder setDefaultVersion(final SchemaVersion version) {
        ValidationConfigurationBuilder.BUNDLE.checkNotNull(version, "nullVersion");
        this.defaultLibrary = ValidationConfigurationBuilder.DEFAULT_LIBRARIES.get(version);
        return this;
    }
    
    public ValidationConfigurationBuilder setDefaultLibrary(final String uri, final Library library) {
        this.addLibrary(uri, library);
        this.defaultLibrary = library;
        return this;
    }
    
    public ValidationConfigurationBuilder setUseFormat(final boolean useFormat) {
        this.useFormat = useFormat;
        return this;
    }
    
    public ValidationConfigurationBuilder setSyntaxMessages(final MessageBundle syntaxMessages) {
        ValidationConfigurationBuilder.BUNDLE.checkNotNull(syntaxMessages, "nullMessageBundle");
        this.syntaxMessages = syntaxMessages;
        return this;
    }
    
    public ValidationConfigurationBuilder setValidationMessages(final MessageBundle validationMessages) {
        ValidationConfigurationBuilder.BUNDLE.checkNotNull(validationMessages, "nullMessageBundle");
        this.validationMessages = validationMessages;
        return this;
    }
    
    @Override
    public ValidationConfiguration freeze() {
        return new ValidationConfiguration(this);
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaConfigurationBundle.class);
        (DEFAULT_LIBRARIES = Maps.newEnumMap(SchemaVersion.class)).put(SchemaVersion.DRAFTV3, DraftV3Library.get());
        ValidationConfigurationBuilder.DEFAULT_LIBRARIES.put(SchemaVersion.DRAFTV4, DraftV4Library.get());
        ValidationConfigurationBuilder.DEFAULT_LIBRARIES.put(SchemaVersion.DRAFTV4_HYPERSCHEMA, DraftV4HyperSchemaLibrary.get());
    }
}
