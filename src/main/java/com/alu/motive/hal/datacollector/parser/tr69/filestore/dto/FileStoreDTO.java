// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.parser.tr69.filestore.dto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import com.alu.motive.hal.datacollector.commons.plugin.dto.DataCollectorDTO;

public abstract class FileStoreDTO implements DataCollectorDTO
{
    public static final String DATA = "parameters";
    public static final String OPERATION = "operations";
    public static final String ALARM = "alarm";
    public static final Map<String, Integer> COLUMN_COUNT;
    public static final int PARAMETER_COLUMN_COUNT = 13;
    public static final int OPERATION_COLUMN_COUNT = 19;
    public static final int ALARM_COLUMN_COUNT = 15;
    
    private static Map<String, Integer> createColumnMap() {
        final Map<String, Integer> result = new HashMap<String, Integer>(3);
        result.put("parameters", 13);
        result.put("operations", 19);
        result.put("alarm", 15);
        return Collections.unmodifiableMap((Map<? extends String, ? extends Integer>)result);
    }
    
    public abstract boolean isAllowedToCOD();
    
    public abstract void setIsAllowedToCOD(final boolean p0);
    
    public abstract Integer getTtl();
    
    public abstract void setTtl(final Integer p0);
    
    public abstract String getDataType();
    
    public abstract void setDataType(final String p0);
    
    public abstract RowDTO createPersistableRow(final FileStoreFileFormat p0, final int p1, final char p2);
    
    public abstract long getSizeOfPersistableRow(final int p0);
    
    public abstract int getNumberOfPersitableRows();
    
    public abstract String[] getIdentifiers();
    
    public abstract void setIdentifiers(final String[] p0);
    
    @Override
    public abstract String toString();
    
    static {
        COLUMN_COUNT = createColumnMap();
    }
}
