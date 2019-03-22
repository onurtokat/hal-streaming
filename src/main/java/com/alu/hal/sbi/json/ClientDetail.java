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
public class ClientDetail extends SpecificRecordBase implements SpecificRecord
{
    public static final Schema SCHEMA$;
    private CharSequence mac;
    private CharSequence platform;
    private CharSequence osVersion;
    private CharSequence hostname;
    
    public static Schema getClassSchema() {
        return ClientDetail.SCHEMA$;
    }
    
    public ClientDetail() {
    }
    
    public ClientDetail(final CharSequence mac, final CharSequence platform, final CharSequence osVersion, final CharSequence hostname) {
        this.mac = mac;
        this.platform = platform;
        this.osVersion = osVersion;
        this.hostname = hostname;
    }
    
    @Override
    public Schema getSchema() {
        return ClientDetail.SCHEMA$;
    }
    
    @Override
    public Object get(final int field$) {
        switch (field$) {
            case 0: {
                return this.mac;
            }
            case 1: {
                return this.platform;
            }
            case 2: {
                return this.osVersion;
            }
            case 3: {
                return this.hostname;
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
                this.mac = (CharSequence)value$;
                break;
            }
            case 1: {
                this.platform = (CharSequence)value$;
                break;
            }
            case 2: {
                this.osVersion = (CharSequence)value$;
                break;
            }
            case 3: {
                this.hostname = (CharSequence)value$;
                break;
            }
            default: {
                throw new AvroRuntimeException("Bad index");
            }
        }
    }
    
    public CharSequence getMac() {
        return this.mac;
    }
    
    public void setMac(final CharSequence value) {
        this.mac = value;
    }
    
    public CharSequence getPlatform() {
        return this.platform;
    }
    
    public void setPlatform(final CharSequence value) {
        this.platform = value;
    }
    
    public CharSequence getOsVersion() {
        return this.osVersion;
    }
    
    public void setOsVersion(final CharSequence value) {
        this.osVersion = value;
    }
    
    public CharSequence getHostname() {
        return this.hostname;
    }
    
    public void setHostname(final CharSequence value) {
        this.hostname = value;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static Builder newBuilder(final Builder other) {
        return new Builder(other);
    }
    
    public static Builder newBuilder(final ClientDetail other) {
        return new Builder(other);
    }
    
    static {
        SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"ClientDetail\",\"namespace\":\"com.alu.hal.sbi.json\",\"fields\":[{\"name\":\"mac\",\"type\":\"string\"},{\"name\":\"platform\",\"type\":\"string\"},{\"name\":\"osVersion\",\"type\":\"string\"},{\"name\":\"hostname\",\"type\":\"string\"}]}");
    }
    
    public static class Builder extends SpecificRecordBuilderBase<ClientDetail> implements RecordBuilder<ClientDetail>
    {
        private CharSequence mac;
        private CharSequence platform;
        private CharSequence osVersion;
        private CharSequence hostname;
        
        private Builder() {
            super(ClientDetail.SCHEMA$);
        }
        
        private Builder(final Builder other) {
            super(other);
            if (RecordBuilderBase.isValidValue(this.fields()[0], other.mac)) {
                this.mac = this.data().deepCopy(this.fields()[0].schema(), other.mac);
                this.fieldSetFlags()[0] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[1], other.platform)) {
                this.platform = this.data().deepCopy(this.fields()[1].schema(), other.platform);
                this.fieldSetFlags()[1] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[2], other.osVersion)) {
                this.osVersion = this.data().deepCopy(this.fields()[2].schema(), other.osVersion);
                this.fieldSetFlags()[2] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[3], other.hostname)) {
                this.hostname = this.data().deepCopy(this.fields()[3].schema(), other.hostname);
                this.fieldSetFlags()[3] = true;
            }
        }
        
        private Builder(final ClientDetail other) {
            super(ClientDetail.SCHEMA$);
            if (RecordBuilderBase.isValidValue(this.fields()[0], other.mac)) {
                this.mac = this.data().deepCopy(this.fields()[0].schema(), other.mac);
                this.fieldSetFlags()[0] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[1], other.platform)) {
                this.platform = this.data().deepCopy(this.fields()[1].schema(), other.platform);
                this.fieldSetFlags()[1] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[2], other.osVersion)) {
                this.osVersion = this.data().deepCopy(this.fields()[2].schema(), other.osVersion);
                this.fieldSetFlags()[2] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[3], other.hostname)) {
                this.hostname = this.data().deepCopy(this.fields()[3].schema(), other.hostname);
                this.fieldSetFlags()[3] = true;
            }
        }
        
        public CharSequence getMac() {
            return this.mac;
        }
        
        public Builder setMac(final CharSequence value) {
            this.validate(this.fields()[0], value);
            this.mac = value;
            this.fieldSetFlags()[0] = true;
            return this;
        }
        
        public boolean hasMac() {
            return this.fieldSetFlags()[0];
        }
        
        public Builder clearMac() {
            this.mac = null;
            this.fieldSetFlags()[0] = false;
            return this;
        }
        
        public CharSequence getPlatform() {
            return this.platform;
        }
        
        public Builder setPlatform(final CharSequence value) {
            this.validate(this.fields()[1], value);
            this.platform = value;
            this.fieldSetFlags()[1] = true;
            return this;
        }
        
        public boolean hasPlatform() {
            return this.fieldSetFlags()[1];
        }
        
        public Builder clearPlatform() {
            this.platform = null;
            this.fieldSetFlags()[1] = false;
            return this;
        }
        
        public CharSequence getOsVersion() {
            return this.osVersion;
        }
        
        public Builder setOsVersion(final CharSequence value) {
            this.validate(this.fields()[2], value);
            this.osVersion = value;
            this.fieldSetFlags()[2] = true;
            return this;
        }
        
        public boolean hasOsVersion() {
            return this.fieldSetFlags()[2];
        }
        
        public Builder clearOsVersion() {
            this.osVersion = null;
            this.fieldSetFlags()[2] = false;
            return this;
        }
        
        public CharSequence getHostname() {
            return this.hostname;
        }
        
        public Builder setHostname(final CharSequence value) {
            this.validate(this.fields()[3], value);
            this.hostname = value;
            this.fieldSetFlags()[3] = true;
            return this;
        }
        
        public boolean hasHostname() {
            return this.fieldSetFlags()[3];
        }
        
        public Builder clearHostname() {
            this.hostname = null;
            this.fieldSetFlags()[3] = false;
            return this;
        }
        
        @Override
        public ClientDetail build() {
            try {
                final ClientDetail record = new ClientDetail();
                record.mac = (CharSequence)(this.fieldSetFlags()[0] ? this.mac : this.defaultValue(this.fields()[0]));
                record.platform = (CharSequence)(this.fieldSetFlags()[1] ? this.platform : this.defaultValue(this.fields()[1]));
                record.osVersion = (CharSequence)(this.fieldSetFlags()[2] ? this.osVersion : this.defaultValue(this.fields()[2]));
                record.hostname = (CharSequence)(this.fieldSetFlags()[3] ? this.hostname : this.defaultValue(this.fields()[3]));
                return record;
            }
            catch (Exception e) {
                throw new AvroRuntimeException(e);
            }
        }
    }
}
