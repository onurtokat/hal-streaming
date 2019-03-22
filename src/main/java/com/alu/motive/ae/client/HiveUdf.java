// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client;

public class HiveUdf
{
    private String functionName;
    private String implClass;
    private String jarLocation;
    
    public HiveUdf(final String functionName, final String implClass, final String jarLocation) {
        this.functionName = functionName;
        this.implClass = implClass;
        this.jarLocation = jarLocation;
    }
    
    public String getFunctionName() {
        return this.functionName;
    }
    
    public void setFunctionName(final String functionName) {
        this.functionName = functionName;
    }
    
    public String getImplClass() {
        return this.implClass;
    }
    
    public void setImplClass(final String implClass) {
        this.implClass = implClass;
    }
    
    public String getJarLocation() {
        return this.jarLocation;
    }
    
    public void setJarLocation(final String jarLocation) {
        this.jarLocation = jarLocation;
    }
}
