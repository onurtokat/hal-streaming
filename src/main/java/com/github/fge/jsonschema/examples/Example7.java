// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.examples;

import java.io.InputStream;
import java.net.URI;
import com.github.fge.jsonschema.core.load.download.URIDownloader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.io.IOException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.core.load.configuration.LoadingConfiguration;

public final class Example7
{
    public static void main(final String... args) throws IOException, ProcessingException {
        final JsonNode good = Utils.loadResource("/fstab-good.json");
        final JsonNode bad = Utils.loadResource("/fstab-bad.json");
        final JsonNode bad2 = Utils.loadResource("/fstab-bad2.json");
        final LoadingConfiguration cfg = LoadingConfiguration.newBuilder().addScheme("foobar", CustomDownloader.getInstance()).freeze();
        final JsonSchemaFactory factory = JsonSchemaFactory.newBuilder().setLoadingConfiguration(cfg).freeze();
        final JsonSchema schema = factory.getJsonSchema("foobar:/fstab.json#");
        ProcessingReport report = schema.validate(good);
        System.out.println(report);
        report = schema.validate(bad);
        System.out.println(report);
        report = schema.validate(bad2);
        System.out.println(report);
    }
    
    private static final class CustomDownloader implements URIDownloader
    {
        private static final String PREFIX;
        private static final URIDownloader INSTANCE;
        
        public static URIDownloader getInstance() {
            return CustomDownloader.INSTANCE;
        }
        
        @Override
        public InputStream fetch(final URI source) throws IOException {
            final String path = CustomDownloader.PREFIX + source.getPath();
            final InputStream ret = this.getClass().getResourceAsStream(path);
            if (ret == null) {
                throw new IOException("resource " + path + " not found");
            }
            return ret;
        }
        
        static {
            INSTANCE = new CustomDownloader();
            final String pkgname = CustomDownloader.class.getPackage().getName();
            PREFIX = '/' + pkgname.replace(".", "/");
        }
    }
}
