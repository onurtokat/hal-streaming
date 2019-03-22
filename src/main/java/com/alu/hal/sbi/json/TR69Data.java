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
public class TR69Data extends SpecificRecordBase implements SpecificRecord
{
    public static final Schema SCHEMA$;
    private CharSequence device_id;
    private CharSequence oui;
    private CharSequence product_class;
    private CharSequence serial_number;
    private CharSequence parameter_name;
    private CharSequence parameter_value;
    private CharSequence parameter_type;
    private long collection_time;
    private CharSequence custom_attr1;
    private CharSequence custom_attr2;
    private CharSequence custom_attr3;
    private CharSequence custom_attr4;
    private CharSequence custom_attr5;
    
    public static Schema getClassSchema() {
        return TR69Data.SCHEMA$;
    }
    
    public TR69Data() {
    }
    
    public TR69Data(final CharSequence device_id, final CharSequence oui, final CharSequence product_class, final CharSequence serial_number, final CharSequence parameter_name, final CharSequence parameter_value, final CharSequence parameter_type, final Long collection_time, final CharSequence custom_attr1, final CharSequence custom_attr2, final CharSequence custom_attr3, final CharSequence custom_attr4, final CharSequence custom_attr5) {
        this.device_id = device_id;
        this.oui = oui;
        this.product_class = product_class;
        this.serial_number = serial_number;
        this.parameter_name = parameter_name;
        this.parameter_value = parameter_value;
        this.parameter_type = parameter_type;
        this.collection_time = collection_time;
        this.custom_attr1 = custom_attr1;
        this.custom_attr2 = custom_attr2;
        this.custom_attr3 = custom_attr3;
        this.custom_attr4 = custom_attr4;
        this.custom_attr5 = custom_attr5;
    }
    
    @Override
    public Schema getSchema() {
        return TR69Data.SCHEMA$;
    }
    
    @Override
    public Object get(final int field$) {
        switch (field$) {
            case 0: {
                return this.device_id;
            }
            case 1: {
                return this.oui;
            }
            case 2: {
                return this.product_class;
            }
            case 3: {
                return this.serial_number;
            }
            case 4: {
                return this.parameter_name;
            }
            case 5: {
                return this.parameter_value;
            }
            case 6: {
                return this.parameter_type;
            }
            case 7: {
                return this.collection_time;
            }
            case 8: {
                return this.custom_attr1;
            }
            case 9: {
                return this.custom_attr2;
            }
            case 10: {
                return this.custom_attr3;
            }
            case 11: {
                return this.custom_attr4;
            }
            case 12: {
                return this.custom_attr5;
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
                this.device_id = (CharSequence)value$;
                break;
            }
            case 1: {
                this.oui = (CharSequence)value$;
                break;
            }
            case 2: {
                this.product_class = (CharSequence)value$;
                break;
            }
            case 3: {
                this.serial_number = (CharSequence)value$;
                break;
            }
            case 4: {
                this.parameter_name = (CharSequence)value$;
                break;
            }
            case 5: {
                this.parameter_value = (CharSequence)value$;
                break;
            }
            case 6: {
                this.parameter_type = (CharSequence)value$;
                break;
            }
            case 7: {
                this.collection_time = (long)value$;
                break;
            }
            case 8: {
                this.custom_attr1 = (CharSequence)value$;
                break;
            }
            case 9: {
                this.custom_attr2 = (CharSequence)value$;
                break;
            }
            case 10: {
                this.custom_attr3 = (CharSequence)value$;
                break;
            }
            case 11: {
                this.custom_attr4 = (CharSequence)value$;
                break;
            }
            case 12: {
                this.custom_attr5 = (CharSequence)value$;
                break;
            }
            default: {
                throw new AvroRuntimeException("Bad index");
            }
        }
    }
    
    public CharSequence getDeviceId() {
        return this.device_id;
    }
    
    public void setDeviceId(final CharSequence value) {
        this.device_id = value;
    }
    
    public CharSequence getOui() {
        return this.oui;
    }
    
    public void setOui(final CharSequence value) {
        this.oui = value;
    }
    
    public CharSequence getProductClass() {
        return this.product_class;
    }
    
    public void setProductClass(final CharSequence value) {
        this.product_class = value;
    }
    
    public CharSequence getSerialNumber() {
        return this.serial_number;
    }
    
