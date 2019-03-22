// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.core.JsonParser;

public class StackTraceElementDeserializer extends StdScalarDeserializer<StackTraceElement>
{
    private static final long serialVersionUID = 1L;
    
    public StackTraceElementDeserializer() {
        super(StackTraceElement.class);
    }
    
    @Override
    public StackTraceElement deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            String className = "";
            String methodName = "";
            String fileName = "";
            int lineNumber = -1;
            while ((t = jp.nextValue()) != JsonToken.END_OBJECT) {
                final String propName = jp.getCurrentName();
                if ("className".equals(propName)) {
                    className = jp.getText();
                }
                else if ("fileName".equals(propName)) {
                    fileName = jp.getText();
                }
                else if ("lineNumber".equals(propName)) {
                    if (!t.isNumeric()) {
                        throw JsonMappingException.from(jp, "Non-numeric token (" + t + ") for property 'lineNumber'");
                    }
                    lineNumber = jp.getIntValue();
                }
                else if ("methodName".equals(propName)) {
                    methodName = jp.getText();
                }
                else {
                    if ("nativeMethod".equals(propName)) {
                        continue;
                    }
                    this.handleUnknownProperty(jp, ctxt, this._valueClass, propName);
                }
            }
            return new StackTraceElement(className, methodName, fileName, lineNumber);
        }
        if (t != JsonToken.START_ARRAY || !ctxt.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
            throw ctxt.mappingException(this._valueClass, t);
        }
        jp.nextToken();
        final StackTraceElement value = this.deserialize(jp, ctxt);
        if (jp.nextToken() != JsonToken.END_ARRAY) {
            throw ctxt.wrongTokenException(jp, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'java.lang.StackTraceElement' value but there was more than a single value in the array");
        }
        return value;
    }
}
