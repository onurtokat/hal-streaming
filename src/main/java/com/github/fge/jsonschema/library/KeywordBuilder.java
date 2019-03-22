// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.library;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.messages.JsonSchemaConfigurationBundle;
import com.github.fge.Frozen;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.keyword.digest.helpers.SimpleDigester;
import com.github.fge.jsonschema.keyword.digest.helpers.IdentityDigester;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.keyword.validator.KeywordValidator;
import java.lang.reflect.Constructor;
import com.github.fge.jsonschema.keyword.digest.Digester;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.Thawed;

public final class KeywordBuilder implements Thawed<Keyword>
{
    private static final MessageBundle BUNDLE;
    final String name;
    SyntaxChecker syntaxChecker;
    Digester digester;
    Constructor<? extends KeywordValidator> constructor;
    
    KeywordBuilder(final String name) {
        KeywordBuilder.BUNDLE.checkNotNull(name, "nullName");
        this.name = name;
    }
    
    KeywordBuilder(final Keyword keyword) {
        this.name = keyword.name;
        this.syntaxChecker = keyword.syntaxChecker;
        this.digester = keyword.digester;
        this.constructor = keyword.constructor;
    }
    
    public KeywordBuilder withSyntaxChecker(final SyntaxChecker syntaxChecker) {
        KeywordBuilder.BUNDLE.checkNotNullPrintf(syntaxChecker, "nullSyntaxChecker", this.name);
        this.syntaxChecker = syntaxChecker;
        return this;
    }
    
    public KeywordBuilder withDigester(final Digester digester) {
        KeywordBuilder.BUNDLE.checkNotNullPrintf(digester, "nullDigester", this.name);
        this.digester = digester;
        return this;
    }
    
    public KeywordBuilder withIdentityDigester(final NodeType first, final NodeType... other) {
        this.digester = new IdentityDigester(this.name, checkType(first), checkTypes(other));
        return this;
    }
    
    public KeywordBuilder withSimpleDigester(final NodeType first, final NodeType... other) {
        this.digester = new SimpleDigester(this.name, checkType(first), checkTypes(other));
        return this;
    }
    
    public KeywordBuilder withValidatorClass(final Class<? extends KeywordValidator> c) {
        this.constructor = getConstructor(this.name, c);
        return this;
    }
    
    @Override
    public Keyword freeze() {
        KeywordBuilder.BUNDLE.checkArgumentPrintf(this.syntaxChecker != null, "noChecker", this.name);
        KeywordBuilder.BUNDLE.checkArgumentPrintf(this.constructor == null || this.digester != null, "malformedKeyword", this.name);
        return new Keyword(this);
    }
    
    private static Constructor<? extends KeywordValidator> getConstructor(final String name, final Class<? extends KeywordValidator> c) {
        try {
            return c.getConstructor(JsonNode.class);
        }
        catch (NoSuchMethodException ignored) {
            throw new IllegalArgumentException(KeywordBuilder.BUNDLE.printf("noAppropriateConstructor", name, c.getCanonicalName()));
        }
    }
    
    private static NodeType checkType(final NodeType type) {
        return KeywordBuilder.BUNDLE.checkNotNull(type, "nullType");
    }
    
    private static NodeType[] checkTypes(final NodeType... types) {
        for (final NodeType type : types) {
            KeywordBuilder.BUNDLE.checkNotNull(type, "nullType");
        }
        return types;
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaConfigurationBundle.class);
    }
}
