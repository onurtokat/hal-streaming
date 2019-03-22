// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.library;

import com.github.fge.jsonschema.library.format.DraftV3FormatAttributesDictionary;
import com.github.fge.jsonschema.library.validator.DraftV3ValidatorDictionary;
import com.github.fge.jsonschema.library.digest.DraftV3DigesterDictionary;
import com.github.fge.jsonschema.core.keyword.syntax.dictionaries.DraftV3SyntaxCheckerDictionary;

public final class DraftV3Library
{
    private static final Library LIBRARY;
    
    public static Library get() {
        return DraftV3Library.LIBRARY;
    }
    
    static {
        LIBRARY = new Library(DraftV3SyntaxCheckerDictionary.get(), DraftV3DigesterDictionary.get(), DraftV3ValidatorDictionary.get(), DraftV3FormatAttributesDictionary.get());
    }
}
