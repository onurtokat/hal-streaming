// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.library.digest;

import com.github.fge.jsonschema.core.util.DictionaryBuilder;
import com.github.fge.jsonschema.keyword.digest.helpers.NullDigester;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.keyword.digest.helpers.DraftV3TypeKeywordDigester;
import com.github.fge.jsonschema.keyword.digest.draftv3.DraftV3DependenciesDigester;
import com.github.fge.jsonschema.keyword.digest.draftv3.DraftV3PropertiesDigester;
import com.github.fge.jsonschema.keyword.digest.draftv3.DivisibleByDigester;
import com.github.fge.jsonschema.keyword.digest.Digester;
import com.github.fge.jsonschema.core.util.Dictionary;

public final class DraftV3DigesterDictionary
{
    private static final Dictionary<Digester> DICTIONARY;
    
    public static Dictionary<Digester> get() {
        return DraftV3DigesterDictionary.DICTIONARY;
    }
    
    static {
        final DictionaryBuilder<Digester> builder = Dictionary.newBuilder();
        builder.addAll(CommonDigesterDictionary.get());
        String keyword = "divisibleBy";
        Digester digester = DivisibleByDigester.getInstance();
        builder.addEntry(keyword, digester);
        keyword = "properties";
        digester = DraftV3PropertiesDigester.getInstance();
        builder.addEntry(keyword, digester);
        keyword = "dependencies";
        digester = DraftV3DependenciesDigester.getInstance();
        builder.addEntry(keyword, digester);
        keyword = "type";
        digester = new DraftV3TypeKeywordDigester(keyword);
        builder.addEntry(keyword, digester);
        keyword = "disallow";
        digester = new DraftV3TypeKeywordDigester(keyword);
        builder.addEntry(keyword, digester);
        keyword = "extends";
        digester = new NullDigester(keyword, NodeType.ARRAY, NodeType.values());
        builder.addEntry(keyword, digester);
        DICTIONARY = builder.freeze();
    }
}
