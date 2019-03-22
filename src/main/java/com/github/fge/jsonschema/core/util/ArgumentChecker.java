// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.core.util;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.jsonschema.core.messages.JsonSchemaCoreMessageBundle;
import javax.annotation.Nullable;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.google.common.annotations.Beta;

@Beta
public abstract class ArgumentChecker<T>
{
    protected static final MessageBundle BUNDLE;
    
    public static <X> ArgumentChecker<X> anythingGoes() {
        return new ArgumentChecker<X>() {
            @Override
            public void check(final X argument) {
            }
        };
    }
    
    public static <X> ArgumentChecker<X> notNull() {
        return new ArgumentChecker<X>() {
            @Override
            public void check(@Nullable final X argument) {
                ArgumentChecker$2.BUNDLE.checkNotNull(argument, "argChecker.notNull");
            }
        };
    }
    
    public static <X> ArgumentChecker<X> notNull(final String message) {
        ArgumentChecker.BUNDLE.checkNotNull(message, "argChecker.nullMessage");
        return new ArgumentChecker<X>() {
            @Override
            public void check(@Nullable final X argument) {
                if (argument == null) {
                    throw new NullPointerException(message);
                }
            }
        };
    }
    
    public abstract void check(@Nullable final T p0);
    
    static {
        BUNDLE = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);
    }
}
