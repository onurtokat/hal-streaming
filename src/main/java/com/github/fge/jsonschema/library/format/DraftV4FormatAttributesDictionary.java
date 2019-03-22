// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.library.format;

import com.github.fge.jsonschema.core.util.DictionaryBuilder;
import com.github.fge.jsonschema.format.helpers.IPv4FormatAttribute;
import com.github.fge.jsonschema.format.helpers.SharedHostNameAttribute;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.core.util.Dictionary;

public final class DraftV4FormatAttributesDictionary
{
    private static final Dictionary<FormatAttribute> DICTIONARY;
    
    public static Dictionary<FormatAttribute> get() {
        return DraftV4FormatAttributesDictionary.DICTIONARY;
    }
    
    static {
        final DictionaryBuilder<FormatAttribute> builder = Dictionary.newBuilder();
        builder.addAll(CommonFormatAttributesDictionary.get());
        builder.addEntry("hostname", new SharedHostNameAttribute("hostname"));
        builder.addEntry("ipv4", new IPv4FormatAttribute("ipv4"));
        DICTIONARY = builder.freeze();
    }
}
