// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.parser.tr69.filestore.dto;

public class TextRowDTO extends RowDTO
{
    private StringBuilder persistableRow;
    private char columnDelimiter;
    
    public TextRowDTO(final char columnDelimiter) {
        this.persistableRow = new StringBuilder();
        this.columnDelimiter = '^';
        this.columnDelimiter = columnDelimiter;
        this.sizeOfPersistableRow = 0L;
    }
    
    public StringBuilder getPersistableRow() {
        return this.persistableRow;
    }
    
    @Override
    public void append(final String value) {
        if (value != null) {
            this.persistableRow.append(value);
            this.persistableRow.append(this.columnDelimiter);
            this.sizeOfPersistableRow += value.length() + 1;
        }
    }
    
    @Override
    public String toString() {
        return this.persistableRow.toString();
    }
}
