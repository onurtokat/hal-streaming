// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.vars.values;

import com.github.fge.uritemplate.render.MapRenderer;
import com.github.fge.uritemplate.render.ListRenderer;
import com.github.fge.uritemplate.render.StringRenderer;
import com.github.fge.uritemplate.render.ValueRenderer;
import com.github.fge.uritemplate.expression.ExpressionType;

public enum ValueType
{
    SCALAR("scalar") {
        @Override
        public ValueRenderer selectRenderer(final ExpressionType type) {
            return new StringRenderer(type);
        }
    }, 
    ARRAY("list") {
        @Override
        public ValueRenderer selectRenderer(final ExpressionType type) {
            return new ListRenderer(type);
        }
    }, 
    MAP("map") {
        @Override
        public ValueRenderer selectRenderer(final ExpressionType type) {
            return new MapRenderer(type);
        }
    };
    
    private final String name;
    
    private ValueType(final String name) {
        this.name = name;
    }
    
    public abstract ValueRenderer selectRenderer(final ExpressionType p0);
    
    @Override
    public String toString() {
        return this.name;
    }
}
