// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.load.uri;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.core.messages.JsonSchemaCoreMessageBundle;
import com.github.fge.jsonschema.core.util.URIUtils;
import com.github.fge.msgsimple.bundle.MessageBundle;
import java.net.URI;
import com.github.fge.jsonschema.core.util.Registry;

final class PathRedirectRegistry extends Registry<URI, URI>
{
    private static final MessageBundle BUNDLE;
    
    PathRedirectRegistry() {
        super(URIUtils.uriNormalizer(), URIUtils.pathURIChecker(), URIUtils.uriNormalizer(), URIUtils.pathURIChecker());
    }
    
    @Override
    protected void checkEntry(final URI key, final URI value) {
        PathRedirectRegistry.BUNDLE.checkArgumentFormat(!key.equals(value), "pathRedirect.selfRedirect", key);
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);
    }
}
