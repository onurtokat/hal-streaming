// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.parser.tr69.filestore;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import com.alu.motive.hal.datacollector.parser.tr69.TR69DefaultParser;

public class FileStoreTR069InformParser extends TR69DefaultParser
{
    public static final String TR069_DEVICE_ID_SEPRTR = "-";
    private static Logger logger;
    
    static {
        FileStoreTR069InformParser.logger = LoggerFactory.getLogger(FileStoreTR069InformParser.class);
    }
}
