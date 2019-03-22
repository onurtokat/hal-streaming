// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jackson.jsonpointer;

import com.github.fge.msgsimple.bundle.PropertiesBundle;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.load.MessageBundleLoader;

public final class JsonPointerMessages implements MessageBundleLoader
{
    @Override
    public MessageBundle getBundle() {
        return PropertiesBundle.forPath("com/github/fge/jackson/jsonpointer");
    }
}
