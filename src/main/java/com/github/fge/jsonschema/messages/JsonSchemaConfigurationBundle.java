// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.messages;

import com.github.fge.msgsimple.bundle.PropertiesBundle;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.load.MessageBundleLoader;

public final class JsonSchemaConfigurationBundle implements MessageBundleLoader
{
    private static final String PATH = "com/github/fge/jsonschema/validator/configuration.properties";
    
    @Override
    public MessageBundle getBundle() {
        return PropertiesBundle.forPath("com/github/fge/jsonschema/validator/configuration.properties");
    }
}
