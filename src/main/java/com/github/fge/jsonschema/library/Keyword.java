// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.library;

import com.github.fge.Thawed;
import com.github.fge.jsonschema.keyword.validator.KeywordValidator;
import java.lang.reflect.Constructor;
import com.github.fge.jsonschema.keyword.digest.Digester;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.github.fge.Frozen;

public final class Keyword implements Frozen<KeywordBuilder>
{
    final String name;
    final SyntaxChecker syntaxChecker;
    final Digester digester;
    final Constructor<? extends KeywordValidator> constructor;
    
    public static KeywordBuilder newBuilder(final String name) {
        return new KeywordBuilder(name);
    }
    
    Keyword(final KeywordBuilder builder) {
        this.name = builder.name;
        this.syntaxChecker = builder.syntaxChecker;
        this.digester = builder.digester;
        this.constructor = builder.constructor;
    }
    
    @Override
    public KeywordBuilder thaw() {
        return new KeywordBuilder(this);
    }
}
