// 
// Decompiled by Procyon v0.5.30
// 

package com.sun.jersey.server.impl.model.parameter;

import com.sun.jersey.api.core.HttpRequestContext;
import com.sun.jersey.core.header.MediaTypes;
import javax.ws.rs.core.MediaType;
import com.sun.jersey.server.impl.model.parameter.multivalued.ExtractorContainerException;
import com.sun.jersey.api.ParamException;
import javax.ws.rs.core.MultivaluedMap;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import java.lang.annotation.Annotation;
import com.sun.jersey.server.impl.model.parameter.multivalued.MultivaluedParameterExtractor;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.api.model.Parameter;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.server.impl.model.parameter.multivalued.MultivaluedParameterExtractorProvider;
import javax.ws.rs.FormParam;

public final class FormParamInjectableProvider extends BaseParamInjectableProvider<FormParam>
{
    public FormParamInjectableProvider(final MultivaluedParameterExtractorProvider w) {
        super(w);
    }
    
    @Override
    public Injectable getInjectable(final ComponentContext ic, final FormParam a, final Parameter c) {
        final String parameterName = c.getSourceName();
        if (parameterName == null || parameterName.length() == 0) {
            return null;
        }
        final MultivaluedParameterExtractor e = this.get(c);
        if (e == null) {
            return null;
        }
        return new FormParamInjectable(e, !c.isEncoded());
    }
    
    private static final class FormParamInjectable extends AbstractHttpContextInjectable<Object>
    {
        private final MultivaluedParameterExtractor extractor;
        private final boolean decode;
        
        FormParamInjectable(final MultivaluedParameterExtractor extractor, final boolean decode) {
            this.extractor = extractor;
            this.decode = decode;
        }
        
        @Override
        public Object getValue(final HttpContext context) {
            Form form = context.getProperties().get("com.sun.jersey.api.representation.form");
            if (form == null) {
                form = this.getForm(context);
                if (form == null) {
                    throw new IllegalStateException("The @FormParam is utilized when the content type of the request entity is not application/x-www-form-urlencoded");
                }
            }
            try {
                return this.extractor.extract(form);
            }
            catch (ExtractorContainerException e) {
                throw new ParamException.FormParamException(e.getCause(), this.extractor.getName(), this.extractor.getDefaultStringValue());
            }
        }
        
        private Form getForm(final HttpContext context) {
            final HttpRequestContext r = context.getRequest();
            if (r.getMethod().equals("GET")) {
                return null;
            }
            if (!MediaTypes.typeEquals(MediaType.APPLICATION_FORM_URLENCODED_TYPE, r.getMediaType())) {
                return null;
            }
            final Form form = r.getFormParameters();
            context.getProperties().put("com.sun.jersey.api.representation.form", form);
            return form;
        }
    }
}
