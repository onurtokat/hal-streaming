// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.exc;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import com.fasterxml.jackson.core.JsonLocation;
import java.util.Collection;
import com.fasterxml.jackson.databind.JsonMappingException;

public abstract class PropertyBindingException extends JsonMappingException
{
    protected final Class<?> _referringClass;
    protected final String _propertyName;
    protected final Collection<Object> _propertyIds;
    protected transient String _propertiesAsString;
    private static final int MAX_DESC_LENGTH = 200;
    
    protected PropertyBindingException(final String msg, final JsonLocation loc, final Class<?> referringClass, final String propName, final Collection<Object> propertyIds) {
        super(msg, loc);
        this._referringClass = referringClass;
        this._propertyName = propName;
        this._propertyIds = propertyIds;
    }
    
    public String getMessageSuffix() {
        String suffix = this._propertiesAsString;
        if (suffix == null && this._propertyIds != null) {
            final StringBuilder sb = new StringBuilder(100);
            final int len = this._propertyIds.size();
            if (len == 1) {
                sb.append(" (one known property: \"");
                sb.append(String.valueOf(this._propertyIds.iterator().next()));
                sb.append('\"');
            }
            else {
                sb.append(" (").append(len).append(" known properties: ");
                final Iterator<Object> it = this._propertyIds.iterator();
                while (it.hasNext()) {
                    sb.append('\"');
                    sb.append(String.valueOf(it.next()));
                    sb.append('\"');
                    if (sb.length() > 200) {
                        sb.append(" [truncated]");
                        break;
                    }
                    if (!it.hasNext()) {
                        continue;
                    }
                    sb.append(", ");
                }
            }
            sb.append("])");
            suffix = (this._propertiesAsString = sb.toString());
        }
        return suffix;
    }
    
    public Class<?> getReferringClass() {
        return this._referringClass;
    }
    
    public String getPropertyName() {
        return this._propertyName;
    }
    
    public Collection<Object> getKnownPropertyIds() {
        if (this._propertyIds == null) {
            return null;
        }
        return Collections.unmodifiableCollection((Collection<?>)this._propertyIds);
    }
}
