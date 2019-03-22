// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.library;

import com.github.fge.jsonschema.library.format.DraftV4FormatAttributesDictionary;
import com.github.fge.jsonschema.library.validator.DraftV4ValidatorDictionary;
import com.github.fge.jsonschema.library.digest.DraftV4DigesterDictionary;
import com.github.fge.jsonschema.core.keyword.syntax.dictionaries.DraftV4HyperSchemaSyntaxCheckerDictionary;

public final class DraftV4HyperSchemaLibrary
{
    private static final Library LIBRARY;
    
    public static Library get() {
        return DraftV4HyperSchemaLibrary.LIBRARY;
    }
    
    static {
        LIBRARY = new Library(DraftV4HyperSchemaSyntaxCheckerDictionary.get(), DraftV4DigesterDictionary.get(), DraftV4ValidatorDictionary.get(), DraftV4FormatAttributesDictionary.get());
    }
}
