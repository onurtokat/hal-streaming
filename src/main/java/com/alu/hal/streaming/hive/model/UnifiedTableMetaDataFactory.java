// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import com.alu.hal.streaming.exception.WifiTemplateParseException;
import java.util.HashMap;
import java.util.function.Function;
import com.alu.motive.hal.commons.generated.parser.templates.Column;
import org.apache.spark.sql.types.StructField;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Map;
import org.apache.spark.sql.types.StructType;
import com.alu.motive.hal.commons.generated.parser.templates.UnifiedMappingTemplate;
import org.apache.log4j.Logger;

public class UnifiedTableMetaDataFactory
{
    private static Logger LOG;
    private final UnifiedMappingTemplate unifiedMappingTemplate;
    private final TransposeTableMetadata tr98TransposeTableMetadata;
    private final TransposeTableMetadata tr181TransposeTableMetadata;
    
    public UnifiedTableMetaDataFactory(final UnifiedMappingTemplate unifiedMappingTemplate, final TransposeTableMetadata tr98TransposeTableMetadata, final TransposeTableMetadata tr181TransposeTableMetadata) {
        this.unifiedMappingTemplate = unifiedMappingTemplate;
        this.tr98TransposeTableMetadata = tr98TransposeTableMetadata;
        this.tr181TransposeTableMetadata = tr181TransposeTableMetadata;
    }
    
    private Map<String, Integer> createColumnIdxsByName(final StructType schema) {
        final StructField[] fields = schema.fields();
        return IntStream.range(0, fields.length).boxed().collect(Collectors.toMap(i -> fields[i].name(), i -> i));
    }
    
    private Map<String, String> createTr181ColNamesByTr98ColNames(final UnifiedMappingTemplate unifiedMappingTemplate) {
        return unifiedMappingTemplate.getColumns().getColumn().stream().peek(mapping -> {
            if (mapping.getNormalizedTR98Name() == null) {
                UnifiedTableMetaDataFactory.LOG.warn("Unified table mapping configuration processing - Incomplete unified mapping configuration skipped:  TR98 column name missing for TR181 column name : " + mapping.getNormalizedTR181Name());
            }
            if (mapping.getNormalizedTR181Name() == null) {
                UnifiedTableMetaDataFactory.LOG.warn("Unified table mapping configuration processing - Incomplete unified mapping skipped:  TR181 column name missing for TR98 column name : " + mapping.getNormalizedTR98Name());
            }
        }).filter(mapping -> mapping.getNormalizedTR98Name() != null && mapping.getNormalizedTR181Name() != null).collect(Collectors.toMap((Function<? super Object, ? extends String>)Column::getNormalizedTR98Name, (Function<? super Object, ? extends String>)Column::getNormalizedTR181Name));
    }
    
    private Map<Integer, Integer> createUnifiedColIdxsByTr181ColIdxs() {
        final Map<Integer, Integer> unifiedColIdxsByTr181ColIdxs = new HashMap<Integer, Integer>();
        final Map<String, Integer> tr181ColIdxsByName = this.createColumnIdxsByName(this.tr181TransposeTableMetadata.getTransposeSchema());
        final Map<String, Integer> unifiedColIdxsByName = this.createColumnIdxsByName(this.tr181TransposeTableMetadata.getTransposeSchemaWithoutIdxs());
        final Integer tr181TableColIdx;
        final Map<K, Integer> map;
        final Integer unifiedTableColIdx;
        final Map<Integer, Integer> map2;
        tr181ColIdxsByName.entrySet().forEach(tr181ColIdxByName -> {
            tr181TableColIdx = tr181ColIdxByName.getValue();
            unifiedTableColIdx = map.get(tr181ColIdxByName.getKey());
            if (unifiedTableColIdx == null) {
                return;
            }
            else {
                map2.put(tr181TableColIdx, unifiedTableColIdx);
                return;
            }
        });
        return unifiedColIdxsByTr181ColIdxs;
    }
    
    private Map<Integer, Integer> createUnifiedColIdxsByTr98ColIdxs() {
        final Map<Integer, Integer> UnifiedColIdxsByTr98ColIdxs = new HashMap<Integer, Integer>();
        final Map<String, String> tr181ColNamesByTr98ColNames = this.createTr181ColNamesByTr98ColNames(this.unifiedMappingTemplate);
        final StructType tr98Schema = this.tr98TransposeTableMetadata.getTransposeSchema();
        final StructType tr181WithoutIdxsSchema = this.tr181TransposeTableMetadata.getTransposeSchemaWithoutIdxs();
        final Map<String, Integer> tr98ColIdxsByName = this.createColumnIdxsByName(tr98Schema);
        final Map<String, Integer> tr181ColIdxsByName = this.createColumnIdxsByName(tr181WithoutIdxsSchema);
        final Map<K, Integer> map;
        final Integer tr98ColIdx;
        final Map<K, Integer> map2;
        final Integer tr181ColIdx;
        final StructType structType;
        final StructType structType2;
        final String dataTypeMismatchError;
        final Map<Integer, Integer> map3;
        tr181ColNamesByTr98ColNames.entrySet().forEach(tr181ColNameByTr98ColName -> {
            tr98ColIdx = map.get(tr181ColNameByTr98ColName.getKey());
            tr181ColIdx = map2.get(tr181ColNameByTr98ColName.getValue());
            if (tr98ColIdx == null || tr181ColIdx == null) {
                UnifiedTableMetaDataFactory.LOG.warn("Unified table mapping configuration processing - Unified mapping configuration TR98: " + tr181ColNameByTr98ColName.getKey() + " - TR181: " + (String)tr181ColNameByTr98ColName.getValue() + " skipped. " + "No corresponding TR98 or TR181 parameter in TR98/TR181 template ");
                return;
            }
            else if (structType.fields()[tr98ColIdx].dataType() != structType2.fields()[tr181ColIdx].dataType()) {
                dataTypeMismatchError = "Unified table mapping configuration processing failedTR98 column: " + tr181ColNameByTr98ColName.getKey() + " and TR181: " + (String)tr181ColNameByTr98ColName.getValue() + " cannot be unified without having the same type. ";
                UnifiedTableMetaDataFactory.LOG.error(dataTypeMismatchError);
                throw new WifiTemplateParseException(dataTypeMismatchError);
            }
            else {
                map3.put(tr98ColIdx, tr181ColIdx);
                return;
            }
        });
        return UnifiedColIdxsByTr98ColIdxs;
    }
    
    public UnifiedTableMetaData create() {
        final Map<Integer, Integer> UnifiedColIdxsByTr98ColIdxs = this.createUnifiedColIdxsByTr98ColIdxs();
        final Map<Integer, Integer> UnifiedColIdxsByTr181ColIdxs = this.createUnifiedColIdxsByTr181ColIdxs();
        return new UnifiedTableMetaData(this.tr181TransposeTableMetadata.getTransposeSchemaWithoutIdxs(), UnifiedColIdxsByTr98ColIdxs, UnifiedColIdxsByTr181ColIdxs);
    }
    
    static {
        UnifiedTableMetaDataFactory.LOG = Logger.getLogger(UnifiedTableMetaDataFactory.class);
    }
}
