// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model.recommendation;

import java.util.Map;
import org.apache.spark.sql.Row;
import org.apache.log4j.Logger;

public class RecommInputBean
{
    public static final String DELIM = "@";
    private static Logger LOG;
    private final long latestTs;
    private final RecommendationType recommType;
    private final boolean isPositive;
    private final Row recRow;
    
    public RecommInputBean(final Row row, final Map<String, Integer> columnIndexesByName, final RecommendationType recommType, final boolean isPositive) {
        this.recRow = row;
        this.latestTs = Long.parseLong("" + row.get((int)columnIndexesByName.get("TS")));
        this.recommType = recommType;
        this.isPositive = isPositive;
    }
    
    public long getLatestTs() {
        return this.latestTs;
    }
    
    public RecommendationType getRecommType() {
        return this.recommType;
    }
    
    public int getPositiveThreshold() {
        return this.recommType.getNoPositiveObsBeforeActivation();
    }
    
    public int getNegativeThreshold() {
        return this.recommType.getNoNegativeObsBeforeDeActivation();
    }
    
    public boolean isPositive() {
        return this.isPositive;
    }
    
    public Row getRecRow() {
        return this.recRow;
    }
    
    @Override
    public String toString() {
        return "RecommInputBean [latestTs=" + this.latestTs + ", recommType=" + this.recommType + ", isPositive=" + this.isPositive + ", recRow=" + this.recRow + "]";
    }
    
    static {
        RecommInputBean.LOG = Logger.getLogger(RecommInputBean.class);
    }
}
