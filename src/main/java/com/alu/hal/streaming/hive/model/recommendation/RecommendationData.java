// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model.recommendation;

import org.apache.spark.sql.Row;

public class RecommendationData
{
    private final Row recRow;
    
    public RecommendationData(final RecommInputBean input) {
        this.recRow = input.getRecRow();
    }
    
    public Row getRecRow() {
        return this.recRow;
    }
    
    @Override
    public String toString() {
        return "RecommendationData [ recRow=" + this.recRow + "]";
    }
}
