// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.examples;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.io.IOException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.core.load.configuration.LoadingConfiguration;
import com.github.fge.jsonschema.core.load.uri.URITranslatorConfiguration;

public final class Example5
{
    private static final String NAMESPACE = "resource:/com/github/fge/jsonschema/examples/split/";
    
    public static void main(final String... args) throws IOException, ProcessingException {
        final JsonNode good = Utils.loadResource("/fstab-good.json");
        final JsonNode bad = Utils.loadResource("/fstab-bad.json");
        final JsonNode bad2 = Utils.loadResource("/fstab-bad2.json");
        final URITranslatorConfiguration translatorCfg = URITranslatorConfiguration.newBuilder().setNamespace("resource:/com/github/fge/jsonschema/examples/split/").freeze();
        final LoadingConfiguration cfg = LoadingConfiguration.newBuilder().setURITranslatorConfiguration(translatorCfg).freeze();
        final JsonSchemaFactory factory = JsonSchemaFactory.newBuilder().setLoadingConfiguration(cfg).freeze();
        final JsonSchema schema = factory.getJsonSchema("fstab.json");
        ProcessingReport report = schema.validate(good);
        System.out.println(report);
        report = schema.validate(bad);
        System.out.println(report);
        report = schema.validate(bad2);
        System.out.println(report);
    }
}
