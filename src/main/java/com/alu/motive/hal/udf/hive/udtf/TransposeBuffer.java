// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.udf.hive.udtf;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import java.util.Map;

public class TransposeBuffer
{
    private TransposeInputEvent bufferedEvent;
    
    TransposeBuffer() {
        this.bufferedEvent = null;
    }
    
    public void dispatchEvent(final TransposeEventsToObservationsUDTF udtf, final TransposeInputEvent incomingEvent) throws HiveException {
        if (this.bufferedEvent == null) {
            this.initializeBuffer(incomingEvent);
        }
        else if (this.bufferedEvent.hasTheSameMonitoredPoint(incomingEvent)) {
            final Map.Entry<String, String> useCase = incomingEvent.getUsecase();
            if (useCase != null) {
                this.bufferedEvent.addUsecase(useCase.getKey(), useCase.getValue());
            }
        }
        else {
            udtf.forwardEvent(this.bufferedEvent);
            this.initializeBuffer(incomingEvent);
        }
    }
    
    public void dispatchBuffer(final TransposeEventsToObservationsUDTF udtf) throws HiveException {
        if (this.bufferedEvent != null) {
            udtf.forwardEvent(this.bufferedEvent);
            this.initializeBuffer(null);
        }
    }
    
    protected void initializeBuffer(final TransposeInputEvent event) {
        this.bufferedEvent = event;
    }
}
