// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.avro.Schema;
import java.net.URI;
import java.io.Serializable;

public class AvroKafkaTopic implements Serializable
{
    private URI avroSchemaURI;
    private String topicName;
    
    public AvroKafkaTopic(final URI avroSchemaURI, final String topicName) {
        if (avroSchemaURI == null || topicName == null) {
            throw new IllegalArgumentException("Cannot create Avro topic with avroSchemaURI : " + avroSchemaURI + " or topic name : " + topicName + "being null");
        }
        this.avroSchemaURI = avroSchemaURI;
        this.topicName = topicName;
    }
    
    public URI getAvroSchemaURI() {
        return this.avroSchemaURI;
    }
    
    public Schema getAvroSchema() throws IOException {
        final Schema.Parser parser = new Schema.Parser();
        final Path pt = new Path(this.avroSchemaURI);
        final FileSystem fs = FileSystem.get(new Configuration());
        final BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(pt)));
        final StringBuilder sb = new StringBuilder();
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            sb.append(line + "\n");
        }
        return parser.parse(sb.toString());
    }
    
    public String getTopicName() {
        return this.topicName;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvroKafkaTopic)) {
            return false;
        }
        final AvroKafkaTopic avroKafkaTopic = (AvroKafkaTopic)o;
        return this.getAvroSchemaURI().equals(avroKafkaTopic.getAvroSchemaURI()) && this.getTopicName().equals(avroKafkaTopic.getTopicName());
    }
    
    @Override
    public int hashCode() {
        int result = this.getAvroSchemaURI().hashCode();
        result = 31 * result + this.getTopicName().hashCode();
        return result;
    }
    
    @Override
    public String toString() {
        return "AvroTopic{avroSchemaURI=" + this.avroSchemaURI + ", topicName='" + this.topicName + '\'' + '}';
    }
}
