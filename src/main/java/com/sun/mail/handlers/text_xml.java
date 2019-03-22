// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.mail.handlers;

import javax.activation.ActivationDataFlavor;

public class text_xml extends text_plain
{
    private static ActivationDataFlavor myDF;
    static /* synthetic */ Class class$java$lang$String;
    
    protected ActivationDataFlavor getDF() {
        return text_xml.myDF;
    }
    
    static /* synthetic */ Class class$(final String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x) {
            throw new NoClassDefFoundError().initCause(x);
        }
    }
    
    static {
        text_xml.myDF = new ActivationDataFlavor((text_xml.class$java$lang$String == null) ? (text_xml.class$java$lang$String = class$("java.lang.String")) : text_xml.class$java$lang$String, "text/xml", "XML String");
    }
}
