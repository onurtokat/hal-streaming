// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.process;

import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import org.apache.spark.sql.RowFactory;
import com.alu.hal.streaming.hive.model.ColumnInfo;
import com.alu.hal.streaming.hive.model.ModelMetadataFactory;
import java.util.Collections;
import org.apache.spark.api.java.Optional;
import com.alu.hal.streaming.utils.ParamUtils;
import java.util.Comparator;
import java.util.HashMap;
import com.alu.hal.streaming.exception.MessageMismatchException;
import java.util.Map;
import com.alu.hal.streaming.hive.model.SpecialParametersCreator;
import com.alu.hal.streaming.hive.model.DecoratedParameter;
import java.util.ArrayList;
import org.apache.spark.sql.Row;
import java.util.List;
import com.alu.hal.streaming.hive.model.ModelMetadata;
import com.alu.hal.streaming.hive.model.MgdDevReport;
import org.apache.log4j.Logger;

public class TransposeTableRowFactory
{
    private static Logger LOG;
    
    public static List<Row> create(final MgdDevReport mgdDevReport, final ModelMetadata modelMetadata) throws MessageMismatchException {
        final List<DecoratedParameter> decoratedParameters = new ArrayList<DecoratedParameter>(mgdDevReport.getDecoratedParameters());
        SpecialParametersCreator.create(decoratedParameters, modelMetadata);
        final List<Row> rows = new ArrayList<Row>();
        final Map<IndexPath, List<DecoratedParameter>> rowMap = createTableRowMap(mgdDevReport, decoratedParameters);
        rowMap.entrySet().forEach(rowEntry -> rows.add(createRow(mgdDevReport, rowEntry.getValue(), modelMetadata.getTransposeTableMetadata().getNumberOfColumns())));
        return rows;
    }
    
    private static Map<IndexPath, List<DecoratedParameter>> createTableRowMap(final MgdDevReport mgdDevReport, final List<DecoratedParameter> decoratedParameters) {
        final Map<IndexPath, List<DecoratedParameter>> tableRowMap = new HashMap<IndexPath, List<DecoratedParameter>>();
        final IndexPathLookupForReferredGroupParams indexPathLookupForReferredGroupParams = new IndexPathLookupForReferredGroupParams();
        final Map<IndexPath, List<DecoratedParameter>> tableRowMap2;
        final IndexPathLookupForReferredGroupParams \u0131ndexPathLookupForReferredGroupParams;
        decoratedParameters.stream().sorted((Comparator<? super Object>)DecoratedParameter.COMPARING_BY_MOST_NBR_OF_IDXS_FIRST).forEach(decoratedParameter -> {
            if (!decoratedParameter.isParameterOfReferredGroup()) {
                updateTableRowMap(tableRowMap2, decoratedParameter);
            }
            if (decoratedParameter.isReferenceParameter()) {
                \u0131ndexPathLookupForReferredGroupParams.updateIndexPathLookups(decoratedParameter);
            }
            return;
        });
        final IndexPathLookupForReferredGroupParams \u0131ndexPathLookupForReferredGroupParams2;
        final List<Optional<IndexPath>> indexPaths;
        final Map<IndexPath, List<DecoratedParameter>> tableRowMap3;
        decoratedParameters.stream().filter(decoratedParameter -> decoratedParameter.isParameterOfReferredGroup()).forEach(decoratedParameterWithReferedPath -> {
            indexPaths = \u0131ndexPathLookupForReferredGroupParams2.getIndexPaths(decoratedParameterWithReferedPath);
            indexPaths.forEach(indexPath -> {
                if (indexPath.isPresent()) {
                    updateTableRowMapWithIndexPath(tableRowMap3, decoratedParameterWithReferedPath, (IndexPath)indexPath.get());
                }
                else {
                    TransposeTableRowFactory.LOG.warn("Report for deviceId: " + mgdDevReport.getDeviceId() + " @TS: " + mgdDevReport.getCollectionTimeStamp() + ": Unable to map parameter " + decoratedParameterWithReferedPath.getParameterName() + " to a table row due to a missing reference parameter " + decoratedParameterWithReferedPath.getParamMetaData().getParameterReferringParentGroup() + " or due to a missing reference parameter referring on its turn to group " + ParamUtils.getGroupName(decoratedParameterWithReferedPath.getParamMetaData().getParameterReferringParentGroup()));
                }
            });
            return;
        });
        return tableRowMap;
    }
    
