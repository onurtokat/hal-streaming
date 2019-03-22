// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.examples;

import java.io.IOException;
import com.github.fge.jackson.JsonLoader;
import com.fasterxml.jackson.databind.JsonNode;

public final class Utils
{
    private static final String PKGBASE;
    
    public static JsonNode loadResource(final String name) throws IOException {
        return JsonLoader.fromResource(Utils.PKGBASE + name);
    }
    
    static {
        final String pkgName = Utils.class.getPackage().getName();
        PKGBASE = '/' + pkgName.replace(".", "/");
    }
}
