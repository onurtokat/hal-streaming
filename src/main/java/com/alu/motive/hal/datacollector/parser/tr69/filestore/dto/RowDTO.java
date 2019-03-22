// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.parser.tr69.filestore.dto;

public abstract class RowDTO
{
    protected final char DEFAULT_COLUMN_DELIMITER = '^';
    protected long sizeOfPersistableRow;
    
    public RowDTO() {
        this.sizeOfPersistableRow = 0L;
    }
    
    public long getSizeOfPersistableRow() {
        return this.sizeOfPersistableRow;
    }
    
    public abstract void append(final String p0);
    
    @Override
    public abstract String toString();
}
