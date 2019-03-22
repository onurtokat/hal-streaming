// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.config.insights.hql;

public class ExpressionOrderPair
{
    private String physicalTarget;
    private Integer colOrder;
    private String expression;
    
    public ExpressionOrderPair(final String physicalTarget, final Integer colOrder, final String expression) {
        this.colOrder = colOrder;
        this.expression = expression;
        this.physicalTarget = physicalTarget;
    }
    
    public String getPhysicalTarget() {
        return this.physicalTarget;
    }
    
    public void setPhysicalTarget(final String physicalTarget) {
        this.physicalTarget = physicalTarget;
    }
    
    public Integer getColOrder() {
        return this.colOrder;
    }
    
    public void setColOrder(final Integer colOrder) {
        this.colOrder = colOrder;
    }
    
    public String getExpression() {
        return this.expression;
    }
    
    public void setExpression(final String expression) {
        this.expression = expression;
    }
}