    public void setSerialNumber(final CharSequence value) {
        this.serial_number = value;
    }
    
    public CharSequence getParameterName() {
        return this.parameter_name;
    }
    
    public void setParameterName(final CharSequence value) {
        this.parameter_name = value;
    }
    
    public CharSequence getParameterValue() {
        return this.parameter_value;
    }
    
    public void setParameterValue(final CharSequence value) {
        this.parameter_value = value;
    }
    
    public CharSequence getParameterType() {
        return this.parameter_type;
    }
    
    public void setParameterType(final CharSequence value) {
        this.parameter_type = value;
    }
    
    public Long getCollectionTime() {
        return this.collection_time;
    }
    
    public void setCollectionTime(final Long value) {
        this.collection_time = value;
    }
    
    public CharSequence getCustomAttr1() {
        return this.custom_attr1;
    }
    
    public void setCustomAttr1(final CharSequence value) {
        this.custom_attr1 = value;
    }
    
    public CharSequence getCustomAttr2() {
        return this.custom_attr2;
    }
    
    public void setCustomAttr2(final CharSequence value) {
        this.custom_attr2 = value;
    }
    
    public CharSequence getCustomAttr3() {
        return this.custom_attr3;
    }
    
    public void setCustomAttr3(final CharSequence value) {
        this.custom_attr3 = value;
    }
    
    public CharSequence getCustomAttr4() {
        return this.custom_attr4;
    }
    
    public void setCustomAttr4(final CharSequence value) {
        this.custom_attr4 = value;
    }
    
    public CharSequence getCustomAttr5() {
        return this.custom_attr5;
    }
    
