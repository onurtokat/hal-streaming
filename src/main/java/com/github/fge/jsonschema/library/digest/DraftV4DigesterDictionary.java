// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.library.digest;

import com.github.fge.jsonschema.core.util.DictionaryBuilder;
import com.github.fge.jsonschema.keyword.digest.draftv4.DraftV4TypeDigester;
import com.github.fge.jsonschema.keyword.digest.helpers.NullDigester;
import com.github.fge.jsonschema.keyword.digest.draftv4.DraftV4DependenciesDigester;
import com.github.fge.jsonschema.keyword.digest.draftv4.RequiredDigester;
import com.github.fge.jsonschema.keyword.digest.helpers.SimpleDigester;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.keyword.digest.draftv4.MultipleOfDigester;
import com.github.fge.jsonschema.keyword.digest.Digester;
import com.github.fge.jsonschema.core.util.Dictionary;

public final class DraftV4DigesterDictionary
{
    private static final Dictionary<Digester> DICTIONARY;
    
    public static Dictionary<Digester> get() {
        return DraftV4DigesterDictionary.DICTIONARY;
    }
    
    static {
        final DictionaryBuilder<Digester> builder = Dictionary.newBuilder();
        builder.addAll(CommonDigesterDictionary.get());
        String keyword = "multipleOf";
        Digester digester = MultipleOfDigester.getInstance();
        builder.addEntry(keyword, digester);
        keyword = "minProperties";
        digester = new SimpleDigester(keyword, NodeType.OBJECT, new NodeType[0]);
        builder.addEntry(keyword, digester);
        keyword = "maxProperties";
        digester = new SimpleDigester(keyword, NodeType.OBJECT, new NodeType[0]);
        builder.addEntry(keyword, digester);
        keyword = "required";
        digester = RequiredDigester.getInstance();
        builder.addEntry(keyword, digester);
        keyword = "dependencies";
        digester = DraftV4DependenciesDigester.getInstance();
        builder.addEntry(keyword, digester);
        keyword = "anyOf";
        digester = new NullDigester(keyword, NodeType.ARRAY, NodeType.values());
        builder.addEntry(keyword, digester);
        keyword = "allOf";
        digester = new NullDigester(keyword, NodeType.ARRAY, NodeType.values());
        builder.addEntry(keyword, digester);
        keyword = "oneOf";
        digester = new NullDigester(keyword, NodeType.ARRAY, NodeType.values());
        builder.addEntry(keyword, digester);
        keyword = "not";
        digester = new NullDigester(keyword, NodeType.ARRAY, NodeType.values());
        builder.addEntry(keyword, digester);
        keyword = "type";
        digester = DraftV4TypeDigester.getInstance();
        builder.addEntry(keyword, digester);
        DICTIONARY = builder.freeze();
    }
}
