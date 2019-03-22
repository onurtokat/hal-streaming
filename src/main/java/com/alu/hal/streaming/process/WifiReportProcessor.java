// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.hal.streaming.process;

import com.alu.motive.hal.commons.dto.TR69DTO;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import java.io.Serializable;

public interface WifiReportProcessor extends Serializable
{
    void processReport(final JavaStreamingContext p0, final JavaDStream<TR69DTO> p1, final JavaDStream<String> p2);
}