    public void setCustomAttr5(final CharSequence value) {
        this.custom_attr5 = value;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static Builder newBuilder(final Builder other) {
        return new Builder(other);
    }
    
    public static Builder newBuilder(final TR69Data other) {
        return new Builder(other);
    }
    
    static {
        SCHEMA$ = new Schema.Parser().parse("{\"type\":\"record\",\"name\":\"TR69Data\",\"namespace\":\"com.alu.hal.sbi.json\",\"fields\":[{\"name\":\"device_id\",\"type\":\"string\"},{\"name\":\"oui\",\"type\":\"string\"},{\"name\":\"product_class\",\"type\":\"string\"},{\"name\":\"serial_number\",\"type\":\"string\"},{\"name\":\"parameter_name\",\"type\":\"string\"},{\"name\":\"parameter_value\",\"type\":[\"string\",\"null\"]},{\"name\":\"parameter_type\",\"type\":[\"string\",\"null\"]},{\"name\":\"collection_time\",\"type\":\"long\"},{\"name\":\"custom_attr1\",\"type\":[\"string\",\"null\"]},{\"name\":\"custom_attr2\",\"type\":[\"string\",\"null\"]},{\"name\":\"custom_attr3\",\"type\":\"string\"},{\"name\":\"custom_attr4\",\"type\":\"string\"},{\"name\":\"custom_attr5\",\"type\":\"string\"}]}");
    }
    
    public static class Builder extends SpecificRecordBuilderBase<TR69Data> implements RecordBuilder<TR69Data>
    {
        private CharSequence device_id;
        private CharSequence oui;
        private CharSequence product_class;
        private CharSequence serial_number;
        private CharSequence parameter_name;
        private CharSequence parameter_value;
        private CharSequence parameter_type;
        private long collection_time;
        private CharSequence custom_attr1;
        private CharSequence custom_attr2;
        private CharSequence custom_attr3;
        private CharSequence custom_attr4;
        private CharSequence custom_attr5;
        
        private Builder() {
            super(TR69Data.SCHEMA$);
        }
        
        private Builder(final Builder other) {
            super(other);
            if (RecordBuilderBase.isValidValue(this.fields()[0], other.device_id)) {
                this.device_id = this.data().deepCopy(this.fields()[0].schema(), other.device_id);
                this.fieldSetFlags()[0] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[1], other.oui)) {
                this.oui = this.data().deepCopy(this.fields()[1].schema(), other.oui);
                this.fieldSetFlags()[1] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[2], other.product_class)) {
                this.product_class = this.data().deepCopy(this.fields()[2].schema(), other.product_class);
                this.fieldSetFlags()[2] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[3], other.serial_number)) {
                this.serial_number = this.data().deepCopy(this.fields()[3].schema(), other.serial_number);
                this.fieldSetFlags()[3] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[4], other.parameter_name)) {
                this.parameter_name = this.data().deepCopy(this.fields()[4].schema(), other.parameter_name);
                this.fieldSetFlags()[4] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[5], other.parameter_value)) {
                this.parameter_value = this.data().deepCopy(this.fields()[5].schema(), other.parameter_value);
                this.fieldSetFlags()[5] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[6], other.parameter_type)) {
                this.parameter_type = this.data().deepCopy(this.fields()[6].schema(), other.parameter_type);
                this.fieldSetFlags()[6] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[7], other.collection_time)) {
                this.collection_time = this.data().deepCopy(this.fields()[7].schema(), other.collection_time);
                this.fieldSetFlags()[7] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[8], other.custom_attr1)) {
                this.custom_attr1 = this.data().deepCopy(this.fields()[8].schema(), other.custom_attr1);
                this.fieldSetFlags()[8] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[9], other.custom_attr2)) {
                this.custom_attr2 = this.data().deepCopy(this.fields()[9].schema(), other.custom_attr2);
                this.fieldSetFlags()[9] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[10], other.custom_attr3)) {
                this.custom_attr3 = this.data().deepCopy(this.fields()[10].schema(), other.custom_attr3);
                this.fieldSetFlags()[10] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[11], other.custom_attr4)) {
                this.custom_attr4 = this.data().deepCopy(this.fields()[11].schema(), other.custom_attr4);
                this.fieldSetFlags()[11] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[12], other.custom_attr5)) {
                this.custom_attr5 = this.data().deepCopy(this.fields()[12].schema(), other.custom_attr5);
                this.fieldSetFlags()[12] = true;
            }
        }
        
        private Builder(final TR69Data other) {
            super(TR69Data.SCHEMA$);
            if (RecordBuilderBase.isValidValue(this.fields()[0], other.device_id)) {
                this.device_id = this.data().deepCopy(this.fields()[0].schema(), other.device_id);
                this.fieldSetFlags()[0] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[1], other.oui)) {
                this.oui = this.data().deepCopy(this.fields()[1].schema(), other.oui);
                this.fieldSetFlags()[1] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[2], other.product_class)) {
                this.product_class = this.data().deepCopy(this.fields()[2].schema(), other.product_class);
                this.fieldSetFlags()[2] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[3], other.serial_number)) {
                this.serial_number = this.data().deepCopy(this.fields()[3].schema(), other.serial_number);
                this.fieldSetFlags()[3] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[4], other.parameter_name)) {
                this.parameter_name = this.data().deepCopy(this.fields()[4].schema(), other.parameter_name);
                this.fieldSetFlags()[4] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[5], other.parameter_value)) {
                this.parameter_value = this.data().deepCopy(this.fields()[5].schema(), other.parameter_value);
                this.fieldSetFlags()[5] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[6], other.parameter_type)) {
                this.parameter_type = this.data().deepCopy(this.fields()[6].schema(), other.parameter_type);
                this.fieldSetFlags()[6] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[7], other.collection_time)) {
                this.collection_time = this.data().deepCopy(this.fields()[7].schema(), other.collection_time);
                this.fieldSetFlags()[7] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[8], other.custom_attr1)) {
                this.custom_attr1 = this.data().deepCopy(this.fields()[8].schema(), other.custom_attr1);
                this.fieldSetFlags()[8] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[9], other.custom_attr2)) {
                this.custom_attr2 = this.data().deepCopy(this.fields()[9].schema(), other.custom_attr2);
                this.fieldSetFlags()[9] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[10], other.custom_attr3)) {
                this.custom_attr3 = this.data().deepCopy(this.fields()[10].schema(), other.custom_attr3);
                this.fieldSetFlags()[10] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[11], other.custom_attr4)) {
                this.custom_attr4 = this.data().deepCopy(this.fields()[11].schema(), other.custom_attr4);
                this.fieldSetFlags()[11] = true;
            }
            if (RecordBuilderBase.isValidValue(this.fields()[12], other.custom_attr5)) {
                this.custom_attr5 = this.data().deepCopy(this.fields()[12].schema(), other.custom_attr5);
                this.fieldSetFlags()[12] = true;
            }
        }
        
        public CharSequence getDeviceId() {
            return this.device_id;
        }
        
        public Builder setDeviceId(final CharSequence value) {
            this.validate(this.fields()[0], value);
            this.device_id = value;
            this.fieldSetFlags()[0] = true;
            return this;
        }
        
        public boolean hasDeviceId() {
            return this.fieldSetFlags()[0];
        }
        
        public Builder clearDeviceId() {
            this.device_id = null;
            this.fieldSetFlags()[0] = false;
            return this;
        }
        
        public CharSequence getOui() {
            return this.oui;
        }
        
        public Builder setOui(final CharSequence value) {
            this.validate(this.fields()[1], value);
            this.oui = value;
            this.fieldSetFlags()[1] = true;
            return this;
        }
        
        public boolean hasOui() {
            return this.fieldSetFlags()[1];
        }
        
        public Builder clearOui() {
            this.oui = null;
            this.fieldSetFlags()[1] = false;
            return this;
        }
        
        public CharSequence getProductClass() {
            return this.product_class;
        }
        
        public Builder setProductClass(final CharSequence value) {
            this.validate(this.fields()[2], value);
            this.product_class = value;
            this.fieldSetFlags()[2] = true;
            return this;
        }
        
        public boolean hasProductClass() {
            return this.fieldSetFlags()[2];
        }
        
        public Builder clearProductClass() {
            this.product_class = null;
            this.fieldSetFlags()[2] = false;
            return this;
        }
        
        public CharSequence getSerialNumber() {
            return this.serial_number;
        }
        
        public Builder setSerialNumber(final CharSequence value) {
            this.validate(this.fields()[3], value);
            this.serial_number = value;
            this.fieldSetFlags()[3] = true;
            return this;
        }
        
        public boolean hasSerialNumber() {
            return this.fieldSetFlags()[3];
        }
        
        public Builder clearSerialNumber() {
            this.serial_number = null;
            this.fieldSetFlags()[3] = false;
            return this;
        }
        
        public CharSequence getParameterName() {
            return this.parameter_name;
        }
        
        public Builder setParameterName(final CharSequence value) {
            this.validate(this.fields()[4], value);
            this.parameter_name = value;
            this.fieldSetFlags()[4] = true;
            return this;
        }
        
        public boolean hasParameterName() {
            return this.fieldSetFlags()[4];
        }
        
        public Builder clearParameterName() {
            this.parameter_name = null;
            this.fieldSetFlags()[4] = false;
            return this;
        }
        
        public CharSequence getParameterValue() {
            return this.parameter_value;
        }
        
        public Builder setParameterValue(final CharSequence value) {
            this.validate(this.fields()[5], value);
            this.parameter_value = value;
            this.fieldSetFlags()[5] = true;
            return this;
        }
        
        public boolean hasParameterValue() {
            return this.fieldSetFlags()[5];
        }
        
        public Builder clearParameterValue() {
            this.parameter_value = null;
            this.fieldSetFlags()[5] = false;
            return this;
        }
        
        public CharSequence getParameterType() {
            return this.parameter_type;
        }
        
        public Builder setParameterType(final CharSequence value) {
            this.validate(this.fields()[6], value);
            this.parameter_type = value;
            this.fieldSetFlags()[6] = true;
            return this;
        }
        
        public boolean hasParameterType() {
            return this.fieldSetFlags()[6];
        }
        
        public Builder clearParameterType() {
            this.parameter_type = null;
            this.fieldSetFlags()[6] = false;
            return this;
        }
        
        public Long getCollectionTime() {
            return this.collection_time;
        }
        
        public Builder setCollectionTime(final long value) {
            this.validate(this.fields()[7], value);
            this.collection_time = value;
            this.fieldSetFlags()[7] = true;
            return this;
        }
        
        public boolean hasCollectionTime() {
            return this.fieldSetFlags()[7];
        }
        
        public Builder clearCollectionTime() {
            this.fieldSetFlags()[7] = false;
            return this;
        }
        
        public CharSequence getCustomAttr1() {
            return this.custom_attr1;
        }
        
        public Builder setCustomAttr1(final CharSequence value) {
            this.validate(this.fields()[8], value);
            this.custom_attr1 = value;
            this.fieldSetFlags()[8] = true;
            return this;
        }
        
        public boolean hasCustomAttr1() {
            return this.fieldSetFlags()[8];
        }
        
        public Builder clearCustomAttr1() {
            this.custom_attr1 = null;
            this.fieldSetFlags()[8] = false;
            return this;
        }
        
        public CharSequence getCustomAttr2() {
            return this.custom_attr2;
        }
        
        public Builder setCustomAttr2(final CharSequence value) {
            this.validate(this.fields()[9], value);
            this.custom_attr2 = value;
            this.fieldSetFlags()[9] = true;
            return this;
        }
        
        public boolean hasCustomAttr2() {
            return this.fieldSetFlags()[9];
        }
        
        public Builder clearCustomAttr2() {
            this.custom_attr2 = null;
            this.fieldSetFlags()[9] = false;
            return this;
        }
        
        public CharSequence getCustomAttr3() {
            return this.custom_attr3;
        }
        
        public Builder setCustomAttr3(final CharSequence value) {
            this.validate(this.fields()[10], value);
            this.custom_attr3 = value;
            this.fieldSetFlags()[10] = true;
            return this;
        }
        
        public boolean hasCustomAttr3() {
            return this.fieldSetFlags()[10];
        }
        
        public Builder clearCustomAttr3() {
            this.custom_attr3 = null;
            this.fieldSetFlags()[10] = false;
            return this;
        }
        
        public CharSequence getCustomAttr4() {
            return this.custom_attr4;
        }
        
        public Builder setCustomAttr4(final CharSequence value) {
            this.validate(this.fields()[11], value);
            this.custom_attr4 = value;
            this.fieldSetFlags()[11] = true;
            return this;
        }
        
        public boolean hasCustomAttr4() {
            return this.fieldSetFlags()[11];
        }
        
        public Builder clearCustomAttr4() {
            this.custom_attr4 = null;
            this.fieldSetFlags()[11] = false;
            return this;
        }
        
        public CharSequence getCustomAttr5() {
            return this.custom_attr5;
        }
        
        public Builder setCustomAttr5(final CharSequence value) {
            this.validate(this.fields()[12], value);
            this.custom_attr5 = value;
            this.fieldSetFlags()[12] = true;
            return this;
        }
        
        public boolean hasCustomAttr5() {
            return this.fieldSetFlags()[12];
        }
        
        public Builder clearCustomAttr5() {
            this.custom_attr5 = null;
            this.fieldSetFlags()[12] = false;
            return this;
        }
        
        @Override
        public TR69Data build() {
            try {
                final TR69Data record = new TR69Data();
                record.device_id = (CharSequence)(this.fieldSetFlags()[0] ? this.device_id : this.defaultValue(this.fields()[0]));
                record.oui = (CharSequence)(this.fieldSetFlags()[1] ? this.oui : this.defaultValue(this.fields()[1]));
                record.product_class = (CharSequence)(this.fieldSetFlags()[2] ? this.product_class : this.defaultValue(this.fields()[2]));
                record.serial_number = (CharSequence)(this.fieldSetFlags()[3] ? this.serial_number : this.defaultValue(this.fields()[3]));
                record.parameter_name = (CharSequence)(this.fieldSetFlags()[4] ? this.parameter_name : this.defaultValue(this.fields()[4]));
                record.parameter_value = (CharSequence)(this.fieldSetFlags()[5] ? this.parameter_value : this.defaultValue(this.fields()[5]));
                record.parameter_type = (CharSequence)(this.fieldSetFlags()[6] ? this.parameter_type : this.defaultValue(this.fields()[6]));
                record.collection_time = (long)(this.fieldSetFlags()[7] ? this.collection_time : this.defaultValue(this.fields()[7]));
                record.custom_attr1 = (CharSequence)(this.fieldSetFlags()[8] ? this.custom_attr1 : this.defaultValue(this.fields()[8]));
                record.custom_attr2 = (CharSequence)(this.fieldSetFlags()[9] ? this.custom_attr2 : this.defaultValue(this.fields()[9]));
                record.custom_attr3 = (CharSequence)(this.fieldSetFlags()[10] ? this.custom_attr3 : this.defaultValue(this.fields()[10]));
                record.custom_attr4 = (CharSequence)(this.fieldSetFlags()[11] ? this.custom_attr4 : this.defaultValue(this.fields()[11]));
                record.custom_attr5 = (CharSequence)(this.fieldSetFlags()[12] ? this.custom_attr5 : this.defaultValue(this.fields()[12]));
                return record;
            }
            catch (Exception e) {
                throw new AvroRuntimeException(e);
            }
        }
    }
}
