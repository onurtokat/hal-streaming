// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.main;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.messages.JsonSchemaConfigurationBundle;
import com.github.fge.Frozen;
import com.github.fge.jsonschema.core.report.ListReportProvider;
import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.core.load.configuration.LoadingConfiguration;
import com.github.fge.jsonschema.core.report.ReportProvider;
import com.github.fge.msgsimple.bundle.MessageBundle;
import javax.annotation.concurrent.NotThreadSafe;
import com.github.fge.Thawed;

@NotThreadSafe
public final class JsonSchemaFactoryBuilder implements Thawed<JsonSchemaFactory>
{
    private static final MessageBundle BUNDLE;
    ReportProvider reportProvider;
    LoadingConfiguration loadingCfg;
    ValidationConfiguration validationCfg;
    
    JsonSchemaFactoryBuilder() {
        this.reportProvider = new ListReportProvider(LogLevel.INFO, LogLevel.FATAL);
        this.loadingCfg = LoadingConfiguration.byDefault();
        this.validationCfg = ValidationConfiguration.byDefault();
    }
    
    JsonSchemaFactoryBuilder(final JsonSchemaFactory factory) {
        this.reportProvider = factory.reportProvider;
        this.loadingCfg = factory.loadingCfg;
        this.validationCfg = factory.validationCfg;
    }
    
    public JsonSchemaFactoryBuilder setReportProvider(final ReportProvider reportProvider) {
        JsonSchemaFactoryBuilder.BUNDLE.checkNotNull(reportProvider, "nullReportProvider");
        this.reportProvider = reportProvider;
        return this;
    }
    
    public JsonSchemaFactoryBuilder setLoadingConfiguration(final LoadingConfiguration loadingCfg) {
        JsonSchemaFactoryBuilder.BUNDLE.checkNotNull(loadingCfg, "nullLoadingCfg");
        this.loadingCfg = loadingCfg;
        return this;
    }
    
    public JsonSchemaFactoryBuilder setValidationConfiguration(final ValidationConfiguration validationCfg) {
        JsonSchemaFactoryBuilder.BUNDLE.checkNotNull(validationCfg, "nullValidationCfg");
        this.validationCfg = validationCfg;
        return this;
    }
    
    @Override
    public JsonSchemaFactory freeze() {
        return new JsonSchemaFactory(this);
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaConfigurationBundle.class);
    }
}
