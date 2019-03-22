// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector;

import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonNull;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonDeserializer;
import java.util.Date;
import com.google.gson.JsonSerializer;

class DateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date>
{
    private final ApiClient apiClient;
    
    public DateAdapter(final ApiClient apiClient) {
        this.apiClient = apiClient;
    }
    
    @Override
    public JsonElement serialize(final Date src, final Type typeOfSrc, final JsonSerializationContext context) {
        if (src == null) {
            return JsonNull.INSTANCE;
        }
        return new JsonPrimitive(this.apiClient.formatDatetime(src));
    }
    
    @Override
    public Date deserialize(final JsonElement json, final Type date, final JsonDeserializationContext context) throws JsonParseException {
        final String str = json.getAsJsonPrimitive().getAsString();
        try {
            return this.apiClient.parseDateOrDatetime(str);
        }
        catch (RuntimeException e) {
            throw new JsonParseException(e);
        }
    }
}
