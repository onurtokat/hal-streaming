// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.hive.model;

import org.apache.spark.api.java.Optional;
import com.alu.hal.streaming.exception.ParameterFormatException;
import java.util.Comparator;
import org.apache.log4j.Logger;
import com.alu.motive.hal.commons.dto.ParameterDTO;
import java.io.Serializable;

public class DecoratedParameter implements Serializable
{
    private final ParameterDTO parameterDTO;
    private final ParamMetaData paramMetaData;
    private final Id id;
    private Object typedValue;
    private Object deltaValue;
    private Long devUpTimeRefValue;
    private static Logger LOG;
    public static final Comparator<DecoratedParameter> COMPARING_BY_MOST_NBR_OF_IDXS_FIRST;
    private static final DeltaComputer<Long> DELTA_COMPUTER_OF_LONGS;
    
    public DecoratedParameter(final ParameterDTO parameterDTO, final ParamMetaData paramMetaData) {
        this.parameterDTO = parameterDTO;
        this.paramMetaData = paramMetaData;
        this.id = new Id();
        try {
            this.typedValue = ParamType.getTypedValue(this.paramMetaData.getParamType().getDataType(), this.parameterDTO.getValue());
        }
        catch (ParameterFormatException ex) {
            DecoratedParameter.LOG.warn("Parameter " + this.parameterDTO.getName() + " (" + this.paramMetaData.getParamType().toString() + ") skipped due to invalid value (" + this.parameterDTO.getValue() + ") : " + ex.getMessage());
        }
    }
    
    public ParameterDTO getParameterDTO() {
        return this.parameterDTO;
    }
    
    public ParamMetaData getParamMetaData() {
        return this.paramMetaData;
    }
    
    public Id getId() {
        return this.id;
    }
    
    public String getParameterName() {
        return this.parameterDTO.getName();
    }
    
    public Object getValue() {
        if (this.deltaValue != null) {
            return this.deltaValue;
        }
        return this.typedValue;
    }
    
    private Object getOrigValue() {
        return this.typedValue;
    }
    
    public boolean isCounterParameter() {
        return this.paramMetaData.isCounter();
    }
    
    public boolean isReferenceParameter() {
        return this.paramMetaData.isReferenceParameter();
    }
    
    public boolean isParameterOfReferredGroup() {
        return this.paramMetaData.isParameterOfReferredGroup();
    }
    
    public boolean hasDevUpTimeRefParameter() {
        return this.paramMetaData.isCounter() && ((CounterMetaData)this.paramMetaData.getCounterMetaData().get()).getDevUpTimeParameterRef().isPresent();
    }
    
    public void setDevUpTimeRefValue(final DecoratedParameter devUpTimeRefParameter) {
        switch (devUpTimeRefParameter.getParamMetaData().getParamType()) {
            case INT: {
                this.devUpTimeRefValue = (long)devUpTimeRefParameter.getValue();
                break;
            }
            case LONG: {
                this.devUpTimeRefValue = (Long)devUpTimeRefParameter.getValue();
                break;
            }
            case UNSIGNED_INT: {
                this.devUpTimeRefValue = (Long)devUpTimeRefParameter.getValue();
                break;
            }
            default: {
                DecoratedParameter.LOG.warn("Parameter " + devUpTimeRefParameter.getParameterDTO().getName() + " with type " + devUpTimeRefParameter.getParamMetaData().getParamType() + " is not supported to be casted as rolling over reference parameter");
                break;
            }
        }
    }
    
    public Optional<String> getDevUpTimeRefParameterName() {
        if (this.paramMetaData.getCounterMetaData().isPresent()) {
            return ((CounterMetaData)this.paramMetaData.getCounterMetaData().get()).getDevUpTimeParameterRef();
        }
        return (Optional<String>)Optional.absent();
    }
    
    private boolean isResetted(final DecoratedParameter previousDecoratedParameter) {
        if (this.hasDevUpTimeRefParameter() && previousDecoratedParameter.hasDevUpTimeRefParameter()) {
            if (previousDecoratedParameter.devUpTimeRefValue != null && this.devUpTimeRefValue != null) {
                return previousDecoratedParameter.devUpTimeRefValue > this.devUpTimeRefValue;
            }
            if (this.devUpTimeRefValue == null) {
                DecoratedParameter.LOG.warn("Can't decide if parameter " + this.getParameterName() + " is resetted, because its uptime value is unavailable");
                return false;
            }
            if (previousDecoratedParameter.devUpTimeRefValue == null) {
                DecoratedParameter.LOG.warn("Can't decide if parameter " + previousDecoratedParameter.getParameterName() + " is resetted, because its uptime value is unavailable");
                return false;
            }
        }
        return false;
    }
    
