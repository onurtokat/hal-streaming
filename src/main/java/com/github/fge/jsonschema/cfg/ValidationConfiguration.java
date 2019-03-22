// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.cfg;

import com.github.fge.Thawed;
import com.google.common.collect.ImmutableMap;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.library.Library;
import com.github.fge.jsonschema.core.ref.JsonRef;
import java.util.Map;
import com.github.fge.Frozen;

public final class ValidationConfiguration implements Frozen<ValidationConfigurationBuilder>
{
    final Map<JsonRef, Library> libraries;
    final Library defaultLibrary;
    final boolean useFormat;
    final MessageBundle syntaxMessages;
    final MessageBundle validationMessages;
    
    public static ValidationConfigurationBuilder newBuilder() {
        return new ValidationConfigurationBuilder();
    }
    
    public static ValidationConfiguration byDefault() {
        return newBuilder().freeze();
    }
    
    ValidationConfiguration(final ValidationConfigurationBuilder builder) {
        this.libraries = (Map<JsonRef, Library>)ImmutableMap.copyOf((Map<?, ?>)builder.libraries);
        this.defaultLibrary = builder.defaultLibrary;
        this.useFormat = builder.useFormat;
        this.syntaxMessages = builder.syntaxMessages;
        this.validationMessages = builder.validationMessages;
    }
    
    public Map<JsonRef, Library> getLibraries() {
        return this.libraries;
    }
    
    public Library getDefaultLibrary() {
        return this.defaultLibrary;
    }
    
    public boolean getUseFormat() {
        return this.useFormat;
    }
    
    public MessageBundle getSyntaxMessages() {
        return this.syntaxMessages;
    }
    
    public MessageBundle getValidationMessages() {
        return this.validationMessages;
    }
    
    @Override
    public ValidationConfigurationBuilder thaw() {
        return new ValidationConfigurationBuilder(this);
    }
}
