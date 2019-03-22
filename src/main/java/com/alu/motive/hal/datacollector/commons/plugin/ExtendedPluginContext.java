// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.commons.plugin;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ExtendedPluginContext
{
    private String context;
    private String baseContextPath;
    private ExtendedContextType paramType;
    private String paramName;
    private String paramValue;
    
    public ExtendedPluginContext(final String baseContext, final ExtendedContextType type, final String paramName, final String paramValue) {
        this.paramType = null;
        this.paramName = null;
        this.paramValue = null;
        this.baseContextPath = baseContext;
        this.paramType = type;
        this.paramName = paramName;
        this.paramValue = paramValue;
        this.context = this.generateContext();
    }
    
    public String getContext() {
        return this.context;
    }
    
    public String getBaseContextPath() {
        return this.baseContextPath;
    }
    
    public ExtendedContextType getParamType() {
        return this.paramType;
    }
    
    public String getParamName() {
        return this.paramName;
    }
    
    public String getParamValue() {
        return this.paramValue;
    }
    
    private String generateContext() {
        if (ExtendedContextType.HEADER.equals(this.getParamType())) {
            return this.getBaseContextPath() + "_h_" + this.getParamName() + "=" + this.getParamValue();
        }
        return this.getBaseContextPath() + "_p_" + this.getParamName() + "=" + this.getParamValue();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).append(this.getContext()).toHashCode();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == null || !(obj instanceof ExtendedPluginContext)) {
            return false;
        }
        final ExtendedPluginContext ctx = (ExtendedPluginContext)obj;
        return this.getContext().equals(ctx.getContext());
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("context");
        sb.append("\n\t\t param type = " + this.getParamType());
        sb.append("\n\t\t param name = " + this.getParamName());
        sb.append("\n\t\t param value = " + this.getParamValue());
        sb.append("\n\t\t base context path = " + this.getBaseContextPath());
        sb.append("\n");
        return sb.toString();
    }
}
