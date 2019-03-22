// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector;

import com.google.gson.JsonParseException;
import java.io.Reader;
import com.google.gson.stream.JsonReader;
import java.io.StringReader;
import org.joda.time.LocalDate;
import org.joda.time.DateTime;
import java.lang.reflect.Type;
import java.util.Date;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class JSON
{
    private ApiClient apiClient;
    private Gson gson;
    
    public JSON(final ApiClient apiClient) {
        this.apiClient = apiClient;
        this.gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateAdapter(apiClient)).registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter()).registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter()).create();
    }
    
    public Gson getGson() {
        return this.gson;
    }
    
    public void setGson(final Gson gson) {
        this.gson = gson;
    }
    
    public String serialize(final Object obj) {
        return this.gson.toJson(obj);
    }
    
    public <T> T deserialize(final String body, final Type returnType) {
        try {
            if (this.apiClient.isLenientOnJson()) {
                final JsonReader jsonReader = new JsonReader(new StringReader(body));
                jsonReader.setLenient(true);
                return this.gson.fromJson(jsonReader, returnType);
            }
            return this.gson.fromJson(body, returnType);
        }
        catch (JsonParseException e) {
            if (returnType.equals(String.class)) {
                return (T)body;
            }
            if (returnType.equals(Date.class)) {
                return (T)this.apiClient.parseDateOrDatetime(body);
            }
            throw e;
        }
    }
}
