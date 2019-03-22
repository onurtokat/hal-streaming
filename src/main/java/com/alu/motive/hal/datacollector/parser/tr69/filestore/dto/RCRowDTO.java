// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.parser.tr69.filestore.dto;

import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.helpers.LogLog;
import org.apache.hadoop.hive.serde2.columnar.BytesRefWritable;
import org.apache.hadoop.hive.serde2.columnar.BytesRefArrayWritable;
import org.slf4j.Logger;

public class RCRowDTO extends RowDTO
{
    private static final Logger LOG;
    protected BytesRefArrayWritable rowBytes;
    protected int columnCount;
    int curColumnIdx;
    
    public RCRowDTO() {
        this.rowBytes = new BytesRefArrayWritable(20);
        this.columnCount = 0;
        this.curColumnIdx = 0;
        this.sizeOfPersistableRow = 0L;
    }
    
    @Override
    public void append(final String value) {
        if (value != null) {
            try {
                final byte[] b = value.getBytes("UTF-8");
                final BytesRefWritable columnBytes = new BytesRefWritable(b, 0, b.length);
                this.rowBytes.set(this.curColumnIdx, columnBytes);
                this.sizeOfPersistableRow += value.length();
                ++this.curColumnIdx;
            }
            catch (Throwable e) {
                LogLog.error("RCRowDTO::append", e);
            }
        }
    }
    
    @Override
    public String toString() {
        return "RCRowDTO";
    }
    
    public BytesRefArrayWritable getRowBytes() {
        return this.rowBytes;
    }
    
    public List<String> getColumns() {
        final ArrayList<String> columns = new ArrayList<String>();
        for (int i = 0; i < this.rowBytes.size(); ++i) {
            try {
                columns.add(new String(this.rowBytes.get(i).getBytesCopy(), "UTF-8"));
            }
            catch (IOException e) {
                RCRowDTO.LOG.error("Exception was thrown:", e);
            }
        }
        return columns;
    }
    
    @Override
    public boolean equals(final Object arg0) {
        if (!(arg0 instanceof RCRowDTO)) {
            return false;
        }
        final BytesRefArrayWritable arg = ((RCRowDTO)arg0).getRowBytes();
        final StringBuilder sb = new StringBuilder(50);
        for (int i = 0; i < arg.size(); ++i) {
            if (i != 0) {
                sb.append(',');
            }
            sb.append(arg.get(i));
        }
        final StringBuilder sb2 = new StringBuilder(50);
        for (int j = 0; j < this.rowBytes.size(); ++j) {
            if (j != 0) {
                sb2.append(',');
            }
            sb2.append(this.rowBytes.get(j));
        }
        return sb.toString().equals(sb2.toString());
    }
    
    static {
        LOG = LoggerFactory.getLogger(RCRowDTO.class);
    }
}
