// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import org.apache.spark.sql.types.DataTypes;
import com.alu.hal.streaming.exception.ParameterFormatException;
import java.util.Arrays;
import org.apache.spark.api.java.Optional;
import org.apache.spark.sql.types.DataType;
import com.alu.motive.hal.commons.generated.parser.templates.TypeEnum;
import org.apache.log4j.Logger;
import java.io.Serializable;

public enum ParamType implements Serializable
{
    STRING(TypeEnum.STRING, DataTypes.StringType, "string", Optional.absent()) {
        @Override
        public Object toDataTypeObject(final String value) {
            return value;
        }
        
        @Override
        public boolean isNumerical() {
            return false;
        }
    }, 
    UNSIGNED_INT(TypeEnum.UNSIGNED_INT, DataTypes.LongType, "bigint", Optional.of((Object)4294967295L)) {
        @Override
        public Object toDataTypeObject(final String value) {
            try {
                return (Object)Double.valueOf(value);
            }
            catch (NumberFormatException e) {
                throw new ParameterFormatException("Unable to format parameter value " + value + "to ParamType " + ParamType.UNSIGNED_INT.name(), e);
            }
        }
        
        @Override
        public boolean isNumerical() {
            return true;
        }
    }, 
    UNSIGNED_LONG(TypeEnum.UNSIGNED_LONG, DataTypes.LongType, "bigint", Optional.of((Object)4294967295L)) {
        @Override
        public Object toDataTypeObject(final String value) {
            try {
                return (Object)Double.valueOf(value);
            }
            catch (NumberFormatException e) {
                throw new ParameterFormatException("Unable to format parameter value " + value + "to ParamType " + ParamType.UNSIGNED_LONG.name(), e);
            }
        }
        
        @Override
        public boolean isNumerical() {
            return true;
        }
    }, 
    INT(TypeEnum.INT, DataTypes.IntegerType, "int", Optional.of((Object)2147483647L)) {
        @Override
        public Object toDataTypeObject(final String value) {
            try {
                return (Object)Double.valueOf(value);
            }
            catch (NumberFormatException e) {
                throw new ParameterFormatException("Unable to format parameter value " + value + "to ParamType " + ParamType.INT.name(), e);
            }
        }
        
        @Override
        public boolean isNumerical() {
            return true;
        }
    }, 
    LONG(TypeEnum.LONG, DataTypes.LongType, "bigint", Optional.of((Object)4294967295L)) {
        @Override
        public Object toDataTypeObject(final String value) {
            try {
                return (Object)Double.valueOf(value);
            }
            catch (NumberFormatException e) {
                throw new ParameterFormatException("Unable to format parameter value " + value + "to ParamType " + ParamType.LONG.name(), e);
            }
        }
        
        @Override
        public boolean isNumerical() {
            return true;
        }
    }, 
    BOOLEAN(TypeEnum.BOOLEAN, DataTypes.BooleanType, "boolean", Optional.absent()) {
        @Override
        public Object toDataTypeObject(final String value) {
            if ("1".equals(value)) {
                return Boolean.TRUE;
            }
            return Boolean.valueOf(value);
        }
        
        @Override
        public boolean isNumerical() {
            return false;
        }
    };
    
    private static Logger LOG;
    private final TypeEnum typeEnum;
    private final DataType dataType;
    private final String hiveType;
    private Optional<Long> maxValue;
    
    private ParamType(final TypeEnum typeEnum, final DataType dataType, final String hiveType, final Optional<Long> maxValue) {
        this.typeEnum = typeEnum;
        this.dataType = dataType;
        this.hiveType = hiveType;
        this.maxValue = maxValue;
    }
    
    public static ParamType fromDataType(final DataType dataType) {
        return Arrays.stream(values()).filter(paramType -> paramType.getDataType().equals(dataType)).findFirst().orElseThrow(() -> new IllegalArgumentException(dataType.toString()));
    }
    
    public static ParamType fromTypeEnum(final TypeEnum typeEnum) {
        return Arrays.stream(values()).filter(paramType -> paramType.getTypeEnum().equals(typeEnum)).findFirst().orElseThrow(() -> new IllegalArgumentException(typeEnum.toString()));
    }
    
    public static Object getTypedValue(final DataType dataType, final String value) throws ParameterFormatException {
        Object dataTypeObject = null;
        dataTypeObject = fromDataType(dataType).toDataTypeObject(value);
        return dataTypeObject;
    }
    
    public TypeEnum getTypeEnum() {
        return this.typeEnum;
    }
    
    public DataType getDataType() {
        return this.dataType;
    }
    
    public String getHiveType() {
        return this.hiveType;
    }
    
    public Optional<Long> getMaxValue() {
        return this.maxValue;
    }
    
    public Long getLongMaxValue() {
        return this.maxValue.isPresent() ? ((Long)this.maxValue.get()) : null;
    }
    
    public abstract Object toDataTypeObject(final String p0) throws ParameterFormatException;
    
    public abstract boolean isNumerical();
    
    static {
        ParamType.LOG = Logger.getLogger(ParamType.class);
    }
}
