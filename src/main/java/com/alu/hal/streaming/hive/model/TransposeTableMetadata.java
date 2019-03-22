// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import org.apache.spark.sql.types.StructType;
import java.util.Map;
import java.io.Serializable;

public class TransposeTableMetadata implements Serializable
{
    private final ModelType modelType;
    private Map<String, ColumnInfo> transposeTableColumnInfos;
    private StructType transposeSchema;
    private StructType transposeSchemaWithoutIdxs;
    
    public TransposeTableMetadata(final Map<String, ColumnInfo> transposeTableColumnInfos, final StructType transposeSchema, final StructType transposeSchemaWithoutIdxs, final ModelType modelType) {
        this.modelType = modelType;
        this.transposeTableColumnInfos = transposeTableColumnInfos;
        this.transposeSchema = transposeSchema;
        this.transposeSchemaWithoutIdxs = transposeSchemaWithoutIdxs;
    }
    
    public Map<String, ColumnInfo> getTransposeTableColumnInfos() {
        return this.transposeTableColumnInfos;
    }
    
    public StructType getTransposeSchema() {
        return this.transposeSchema;
    }
    
    public StructType getTransposeSchemaWithoutIdxs() {
        return this.transposeSchemaWithoutIdxs;
    }
    
    public ModelType getModelType() {
        return this.modelType;
    }
    
    public int getNumberOfColumns() {
        return this.transposeTableColumnInfos.size();
    }
}
