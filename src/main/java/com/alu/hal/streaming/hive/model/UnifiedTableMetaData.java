// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import org.apache.spark.sql.types.StructField;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.spark.sql.types.StructType;
import java.io.Serializable;

public class UnifiedTableMetaData implements Serializable
{
    private StructType schema;
    private Map<Integer, Integer> unifiedColIdxsByTr98ColIdxs;
    private Map<Integer, Integer> unifiedColIdxsByTr181ColIdxs;
    
    public UnifiedTableMetaData(final StructType schema, final Map<Integer, Integer> unifiedColIdxsByTr98ColIdxs, final Map<Integer, Integer> unifiedColIdxsByTr181ColIdxs) {
        this.schema = schema;
        this.unifiedColIdxsByTr98ColIdxs = unifiedColIdxsByTr98ColIdxs;
        this.unifiedColIdxsByTr181ColIdxs = unifiedColIdxsByTr181ColIdxs;
    }
    
    public Map<Integer, Integer> getUnifiedColIdxsByTr98ColIdxs() {
        return this.unifiedColIdxsByTr98ColIdxs;
    }
    
    public Map<Integer, Integer> getUnifiedColIdxsByTr181ColIdxs() {
        return this.unifiedColIdxsByTr181ColIdxs;
    }
    
    public List<String> getColumns() {
        return Arrays.stream(this.schema.fields()).map((Function<? super StructField, ?>)StructField::name).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
    
    public StructType getSchema() {
        return this.schema;
    }
}
