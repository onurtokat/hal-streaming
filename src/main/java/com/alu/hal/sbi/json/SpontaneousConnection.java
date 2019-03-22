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
public class SpontaneousConnection extends SpecificRecordBase implements SpecificRecord
{
    public static final Schema SCHEMA$;
    private CharSequence spontaneousConnectedSsid;
    private CharSequence spontaneousConnectedBssid;
    
    public static Schema getClassSchema() {
        return SpontaneousConnection.SCHEMA$;
    }
    
    public SpontaneousConnection() {
    }
    
    public SpontaneousConnection(final CharSequence spontaneousConnectedSsid, final CharSequence spontaneousConnectedBssid) {
        this.spontaneousConnectedSsid = spontaneousConnectedSsid;
        this.spontaneousConnectedBssid = spontaneousConnectedBssid;
    }
    
    @Override
    public Schema getSchema() {
        return SpontaneousConnection.SCHEMA$;
    }
    
    @Override
    public Object get(final int field$) {
        switch (field$) {
            case 0: {
                return this.spontaneousConnectedSsid;
            }
            case 1: {
                return this.spontaneousConnectedBssid;
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
                this.spontaneousConnectedSsid = (CharSequence)value$;
                break;
            }
            case 1: {
                this.spontaneousConnectedBssid = (CharSequence)value$;
                break;
            }
            default: {
                throw new AvroRuntimeException("Bad index");
            }
        }
    }
    
    public CharSequence getSpontaneousConnectedSsid() {
        return this.spontaneousConnectedSsid;
    }
    
    public void setSpontaneousConnectedSsid(final CharSequence value) {
        this.spontaneousConnectedSsid = value;
    }
    
    public CharSequence getSpontaneousConnectedBssid() {
        return this.spontaneousConnectedBssid;
    }
    
    public void setSpontaneousConnectedBssid(final CharSequence value) {
        this.spontaneousConnectedBssid = value;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static Builder newBuilder(final Builder other) {
        return new Builder(other);
    }
    
    public static Builder newBuilder(final SpontaneousConnection other) {
        return new Builder(other);
    }
    
    static {
        SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"SpontaneousConnection\",\"namespace\":\"com.alu.hal.sbi.json\",\"fields\":[{\"name\":\"spontaneousConnectedSsid\",\"type\":\"string\"},{\"name\":\"spontaneousConnectedBssid\",\"type\":\"string\"}]}");
    }
    
    public static class Builder extends SpecificRecordBuilderBase<SpontaneousConnection> implements RecordBuilder<SpontaneousConnection>
    {
        private CharSequence spontaneousConnectedSsid;
        private CharSequence spontaneousConnectedBssid;
        
        private Builder() {
            super(SpontaneousConnection.SCHEMA$);
        }
        
        private Builder(final Builder other) {
            super(other);
            if (RecordBuilderBase.isValidValue(this.fields()[0], other.spontaneousConnectedSsid)) {
                this.spontaneousConnectedSsid = this.data().deepCopy(this.fields()[0].schema(), other.spontaneousConnectedSsid);
                this.fieldSetFlags()[0] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[1], other.spontaneousConnectedBssid)) {
                this.spontaneousConnectedBssid = this.data().deepCopy(this.fields()[1].schema(), other.spontaneousConnectedBssid);
                this.fieldSetFlags()[1] = true;
            }
        }
        
        private Builder(final SpontaneousConnection other) {
            super(SpontaneousConnection.SCHEMA$);
            if (RecordBuilderBase.isValidValue(this.fields()[0], other.spontaneousConnectedSsid)) {
                this.spontaneousConnectedSsid = this.data().deepCopy(this.fields()[0].schema(), other.spontaneousConnectedSsid);
                this.fieldSetFlags()[0] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[1], other.spontaneousConnectedBssid)) {
                this.spontaneousConnectedBssid = this.data().deepCopy(this.fields()[1].schema(), other.spontaneousConnectedBssid);
                this.fieldSetFlags()[1] = true;
            }
        }
        
        public CharSequence getSpontaneousConnectedSsid() {
            return this.spontaneousConnectedSsid;
        }
        
        public Builder setSpontaneousConnectedSsid(final CharSequence value) {
            this.validate(this.fields()[0], value);
            this.spontaneousConnectedSsid = value;
            this.fieldSetFlags()[0] = true;
            return this;
        }
        
        public boolean hasSpontaneousConnectedSsid() {
            return this.fieldSetFlags()[0];
        }
        
        public Builder clearSpontaneousConnectedSsid() {
            this.spontaneousConnectedSsid = null;
            this.fieldSetFlags()[0] = false;
            return this;
        }
        
        public CharSequence getSpontaneousConnectedBssid() {
            return this.spontaneousConnectedBssid;
        }
        
        public Builder setSpontaneousConnectedBssid(final CharSequence value) {
            this.validate(this.fields()[1], value);
            this.spontaneousConnectedBssid = value;
            this.fieldSetFlags()[1] = true;
            return this;
        }
        
        public boolean hasSpontaneousConnectedBssid() {
            return this.fieldSetFlags()[1];
        }
        
        public Builder clearSpontaneousConnectedBssid() {
            this.spontaneousConnectedBssid = null;
            this.fieldSetFlags()[1] = false;
            return this;
        }
        
        @Override
        public SpontaneousConnection build() {
            try {
                final SpontaneousConnection record = new SpontaneousConnection();
                record.spontaneousConnectedSsid = (CharSequence)(this.fieldSetFlags()[0] ? this.spontaneousConnectedSsid : this.defaultValue(this.fields()[0]));
                record.spontaneousConnectedBssid = (CharSequence)(this.fieldSetFlags()[1] ? this.spontaneousConnectedBssid : this.defaultValue(this.fields()[1]));
                return record;
            }
            catch (Exception e) {
                throw new AvroRuntimeException(e);
            }
        }
    }
}
