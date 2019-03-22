// 
// Decompiled by Procyon v0.5.30
// 

package com.alu.motive.ae.client.template.wrapper;

import java.util.List;

public interface BaseTemplateWrapper<T>
{
    T getRow(final String p0);
    
    List<T> getRowsInOriginalOrder();
}
