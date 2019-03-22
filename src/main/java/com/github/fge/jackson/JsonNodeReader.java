// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jackson;

import javax.annotation.Nonnull;
import com.fasterxml.jackson.core.JsonLocation;
import com.github.fge.Builder;
import com.github.fge.msgsimple.bundle.PropertiesBundle;
import com.fasterxml.jackson.core.JsonParseException;
import java.io.Reader;
import java.io.IOException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.google.common.io.Closer;
import java.io.InputStream;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.github.fge.msgsimple.bundle.MessageBundle;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class JsonNodeReader
{
    private static final MessageBundle BUNDLE;
    private final ObjectReader reader;
    
    public JsonNodeReader(final ObjectMapper mapper) {
        this.reader = mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true).reader(JsonNode.class);
    }
    
    public JsonNodeReader() {
        this(JacksonUtils.newMapper());
    }
    
    public JsonNode fromInputStream(final InputStream in) throws IOException {
        final Closer closer = Closer.create();
        try {
            final JsonParser parser = closer.register(this.reader.getFactory().createParser(in));
            final MappingIterator<JsonNode> iterator = this.reader.readValues(parser);
            return readNode(closer.register(iterator));
        }
        finally {
            closer.close();
        }
    }
    
    public JsonNode fromReader(final Reader r) throws IOException {
        final Closer closer = Closer.create();
        try {
            final JsonParser parser = closer.register(this.reader.getFactory().createParser(r));
            final MappingIterator<JsonNode> iterator = this.reader.readValues(parser);
            return readNode(closer.register(iterator));
        }
        finally {
            closer.close();
        }
    }
    
    private static JsonNode readNode(final MappingIterator<JsonNode> iterator) throws IOException {
        final Object source = iterator.getParser().getInputSource();
        final JsonParseExceptionBuilder builder = new JsonParseExceptionBuilder(source);
        builder.setMessage(JsonNodeReader.BUNDLE.getMessage("read.noContent"));
        if (!iterator.hasNextValue()) {
            throw builder.build();
        }
        final JsonNode ret = iterator.nextValue();
        builder.setMessage(JsonNodeReader.BUNDLE.getMessage("read.trailingData")).setLocation(iterator.getCurrentLocation());
        try {
            if (iterator.hasNextValue()) {
                throw builder.build();
            }
        }
        catch (JsonParseException e) {
            throw builder.setLocation(e.getLocation()).build();
        }
        return ret;
    }
    
    static {
        BUNDLE = PropertiesBundle.forPath("/com/github/fge/jackson/jsonNodeReader");
    }
    
    private static final class JsonParseExceptionBuilder implements Builder<JsonParseException>
    {
        private String message;
        private JsonLocation location;
        
        private JsonParseExceptionBuilder(@Nonnull final Object source) {
            this.message = "";
            JsonNodeReader.BUNDLE.checkNotNull(source, "read.nullArgument");
            this.location = new JsonLocation(source, 0L, 1, 1);
        }
        
        private JsonParseExceptionBuilder setMessage(@Nonnull final String message) {
            this.message = JsonNodeReader.BUNDLE.checkNotNull(message, "read.nullArgument");
            return this;
        }
        
        private JsonParseExceptionBuilder setLocation(@Nonnull final JsonLocation location) {
            this.location = JsonNodeReader.BUNDLE.checkNotNull(location, "read.nullArgument");
            return this;
        }
        
        @Override
        public JsonParseException build() {
            return new JsonParseException(this.message, this.location);
        }
    }
}
