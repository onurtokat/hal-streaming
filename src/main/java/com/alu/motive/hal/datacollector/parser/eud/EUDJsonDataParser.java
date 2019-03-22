// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.parser.eud;

import org.slf4j.LoggerFactory;
import com.alu.motive.hal.datacollector.commons.plugin.parser.ParseException;
import com.alu.motive.hal.datacollector.commons.plugin.dto.DataCollectorDTO;
import com.alu.motive.hal.datacollector.commons.configuration.DataCollectorConfigurationService;
import org.slf4j.Logger;
import com.alu.motive.hal.datacollector.commons.plugin.parser.PluginDataParser;

public class EUDJsonDataParser implements PluginDataParser
{
    private static final Logger logger;
    private String pluginName;
    private JSONValidator jsonValidator;
    
    @Override
    public void configure(final DataCollectorConfigurationService cfgService) {
        this.jsonValidator = new JSONValidator(cfgService, this.pluginName);
    }
    
    @Override
    public DataCollectorDTO parse(final Object request) throws ParseException {
        final String extractedJSON = (String)request;
        try {
            this.jsonValidator.validate(extractedJSON);
        }
        catch (InvalidJSONException e) {
            throw new ParseException("Invalid json message received", e);
        }
        catch (JSONValidationException e2) {
            EUDJsonDataParser.logger.error("Could not validate received json", e2);
        }
        final EUDJsonDataCollectorDTO retDTO = new EUDJsonDataCollectorDTO(extractedJSON);
        return retDTO;
    }
    
    @Override
    public void setPluginName(final String pluginName) {
        this.pluginName = pluginName;
    }
    
    static {
        logger = LoggerFactory.getLogger(EUDJsonDataParser.class);
    }
}
