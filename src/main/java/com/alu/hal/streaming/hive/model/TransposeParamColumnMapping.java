// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import java.util.Collections;
import java.util.List;
import java.io.Serializable;

public class TransposeParamColumnMapping implements Serializable
{
    private final ColumnInfo valueColumn;
    private final List<ColumnInfo> transposeIndexColumns;
    
    public TransposeParamColumnMapping(final List<ColumnInfo> indexColumns, final ColumnInfo valueColumn) {
        this.valueColumn = valueColumn;
        this.transposeIndexColumns = indexColumns;
    }
    
    public ColumnInfo getTransposeValueColumn() {
        return this.valueColumn;
    }
    
    public List<ColumnInfo> getTransposeIndexColumns() {
        return (this.transposeIndexColumns == null) ? Collections.emptyList() : this.transposeIndexColumns;
    }
    
    @Override
    public String toString() {
        return "TransposeParamColumnMapping{valueColumn=" + this.valueColumn + ", transposeIndexColumns=" + this.transposeIndexColumns + '}';
    }
}
