// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.dictionaries;

import com.github.fge.jsonschema.core.util.DictionaryBuilder;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.draftv3.DraftV3TypeKeywordSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.draftv3.ExtendsSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.draftv3.DraftV3DependenciesSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.draftv3.DraftV3PropertiesSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers.DivisorSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.draftv3.DraftV3ItemsSyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.github.fge.jsonschema.core.util.Dictionary;

public final class DraftV3SyntaxCheckerDictionary
{
    private static final Dictionary<SyntaxChecker> DICTIONARY;
    
    public static Dictionary<SyntaxChecker> get() {
        return DraftV3SyntaxCheckerDictionary.DICTIONARY;
    }
    
    static {
        final DictionaryBuilder<SyntaxChecker> builder = Dictionary.newBuilder();
        builder.addAll(CommonSyntaxCheckerDictionary.get());
        String keyword = "items";
        SyntaxChecker checker = DraftV3ItemsSyntaxChecker.getInstance();
        builder.addEntry(keyword, checker);
        keyword = "divisibleBy";
        checker = new DivisorSyntaxChecker(keyword);
        builder.addEntry(keyword, checker);
        keyword = "properties";
        checker = DraftV3PropertiesSyntaxChecker.getInstance();
        builder.addEntry(keyword, checker);
        keyword = "dependencies";
        checker = DraftV3DependenciesSyntaxChecker.getInstance();
        builder.addEntry(keyword, checker);
        keyword = "extends";
        checker = ExtendsSyntaxChecker.getInstance();
        builder.addEntry(keyword, checker);
        keyword = "type";
        checker = new DraftV3TypeKeywordSyntaxChecker(keyword);
        builder.addEntry(keyword, checker);
        keyword = "disallow";
        checker = new DraftV3TypeKeywordSyntaxChecker(keyword);
        builder.addEntry(keyword, checker);
        DICTIONARY = builder.freeze();
    }
}
