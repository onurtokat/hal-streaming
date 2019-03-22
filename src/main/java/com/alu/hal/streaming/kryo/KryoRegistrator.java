// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.kryo;

import com.alu.hal.streaming.hive.model.AvroKafkaTopic;
import com.alu.hal.streaming.kafka.KafkaWriter;
import com.alu.hal.streaming.kafka.KafkaProducerWrapper;
import com.alu.hal.streaming.config.WifiSparkConfig;
import com.alu.hal.streaming.hive.model.recommendation.RecommInputBean;
import com.alu.hal.streaming.hive.model.recommendation.GenericRecommendation;
import com.alu.hal.streaming.hive.model.recommendation.Recommendation;
import com.alu.hal.streaming.hive.model.UnifiedTableMetaData;
import com.alu.hal.streaming.hive.model.TransposeTableMetadata;
import com.alu.hal.streaming.hive.model.TransposeParamColumnMapping;
import com.alu.hal.streaming.hive.model.ParamType;
import com.alu.hal.streaming.hive.model.ParamMetaData;
import com.alu.hal.streaming.hive.model.ModelType;
import com.alu.hal.streaming.hive.model.ModelMetadata;
import com.alu.hal.streaming.hive.model.MgdDevTransposeColumnState;
import com.alu.hal.streaming.hive.model.MgdDevReport;
import com.alu.hal.streaming.hive.model.DeviceModelTypeMappingConfig;
import com.alu.hal.streaming.hive.model.DeltaStagingState;
import com.alu.hal.streaming.hive.model.DecoratedParameter;
import com.alu.hal.streaming.hive.model.HiveTable;
import com.alu.hal.streaming.hive.model.SparkTempView;
import com.alu.hal.streaming.hive.model.CounterMetaData;
import com.alu.hal.streaming.hive.model.ColumnInfo;
import com.alu.motive.hal.commons.dto.ParameterDTO;
import com.alu.motive.hal.commons.dto.DeviceIdDTO;
import com.alu.motive.hal.commons.dto.TR69DTO;
import com.esotericsoftware.kryo.Kryo;

public class KryoRegistrator implements org.apache.spark.serializer.KryoRegistrator
{
    public void registerClasses(final Kryo kryo) {
        kryo.register(TR69DTO.class);
        kryo.register(DeviceIdDTO.class);
        kryo.register(ParameterDTO.class);
        kryo.register(ColumnInfo.class);
        kryo.register(CounterMetaData.class);
        kryo.register(SparkTempView.class);
        kryo.register(HiveTable.class);
        kryo.register(DecoratedParameter.class);
        kryo.register(DeltaStagingState.class);
        kryo.register(DeviceModelTypeMappingConfig.class);
        kryo.register(MgdDevReport.class);
        kryo.register(MgdDevTransposeColumnState.class);
        kryo.register(ModelMetadata.class);
        kryo.register(ModelType.class);
        kryo.register(ParamMetaData.class);
        kryo.register(ParamType.class);
        kryo.register(TransposeParamColumnMapping.class);
        kryo.register(TransposeTableMetadata.class);
        kryo.register(UnifiedTableMetaData.class);
        kryo.register(Recommendation.class);
        kryo.register(GenericRecommendation.class);
        kryo.register(RecommInputBean.class);
        kryo.register(WifiSparkConfig.class);
        kryo.register(KafkaProducerWrapper.class);
        kryo.register(KafkaWriter.class);
        kryo.register(AvroKafkaTopic.class);
    }
}
