// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.datacollector.dto.converter;

import org.slf4j.LoggerFactory;
import com.alu.motive.hal.datacollector.commons.plugin.dto.DataCollectorDTO;
import java.util.Iterator;
import com.alu.motive.hal.commons.dto.ParameterDTO;
import com.alu.motive.hal.datacollector.commons.plugin.dto.impl.CollectionDTOImpl;
import org.slf4j.Logger;
import com.alu.motive.hal.datacollector.commons.plugin.dto.CollectionDTO;
import com.alu.motive.hal.datacollector.parser.tr69.TR69DataCollectorDTO;
import com.alu.motive.hal.datacollector.commons.plugin.dto.DTOConverter;

public class TR69DTO2Data implements DTOConverter<TR69DataCollectorDTO, CollectionDTO<TR69DataDTO>>
{
    private static final Logger logger;
    
    @Override
    public CollectionDTO<TR69DataDTO> convert(final TR69DataCollectorDTO tr69DTO) {
        final CollectionDTOImpl<TR69DataDTO> collection = new CollectionDTOImpl<TR69DataDTO>();
        TR69DTO2Data.logger.info("Converting {} to {}", tr69DTO.getClass().getName(), collection.getClass().getName());
        for (final ParameterDTO parameter : tr69DTO.getParameterList()) {
            if (!tr69DTO.getAugmentingParameters().contains(parameter.getName())) {
                final TR69DataDTO record = new TR69DataDTO(parameter, tr69DTO);
                collection.add(record);
            }
        }
        return collection;
    }
    
    static {
        logger = LoggerFactory.getLogger(TR69DTO2Data.class);
    }
}
