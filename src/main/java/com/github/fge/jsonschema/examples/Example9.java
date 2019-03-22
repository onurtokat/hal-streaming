// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.examples;

import com.google.common.collect.Lists;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.core.processing.Processor;
import com.google.common.collect.ImmutableList;
import java.util.List;
import com.github.fge.jsonschema.keyword.validator.AbstractKeywordValidator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.Comparator;
import com.github.fge.jsonschema.keyword.digest.Digester;
import com.github.fge.jsonschema.keyword.digest.AbstractDigester;
import java.util.Set;
import java.math.BigInteger;
import com.google.common.collect.Sets;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.util.Collection;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.SyntaxChecker;
import com.github.fge.jsonschema.core.keyword.syntax.checkers.AbstractSyntaxChecker;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.io.IOException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.source.MessageSource;
import com.github.fge.jsonschema.library.Library;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.messages.JsonSchemaValidationBundle;
import com.github.fge.msgsimple.source.MapMessageSource;
import com.github.fge.jsonschema.library.DraftV4Library;
import com.github.fge.jsonschema.keyword.validator.KeywordValidator;
import com.github.fge.jsonschema.library.Keyword;

public final class Example9
{
    public static void main(final String... args) throws IOException, ProcessingException {
        final JsonNode customSchema = Utils.loadResource("/custom-keyword.json");
        final JsonNode good = Utils.loadResource("/custom-keyword-good.json");
        final JsonNode bad = Utils.loadResource("/custom-keyword-bad.json");
        final Keyword keyword = Keyword.newBuilder("divisors").withSyntaxChecker(DivisorsSyntaxChecker.getInstance()).withDigester(DivisorsDigesters.getInstance()).withValidatorClass(DivisorsKeywordValidator.class).freeze();
        final Library library = DraftV4Library.get().thaw().addKeyword(keyword).freeze();
        final String key = "missingDivisors";
        final String value = "integer value is not a multiple of all divisors";
        final MessageSource source = MapMessageSource.newBuilder().put("missingDivisors", "integer value is not a multiple of all divisors").build();
        final MessageBundle bundle = MessageBundles.getBundle(JsonSchemaValidationBundle.class).thaw().appendSource(source).freeze();
        final ValidationConfiguration cfg = ValidationConfiguration.newBuilder().setDefaultLibrary("http://my.site/myschema#", library).setValidationMessages(bundle).freeze();
        final JsonSchemaFactory factory = JsonSchemaFactory.newBuilder().setValidationConfiguration(cfg).freeze();
        final JsonSchema schema = factory.getJsonSchema(customSchema);
        ProcessingReport report = schema.validate(good);
        System.out.println(report);
        report = schema.validate(bad);
        System.out.println(report);
    }
    
    private static final class DivisorsSyntaxChecker extends AbstractSyntaxChecker
    {
        private static final SyntaxChecker INSTANCE;
        
        public static SyntaxChecker getInstance() {
            return DivisorsSyntaxChecker.INSTANCE;
        }
        
        private DivisorsSyntaxChecker() {
            super("divisors", NodeType.ARRAY, new NodeType[0]);
        }
        
        @Override
        protected void checkValue(final Collection<JsonPointer> pointers, final MessageBundle bundle, final ProcessingReport report, final SchemaTree tree) throws ProcessingException {
            final JsonNode node = this.getNode(tree);
            final int size = node.size();
            if (size == 0) {
                report.error(this.newMsg(tree, bundle, "emptyArray"));
                return;
            }
            boolean uniqueItems = true;
            final Set<JsonNode> set = (Set<JsonNode>)Sets.newHashSet();
            for (int index = 0; index < size; ++index) {
                final JsonNode element = node.get(index);
                final NodeType type = NodeType.getNodeType(element);
                if (type != NodeType.INTEGER) {
                    report.error(this.newMsg(tree, bundle, "incorrectElementType").put("expected", NodeType.INTEGER).put("found", type));
                }
                else if (element.bigIntegerValue().compareTo(BigInteger.ONE) < 0) {
                    report.error(this.newMsg(tree, bundle, "integerIsNegative").put("value", element));
                }
                uniqueItems = set.add(element);
            }
            if (!uniqueItems) {
                report.error(this.newMsg(tree, bundle, "elementsNotUnique"));
            }
        }
        
        static {
            INSTANCE = new DivisorsSyntaxChecker();
        }
    }
    
    private static final class DivisorsDigesters extends AbstractDigester
    {
        private static final Digester INSTANCE;
        private static final Comparator<JsonNode> COMPARATOR;
        
        public static Digester getInstance() {
            return DivisorsDigesters.INSTANCE;
        }
        
        private DivisorsDigesters() {
            super("divisors", NodeType.INTEGER, new NodeType[0]);
        }
        
        @Override
        public JsonNode digest(final JsonNode schema) {
            final SortedSet<JsonNode> set = (SortedSet<JsonNode>)Sets.newTreeSet((Comparator<? super Object>)DivisorsDigesters.COMPARATOR);
            for (final JsonNode element : schema.get(this.keyword)) {
                set.add(element);
            }
            return DivisorsDigesters.FACTORY.arrayNode().addAll(set);
        }
        
        static {
            INSTANCE = new DivisorsDigesters();
            COMPARATOR = new Comparator<JsonNode>() {
                @Override
                public int compare(final JsonNode o1, final JsonNode o2) {
                    return o1.bigIntegerValue().compareTo(o2.bigIntegerValue());
                }
            };
        }
    }
    
    public static final class DivisorsKeywordValidator extends AbstractKeywordValidator
    {
        private final List<BigInteger> divisors;
        
        public DivisorsKeywordValidator(final JsonNode digest) {
            super("divisors");
            final ImmutableList.Builder<BigInteger> list = ImmutableList.builder();
            for (final JsonNode element : digest) {
                list.add(element.bigIntegerValue());
            }
            this.divisors = list.build();
        }
        
        @Override
        public void validate(final Processor<FullData, FullData> processor, final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
            final BigInteger value = data.getInstance().getNode().bigIntegerValue();
            final List<BigInteger> failed = (List<BigInteger>)Lists.newArrayList();
            for (final BigInteger divisor : this.divisors) {
                if (!value.mod(divisor).equals(BigInteger.ZERO)) {
                    failed.add(divisor);
                }
            }
            if (failed.isEmpty()) {
                return;
            }
            report.error(this.newMsg(data, bundle, "missingDivisors").put("divisors", (Iterable<BigInteger>)this.divisors).put("failed", (Iterable<BigInteger>)failed));
        }
        
        @Override
        public String toString() {
            return "divisors: " + this.divisors;
        }
    }
}
