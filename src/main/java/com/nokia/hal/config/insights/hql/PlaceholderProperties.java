// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.config.insights.hql;

public class PlaceholderProperties
{
    public static final int ARG_NBR = 7;
    public static final String ARG_SEPARATOR = ",";
    public static final String EMPTY_ARG = "-";
    public static final String TOP_LEVEL = "TOP_LEVEL";
    public static final String UNCOMPRESS = "UNCOMPRESS";
    public static final String DUMP_ALL_COLS = "DUMP_ALL_COLS";
    private String text;
    private String domain;
    private String category;
    private String flow;
    private String monitoredPointType;
    private String expression;
    private boolean topLevel;
    private boolean uncompress;
    private String alias;
    private boolean dumpAllColumnsOrdered;
    
    public PlaceholderProperties(final String text) throws IllegalArgumentException {
        this.text = text;
        final String[] prop = text.split(",");
        if (prop.length == 2) {
            this.dumpAllColumnsOrdered = prop[0].equals("DUMP_ALL_COLS");
            this.topLevel = prop[1].equals("TOP_LEVEL");
            this.uncompress = prop[1].equals("UNCOMPRESS");
        }
        else {
            if (prop.length < 7) {
                throw new IllegalArgumentException("Number of Placeholder arguments must be 7. Arguments provided: " + prop.length);
            }
            this.domain = (prop[0].equals("-") ? "" : prop[0]);
            this.category = (prop[1].equals("-") ? "" : prop[1]);
            this.flow = (prop[2].equals("-") ? "" : prop[2]);
            this.monitoredPointType = (prop[3].equals("-") ? "" : prop[3]);
            this.expression = (prop[4].equals("-") ? "" : prop[4]);
            this.topLevel = prop[5].equals("TOP_LEVEL");
            this.uncompress = prop[5].equals("UNCOMPRESS");
            this.alias = (prop[6].equals("-") ? "" : prop[6]);
        }
    }
    
    public String getText() {
        return this.text;
    }
    
    public void setText(final String text) {
        this.text = text;
    }
    
    public String getDomain() {
        return this.domain;
    }
    
    public void setDomain(final String domain) {
        this.domain = domain;
    }
    
    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(final String category) {
        this.category = category;
    }
    
    public String getFlow() {
        return this.flow;
    }
    
    public void setFlow(final String flow) {
        this.flow = flow;
    }
    
    public String getMonitoredPointType() {
        return this.monitoredPointType;
    }
    
    public void setMonitoredPointType(final String monitoredPointType) {
        this.monitoredPointType = monitoredPointType;
    }
    
    public String getExpression() {
        return this.expression;
    }
    
    public void setExpression(final String expression) {
        this.expression = expression;
    }
    
    public boolean getTopLevel() {
        return this.topLevel;
    }
    
    public void setTopLevel(final boolean topLevel) {
        this.topLevel = topLevel;
    }
    
    public boolean getUncompress() {
        return this.uncompress;
    }
    
    public void setUncompress(final boolean uncompress) {
        this.uncompress = uncompress;
    }
    
    public String getAlias() {
        return this.alias;
    }
    
    public void setAlias(final String alias) {
        this.alias = alias;
    }
    
    public boolean isDumpAllColumnsOrdered() {
        return this.dumpAllColumnsOrdered;
    }
    
    public void setDumpAllColumnsOrdered(final boolean dumpAllColumnsOrdered) {
        this.dumpAllColumnsOrdered = dumpAllColumnsOrdered;
    }
}
