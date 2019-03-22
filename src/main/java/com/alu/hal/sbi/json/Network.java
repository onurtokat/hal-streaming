// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.sbi.json;

import org.apache.avro.data.RecordBuilderBase;
import org.apache.avro.data.RecordBuilder;
import org.apache.avro.specific.SpecificRecordBuilderBase;
import org.apache.avro.AvroRuntimeException;
import java.util.List;
import org.apache.avro.Schema;
import org.apache.avro.specific.AvroGenerated;
import org.apache.avro.specific.SpecificRecord;
import org.apache.avro.specific.SpecificRecordBase;

@AvroGenerated
public class Network extends SpecificRecordBase implements SpecificRecord
{
    public static final Schema SCHEMA$;
    private CharSequence ssid;
    private CharSequence bssid;
    private List<Double> rssi_dBm;
    private int channel;
    private int freq_MHz;
    
    public static Schema getClassSchema() {
        return Network.SCHEMA$;
    }
    
    public Network() {
    }
    
    public Network(final CharSequence ssid, final CharSequence bssid, final List<Double> rssi_dBm, final Integer channel, final Integer freq_MHz) {
        this.ssid = ssid;
        this.bssid = bssid;
        this.rssi_dBm = rssi_dBm;
        this.channel = channel;
        this.freq_MHz = freq_MHz;
    }
    
    @Override
    public Schema getSchema() {
        return Network.SCHEMA$;
    }
    
    @Override
    public Object get(final int field$) {
        switch (field$) {
            case 0: {
                return this.ssid;
            }
            case 1: {
                return this.bssid;
            }
            case 2: {
                return this.rssi_dBm;
            }
            case 3: {
                return this.channel;
            }
            case 4: {
                return this.freq_MHz;
            }
            default: {
                throw new AvroRuntimeException("Bad index");
            }
        }
    }
    
    @Override
    public void put(final int field$, final Object value$) {
        switch (field$) {
            case 0: {
                this.ssid = (CharSequence)value$;
                break;
            }
            case 1: {
                this.bssid = (CharSequence)value$;
                break;
            }
            case 2: {
                this.rssi_dBm = (List<Double>)value$;
                break;
            }
            case 3: {
                this.channel = (int)value$;
                break;
            }
            case 4: {
                this.freq_MHz = (int)value$;
                break;
            }
            default: {
                throw new AvroRuntimeException("Bad index");
            }
        }
    }
    
    public CharSequence getSsid() {
        return this.ssid;
    }
    
    public void setSsid(final CharSequence value) {
        this.ssid = value;
    }
    
    public CharSequence getBssid() {
        return this.bssid;
    }
    
    public void setBssid(final CharSequence value) {
        this.bssid = value;
    }
    
    public List<Double> getRssiDBm() {
        return this.rssi_dBm;
    }
    
    public void setRssiDBm(final List<Double> value) {
        this.rssi_dBm = value;
    }
    
    public Integer getChannel() {
        return this.channel;
    }
    
    public void setChannel(final Integer value) {
        this.channel = value;
    }
    
    public Integer getFreqMHz() {
        return this.freq_MHz;
    }
    
    public void setFreqMHz(final Integer value) {
        this.freq_MHz = value;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static Builder newBuilder(final Builder other) {
        return new Builder(other);
    }
    
    public static Builder newBuilder(final Network other) {
        return new Builder(other);
    }
    
    static {
        SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Network\",\"namespace\":\"com.alu.hal.sbi.json\",\"fields\":[{\"name\":\"ssid\",\"type\":\"string\"},{\"name\":\"bssid\",\"type\":\"string\"},{\"name\":\"rssi_dBm\",\"type\":{\"type\":\"array\",\"items\":\"double\"}},{\"name\":\"channel\",\"type\":\"int\"},{\"name\":\"freq_MHz\",\"type\":\"int\"}]}");
    }
    
    public static class Builder extends SpecificRecordBuilderBase<Network> implements RecordBuilder<Network>
    {
        private CharSequence ssid;
        private CharSequence bssid;
        private List<Double> rssi_dBm;
        private int channel;
        private int freq_MHz;
        
        private Builder() {
            super(Network.SCHEMA$);
        }
        
