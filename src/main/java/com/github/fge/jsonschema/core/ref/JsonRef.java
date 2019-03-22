// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.ref;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.core.messages.JsonSchemaCoreMessageBundle;
import com.github.fge.jsonschema.core.exceptions.JsonReferenceException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.util.URIUtils;
import java.net.URISyntaxException;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.google.common.base.Optional;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import java.net.URI;
import com.github.fge.msgsimple.bundle.MessageBundle;
import javax.annotation.concurrent.Immutable;

@Immutable
public abstract class JsonRef
{
    private static final MessageBundle BUNDLE;
    private static final URI EMPTY_URI;
    protected static final URI HASHONLY_URI;
    protected final boolean legal;
    protected final URI uri;
    protected final URI locator;
    protected final JsonPointer pointer;
    private final String asString;
    private final int hashCode;
    
    protected JsonRef(final URI uri) {
        final String scheme = uri.getScheme();
        final String ssp = uri.getSchemeSpecificPart();
        final String fragment = Optional.fromNullable(uri.getFragment()).or("");
        boolean isLegal = true;
        JsonPointer ptr;
        try {
            ptr = (fragment.isEmpty() ? JsonPointer.empty() : new JsonPointer(fragment));
        }
        catch (JsonPointerException ignored) {
            ptr = null;
            isLegal = false;
        }
        this.legal = isLegal;
        this.pointer = ptr;
        try {
            this.uri = new URI(scheme, ssp, fragment);
            this.locator = new URI(scheme, ssp, "");
            this.asString = this.uri.toString();
            this.hashCode = this.asString.hashCode();
        }
        catch (URISyntaxException e) {
            throw new RuntimeException("WTF??", e);
        }
    }
    
    public static JsonRef fromURI(final URI uri) {
        JsonRef.BUNDLE.checkNotNull(uri, "jsonRef.nullURI");
        final URI normalized = URIUtils.normalizeURI(uri);
        if (JsonRef.HASHONLY_URI.equals(normalized) || JsonRef.EMPTY_URI.equals(normalized)) {
            return EmptyJsonRef.getInstance();
        }
        return "jar".equals(normalized.getScheme()) ? new JarJsonRef(normalized) : new HierarchicalJsonRef(normalized);
    }
    
    public static JsonRef fromString(final String s) throws JsonReferenceException {
        JsonRef.BUNDLE.checkNotNull(s, "jsonRef.nullInput");
        try {
            return fromURI(new URI(s));
        }
        catch (URISyntaxException e) {
            throw new JsonReferenceException(new ProcessingMessage().setMessage(JsonRef.BUNDLE.getMessage("jsonRef.invalidURI")).putArgument("input", s), e);
        }
    }
    
    public static JsonRef emptyRef() {
        return EmptyJsonRef.getInstance();
    }
    
    public final URI toURI() {
        return this.uri;
    }
    
    public abstract boolean isAbsolute();
    
    public abstract JsonRef resolve(final JsonRef p0);
    
    public final URI getLocator() {
        return this.locator;
    }
    
    public final boolean isLegal() {
        return this.legal;
    }
    
    public final JsonPointer getPointer() {
        return this.pointer;
    }
    
    public final boolean contains(final JsonRef other) {
        return this.locator.equals(other.locator);
    }
    
    @Override
    public final int hashCode() {
        return this.hashCode;
    }
    
    @Override
    public final boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof JsonRef)) {
            return false;
        }
        final JsonRef that = (JsonRef)obj;
        return this.asString.equals(that.asString);
    }
    
    @Override
    public final String toString() {
        return this.asString;
    }
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);
        EMPTY_URI = URI.create("");
        HASHONLY_URI = URI.create("#");
    }
}
