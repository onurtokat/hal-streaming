// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.parser.tr69.filestore.dto;

import java.util.ArrayList;
import java.util.List;

public class SequenceRowDTO extends RowDTO
{
    private List<String> columns;
    private char columnDelimiter;
    private char[] columndDelArr;
    
    public SequenceRowDTO(final char columnDelimiter) {
        this.columns = new ArrayList<String>();
        this.columnDelimiter = '^';
        this.columndDelArr = new char[1];
        if (columnDelimiter != '\0') {
            this.columnDelimiter = columnDelimiter;
        }
        this.columndDelArr[0] = this.columnDelimiter;
        this.sizeOfPersistableRow = 0L;
    }
    
    public List<String> getColumns() {
        return this.columns;
    }
    
    public void setColumns(final List<String> columns) {
        this.columns = columns;
    }
    
    @Override
    public void append(final String value) {
        if (value != null) {
            this.columns.add(value);
            this.columns.add(new String(this.columndDelArr));
            this.sizeOfPersistableRow += value.length() + this.columndDelArr.length;
        }
    }
    
    @Override
    public String toString() {
        return this.columns.toString();
    }
}
