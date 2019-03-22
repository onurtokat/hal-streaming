// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.library;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.messages.JsonSchemaConfigurationBundle;
import com.github.fge.Frozen;
import com.github.fge.jsonschema.core.util.Dictionary;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.keyword.validator.KeywordValidator;
import java.lang.reflect.Constructor;
import com.github.fge.jsonschema.keyword.digest.Digester;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.github.fge.jsonschema.core.util.DictionaryBuilder;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.Thawed;

public final class LibraryBuilder implements Thawed<Library>
{
    private static final MessageBundle BUNDLE;
    final DictionaryBuilder<SyntaxChecker> syntaxCheckers;
    final DictionaryBuilder<Digester> digesters;
    final DictionaryBuilder<Constructor<? extends KeywordValidator>> validators;
    final DictionaryBuilder<FormatAttribute> formatAttributes;
    
    LibraryBuilder() {
        this.syntaxCheckers = Dictionary.newBuilder();
        this.digesters = Dictionary.newBuilder();
        this.validators = Dictionary.newBuilder();
        this.formatAttributes = Dictionary.newBuilder();
    }
    
    LibraryBuilder(final Library library) {
        this.syntaxCheckers = library.syntaxCheckers.thaw();
        this.digesters = library.digesters.thaw();
        this.validators = library.validators.thaw();
        this.formatAttributes = library.formatAttributes.thaw();
    }
    
    public LibraryBuilder addKeyword(final Keyword keyword) {
        LibraryBuilder.BUNDLE.checkNotNull(keyword, "nullKeyword");
        final String name = keyword.name;
        this.removeKeyword(name);
        this.syntaxCheckers.addEntry(name, keyword.syntaxChecker);
        if (keyword.constructor != null) {
            this.digesters.addEntry(name, keyword.digester);
            this.validators.addEntry(name, keyword.constructor);
        }
        return this;
    }
    
    public LibraryBuilder removeKeyword(final String name) {
        LibraryBuilder.BUNDLE.checkNotNull(name, "nullName");
        this.syntaxCheckers.removeEntry(name);
        this.digesters.removeEntry(name);
        this.validators.removeEntry(name);
        return this;
    }
    
    public LibraryBuilder addFormatAttribute(final String name, final FormatAttribute attribute) {
        this.removeFormatAttribute(name);
        LibraryBuilder.BUNDLE.checkNotNullPrintf(attribute, "nullAttribute", name);
        this.formatAttributes.addEntry(name, attribute);
        return this;
    }
    
    public LibraryBuilder removeFormatAttribute(final String name) {
        LibraryBuilder.BUNDLE.checkNotNull(name, "nullFormat");
        this.formatAttributes.removeEntry(name);
        return this;
    }
    
    @Override
    public Library freeze() {
        return new Library(this);
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaConfigurationBundle.class);
    }
}
