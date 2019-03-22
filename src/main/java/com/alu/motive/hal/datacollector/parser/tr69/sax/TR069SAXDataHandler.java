// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.hal.datacollector.parser.tr69.sax;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.Iterator;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import org.slf4j.LoggerFactory;
import java.util.Set;
import org.slf4j.Logger;
import java.util.regex.Pattern;
import java.util.List;
import com.alu.motive.hal.datacollector.parser.tr69.TR69Parameter;
import java.util.ArrayList;
import org.xml.sax.helpers.DefaultHandler;

public class TR069SAXDataHandler extends DefaultHandler
{
    private static final String OUI_TAG = "OUI";
    private static final String PC_TAG = "ProductClass";
    private static final String SN_TAG = "SerialNumber";
    private static final String GPV_OUI_TAG = "DeviceInfo.ManufacturerOUI";
    private static final String GPV_PC_TAG = "DeviceInfo.ProductClass";
    private static final String GPV_SN_TAG = "DeviceInfo.SerialNumber";
    private static final String PCODE_TAG = "ProvisioningCode";
    private String oui;
    private String pc;
    private String sn;
    private String pCode;
    private String name;
    private String value;
    private String type;
    private boolean insidePVS;
    private boolean insidePVSValue;
    private StringBuffer text;
    private ArrayList<TR69Parameter> params;
    private int paramsInInfom;
    private List<Pattern> compiledWhiteList;
    private Logger logger;
    
    public TR069SAXDataHandler(final Set<String> whiteList) {
        this.insidePVS = false;
        this.insidePVSValue = false;
        this.text = new StringBuffer();
        this.params = new ArrayList<TR69Parameter>();
        this.paramsInInfom = 0;
        this.logger = LoggerFactory.getLogger(TR069SAXDataHandler.class);
        this.compiledWhiteList = this.compileWhiteList(whiteList);
    }
    
    public String getOui() {
        return this.oui;
    }
    
    public String getPc() {
        return this.pc;
    }
    
    public String getSn() {
        return this.sn;
    }
    
    public String getPCode() {
        return this.pCode;
    }
    
    public ArrayList<TR69Parameter> getParamsList() {
        return this.params;
    }
    
    public int getParamsInInform() {
        return this.paramsInInfom;
    }
    
    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        if (qName.length() > 2000) {
            throw new SAXException("Entity name is " + qName.length() + " characters. Maximum 2000 allowed.");
        }
        if (qName.equals("ParameterValueStruct")) {
            this.insidePVS = true;
        }
        if (this.insidePVS && qName.equalsIgnoreCase("Value")) {
            this.insidePVSValue = true;
        }
        this.type = attributes.getValue("xsi:type");
        this.text.setLength(0);
    }
    
    @Override
    public void endElement(final String uri, final String localName, final String qName) throws SAXException {
        if (qName.equalsIgnoreCase("ParameterValueStruct")) {
            this.insidePVS = false;
            this.insidePVSValue = false;
        }
        if (qName.equalsIgnoreCase("OUI")) {
            final String valueText = this.text.toString();
            this.oui = valueText.trim();
        }
        else if (qName.equalsIgnoreCase("ProductClass")) {
            final String valueText = this.text.toString();
            this.pc = valueText.trim();
        }
        else if (qName.equalsIgnoreCase("SerialNumber")) {
            final String valueText = this.text.toString();
            this.sn = valueText.trim();
        }
        else if (qName.equals("Name")) {
            final String valueText = this.text.toString();
            this.name = valueText.trim();
        }
        else if (qName.equals("Value")) {
            this.insidePVSValue = false;
            final String valueText = this.text.toString();
            this.text.setLength(0);
            if (this.name.endsWith("DeviceInfo.ProductClass")) {
                this.pc = valueText.trim();
            }
            else if (this.name.endsWith("DeviceInfo.ManufacturerOUI")) {
                this.oui = valueText.trim();
            }
            else if (this.name.endsWith("DeviceInfo.SerialNumber")) {
                this.sn = valueText.trim();
            }
            else if (this.name.endsWith("ProvisioningCode")) {
                this.pCode = valueText.trim();
            }
            this.value = valueText;
            final TR69Parameter parameter = new TR69Parameter();
            parameter.setName(this.name);
            parameter.setValue(this.value);
            ++this.paramsInInfom;
            if (this.matchWhiteList(this.compiledWhiteList, parameter)) {
                parameter.setType(this.resolveXSDDataType(this.type));
                this.params.add(parameter);
            }
            else if (this.logger.isDebugEnabled()) {
                this.logger.debug("Ignoring parameter " + this.name + " as it doesn't match the configured whiteList.");
            }
        }
    }
    
    public String resolveXSDDataType(final String type) {
        final int i;
        if (type != null && type.length() > 0 && (i = type.indexOf(58)) != -1) {
            return type.substring(i + 1).toUpperCase();
        }
        return "UNDEFINED";
    }
    
    @Override
    public void characters(final char[] data, final int start, final int length) throws SAXException {
        if (!this.insidePVSValue && this.text.length() > 2000) {
            throw new SAXException("Entity value is larger than the maximum of 2000 characters allowed.");
        }
        this.text.append(new String(data, start, length));
    }
    
    private boolean matchWhiteList(final List<Pattern> compiledPatterns, final TR69Parameter parameter) {
        for (final Pattern ptrn : compiledPatterns) {
            final Matcher mtchr = ptrn.matcher(parameter.getName());
            if (mtchr.find()) {
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("param for hadoop matched: " + parameter.getName());
                }
                return true;
            }
        }
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("param for hadoop did not match white list patterns: " + parameter.getName());
        }
        return false;
    }
    
    private ArrayList<Pattern> compileWhiteList(Set<String> whiteList) {
        if (whiteList == null || whiteList.isEmpty()) {
            whiteList = this.useDefaultWhiteList();
        }
        final ArrayList<Pattern> compiled = new ArrayList<Pattern>();
        if (null != whiteList) {
            for (final String param : whiteList) {
                try {
                    final Pattern p = Pattern.compile(param);
                    compiled.add(p);
                }
                catch (Exception e) {
                    this.logger.error("Plugin whitelist parameter: {} is an invalid java regex pattern! ", param, e);
                }
            }
        }
        return compiled;
    }
    
    private Set<String> useDefaultWhiteList() {
        this.logger.warn("No paramWhiteList defined. Accepting all received parameters.");
        final Set<String> whiteList = new HashSet<String>();
        whiteList.add(".*");
        return whiteList;
    }
}