    public void computeAndUpdateDelta(final DecoratedParameter prevDecoratedParameter) {
        if (prevDecoratedParameter.isCounterParameter() && this.isCounterParameter() && prevDecoratedParameter.getParamMetaData().getParamType() == this.paramMetaData.getParamType()) {
            switch (this.paramMetaData.getParamType()) {
                case UNSIGNED_INT: {
                    this.deltaValue = DecoratedParameter.DELTA_COMPUTER_OF_LONGS.calculate(this, prevDecoratedParameter);
                    break;
                }
                case UNSIGNED_LONG: {
                    this.deltaValue = DecoratedParameter.DELTA_COMPUTER_OF_LONGS.calculate(this, prevDecoratedParameter);
                    break;
                }
                default: {
                    DecoratedParameter.LOG.warn("ParameterType " + this.paramMetaData.getParamType() + " is not supported for counter normalization");
                    break;
                }
            }
        }
    }
    
    public boolean hasDevUpTimeValueSet() {
        return this.devUpTimeRefValue != null;
    }
    
    @Override
    public String toString() {
        return "DecoratedParameter [id=" + this.id + ", parameterDTO=" + this.parameterDTO + ", paramMetaData=" + this.paramMetaData + ", typedValue=" + this.typedValue + ", deltaValue=" + this.deltaValue + ", devUpTimeRefValue=" + this.devUpTimeRefValue + "]";
    }
    
    static {
        DecoratedParameter.LOG = Logger.getLogger(DecoratedParameter.class);
        COMPARING_BY_MOST_NBR_OF_IDXS_FIRST = Comparator.comparingInt(decoratedParameter -> decoratedParameter.getParamMetaData().getTransposeParamColumnMapping().getTransposeIndexColumns().size()).reversed();
        DELTA_COMPUTER_OF_LONGS = new DeltaComputer<Long>() {
            @Override
            protected Long calculateDeltaWithOverflow(final Long currentValue, final Long previousValue, final Long maxValue) {
                return maxValue - previousValue + currentValue;
            }
            
            @Override
            protected Long calculateDeltaWithNormalUpdate(final Long currentValue, final Long previousValue) {
                return currentValue - previousValue;
            }
        };
    }
    
    public class Id
    {
        private String value;
        
        protected Id() {
            this.value = DecoratedParameter.this.parameterDTO.getName() + "^" + DecoratedParameter.this.paramMetaData.getId();
        }
        
        String getValue() {
            return this.value;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final Id id = (Id)o;
            return this.value.equals(id.value);
        }
        
        @Override
        public int hashCode() {
            return this.value.hashCode();
        }
    }
    
    public abstract static class DeltaComputer<T extends Number> implements Serializable
    {
        public T calculate(final DecoratedParameter currentDecoratedParameter, final DecoratedParameter previousDecoratedParameter) {
            final T currentValue = (T)currentDecoratedParameter.getOrigValue();
            final T previousValue = (T)previousDecoratedParameter.getOrigValue();
            if (previousValue != null && currentValue != null) {
                if (currentDecoratedParameter.isResetted(previousDecoratedParameter)) {
                    return this.calculateDeltaWithReset(currentValue, previousValue);
                }
                if (((Comparable)previousValue).compareTo(currentValue) <= 0) {
                    return this.calculateDeltaWithNormalUpdate(currentValue, previousValue);
                }
                final Long maxValueThreshold = ((CounterMetaData)currentDecoratedParameter.getParamMetaData().getCounterMetaData().get()).getMaxValueThreshold();
                if (maxValueThreshold != null) {
                    return this.calculateDeltaWithOverflow(currentValue, previousValue, maxValueThreshold);
                }
                return this.calculateDeltaWithReset(currentValue, previousValue);
            }
            else {
                if (previousValue == null) {
                    return currentValue;
                }
                return previousValue;
            }
        }
        
        private T calculateDeltaWithReset(final T curValue, final T prevValue) {
            return curValue;
        }
        
        protected abstract T calculateDeltaWithOverflow(final T p0, final T p1, final Long p2);
        
        protected abstract T calculateDeltaWithNormalUpdate(final T p0, final T p1);
    }
}
