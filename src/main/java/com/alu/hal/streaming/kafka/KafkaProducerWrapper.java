// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.kafka;

import scala.reflect.ClassTag$;
import java.io.IOException;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.avro.generic.GenericData;
import com.twitter.bijection.avro.GenericAvroCodecs;
import java.util.HashMap;
import org.apache.kafka.clients.producer.KafkaProducer;
import com.alu.hal.streaming.config.KafkaProducerConfig;
import org.apache.spark.sql.Row;
import org.apache.spark.SparkContext;
import scala.reflect.ClassTag;
import org.apache.avro.generic.GenericRecord;
import com.twitter.bijection.Injection;
import org.apache.avro.Schema;
import com.alu.hal.streaming.hive.model.AvroKafkaTopic;
import java.util.Map;
import org.apache.kafka.clients.producer.Producer;
import org.apache.spark.broadcast.Broadcast;
import org.apache.log4j.Logger;
import java.io.Serializable;

public class KafkaProducerWrapper implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOG;
    private static volatile Broadcast<KafkaProducerWrapper> broadcastInstance;
    private static transient Producer<String, byte[]> producer;
    private static transient Map<AvroKafkaTopic, Schema> avroSchemaByAvroTopic;
    private static transient Map<AvroKafkaTopic, Injection<GenericRecord, byte[]>> injections;
    private static final ClassTag<KafkaProducerWrapper> KAFKA_PRODUCER_WRAPPER_SCALA_CLASS_TAG;
    
    public static Broadcast<KafkaProducerWrapper> getBroadCastInstance(final SparkContext sc) {
        if (KafkaProducerWrapper.broadcastInstance == null) {
            KafkaProducerWrapper.LOG.info("create KafkaProducerWrapper broadcast instance");
            synchronized (KafkaProducerWrapper.class) {
                if (KafkaProducerWrapper.broadcastInstance == null) {
                    final KafkaProducerWrapper instance = new KafkaProducerWrapper();
                    KafkaProducerWrapper.broadcastInstance = (Broadcast<KafkaProducerWrapper>)sc.broadcast((Object)instance, (ClassTag)KafkaProducerWrapper.KAFKA_PRODUCER_WRAPPER_SCALA_CLASS_TAG);
                }
            }
        }
        KafkaProducerWrapper.LOG.info("Return KafkaProducerWrapper broadcast instance = " + KafkaProducerWrapper.broadcastInstance);
        return KafkaProducerWrapper.broadcastInstance;
    }
    
    public void send(final AvroKafkaTopic avroKafkaTopic, final Row value) throws IOException {
        if (KafkaProducerWrapper.producer == null) {
            synchronized (KafkaProducerWrapper.class) {
                if (KafkaProducerWrapper.producer == null) {
                    final KafkaProducerConfig kafkaProducerConfig = KafkaProducerConfig.instance();
                    KafkaProducerWrapper.LOG.info("Creating new KafkaProducer with config " + kafkaProducerConfig);
                    KafkaProducerWrapper.producer = new KafkaProducer<String, byte[]>(kafkaProducerConfig.getProperties());
                    Runtime.getRuntime().addShutdownHook(new Thread() {
                        @Override
                        public void run() {
                            KafkaProducerWrapper.producer.close();
                        }
                    });
                }
            }
        }
        if (KafkaProducerWrapper.avroSchemaByAvroTopic == null || !KafkaProducerWrapper.avroSchemaByAvroTopic.containsKey(avroKafkaTopic)) {
            synchronized (KafkaProducerWrapper.class) {
                if (KafkaProducerWrapper.avroSchemaByAvroTopic == null || !KafkaProducerWrapper.avroSchemaByAvroTopic.containsKey(avroKafkaTopic)) {
                    if (KafkaProducerWrapper.avroSchemaByAvroTopic == null) {
                        KafkaProducerWrapper.avroSchemaByAvroTopic = new HashMap<AvroKafkaTopic, Schema>();
                        KafkaProducerWrapper.injections = new HashMap<AvroKafkaTopic, Injection<GenericRecord, byte[]>>();
                    }
                    final Schema newSchema = avroKafkaTopic.getAvroSchema();
                    KafkaProducerWrapper.avroSchemaByAvroTopic.put(avroKafkaTopic, newSchema);
                    KafkaProducerWrapper.injections.put(avroKafkaTopic, GenericAvroCodecs.toBinary(newSchema));
                }
            }
        }
        final GenericRecord avroRecord = new GenericData.Record(KafkaProducerWrapper.avroSchemaByAvroTopic.get(avroKafkaTopic));
        final String[] fields = value.schema().fieldNames();
        for (int i = 0; i < value.size(); ++i) {
            avroRecord.put(fields[i], value.get(i));
        }
        final byte[] bytes = KafkaProducerWrapper.injections.get(avroKafkaTopic).apply(avroRecord);
        KafkaProducerWrapper.producer.send(new ProducerRecord<String, byte[]>(avroKafkaTopic.getTopicName(), bytes));
    }
    
    static {
        LOG = Logger.getLogger(KafkaProducerWrapper.class);
        KafkaProducerWrapper.broadcastInstance = null;
        KafkaProducerWrapper.producer = null;
        KafkaProducerWrapper.avroSchemaByAvroTopic = null;
        KafkaProducerWrapper.injections = null;
        KAFKA_PRODUCER_WRAPPER_SCALA_CLASS_TAG = ClassTag$.MODULE$.apply(KafkaProducerWrapper.class);
    }
}