    private static Row createRow(final MgdDevReport mgdDevReport, final List<DecoratedParameter> decoratedParameters, final int nColumns) {
        final List<Object> columnValues = new ArrayList<Object>(Collections.nCopies(nColumns, (Object)null));
        columnValues.set(ModelMetadataFactory.DEVICE_ID_COLUMN.getColPosition(), mgdDevReport.getDeviceId().getDeviceIdString());
        columnValues.set(ModelMetadataFactory.COLLECTION_TS_COLUMN.getColPosition(), mgdDevReport.getCollectionTimeStamp());
        final List<ColumnInfo> indexColumns;
        final List<String> indexValues;
        int indexSize;
        int i;
        final List<Integer> list;
        decoratedParameters.forEach(decoratedParameter -> {
            indexColumns = decoratedParameter.getParamMetaData().getTransposeParamColumnMapping().getTransposeIndexColumns();
            indexValues = ParamUtils.getIndexes(decoratedParameter.getParameterDTO().getName(), decoratedParameter.getParamMetaData().getCompiledParamWithPathPattern());
            for (indexSize = Math.min(indexColumns.size(), indexValues.size()), i = 0; i < indexSize; ++i) {
                list.set(indexColumns.get(i).getColPosition(), Integer.parseInt(indexValues.get(i)));
            }
            list.set(decoratedParameter.getParamMetaData().getTransposeParamColumnMapping().getTransposeValueColumn().getColPosition(), (Integer)decoratedParameter.getValue());
            return;
        });
        return RowFactory.create(columnValues.toArray());
    }
    
    private static void updateTableRowMap(final Map<IndexPath, List<DecoratedParameter>> tableRowMap, final DecoratedParameter decoratedParameter) {
        final List<String> indexes = ParamUtils.getIndexes(decoratedParameter.getParameterDTO().getName(), decoratedParameter.getParamMetaData().getCompiledParamWithPathPattern());
        final IndexPath indexPath = IndexPath.create(indexes);
        updateTableRowMapWithIndexPath(tableRowMap, decoratedParameter, indexPath);
    }
    
    private static void updateTableRowMapWithIndexPath(final Map<IndexPath, List<DecoratedParameter>> tableRowMap, final DecoratedParameter decoratedParameter, final IndexPath indexPath) {
        if (!indexPath.getValue().equals("")) {
            final long count = tableRowMap.keySet().stream().filter((Predicate<? super Object>)IndexPath.startsWith(indexPath)).map(indexPathKey -> tableRowMap.get(indexPathKey).add(decoratedParameter)).count();
            if (count == 0L) {
                final List<DecoratedParameter> list = new ArrayList<DecoratedParameter>();
                list.add(decoratedParameter);
                tableRowMap.put(indexPath, list);
            }
        }
        else {
            tableRowMap.keySet().stream().forEach(indexPathKey -> tableRowMap.get(indexPathKey).add(decoratedParameter));
        }
    }
    
    static {
        TransposeTableRowFactory.LOG = Logger.getLogger(TransposeTableRowFactory.class);
    }
    
    public static class IndexPathLookupForReferredGroupParams
    {
        private Map<String, List<IndexPathLookup>> referenceIndexPathByReferenceGroup;
        
        public IndexPathLookupForReferredGroupParams() {
            this.referenceIndexPathByReferenceGroup = new HashMap<String, List<IndexPathLookup>>();
            this.referenceIndexPathByReferenceGroup = new HashMap<String, List<IndexPathLookup>>();
        }
        
        public void updateIndexPathLookups(final DecoratedParameter decoratedParameter) {
            if (decoratedParameter.isReferenceParameter()) {
                if (!decoratedParameter.isParameterOfReferredGroup()) {
                    final List<String> indexes = ParamUtils.getIndexes(decoratedParameter.getParameterDTO().getName(), decoratedParameter.getParamMetaData().getCompiledParamWithPathPattern());
                    final List<IndexPathLookup> incomingIndexPathLookupAsList = new ArrayList<IndexPathLookup>(1);
                    incomingIndexPathLookupAsList.add(new IndexPathLookup((Optional<String>)Optional.absent(), (Optional<IndexPath>)Optional.of((Object)IndexPath.create(indexes))));
                    this.referenceIndexPathByReferenceGroup.merge(decoratedParameter.getParameterDTO().getValue(), incomingIndexPathLookupAsList, (existingIndexPathLookups, newIndexPathLookupAsArray) -> {
                        existingIndexPathLookups.addAll(newIndexPathLookupAsArray);
                        return existingIndexPathLookups;
                    });
                }
                else {
                    final List<IndexPathLookup> incomingIndexPathLookupAsList2 = new ArrayList<IndexPathLookup>(1);
                    incomingIndexPathLookupAsList2.add(new IndexPathLookup((Optional<String>)Optional.of((Object)ParamUtils.getGroupName(decoratedParameter.getParameterName())), (Optional<IndexPath>)Optional.absent()));
                    this.referenceIndexPathByReferenceGroup.merge(decoratedParameter.getParameterDTO().getValue(), incomingIndexPathLookupAsList2, (existingIndexPathLookups, newIndexPathLookupAsArray) -> {
                        existingIndexPathLookups.addAll(newIndexPathLookupAsArray);
                        return existingIndexPathLookups;
                    });
                }
            }
        }
        
