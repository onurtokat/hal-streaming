// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.msgsimple.load;

import java.util.IdentityHashMap;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import com.github.fge.msgsimple.bundle.MessageBundle;
import java.util.Map;
import com.github.fge.msgsimple.InternalBundle;

public final class MessageBundles
{
    private static final InternalBundle BUNDLE;
    private static final Map<Class<? extends MessageBundleLoader>, MessageBundle> BUNDLES;
    
    public static synchronized MessageBundle getBundle(final Class<? extends MessageBundleLoader> c) {
        MessageBundle ret = MessageBundles.BUNDLES.get(c);
        if (ret == null) {
            ret = doGetBundle(c);
            MessageBundles.BUNDLES.put(c, ret);
        }
        return ret;
    }
    
    private static MessageBundle doGetBundle(final Class<? extends MessageBundleLoader> c) {
        String message = MessageBundles.BUNDLE.getMessage("factory.noConstructor");
        Constructor<? extends MessageBundleLoader> constructor;
        try {
            constructor = c.getConstructor((Class<?>[])new Class[0]);
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException(message, e);
        }
        message = MessageBundles.BUNDLE.getMessage("factory.cannotInstantiate");
        MessageBundleLoader provider;
        try {
            provider = (MessageBundleLoader)constructor.newInstance(new Object[0]);
        }
        catch (InstantiationException e2) {
            throw new RuntimeException(message, e2);
        }
        catch (IllegalAccessException e3) {
            throw new RuntimeException(message, e3);
        }
        catch (InvocationTargetException e4) {
            throw new RuntimeException(message, e4);
        }
        return MessageBundles.BUNDLE.checkNotNull(provider.getBundle(), "factory.illegalProvider");
    }
    
    static {
        BUNDLE = InternalBundle.getInstance();
        BUNDLES = new IdentityHashMap<Class<? extends MessageBundleLoader>, MessageBundle>();
    }
}