        private Builder(final Builder other) {
            super(other);
            if (RecordBuilderBase.isValidValue(this.fields()[0], other.ssid)) {
                this.ssid = this.data().deepCopy(this.fields()[0].schema(), other.ssid);
                this.fieldSetFlags()[0] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[1], other.bssid)) {
                this.bssid = this.data().deepCopy(this.fields()[1].schema(), other.bssid);
                this.fieldSetFlags()[1] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[2], other.rssi_dBm)) {
                this.rssi_dBm = this.data().deepCopy(this.fields()[2].schema(), other.rssi_dBm);
                this.fieldSetFlags()[2] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[3], other.channel)) {
                this.channel = this.data().deepCopy(this.fields()[3].schema(), other.channel);
                this.fieldSetFlags()[3] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[4], other.freq_MHz)) {
                this.freq_MHz = this.data().deepCopy(this.fields()[4].schema(), other.freq_MHz);
                this.fieldSetFlags()[4] = true;
            }
        }
        
        private Builder(final Network other) {
            super(Network.SCHEMA$);
            if (RecordBuilderBase.isValidValue(this.fields()[0], other.ssid)) {
                this.ssid = this.data().deepCopy(this.fields()[0].schema(), other.ssid);
                this.fieldSetFlags()[0] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[1], other.bssid)) {
                this.bssid = this.data().deepCopy(this.fields()[1].schema(), other.bssid);
                this.fieldSetFlags()[1] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[2], other.rssi_dBm)) {
                this.rssi_dBm = this.data().deepCopy(this.fields()[2].schema(), other.rssi_dBm);
                this.fieldSetFlags()[2] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[3], other.channel)) {
                this.channel = this.data().deepCopy(this.fields()[3].schema(), other.channel);
                this.fieldSetFlags()[3] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[4], other.freq_MHz)) {
                this.freq_MHz = this.data().deepCopy(this.fields()[4].schema(), other.freq_MHz);
                this.fieldSetFlags()[4] = true;
            }
        }
        
        public CharSequence getSsid() {
            return this.ssid;
        }
        
        public Builder setSsid(final CharSequence value) {
            this.validate(this.fields()[0], value);
            this.ssid = value;
            this.fieldSetFlags()[0] = true;
            return this;
        }
        
        public boolean hasSsid() {
            return this.fieldSetFlags()[0];
        }
        
        public Builder clearSsid() {
            this.ssid = null;
            this.fieldSetFlags()[0] = false;
            return this;
        }
        
        public CharSequence getBssid() {
            return this.bssid;
        }
        
        public Builder setBssid(final CharSequence value) {
            this.validate(this.fields()[1], value);
            this.bssid = value;
            this.fieldSetFlags()[1] = true;
            return this;
        }
        
        public boolean hasBssid() {
            return this.fieldSetFlags()[1];
        }
        
        public Builder clearBssid() {
            this.bssid = null;
            this.fieldSetFlags()[1] = false;
            return this;
        }
        
        public List<Double> getRssiDBm() {
            return this.rssi_dBm;
        }
        
        public Builder setRssiDBm(final List<Double> value) {
            this.validate(this.fields()[2], value);
            this.rssi_dBm = value;
            this.fieldSetFlags()[2] = true;
            return this;
        }
        
        public boolean hasRssiDBm() {
            return this.fieldSetFlags()[2];
        }
        
        public Builder clearRssiDBm() {
            this.rssi_dBm = null;
            this.fieldSetFlags()[2] = false;
            return this;
        }
        
        public Integer getChannel() {
            return this.channel;
        }
        
        public Builder setChannel(final int value) {
            this.validate(this.fields()[3], value);
            this.channel = value;
            this.fieldSetFlags()[3] = true;
            return this;
        }
        
        public boolean hasChannel() {
            return this.fieldSetFlags()[3];
        }
        
        public Builder clearChannel() {
            this.fieldSetFlags()[3] = false;
            return this;
        }
        
        public Integer getFreqMHz() {
            return this.freq_MHz;
        }
        
        public Builder setFreqMHz(final int value) {
            this.validate(this.fields()[4], value);
            this.freq_MHz = value;
            this.fieldSetFlags()[4] = true;
            return this;
        }
        
        public boolean hasFreqMHz() {
            return this.fieldSetFlags()[4];
        }
        
        public Builder clearFreqMHz() {
            this.fieldSetFlags()[4] = false;
            return this;
        }
        
        @Override
        public Network build() {
            try {
                final Network record = new Network();
                record.ssid = (CharSequence)(this.fieldSetFlags()[0] ? this.ssid : this.defaultValue(this.fields()[0]));
                record.bssid = (CharSequence)(this.fieldSetFlags()[1] ? this.bssid : this.defaultValue(this.fields()[1]));
                record.rssi_dBm = (List<Double>)(this.fieldSetFlags()[2] ? this.rssi_dBm : this.defaultValue(this.fields()[2]));
                record.channel = (int)(this.fieldSetFlags()[3] ? this.channel : this.defaultValue(this.fields()[3]));
                record.freq_MHz = (int)(this.fieldSetFlags()[4] ? this.freq_MHz : this.defaultValue(this.fields()[4]));
                return record;
            }
            catch (Exception e) {
                throw new AvroRuntimeException(e);
            }
        }
    }
}