        public List<Optional<IndexPath>> getIndexPaths(final DecoratedParameter decoratedParameter) {
            final java.util.Optional<Map.Entry<String, List<IndexPathLookup>>> matchingIndexPathLookups = this.referenceIndexPathByReferenceGroup.entrySet().stream().filter(referenceIndexPathByReferenceGroupEntry -> decoratedParameter.getParameterName().startsWith(referenceIndexPathByReferenceGroupEntry.getKey())).findFirst();
            if (matchingIndexPathLookups.isPresent()) {
                return matchingIndexPathLookups.get().getValue().stream().flatMap(indexPathLookup -> indexPathLookup.getResolvedIndexPaths(new ArrayList<Optional<IndexPath>>()).stream()).collect((Collector<? super Object, ?, List<Optional<IndexPath>>>)Collectors.toList());
            }
            TransposeTableRowFactory.LOG.warn("Index of Parameter " + decoratedParameter.getParameterName() + " part of a referenced group cannot be resolved. Reference parameter missing.");
            return Arrays.asList(Optional.absent());
        }
        
        private class IndexPathLookup
        {
            private final Optional<String> linkedReferenceGroup;
            private final Optional<IndexPath> indexPath;
            
            public IndexPathLookup(final Optional<String> linkedReferenceGroup, final Optional<IndexPath> indexPath) {
                this.linkedReferenceGroup = linkedReferenceGroup;
                this.indexPath = indexPath;
            }
            
            public List<Optional<IndexPath>> getResolvedIndexPaths(final List<Optional<IndexPath>> resolvedIndexPaths) {
                if (this.indexPath.isPresent() || !this.linkedReferenceGroup.isPresent()) {
                    resolvedIndexPaths.add(this.indexPath);
                    return resolvedIndexPaths;
                }
                final List<IndexPathLookup> indexPathLookups = IndexPathLookupForReferredGroupParams.this.referenceIndexPathByReferenceGroup.get(this.linkedReferenceGroup.get());
                if (indexPathLookups == null) {
                    TransposeTableRowFactory.LOG.warn("ReferenceGroup " + (String)this.linkedReferenceGroup.get() + " cannot be resolved into an indexPath");
                    return Arrays.asList(Optional.absent());
                }
                final List<Optional<IndexPath>> collect = indexPathLookups.stream().flatMap(indexPathLookup -> indexPathLookup.getResolvedIndexPaths(resolvedIndexPaths).stream()).collect((Collector<? super Object, ?, List<Optional<IndexPath>>>)Collectors.toList());
                return resolvedIndexPaths;
            }
        }
    }
    
    public static class IndexPath
    {
        private final String value;
        
        private IndexPath(final String value) {
            this.value = value;
        }
        
        public static IndexPath create(final List<String> indexes) {
            return new IndexPath(indexes.stream().collect((Collector<? super Object, ?, String>)Collectors.joining(".")));
        }
        
        public static Predicate<IndexPath> startsWith(final IndexPath other) {
            return p -> p.getValue().startsWith(other.getValue());
        }
        
        public String getValue() {
            return this.value;
        }
        
        public List<String> getComponents() {
            return Arrays.asList(this.value.split("\\."));
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final IndexPath indexPath = (IndexPath)o;
            if (this.value != null) {
                if (!this.value.equals(indexPath.value)) {
                    return false;
                }
            }
            else if (indexPath.value != null) {
                return false;
            }
            return true;
            b = false;
            return b;
        }
        
        @Override
        public int hashCode() {
            return (this.value != null) ? this.value.hashCode() : 0;
        }
        
        @Override
        public String toString() {
            return "IndexPath{value='" + this.value + '\'' + '}';
        }
    }
}
