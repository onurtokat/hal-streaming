// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.processors.format;

import com.github.fge.jsonschema.core.report.MessageProvider;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.util.List;
import com.github.fge.jackson.NodeType;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.processors.data.SchemaContext;
import com.github.fge.jsonschema.keyword.validator.KeywordValidator;
import java.util.Collection;
import com.google.common.collect.Lists;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.messages.JsonSchemaValidationBundle;
import com.github.fge.jsonschema.core.util.Dictionary;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.library.Library;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.jsonschema.format.FormatAttribute;
import java.util.Map;
import com.github.fge.jsonschema.processors.data.ValidatorList;
import com.github.fge.jsonschema.core.processing.Processor;

public final class FormatProcessor implements Processor<ValidatorList, ValidatorList>
{
    private final Map<String, FormatAttribute> attributes;
    private final MessageBundle bundle;
    
    public FormatProcessor(final Library library, final ValidationConfiguration cfg) {
        this.attributes = library.getFormatAttributes().entries();
        this.bundle = cfg.getValidationMessages();
    }
    
    FormatProcessor(final Dictionary<FormatAttribute> dict) {
        this.attributes = dict.entries();
        this.bundle = MessageBundles.getBundle(JsonSchemaValidationBundle.class);
    }
    
    @Override
    public ValidatorList process(final ProcessingReport report, final ValidatorList input) throws ProcessingException {
        final SchemaContext context = input.getContext();
        final JsonNode node = context.getSchema().getNode().get("format");
        if (node == null) {
            return input;
        }
        final String fmt = node.textValue();
        final FormatAttribute attr = this.attributes.get(fmt);
        if (attr == null) {
            report.warn(input.newMessage().put("domain", "validation").put("keyword", "format").setMessage(this.bundle.getMessage("warn.format.notSupported")).putArgument("attribute", fmt));
            return input;
        }
        final NodeType type = context.getInstanceType();
        if (!attr.supportedTypes().contains(type)) {
            return input;
        }
        final List<KeywordValidator> validators = (List<KeywordValidator>)Lists.newArrayList((Iterable<?>)input);
        validators.add(formatValidator(attr));
        return new ValidatorList(context, validators);
    }
    
    private static KeywordValidator formatValidator(final FormatAttribute attr) {
        return new KeywordValidator() {
            @Override
            public void validate(final Processor<FullData, FullData> processor, final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
                attr.validate(report, bundle, data);
            }
        };
    }
    
    @Override
    public String toString() {
        return "format attribute handler";
    }
}
