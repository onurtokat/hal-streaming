// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.core.spi.component.ioc;

import com.sun.jersey.core.spi.component.ComponentScope;

public interface IoCManagedComponentProvider extends IoCInstantiatedComponentProvider
{
    ComponentScope getScope();
}
