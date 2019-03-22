// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.keyword.syntax.checkers.hyperschema;

import com.google.common.collect.ImmutableList;
import com.google.common.net.MediaType;
import com.github.fge.uritemplate.URITemplateParseException;
import com.github.fge.uritemplate.URITemplate;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import java.util.Set;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import java.util.Iterator;
import com.google.common.collect.Sets;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.util.Collection;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import java.util.List;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.AbstractSyntaxChecker;

public final class LinksSyntaxChecker extends AbstractSyntaxChecker
{
    private static final List<String> REQUIRED_LDO_PROPERTIES;
    private static final SyntaxChecker INSTANCE;
    
    private LinksSyntaxChecker() {
        super("links", NodeType.ARRAY, new NodeType[0]);
    }
    
    public static SyntaxChecker getInstance() {
        return LinksSyntaxChecker.INSTANCE;
    }
    
    @Override
    protected void checkValue(final Collection<JsonPointer> pointers, final MessageBundle bundle, final ProcessingReport report, final SchemaTree tree) throws ProcessingException {
        final JsonNode node = this.getNode(tree);
        for (int size = node.size(), index = 0; index < size; ++index) {
            final JsonNode ldo = this.getNode(tree).get(index);
            final NodeType type = NodeType.getNodeType(ldo);
            if (type != NodeType.OBJECT) {
                report.error(this.LDOMsg(tree, bundle, "draftv4.ldo.incorrectType", index).put("expected", NodeType.OBJECT).putArgument("found", type));
            }
            else {
                final Set<String> set = (Set<String>)Sets.newHashSet((Iterator<?>)ldo.fieldNames());
                final List<String> list = (List<String>)Lists.newArrayList((Iterable<?>)LinksSyntaxChecker.REQUIRED_LDO_PROPERTIES);
                list.removeAll(set);
                if (!list.isEmpty()) {
                    final ProcessingMessage msg = this.LDOMsg(tree, bundle, "draftv4.ldo.missingRequired", index);
                    report.error(msg.put("required", (Iterable<String>)LinksSyntaxChecker.REQUIRED_LDO_PROPERTIES).putArgument("missing", (Iterable<String>)list));
                }
                else {
                    if (ldo.has("schema")) {
                        pointers.add(JsonPointer.of(this.keyword, index, "schema"));
                    }
                    if (ldo.has("targetSchema")) {
                        pointers.add(JsonPointer.of(this.keyword, index, "targetSchema"));
                    }
                    this.checkLDO(report, bundle, tree, index);
                }
            }
        }
    }
    
    private void checkLDO(final ProcessingReport report, final MessageBundle bundle, final SchemaTree tree, final int index) throws ProcessingException {
        final JsonNode ldo = this.getNode(tree).get(index);
        this.checkLDOProperty(report, bundle, tree, index, "rel", NodeType.STRING, "draftv4.ldo.rel.incorrectType");
        if (this.checkLDOProperty(report, bundle, tree, index, "href", NodeType.STRING, "draftv4.ldo.href.incorrectType")) {
            final String value = ldo.get("href").textValue();
            try {
                new URITemplate(value);
            }
            catch (URITemplateParseException ignored) {
                report.error(this.LDOMsg(tree, bundle, "draftv4.ldo.href.notURITemplate", index).putArgument("value", value));
            }
        }
        this.checkLDOProperty(report, bundle, tree, index, "title", NodeType.STRING, "draftv4.ldo.title.incorrectType");
        if (this.checkLDOProperty(report, bundle, tree, index, "mediaType", NodeType.STRING, "draftv4.ldo.mediaType.incorrectType")) {
            final String value = ldo.get("mediaType").textValue();
            try {
                MediaType.parse(value);
            }
            catch (IllegalArgumentException ignored2) {
                report.error(this.LDOMsg(tree, bundle, "draftv4.ldo.mediaType.notMediaType", index).putArgument("value", value));
            }
        }
        this.checkLDOProperty(report, bundle, tree, index, "method", NodeType.STRING, "draftv4.ldo.method.incorrectType");
        if (this.checkLDOProperty(report, bundle, tree, index, "encType", NodeType.STRING, "draftv4.ldo.enctype.incorrectType")) {
            final String value = ldo.get("encType").textValue();
            try {
                MediaType.parse(value);
            }
            catch (IllegalArgumentException ignored2) {
                report.error(this.LDOMsg(tree, bundle, "draftv4.ldo.enctype.notMediaType", index).putArgument("value", value));
            }
        }
    }
    
    private ProcessingMessage LDOMsg(final SchemaTree tree, final MessageBundle bundle, final String key, final int index) {
        return this.newMsg(tree, bundle, key).put("index", index);
    }
    
    private boolean checkLDOProperty(final ProcessingReport report, final MessageBundle bundle, final SchemaTree tree, final int index, final String name, final NodeType expected, final String key) throws ProcessingException {
        final JsonNode node = this.getNode(tree).get(index).get(name);
        if (node == null) {
            return false;
        }
        final NodeType type = NodeType.getNodeType(node);
        if (type == expected) {
            return true;
        }
        report.error(this.LDOMsg(tree, bundle, key, index).put("expected", expected).putArgument("found", type));
        return false;
    }
    
    static {
        REQUIRED_LDO_PROPERTIES = ImmutableList.of("href", "rel");
        INSTANCE = new LinksSyntaxChecker();
    }
}
