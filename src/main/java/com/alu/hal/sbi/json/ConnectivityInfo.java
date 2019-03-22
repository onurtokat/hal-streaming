// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.sbi.json;

import org.apache.avro.data.RecordBuilderBase;
import org.apache.avro.data.RecordBuilder;
import org.apache.avro.specific.SpecificRecordBuilderBase;
import org.apache.avro.AvroRuntimeException;
import org.apache.avro.Schema;
import org.apache.avro.specific.AvroGenerated;
import org.apache.avro.specific.SpecificRecord;
import org.apache.avro.specific.SpecificRecordBase;

@AvroGenerated
public class ConnectivityInfo extends SpecificRecordBase implements SpecificRecord
{
    public static final Schema SCHEMA$;
    private CharSequence ssid;
    private CharSequence bssid;
    private boolean connectivityTestsPassed;
    
    public static Schema getClassSchema() {
        return ConnectivityInfo.SCHEMA$;
    }
    
    public ConnectivityInfo() {
    }
    
    public ConnectivityInfo(final CharSequence ssid, final CharSequence bssid, final Boolean connectivityTestsPassed) {
        this.ssid = ssid;
        this.bssid = bssid;
        this.connectivityTestsPassed = connectivityTestsPassed;
    }
    
    @Override
    public Schema getSchema() {
        return ConnectivityInfo.SCHEMA$;
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
                return this.connectivityTestsPassed;
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
                this.connectivityTestsPassed = (boolean)value$;
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
    
    public Boolean getConnectivityTestsPassed() {
        return this.connectivityTestsPassed;
    }
    
    public void setConnectivityTestsPassed(final Boolean value) {
        this.connectivityTestsPassed = value;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static Builder newBuilder(final Builder other) {
        return new Builder(other);
    }
    
    public static Builder newBuilder(final ConnectivityInfo other) {
        return new Builder(other);
    }
    
    static {
        SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"ConnectivityInfo\",\"namespace\":\"com.alu.hal.sbi.json\",\"fields\":[{\"name\":\"ssid\",\"type\":\"string\"},{\"name\":\"bssid\",\"type\":\"string\"},{\"name\":\"connectivityTestsPassed\",\"type\":\"boolean\"}]}");
    }
    
    public static class Builder extends SpecificRecordBuilderBase<ConnectivityInfo> implements RecordBuilder<ConnectivityInfo>
    {
        private CharSequence ssid;
        private CharSequence bssid;
        private boolean connectivityTestsPassed;
        
        private Builder() {
            super(ConnectivityInfo.SCHEMA$);
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
            if (RecordBuilderBase.isValidValue(this.fields()[2], other.connectivityTestsPassed)) {
                this.connectivityTestsPassed = this.data().deepCopy(this.fields()[2].schema(), other.connectivityTestsPassed);
                this.fieldSetFlags()[2] = true;
            }
        }
        
        private Builder(final ConnectivityInfo other) {
            super(ConnectivityInfo.SCHEMA$);
            if (RecordBuilderBase.isValidValue(this.fields()[0], other.ssid)) {
                this.ssid = this.data().deepCopy(this.fields()[0].schema(), other.ssid);
                this.fieldSetFlags()[0] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[1], other.bssid)) {
                this.bssid = this.data().deepCopy(this.fields()[1].schema(), other.bssid);
                this.fieldSetFlags()[1] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[2], other.connectivityTestsPassed)) {
                this.connectivityTestsPassed = this.data().deepCopy(this.fields()[2].schema(), other.connectivityTestsPassed);
                this.fieldSetFlags()[2] = true;
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
        
        public Boolean getConnectivityTestsPassed() {
            return this.connectivityTestsPassed;
        }
        
        public Builder setConnectivityTestsPassed(final boolean value) {
            this.validate(this.fields()[2], value);
            this.connectivityTestsPassed = value;
            this.fieldSetFlags()[2] = true;
            return this;
        }
        
        public boolean hasConnectivityTestsPassed() {
            return this.fieldSetFlags()[2];
        }
        
        public Builder clearConnectivityTestsPassed() {
            this.fieldSetFlags()[2] = false;
            return this;
        }
        
        @Override
        public ConnectivityInfo build() {
            try {
                final ConnectivityInfo record = new ConnectivityInfo();
                record.ssid = (CharSequence)(this.fieldSetFlags()[0] ? this.ssid : this.defaultValue(this.fields()[0]));
                record.bssid = (CharSequence)(this.fieldSetFlags()[1] ? this.bssid : this.defaultValue(this.fields()[1]));
                record.connectivityTestsPassed = (boolean)(this.fieldSetFlags()[2] ? this.connectivityTestsPassed : this.defaultValue(this.fields()[2]));
                return record;
            }
            catch (Exception e) {
                throw new AvroRuntimeException(e);
            }
        }
    }
}
