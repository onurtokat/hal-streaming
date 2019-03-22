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
public class ManagedDeviceDetail extends SpecificRecordBase implements SpecificRecord
{
    public static final Schema SCHEMA$;
    private boolean isGateway;
    private List<Radio> radios;
    
    public static Schema getClassSchema() {
        return ManagedDeviceDetail.SCHEMA$;
    }
    
    public ManagedDeviceDetail() {
    }
    
    public ManagedDeviceDetail(final Boolean isGateway, final List<Radio> radios) {
        this.isGateway = isGateway;
        this.radios = radios;
    }
    
    @Override
    public Schema getSchema() {
        return ManagedDeviceDetail.SCHEMA$;
    }
    
    @Override
    public Object get(final int field$) {
        switch (field$) {
            case 0: {
                return this.isGateway;
            }
            case 1: {
                return this.radios;
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
                this.isGateway = (boolean)value$;
                break;
            }
            case 1: {
                this.radios = (List<Radio>)value$;
                break;
            }
            default: {
                throw new AvroRuntimeException("Bad index");
            }
        }
    }
    
    public Boolean getIsGateway() {
        return this.isGateway;
    }
    
    public void setIsGateway(final Boolean value) {
        this.isGateway = value;
    }
    
    public List<Radio> getRadios() {
        return this.radios;
    }
    
    public void setRadios(final List<Radio> value) {
        this.radios = value;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static Builder newBuilder(final Builder other) {
        return new Builder(other);
    }
    
    public static Builder newBuilder(final ManagedDeviceDetail other) {
        return new Builder(other);
    }
    
    static {
        SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"ManagedDeviceDetail\",\"namespace\":\"com.alu.hal.sbi.json\",\"fields\":[{\"name\":\"isGateway\",\"type\":\"boolean\"},{\"name\":\"radios\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"Radio\",\"fields\":[{\"name\":\"ssid\",\"type\":\"string\"},{\"name\":\"bssid\",\"type\":\"string\"},{\"name\":\"band_GHz\",\"type\":{\"type\":\"enum\",\"name\":\"Band\",\"symbols\":[\"BAND24\",\"BAND50\"]}}]}}}]}");
    }
    
    public static class Builder extends SpecificRecordBuilderBase<ManagedDeviceDetail> implements RecordBuilder<ManagedDeviceDetail>
    {
        private boolean isGateway;
        private List<Radio> radios;
        
        private Builder() {
            super(ManagedDeviceDetail.SCHEMA$);
        }
        
        private Builder(final Builder other) {
            super(other);
            if (RecordBuilderBase.isValidValue(this.fields()[0], other.isGateway)) {
                this.isGateway = this.data().deepCopy(this.fields()[0].schema(), other.isGateway);
                this.fieldSetFlags()[0] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[1], other.radios)) {
                this.radios = this.data().deepCopy(this.fields()[1].schema(), other.radios);
                this.fieldSetFlags()[1] = true;
            }
        }
        
        private Builder(final ManagedDeviceDetail other) {
            super(ManagedDeviceDetail.SCHEMA$);
            if (RecordBuilderBase.isValidValue(this.fields()[0], other.isGateway)) {
                this.isGateway = this.data().deepCopy(this.fields()[0].schema(), other.isGateway);
                this.fieldSetFlags()[0] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[1], other.radios)) {
                this.radios = this.data().deepCopy(this.fields()[1].schema(), other.radios);
                this.fieldSetFlags()[1] = true;
            }
        }
        
        public Boolean getIsGateway() {
            return this.isGateway;
        }
        
        public Builder setIsGateway(final boolean value) {
            this.validate(this.fields()[0], value);
            this.isGateway = value;
            this.fieldSetFlags()[0] = true;
            return this;
        }
        
        public boolean hasIsGateway() {
            return this.fieldSetFlags()[0];
        }
        
        public Builder clearIsGateway() {
            this.fieldSetFlags()[0] = false;
            return this;
        }
        
        public List<Radio> getRadios() {
            return this.radios;
        }
        
        public Builder setRadios(final List<Radio> value) {
            this.validate(this.fields()[1], value);
            this.radios = value;
            this.fieldSetFlags()[1] = true;
            return this;
        }
        
        public boolean hasRadios() {
            return this.fieldSetFlags()[1];
        }
        
        public Builder clearRadios() {
            this.radios = null;
            this.fieldSetFlags()[1] = false;
            return this;
        }
        
        @Override
        public ManagedDeviceDetail build() {
            try {
                final ManagedDeviceDetail record = new ManagedDeviceDetail();
                record.isGateway = (boolean)(this.fieldSetFlags()[0] ? this.isGateway : this.defaultValue(this.fields()[0]));
                record.radios = (List<Radio>)(this.fieldSetFlags()[1] ? this.radios : this.defaultValue(this.fields()[1]));
                return record;
            }
            catch (Exception e) {
                throw new AvroRuntimeException(e);
            }
        }
    }
}
