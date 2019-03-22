// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.uritemplate.render;

import com.github.fge.msgsimple.load.MessageBundleLoader;
import com.github.fge.msgsimple.load.MessageBundles;
import com.github.fge.uritemplate.URITemplateMessageBundle;
import com.github.fge.uritemplate.URITemplateException;
import java.util.List;
import com.github.fge.uritemplate.vars.values.VariableValue;
import com.github.fge.uritemplate.vars.specs.VariableSpec;
import com.github.fge.uritemplate.expression.ExpressionType;
import com.google.common.base.Joiner;
import com.github.fge.msgsimple.bundle.MessageBundle;

public abstract class MultiValueRenderer extends ValueRenderer
{
    private static final MessageBundle BUNDLE;
    protected static final Joiner COMMA;
    
    protected MultiValueRenderer(final ExpressionType type) {
        super(type);
    }
    
    @Override
    public final List<String> render(final VariableSpec varspec, final VariableValue value) throws URITemplateException {
        if (varspec.getPrefixLength() != -1) {
            throw new URITemplateException(MultiValueRenderer.BUNDLE.getMessage("expand.incompatVarspecValue"));
        }
        final String varname = varspec.getName();
        if (this.named) {
            return varspec.isExploded() ? this.renderNamedExploded(varname, value) : this.renderNamedNormal(varname, value);
        }
        return varspec.isExploded() ? this.renderUnnamedExploded(value) : this.renderUnnamedNormal(value);
    }
    
    protected abstract List<String> renderNamedExploded(final String p0, final VariableValue p1);
    
    protected abstract List<String> renderUnnamedExploded(final VariableValue p0);
    
    protected abstract List<String> renderNamedNormal(final String p0, final VariableValue p1);
    
    protected abstract List<String> renderUnnamedNormal(final VariableValue p0);
    
    static {
        BUNDLE = MessageBundles.getBundle(URITemplateMessageBundle.class);
        COMMA = Joiner.on(',');
    }
}
