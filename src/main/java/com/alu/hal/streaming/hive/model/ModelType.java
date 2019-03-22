// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import java.util.Arrays;
import java.io.Serializable;

public enum ModelType implements Serializable
{
    TR98("TR-98"), 
    TR181("TR-181");
    
    private final String type;
    
    private ModelType(final String type) {
        this.type = type;
    }
    
    public static ModelType findModel(final String type) {
        final IllegalStateException ex;
        return Arrays.stream(values()).filter(modelType -> modelType.type.equals(type)).findFirst().orElseThrow(() -> {
            new IllegalStateException(String.format("Unsupported modelType %s.", type));
            return ex;
        });
    }
}
