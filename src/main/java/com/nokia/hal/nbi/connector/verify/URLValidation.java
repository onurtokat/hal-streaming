// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector.verify;

import org.slf4j.LoggerFactory;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;

public class URLValidation
{
    private static final Logger LOG;
    
    public void work(final String url) {
        final String[] schemes = { "https" };
        final UrlValidator urlValidator = new UrlValidator(schemes);
        if (!urlValidator.isValid(url)) {
            URLValidation.LOG.warn("Created url (" + url + ") does NOT seem to be OK but I will not fail the runtime here because URL validation is a complex thing...");
        }
    }
    
    static {
        LOG = LoggerFactory.getLogger(URLValidation.class);
    }
}
