// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.json.impl;

import javax.xml.bind.annotation.XmlRootElement;

public final class JSONHelper
{
    public static final String getRootElementName(final Class<Object> clazz) {
        final XmlRootElement e = clazz.getAnnotation(XmlRootElement.class);
        if (e == null) {
            return getVariableName(clazz.getSimpleName());
        }
        if ("##default".equals(e.name())) {
            return getVariableName(clazz.getSimpleName());
        }
        return e.name();
    }
    
    private static final String getVariableName(final String baseName) {
        return NameUtil.toMixedCaseName(NameUtil.toWordList(baseName), false);
    }
}
