// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.draftv3;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.util.Iterator;
import java.util.SortedMap;
import com.github.fge.jackson.NodeType;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
import com.github.fge.jackson.JacksonUtils;
import com.google.common.collect.Maps;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.helpers.SchemaMapSyntaxChecker;

public final class DraftV3PropertiesSyntaxChecker extends SchemaMapSyntaxChecker
{
    private static final SyntaxChecker INSTANCE;
    
    public static SyntaxChecker getInstance() {
        return DraftV3PropertiesSyntaxChecker.INSTANCE;
    }
    
    private DraftV3PropertiesSyntaxChecker() {
        super("properties");
    }
    
    @Override
    protected void extraChecks(final ProcessingReport report, final MessageBundle bundle, final SchemaTree tree) throws ProcessingException {
        final SortedMap<String, JsonNode> map = (SortedMap<String, JsonNode>)Maps.newTreeMap();
        map.putAll((Map<?, ?>)JacksonUtils.asMap(tree.getNode().get(this.keyword)));
        for (final Map.Entry<String, JsonNode> entry : map.entrySet()) {
            final String member = entry.getKey();
            final JsonNode required = entry.getValue().get("required");
            if (required == null) {
                continue;
            }
            final NodeType type = NodeType.getNodeType(required);
            if (type == NodeType.BOOLEAN) {
                continue;
            }
            report.error(this.newMsg(tree, bundle, "draftv3.properties.required.incorrectType").putArgument("property", member).putArgument("found", type).put("expected", NodeType.BOOLEAN));
        }
    }
    
    static {
        INSTANCE = new DraftV3PropertiesSyntaxChecker();
    }
}
