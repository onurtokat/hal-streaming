// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.process;

import java.util.Optional;
import org.apache.spark.sql.types.StructField;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;
import org.apache.spark.sql.RowFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import com.alu.hal.streaming.hive.model.UnifiedTableMetaData;
import org.apache.spark.sql.Row;
import com.alu.hal.streaming.config.DashBoardConfig;
import org.apache.log4j.Logger;

public class UnifiedTableRowFactory
{
    private static final Logger LOG;
    private static final DashBoardConfig CONFIG;
    private static final String RADIO_OPERATING_FREQUENCY_BAND = "Radio_OperatingFrequencyBand";
    private static final String RADIO_NAME = "Radio_Name";
    private static final String SSID_NAME = "SSID_Name";
    private static final String RADIO_CHANNEL = "Radio_Channel";
    
    public static Row fromTr98Row(final Row tr98Row, final UnifiedTableMetaData unifiedTableMetaData) {
        UnifiedTableRowFactory.LOG.debug("Create TR98 unified");
        return fromTrRow(tr98Row, unifiedTableMetaData, unifiedTableMetaData.getUnifiedColIdxsByTr98ColIdxs());
    }
    
    public static Row fromTrRow(final Row trRow, final UnifiedTableMetaData unifiedTableMetaData, final Map<Integer, Integer> unifiedColIdxsByTrColIdxs) {
        final List<Object> unifiedCols = new ArrayList<Object>(Collections.nCopies(unifiedTableMetaData.getSchema().length(), (Object)null));
        final Object colValue;
        final List<Object> list;
        unifiedColIdxsByTrColIdxs.entrySet().forEach(unifiedColIdxByTrColIdx -> {
            colValue = getColumnValue(trRow, unifiedColIdxByTrColIdx);
            list.set(unifiedColIdxByTrColIdx.getValue(), colValue);
            return;
        });
        final List<Object> completeUnifiedCols = computeMandatoryFieldsThatAreMissing(unifiedTableMetaData, unifiedCols);
        final Row row = RowFactory.create(completeUnifiedCols.toArray());
        return row;
    }
    
    private static List<Object> computeMandatoryFieldsThatAreMissing(final UnifiedTableMetaData unifiedTableMetaData, List<Object> unifiedCols) {
        final StructField[] fields = unifiedTableMetaData.getSchema().fields();
        final Map<String, Integer> columnIndexesByName = IntStream.range(0, fields.length).boxed().collect(Collectors.toMap(index -> fields[index].name(), index -> index));
        unifiedCols = computeFrequencyBand(columnIndexesByName, unifiedCols);
        unifiedCols = computeRadioName(columnIndexesByName, unifiedCols);
        return unifiedCols;
    }
    
    private static List<Object> computeRadioName(final Map<String, Integer> columnIndexesByName, final List<Object> unifiedCols) {
        final Optional<Integer> radioNameColumnIndex = Optional.ofNullable(columnIndexesByName.get("Radio_Name"));
        if (radioNameColumnIndex.isPresent() && unifiedCols.get(radioNameColumnIndex.get()) == null) {
            final Optional<Integer> ssidNameColumnIndex = Optional.ofNullable(columnIndexesByName.get("SSID_Name"));
            if (ssidNameColumnIndex.isPresent() && unifiedCols.get(ssidNameColumnIndex.get()) != null) {
                final String ssidName = unifiedCols.get(ssidNameColumnIndex.get());
                unifiedCols.set(radioNameColumnIndex.get(), ssidName);
            }
        }
        return unifiedCols;
    }
    
    private static List<Object> computeFrequencyBand(final Map<String, Integer> columnIndexesByName, final List<Object> unifiedCols) {
        final Optional<Integer> frequencyBandColumnIndex = Optional.ofNullable(columnIndexesByName.get("Radio_OperatingFrequencyBand"));
        if (frequencyBandColumnIndex.isPresent()) {
            final Optional<Integer> channelColumnIndex = Optional.ofNullable(columnIndexesByName.get("Radio_Channel"));
            if (channelColumnIndex.isPresent() && unifiedCols.get(channelColumnIndex.get()) != null) {
                final Long channelValue = unifiedCols.get(channelColumnIndex.get());
                unifiedCols.set(frequencyBandColumnIndex.get(), getFrequencyBand(channelValue));
            }
        }
        return unifiedCols;
    }
    
    private static String getFrequencyBand(final Long channelValue) {
        String columnValue = "unknown";
        if (channelValue >= UnifiedTableRowFactory.CONFIG.getInteger("min.channel.2_4GHz", 1) && channelValue <= UnifiedTableRowFactory.CONFIG.getInteger("max.channel.2_4GHz", 14)) {
            columnValue = "2.4GHz";
        }
        else if (channelValue >= UnifiedTableRowFactory.CONFIG.getInteger("min.channel.5_0GHz", 34) && channelValue <= UnifiedTableRowFactory.CONFIG.getInteger("max.channel.5_0GHz", 165)) {
            columnValue = "5GHz";
        }
        return columnValue;
    }
    
    private static Object getColumnValue(final Row trRow, final Map.Entry<Integer, Integer> unifiedColIdxByTrColIdx) {
        Object trColumnValue;
        try {
            trColumnValue = trRow.get((int)unifiedColIdxByTrColIdx.getKey());
        }
        catch (Throwable e) {
            UnifiedTableRowFactory.LOG.warn("unable to get column value: ", e);
            trColumnValue = null;
        }
        return trColumnValue;
    }
    
    public static Row fromTr181Row(final Row tr181Row, final UnifiedTableMetaData unifiedTableMetaData) {
        UnifiedTableRowFactory.LOG.debug("Create TR181 unified");
        return fromTrRow(tr181Row, unifiedTableMetaData, unifiedTableMetaData.getUnifiedColIdxsByTr181ColIdxs());
    }
    
    static {
        LOG = Logger.getLogger(UnifiedTableRowFactory.class);
        CONFIG = DashBoardConfig.instance();
    }
}
