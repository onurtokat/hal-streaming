// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import javax.annotation.Nonnull;
import org.apache.spark.sql.types.DataType;
import java.io.Serializable;

public class ColumnInfo implements Comparable<ColumnInfo>, Serializable
{
    private String name;
    private int colPosition;
    private DataType type;
    private boolean isIndex;
    
    public ColumnInfo(final String name, final int colPosition, final DataType type, final boolean isIndex) {
        this.name = name;
        this.colPosition = colPosition;
        this.type = type;
        this.isIndex = isIndex;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getColPosition() {
        return this.colPosition;
    }
    
    public DataType getType() {
        return this.type;
    }
    
    @Override
    public String toString() {
        return "ColumnInfo{name='" + this.name + '\'' + ", colPosition=" + this.colPosition + ", type=" + this.type + ", isIndex=" + this.isIndex + '}';
    }
    
    public boolean isIndex() {
        return this.isIndex;
    }
    
    @Override
    public int compareTo(@Nonnull final ColumnInfo o) {
        return this.colPosition - o.colPosition;
    }
}
