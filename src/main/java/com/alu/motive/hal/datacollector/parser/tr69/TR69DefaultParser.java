// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.parser.tr69;

import org.slf4j.LoggerFactory;
import com.alu.motive.hal.datacollector.commons.plugin.dto.DataCollectorDTO;
import java.util.ArrayList;
import com.alu.motive.hal.commons.dto.ParameterDTO;
import java.util.List;
import com.alu.motive.hal.commons.dto.DeviceIdDTO;
import org.xml.sax.helpers.DefaultHandler;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import com.alu.motive.hal.datacollector.parser.tr69.sax.TR069SAXDataHandler;
import java.io.UnsupportedEncodingException;
import com.alu.motive.hal.datacollector.commons.plugin.parser.ParseException;
import java.util.Iterator;
import java.util.Map;
import com.alu.motive.hal.datacollector.commons.configuration.NonexistentConfiguration;
import java.util.HashSet;
import com.alu.motive.hal.datacollector.commons.configuration.DataCollectorConfigurationService;
import java.util.Set;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.slf4j.Logger;
import com.alu.motive.hal.datacollector.commons.plugin.parser.PluginDataParser;

public class TR69DefaultParser implements PluginDataParser<TR69DataCollectorDTO>
{
    private static final String UTF_8 = "utf-8";
    private static Logger logger;
    private static final SAXParserFactory saxFactory;
    private SAXParser saxParser;
    private String pluginName;
    private static final long maxLoggedMalformedRequestSize = 2097152L;
    private Set<String> paramWhiteList;
    
    public TR69DefaultParser() throws Exception {
        this.saxParser = null;
        this.saxParser = TR69DefaultParser.saxFactory.newSAXParser();
    }
    
    @Override
    public void setPluginName(final String pluginName) {
        this.pluginName = pluginName;
    }
    
    protected String getPluginName() {
        return this.pluginName;
    }
    
    @Override
    public void configure(final DataCollectorConfigurationService configurationService) {
        this.configureParamWhiteList(configurationService);
    }
    
    private void configureParamWhiteList(final DataCollectorConfigurationService configurationService) {
        this.paramWhiteList = new HashSet<String>();
        final String paramWhiteListCfgPath = "plugins/" + this.getPluginName() + "/parser/paramWhiteList";
        TR69DefaultParser.logger.debug("Reading paramWhiteList configuration from " + paramWhiteListCfgPath);
        try {
            final Map<String, String> paramWhiteListMap = configurationService.getList(paramWhiteListCfgPath);
            if (paramWhiteListMap != null && !paramWhiteListMap.isEmpty()) {
                for (final String paramName : paramWhiteListMap.keySet()) {
                    final String paramValue = paramWhiteListMap.get(paramName);
                    TR69DefaultParser.logger.debug("read " + paramName + " = " + paramValue);
                    this.paramWhiteList.add(paramValue);
                }
            }
        }
        catch (NonexistentConfiguration e) {
            TR69DefaultParser.logger.info("No paramWhiteList defined for " + this.getPluginName() + "'s parser.");
            TR69DefaultParser.logger.debug(e.getMessage(), e);
        }
    }
    
    @Override
    public synchronized TR69DataCollectorDTO parse(final Object request) throws ParseException {
        TR69DefaultParser.logger.debug("Global parse ! ");
        try {
            final TR069SAXDataHandler handler = this.doParse(((String)request).getBytes("utf-8"));
            return this.createDcDTO(handler);
        }
        catch (UnsupportedEncodingException e) {
            throw new ParseException("Could not get request bytes using 'utf-8' charset..", e);
        }
    }
    
    private TR069SAXDataHandler doParse(final byte[] bytes) throws ParseException {
        final TR069SAXDataHandler handler = new TR069SAXDataHandler(this.paramWhiteList);
        try {
            this.saxParser.parse(new ByteArrayInputStream(bytes), handler);
            return handler;
        }
        catch (Exception e) {
            if (bytes.length < 2097152L) {
                TR69DefaultParser.logger.error("Parsing error in XML: size; {}Mb", (Object)(bytes.length / 1024 / 1024), e);
                TR69DefaultParser.logger.error(new String(bytes));
            }
            else {
                TR69DefaultParser.logger.error("Parsing the XML failed, but its size is too big ({}) to log. Enable debug log level to see it.", (Object)bytes.length);
                TR69DefaultParser.logger.debug(new String(bytes));
            }
            throw new ParseException(e.getMessage(), e);
        }
    }
    
    private DeviceIdDTO generateDeviceId(final TR069SAXDataHandler handler) throws InvalidDeviceIdException {
        String oui = null;
        String productClass = null;
        String serialNumber = null;
        try {
            oui = handler.getOui().trim();
            productClass = handler.getPc().trim();
            serialNumber = handler.getSn().trim();
            return new DeviceIdDTO(oui, productClass, serialNumber);
        }
        catch (NullPointerException e) {
            final String msg = "Missing mandatory field (oui=" + oui + ",pc=" + productClass + ", sn=" + serialNumber + ")";
            TR69DefaultParser.logger.warn(msg, e);
            throw new InvalidDeviceIdException(msg, e);
        }
    }
    
    private List<ParameterDTO> getParameterList(final TR069SAXDataHandler handler) {
        ArrayList<ParameterDTO> retList = null;
        final ArrayList<TR69Parameter> paramList = handler.getParamsList();
        if (paramList != null) {
            retList = new ArrayList<ParameterDTO>(paramList.size());
            for (final TR69Parameter tr69Parameter : paramList) {
                final ParameterDTO paramDTO = new ParameterDTO(tr69Parameter.getName(), tr69Parameter.getValue());
                paramDTO.setType(tr69Parameter.getType());
                retList.add(paramDTO);
            }
        }
        return retList;
    }
    
    protected TR69DataCollectorDTO createDcDTO(final TR069SAXDataHandler handler) throws InvalidDeviceIdException {
        final TR69DataCollectorDTO retDTO = new TR69DataCollectorDTO(this.generateDeviceId(handler));
        retDTO.setParameterList(this.getParameterList(handler));
        return retDTO;
    }
    
    private static SAXParserFactory getSAXParserFactory() {
        final SAXParserFactory sf = SAXParserFactory.newInstance();
        sf.setValidating(false);
        return sf;
    }
    
    static {
        TR69DefaultParser.logger = LoggerFactory.getLogger(TR69DefaultParser.class);
        saxFactory = getSAXParserFactory();
    }
}
