// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.core;

import java.util.Iterator;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;

public abstract class ObjectCodec extends TreeCodec implements Versioned
{
    @Override
    public Version version() {
        return Version.unknownVersion();
    }
    
    public abstract <T> T readValue(final JsonParser p0, final Class<T> p1) throws IOException, JsonProcessingException;
    
    public abstract <T> T readValue(final JsonParser p0, final TypeReference<?> p1) throws IOException, JsonProcessingException;
    
    public abstract <T> T readValue(final JsonParser p0, final ResolvedType p1) throws IOException, JsonProcessingException;
    
    public abstract <T> Iterator<T> readValues(final JsonParser p0, final Class<T> p1) throws IOException, JsonProcessingException;
    
    public abstract <T> Iterator<T> readValues(final JsonParser p0, final TypeReference<?> p1) throws IOException, JsonProcessingException;
    
    public abstract <T> Iterator<T> readValues(final JsonParser p0, final ResolvedType p1) throws IOException, JsonProcessingException;
    
    public abstract void writeValue(final JsonGenerator p0, final Object p1) throws IOException, JsonProcessingException;
    
    @Override
    public abstract <T extends TreeNode> T readTree(final JsonParser p0) throws IOException, JsonProcessingException;
    
    @Override
    public abstract void writeTree(final JsonGenerator p0, final TreeNode p1) throws IOException, JsonProcessingException;
    
    @Override
    public abstract TreeNode createObjectNode();
    
    @Override
    public abstract TreeNode createArrayNode();
    
    @Override
    public abstract JsonParser treeAsTokens(final TreeNode p0);
    
    public abstract <T> T treeToValue(final TreeNode p0, final Class<T> p1) throws JsonProcessingException;
    
    @Deprecated
    public JsonFactory getJsonFactory() {
        return this.getFactory();
    }
    
    public JsonFactory getFactory() {
        return this.getJsonFactory();
    }
}
