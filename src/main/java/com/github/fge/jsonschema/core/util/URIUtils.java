// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.util;

import com.github.fge.jsonschema.core.ref.JsonRef;
import com.google.common.base.Optional;
import java.net.URISyntaxException;
import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.core.messages.JsonSchemaCoreMessageBundle;
import javax.annotation.Nullable;
import java.net.URI;
import com.google.common.base.Function;
import com.google.common.base.CharMatcher;
import com.github.fge.msgsimple.bundle.MessageBundle;

public final class URIUtils
{
    private static final MessageBundle BUNDLE;
    private static final CharMatcher ALPHA;
    private static final CharMatcher SCHEME_LEGAL;
    private static final Function<String, String> LOWERCASE;
    private static final Function<URI, URI> URI_NORMALIZER;
    private static final Function<URI, URI> SCHEMAURI_NORMALIZER;
    private static final ArgumentChecker<String> SCHEME_CHECKER;
    private static final ArgumentChecker<URI> PATHURI_CHECKER;
    private static final ArgumentChecker<URI> SCHEMAURI_CHECKER;
    
    public static Function<String, String> schemeNormalizer() {
        return URIUtils.LOWERCASE;
    }
    
    public static String normalizeScheme(@Nullable final String scheme) {
        return URIUtils.LOWERCASE.apply(scheme);
    }
    
    public static Function<URI, URI> uriNormalizer() {
        return URIUtils.URI_NORMALIZER;
    }
    
    public static URI normalizeURI(@Nullable final URI uri) {
        return URIUtils.URI_NORMALIZER.apply(uri);
    }
    
    public static Function<URI, URI> schemaURINormalizer() {
        return URIUtils.SCHEMAURI_NORMALIZER;
    }
    
    public static URI normalizeSchemaURI(@Nullable final URI uri) {
        return URIUtils.SCHEMAURI_NORMALIZER.apply(uri);
    }
    
    public static ArgumentChecker<String> schemeChecker() {
        return URIUtils.SCHEME_CHECKER;
    }
    
    public static void checkScheme(final String scheme) {
        URIUtils.SCHEME_CHECKER.check(scheme);
    }
    
    public static ArgumentChecker<URI> pathURIChecker() {
        return URIUtils.PATHURI_CHECKER;
    }
    
    public static void checkPathURI(final URI uri) {
        URIUtils.PATHURI_CHECKER.check(uri);
    }
    
    public static ArgumentChecker<URI> schemaURIChecker() {
        return URIUtils.SCHEMAURI_CHECKER;
    }
    
    public static void checkSchemaURI(final URI uri) {
        URIUtils.SCHEMAURI_CHECKER.check(uri);
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);
        ALPHA = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z')).precomputed();
        SCHEME_LEGAL = URIUtils.ALPHA.or(CharMatcher.inRange('0', '9')).or(CharMatcher.anyOf("+-.")).precomputed();
        LOWERCASE = new Function<String, String>() {
            @Nullable
            @Override
            public String apply(@Nullable final String input) {
                return (input == null) ? null : input.toLowerCase();
            }
        };
        URI_NORMALIZER = new Function<URI, URI>() {
            @Nullable
            @Override
            public URI apply(@Nullable final URI input) {
                if (input == null) {
                    return null;
                }
                final URI uri = input.normalize();
                final String scheme = uri.getScheme();
                final String host = uri.getHost();
                if (scheme == null && host == null) {
                    return uri;
                }
                if (uri.isOpaque()) {
                    try {
                        return new URI(URIUtils.LOWERCASE.apply(scheme), uri.getSchemeSpecificPart(), uri.getFragment());
                    }
                    catch (URISyntaxException e) {
                        throw new IllegalStateException("How did I get there??", e);
                    }
                }
                final String userinfo = uri.getUserInfo();
                final int port = uri.getPort();
                final String path = uri.getPath();
                final String query = uri.getQuery();
                final String fragment = uri.getFragment();
                try {
                    return new URI(URIUtils.LOWERCASE.apply(scheme), userinfo, URIUtils.LOWERCASE.apply(host), port, path, query, fragment);
                }
                catch (URISyntaxException e2) {
                    throw new IllegalStateException("How did I get there??", e2);
                }
            }
        };
        SCHEMAURI_NORMALIZER = new Function<URI, URI>() {
            @Nullable
            @Override
            public URI apply(@Nullable final URI input) {
                final URI uri = URIUtils.URI_NORMALIZER.apply(input);
                if (uri == null) {
                    return null;
                }
                try {
                    return new URI(uri.getScheme(), uri.getSchemeSpecificPart(), Optional.fromNullable(uri.getFragment()).or(""));
                }
                catch (URISyntaxException e) {
                    throw new RuntimeException("How did I get there??", e);
                }
            }
        };
        SCHEME_CHECKER = new ArgumentChecker<String>() {
            @Override
            public void check(final String argument) {
                final String errmsg = URIUtils$4.BUNDLE.printf("loadingCfg.illegalScheme", argument);
                if (argument.isEmpty()) {
                    throw new IllegalArgumentException(errmsg);
                }
                if (!URIUtils.ALPHA.matches(argument.charAt(0))) {
                    throw new IllegalArgumentException(errmsg);
                }
                if (!URIUtils.SCHEME_LEGAL.matchesAllOf(argument)) {
                    throw new IllegalArgumentException(errmsg);
                }
            }
        };
        PATHURI_CHECKER = new ArgumentChecker<URI>() {
            @Override
            public void check(final URI argument) {
                URIUtils$5.BUNDLE.checkArgumentPrintf(argument.isAbsolute(), "uriChecks.notAbsolute", argument);
                URIUtils$5.BUNDLE.checkArgumentPrintf(argument.getFragment() == null, "uriChecks.fragmentNotNull", argument);
                URIUtils$5.BUNDLE.checkArgumentPrintf(argument.getQuery() == null, "uriChecks.queryNotNull", argument);
                URIUtils$5.BUNDLE.checkArgumentPrintf(argument.getPath() != null, "uriChecks.noPath", argument);
                URIUtils$5.BUNDLE.checkArgumentPrintf(argument.getPath().endsWith("/"), "uriChecks.noEndingSlash", argument);
            }
        };
        SCHEMAURI_CHECKER = new ArgumentChecker<URI>() {
            @Override
            public void check(final URI argument) {
                URIUtils$6.BUNDLE.checkArgumentPrintf(argument.isAbsolute(), "uriChecks.notAbsolute", argument);
                final JsonRef ref = JsonRef.fromURI(argument);
                URIUtils$6.BUNDLE.checkArgumentPrintf(ref.isAbsolute(), "uriChecks.notAbsoluteRef", argument);
                URIUtils$6.BUNDLE.checkArgumentPrintf(!argument.getPath().endsWith("/"), "uriChecks.endingSlash", argument);
            }
        };
    }
}
