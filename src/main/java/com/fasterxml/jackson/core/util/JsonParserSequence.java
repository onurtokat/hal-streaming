// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonParser;

public class JsonParserSequence extends JsonParserDelegate
{
    protected final JsonParser[] _parsers;
    protected int _nextParser;
    
    protected JsonParserSequence(final JsonParser[] parsers) {
        super(parsers[0]);
        this._parsers = parsers;
        this._nextParser = 1;
    }
    
    public static JsonParserSequence createFlattened(final JsonParser first, final JsonParser second) {
        if (!(first instanceof JsonParserSequence) && !(second instanceof JsonParserSequence)) {
            return new JsonParserSequence(new JsonParser[] { first, second });
        }
        final ArrayList<JsonParser> p = new ArrayList<JsonParser>();
        if (first instanceof JsonParserSequence) {
            ((JsonParserSequence)first).addFlattenedActiveParsers(p);
        }
        else {
            p.add(first);
        }
        if (second instanceof JsonParserSequence) {
            ((JsonParserSequence)second).addFlattenedActiveParsers(p);
        }
        else {
            p.add(second);
        }
        return new JsonParserSequence(p.toArray(new JsonParser[p.size()]));
    }
    
    protected void addFlattenedActiveParsers(final List<JsonParser> result) {
        for (int i = this._nextParser - 1, len = this._parsers.length; i < len; ++i) {
            final JsonParser p = this._parsers[i];
            if (p instanceof JsonParserSequence) {
                ((JsonParserSequence)p).addFlattenedActiveParsers(result);
            }
            else {
                result.add(p);
            }
        }
    }
    
    @Override
    public void close() throws IOException {
        do {
            this.delegate.close();
        } while (this.switchToNext());
    }
    
    @Override
    public JsonToken nextToken() throws IOException, JsonParseException {
        JsonToken t = this.delegate.nextToken();
        if (t != null) {
            return t;
        }
        while (this.switchToNext()) {
            t = this.delegate.nextToken();
            if (t != null) {
                return t;
            }
        }
        return null;
    }
    
    public int containedParsersCount() {
        return this._parsers.length;
    }
    
    protected boolean switchToNext() {
        if (this._nextParser >= this._parsers.length) {
            return false;
        }
        this.delegate = this._parsers[this._nextParser++];
        return true;
    }
}
