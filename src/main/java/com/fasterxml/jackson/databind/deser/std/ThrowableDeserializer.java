// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.deser.std;

import java.io.IOException;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBase;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;

public class ThrowableDeserializer extends BeanDeserializer
{
    private static final long serialVersionUID = 1L;
    protected static final String PROP_NAME_MESSAGE = "message";
    
    public ThrowableDeserializer(final BeanDeserializer baseDeserializer) {
        super(baseDeserializer);
        this._vanillaProcessing = false;
    }
    
    protected ThrowableDeserializer(final BeanDeserializer src, final NameTransformer unwrapper) {
        super(src, unwrapper);
    }
    
    @Override
    public JsonDeserializer<Object> unwrappingDeserializer(final NameTransformer unwrapper) {
        if (this.getClass() != ThrowableDeserializer.class) {
            return this;
        }
        return new ThrowableDeserializer(this, unwrapper);
    }
    
    @Override
    public Object deserializeFromObject(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        if (this._propertyBasedCreator != null) {
            return this._deserializeUsingPropertyBased(jp, ctxt);
        }
        if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(jp, ctxt));
        }
        if (this._beanType.isAbstract()) {
            throw JsonMappingException.from(jp, "Can not instantiate abstract type " + this._beanType + " (need to add/enable type information?)");
        }
        final boolean hasStringCreator = this._valueInstantiator.canCreateFromString();
        final boolean hasDefaultCtor = this._valueInstantiator.canCreateUsingDefault();
        if (!hasStringCreator && !hasDefaultCtor) {
            throw new JsonMappingException("Can not deserialize Throwable of type " + this._beanType + " without having a default contructor, a single-String-arg constructor; or explicit @JsonCreator");
        }
        Object throwable = null;
        Object[] pending = null;
        int pendingIx = 0;
        while (jp.getCurrentToken() != JsonToken.END_OBJECT) {
            final String propName = jp.getCurrentName();
            SettableBeanProperty prop = this._beanProperties.find(propName);
            jp.nextToken();
            if (prop != null) {
                if (throwable != null) {
                    prop.deserializeAndSet(jp, ctxt, throwable);
                }
                else {
                    if (pending == null) {
                        final int len = this._beanProperties.size();
                        pending = new Object[len + len];
                    }
                    pending[pendingIx++] = prop;
                    pending[pendingIx++] = prop.deserialize(jp, ctxt);
                }
            }
            else if ("message".equals(propName) && hasStringCreator) {
                throwable = this._valueInstantiator.createFromString(ctxt, jp.getText());
                if (pending != null) {
                    for (int i = 0, len2 = pendingIx; i < len2; i += 2) {
                        prop = (SettableBeanProperty)pending[i];
                        prop.set(throwable, pending[i + 1]);
                    }
                    pending = null;
                }
            }
            else if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
                jp.skipChildren();
            }
            else if (this._anySetter != null) {
                this._anySetter.deserializeAndSet(jp, ctxt, throwable, propName);
            }
            else {
                this.handleUnknownProperty(jp, ctxt, throwable, propName);
            }
            jp.nextToken();
        }
        if (throwable == null) {
            if (hasStringCreator) {
                throwable = this._valueInstantiator.createFromString(ctxt, null);
            }
            else {
                throwable = this._valueInstantiator.createUsingDefault(ctxt);
            }
            if (pending != null) {
                for (int j = 0, len3 = pendingIx; j < len3; j += 2) {
                    final SettableBeanProperty prop2 = (SettableBeanProperty)pending[j];
                    prop2.set(throwable, pending[j + 1]);
                }
            }
        }
        return throwable;
    }
}
