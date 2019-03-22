// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.library;

import com.github.fge.Thawed;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.keyword.validator.KeywordValidator;
import java.lang.reflect.Constructor;
import com.github.fge.jsonschema.keyword.digest.Digester;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.github.fge.jsonschema.core.util.Dictionary;
import com.github.fge.Frozen;

public final class Library implements Frozen<LibraryBuilder>
{
    final Dictionary<SyntaxChecker> syntaxCheckers;
    final Dictionary<Digester> digesters;
    final Dictionary<Constructor<? extends KeywordValidator>> validators;
    final Dictionary<FormatAttribute> formatAttributes;
    
    public static LibraryBuilder newBuilder() {
        return new LibraryBuilder();
    }
    
    Library(final LibraryBuilder builder) {
        this.syntaxCheckers = builder.syntaxCheckers.freeze();
        this.digesters = builder.digesters.freeze();
        this.validators = builder.validators.freeze();
        this.formatAttributes = builder.formatAttributes.freeze();
    }
    
    Library(final Dictionary<SyntaxChecker> syntaxCheckers, final Dictionary<Digester> digesters, final Dictionary<Constructor<? extends KeywordValidator>> validators, final Dictionary<FormatAttribute> formatAttributes) {
        this.syntaxCheckers = syntaxCheckers;
        this.digesters = digesters;
        this.validators = validators;
        this.formatAttributes = formatAttributes;
    }
    
    public Dictionary<SyntaxChecker> getSyntaxCheckers() {
        return this.syntaxCheckers;
    }
    
    public Dictionary<Digester> getDigesters() {
        return this.digesters;
    }
    
    public Dictionary<Constructor<? extends KeywordValidator>> getValidators() {
        return this.validators;
    }
    
    public Dictionary<FormatAttribute> getFormatAttributes() {
        return this.formatAttributes;
    }
    
    @Override
    public LibraryBuilder thaw() {
        return new LibraryBuilder(this);
    }
}
