// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.server.impl.model.parameter.multivalued;

import java.text.ParseException;
import com.sun.jersey.core.header.HttpDateFormat;
import java.util.Date;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import com.sun.jersey.core.reflection.ReflectionHelper;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import com.sun.jersey.spi.StringReaderProvider;
import com.sun.jersey.api.container.ContainerException;
import java.lang.reflect.InvocationTargetException;
import javax.ws.rs.WebApplicationException;
import com.sun.jersey.spi.StringReader;

public class StringReaderProviders
{
    private abstract static class AbstractStringReader implements StringReader
    {
        @Override
        public Object fromString(final String value) {
            try {
                return this._fromString(value);
            }
            catch (InvocationTargetException ex) {
                final Throwable target = ex.getTargetException();
                if (target instanceof WebApplicationException) {
                    throw (WebApplicationException)target;
                }
                throw new ExtractorContainerException(target);
            }
            catch (Exception ex2) {
                throw new ContainerException(ex2);
            }
        }
        
        protected abstract Object _fromString(final String p0) throws Exception;
    }
    
    public static class StringConstructor implements StringReaderProvider
    {
        @Override
        public StringReader getStringReader(final Class type, final Type genericType, final Annotation[] annotations) {
            final Constructor constructor = ReflectionHelper.getStringConstructor(type);
            if (constructor == null) {
                return null;
            }
            return new AbstractStringReader() {
                @Override
                protected Object _fromString(final String value) throws Exception {
                    return constructor.newInstance(value);
                }
            };
        }
    }
    
    public static class TypeValueOf implements StringReaderProvider
    {
        @Override
        public StringReader getStringReader(final Class type, final Type genericType, final Annotation[] annotations) {
            final Method valueOf = ReflectionHelper.getValueOfStringMethod(type);
            if (valueOf == null) {
                return null;
            }
            return new AbstractStringReader() {
                public Object _fromString(final String value) throws Exception {
                    return valueOf.invoke(null, value);
                }
            };
        }
    }
    
    public static class TypeFromString implements StringReaderProvider
    {
        @Override
        public StringReader getStringReader(final Class type, final Type genericType, final Annotation[] annotations) {
            final Method fromString = ReflectionHelper.getFromStringStringMethod(type);
            if (fromString == null) {
                return null;
            }
            return new AbstractStringReader() {
                public Object _fromString(final String value) throws Exception {
                    return fromString.invoke(null, value);
                }
            };
        }
    }
    
    public static class TypeFromStringEnum extends TypeFromString
    {
        @Override
        public StringReader getStringReader(final Class type, final Type genericType, final Annotation[] annotations) {
            if (!Enum.class.isAssignableFrom(type)) {
                return null;
            }
            return super.getStringReader(type, genericType, annotations);
        }
    }
    
    public static class DateProvider implements StringReaderProvider
    {
        @Override
        public StringReader getStringReader(final Class type, final Type genericType, final Annotation[] annotations) {
            if (type != Date.class) {
                return null;
            }
            return new StringReader() {
                @Override
                public Object fromString(final String value) {
                    try {
                        return HttpDateFormat.readDate(value);
                    }
                    catch (ParseException ex) {
                        throw new ExtractorContainerException(ex);
                    }
                }
            };
        }
    }
}
