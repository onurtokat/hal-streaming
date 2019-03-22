// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.core.impl.provider.xml;

import java.util.logging.Level;
import javax.ws.rs.core.Context;
import com.sun.jersey.core.util.FeaturesAndProperties;
import java.util.logging.Logger;
import javax.xml.parsers.SAXParserFactory;

public class SAXParserContextProvider extends ThreadLocalSingletonContextProvider<SAXParserFactory>
{
    private static final Logger LOGGER;
    private final boolean disableXmlSecurity;
    
    public SAXParserContextProvider(@Context final FeaturesAndProperties fps) {
        super(SAXParserFactory.class);
        this.disableXmlSecurity = fps.getFeature("com.sun.jersey.config.feature.DisableXmlSecurity");
    }
    
    @Override
    protected SAXParserFactory getInstance() {
        final SAXParserFactory f = SAXParserFactory.newInstance();
        f.setNamespaceAware(true);
        if (!this.disableXmlSecurity) {
            try {
                f.setFeature("http://xml.org/sax/features/external-general-entities", Boolean.FALSE);
            }
            catch (Exception ex) {
                throw new RuntimeException("Security features for the SAX parser could not be enabled", ex);
            }
            try {
                f.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
            }
            catch (Exception ex) {
                SAXParserContextProvider.LOGGER.log(Level.WARNING, "JAXP feature XMLConstants.FEATURE_SECURE_PROCESSING cannot be set on a SAXParserFactory. External general entity processing is disabled but other potential security related features will not be enabled.", ex);
            }
        }
        return f;
    }
    
    static {
        LOGGER = Logger.getLogger(SAXParserContextProvider.class.getName());
    }
}
