// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.processors.validation;

import com.github.fge.jsonschema.core.report.MessageProvider;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.fge.jackson.JacksonUtils;
import java.util.List;
import java.util.Collections;
import com.google.common.collect.Lists;
import com.github.fge.jsonschema.core.tree.JsonTree;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Iterator;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.keyword.validator.KeywordValidator;
import com.github.fge.jsonschema.core.exceptions.InvalidSchemaException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.processors.data.ValidatorList;
import com.github.fge.jsonschema.processors.data.SchemaContext;
import com.github.fge.msgsimple.bundle.MessageBundle;
import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.ParametersAreNonnullByDefault;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.core.processing.Processor;

@ParametersAreNonnullByDefault
@NotThreadSafe
final class InstanceValidator implements Processor<FullData, FullData>
{
    private final MessageBundle syntaxMessages;
    private final MessageBundle validationMessages;
    private final Processor<SchemaContext, ValidatorList> keywordBuilder;
    private final ValidationStack stack;
    
    InstanceValidator(final MessageBundle syntaxMessages, final MessageBundle validationMessages, final Processor<SchemaContext, ValidatorList> keywordBuilder) {
        this.syntaxMessages = syntaxMessages;
        this.validationMessages = validationMessages;
        this.keywordBuilder = keywordBuilder;
        final String errmsg = validationMessages.getMessage("err.common.validationLoop");
        this.stack = new ValidationStack(errmsg);
    }
    
    @Override
    public FullData process(final ProcessingReport report, final FullData input) throws ProcessingException {
        this.stack.push(input);
        final SchemaContext context = new SchemaContext(input);
        final ValidatorList fullContext = this.keywordBuilder.process(report, context);
        if (fullContext == null) {
            final ProcessingMessage message = this.collectSyntaxErrors(report);
            throw new InvalidSchemaException(message);
        }
        final SchemaContext newContext = fullContext.getContext();
        final FullData data = new FullData(newContext.getSchema(), input.getInstance(), input.isDeepCheck());
        for (final KeywordValidator validator : fullContext) {
            validator.validate(this, report, this.validationMessages, data);
        }
        if (!report.isSuccess() && !data.isDeepCheck()) {
            this.stack.pop();
            return input;
        }
        final JsonNode node = data.getInstance().getNode();
        if (node.isContainerNode()) {
            if (node.isArray()) {
                this.processArray(report, data);
            }
            else {
                this.processObject(report, data);
            }
        }
        this.stack.pop();
        return input;
    }
    
    @Override
    public String toString() {
        return "instance validator";
    }
    
    private void processArray(final ProcessingReport report, final FullData input) throws ProcessingException {
        final SchemaTree tree = input.getSchema();
        final JsonTree instance = input.getInstance();
        final JsonNode schema = tree.getNode();
        final JsonNode node = instance.getNode();
        final JsonNode digest = ArraySchemaDigester.getInstance().digest(schema);
        final ArraySchemaSelector selector = new ArraySchemaSelector(digest);
        for (int size = node.size(), index = 0; index < size; ++index) {
            final JsonTree newInstance = instance.append(JsonPointer.of(index, new Object[0]));
            FullData data = input.withInstance(newInstance);
            for (final JsonPointer ptr : selector.selectSchemas(index)) {
                data = data.withSchema(tree.append(ptr));
                this.process(report, data);
            }
        }
    }
    
    private void processObject(final ProcessingReport report, final FullData input) throws ProcessingException {
        final SchemaTree tree = input.getSchema();
        final JsonTree instance = input.getInstance();
        final JsonNode schema = tree.getNode();
        final JsonNode node = instance.getNode();
        final JsonNode digest = ObjectSchemaDigester.getInstance().digest(schema);
        final ObjectSchemaSelector selector = new ObjectSchemaSelector(digest);
        final List<String> fields = (List<String>)Lists.newArrayList((Iterator<?>)node.fieldNames());
        Collections.sort(fields);
        for (final String field : fields) {
            final JsonTree newInstance = instance.append(JsonPointer.of(field, new Object[0]));
            FullData data = input.withInstance(newInstance);
            for (final JsonPointer ptr : selector.selectSchemas(field)) {
                data = data.withSchema(tree.append(ptr));
                this.process(report, data);
            }
        }
    }
    
    private ProcessingMessage collectSyntaxErrors(final ProcessingReport report) {
        final String msg = this.syntaxMessages.getMessage("core.invalidSchema");
        final ArrayNode arrayNode = JacksonUtils.nodeFactory().arrayNode();
        for (final ProcessingMessage message : report) {
            final JsonNode node = message.asJson();
            if ("syntax".equals(node.path("domain").asText())) {
                arrayNode.add(node);
            }
        }
        final StringBuilder sb = new StringBuilder(msg);
        sb.append("\nSyntax errors:\n");
        sb.append(JacksonUtils.prettyPrint(arrayNode));
        return new ProcessingMessage().setMessage(sb.toString());
    }
}
