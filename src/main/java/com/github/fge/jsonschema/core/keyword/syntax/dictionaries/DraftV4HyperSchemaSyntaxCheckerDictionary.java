// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.dictionaries;

import com.github.fge.jsonschema.core.util.DictionaryBuilder;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.hyperschema.LinksSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.hyperschema.MediaSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers.TypeOnlySyntaxChecker;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers.URISyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.github.fge.jsonschema.core.util.Dictionary;

public final class DraftV4HyperSchemaSyntaxCheckerDictionary
{
    private static final Dictionary<SyntaxChecker> DICTIONARY;
    
    public static Dictionary<SyntaxChecker> get() {
        return DraftV4HyperSchemaSyntaxCheckerDictionary.DICTIONARY;
    }
    
    static {
        final DictionaryBuilder<SyntaxChecker> builder = Dictionary.newBuilder();
        builder.addAll(DraftV4SyntaxCheckerDictionary.get());
        String keyword = "pathStart";
        SyntaxChecker checker = new URISyntaxChecker(keyword);
        builder.addEntry(keyword, checker);
        keyword = "fragmentResolution";
        checker = new TypeOnlySyntaxChecker(keyword, NodeType.STRING, new NodeType[0]);
        builder.addEntry(keyword, checker);
        keyword = "media";
        checker = MediaSyntaxChecker.getInstance();
        builder.addEntry(keyword, checker);
        keyword = "links";
        checker = LinksSyntaxChecker.getInstance();
        builder.addEntry(keyword, checker);
        DICTIONARY = builder.freeze();
    }
}
