// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.examples;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.io.IOException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.load.configuration.LoadingConfigurationBuilder;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.core.load.configuration.LoadingConfiguration;

public final class Example10
{
    private static final String URI_BASE = "xxx://foo.bar/path/to/";
    
    public static void main(final String... args) throws IOException, ProcessingException {
        final LoadingConfigurationBuilder builder = LoadingConfiguration.newBuilder();
        JsonNode node = Utils.loadResource("/split/fstab.json");
        String uri = "xxx://foo.bar/path/to/fstab.json";
        builder.preloadSchema(uri, node);
        node = Utils.loadResource("/split/mntent.json");
        uri = "xxx://foo.bar/path/to/mntent.json";
        builder.preloadSchema(uri, node);
        final JsonSchemaFactory factory = JsonSchemaFactory.newBuilder().setLoadingConfiguration(builder.freeze()).freeze();
        final JsonSchema schema = factory.getJsonSchema("xxx://foo.bar/path/to/fstab.json");
        final JsonNode good = Utils.loadResource("/fstab-good.json");
        final JsonNode bad = Utils.loadResource("/fstab-bad.json");
        final JsonNode bad2 = Utils.loadResource("/fstab-bad2.json");
        ProcessingReport report = schema.validate(good);
        System.out.println(report);
        report = schema.validate(bad);
        System.out.println(report);
        report = schema.validate(bad2);
        System.out.println(report);
    }
}
