// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.examples;

import java.util.UUID;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.format.AbstractFormatAttribute;
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

public final class Example8
{
    public static void main(final String... args) throws IOException, ProcessingException {
        final JsonNode customSchema = Utils.loadResource("/custom-fmt.json");
        final JsonNode good = Utils.loadResource("/custom-fmt-good.json");
        final JsonNode bad = Utils.loadResource("/custom-fmt-bad.json");
        final Library library = DraftV4Library.get().thaw().addFormatAttribute("uuid", UUIDFormatAttribute.getInstance()).freeze();
        final String key = "invalidUUID";
        final String value = "input is not a valid UUID";
        final MessageSource source = MapMessageSource.newBuilder().put("invalidUUID", "input is not a valid UUID").build();
        final MessageBundle bundle = MessageBundles.getBundle(JsonSchemaValidationBundle.class).thaw().appendSource(source).freeze();
        final ValidationConfiguration cfg = ValidationConfiguration.newBuilder().setDefaultLibrary("http://my.site/myschema#", library).setValidationMessages(bundle).freeze();
        final JsonSchemaFactory factory = JsonSchemaFactory.newBuilder().setValidationConfiguration(cfg).freeze();
        final JsonSchema schema = factory.getJsonSchema(customSchema);
        ProcessingReport report = schema.validate(good);
        System.out.println(report);
        report = schema.validate(bad);
        System.out.println(report);
    }
    
    private static final class UUIDFormatAttribute extends AbstractFormatAttribute
    {
        private static final FormatAttribute INSTANCE;
        
        private UUIDFormatAttribute() {
            super("uuid", NodeType.STRING, new NodeType[0]);
        }
        
        public static FormatAttribute getInstance() {
            return UUIDFormatAttribute.INSTANCE;
        }
        
        @Override
        public void validate(final ProcessingReport report, final MessageBundle bundle, final FullData data) throws ProcessingException {
            final String value = data.getInstance().getNode().textValue();
            try {
                UUID.fromString(value);
            }
            catch (IllegalArgumentException ignored) {
                report.error(this.newMsg(data, bundle, "invalidUUID").put("input", value));
            }
        }
        
        static {
            INSTANCE = new UUIDFormatAttribute();
        }
    }
}
