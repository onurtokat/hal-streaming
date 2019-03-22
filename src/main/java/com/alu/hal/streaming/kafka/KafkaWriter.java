// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.kafka;

import java.io.IOException;
import org.apache.spark.SparkContext;
import com.alu.hal.streaming.hive.model.AvroKafkaTopic;
import org.apache.log4j.Logger;
import java.io.Serializable;
import scala.runtime.BoxedUnit;
import org.apache.spark.sql.Row;
import scala.collection.Iterator;
import scala.runtime.AbstractFunction1;

public class KafkaWriter extends AbstractFunction1<Iterator<Row>, BoxedUnit> implements Serializable
{
    private static Logger LOG;
    private static final long serialVersionUID = -1919222653470217466L;
    private AvroKafkaTopic avroKafkaTopic;
    private KafkaProducerWrapper kafkaProducerWrapper;
    
    public KafkaWriter(final AvroKafkaTopic avroKafkaTopic, final SparkContext sc) {
        this.avroKafkaTopic = avroKafkaTopic;
        this.kafkaProducerWrapper = (KafkaProducerWrapper)KafkaProducerWrapper.getBroadCastInstance(sc).value();
    }
    
    @Override
    public BoxedUnit apply(final Iterator<Row> v1) {
        while (v1.hasNext()) {
            final Row row = v1.next();
            try {
                if (KafkaWriter.LOG.isDebugEnabled()) {
                    KafkaWriter.LOG.debug("Send row : " + row.toString() + " to topic name" + this.avroKafkaTopic.getTopicName());
                }
                this.kafkaProducerWrapper.send(this.avroKafkaTopic, row);
            }
            catch (IOException e) {
                KafkaWriter.LOG.error("IOError writing to " + this.avroKafkaTopic.getTopicName(), e);
            }
        }
        return BoxedUnit.UNIT;
    }
    
    static {
        KafkaWriter.LOG = Logger.getLogger(KafkaWriter.class);
    }
}
