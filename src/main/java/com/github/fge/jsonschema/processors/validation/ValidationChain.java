// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.processors.validation;

import javax.annotation.ParametersAreNonnullByDefault;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ListProcessingReport;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.processors.format.FormatProcessor;
import com.github.fge.jsonschema.processors.build.ValidatorBuilder;
import com.github.fge.jsonschema.processors.digest.SchemaDigester;
import com.google.common.base.Equivalence;
import com.github.fge.jsonschema.core.processing.CachingProcessor;
import com.github.fge.jsonschema.core.report.MessageProvider;
import com.github.fge.jsonschema.core.processing.ProcessorChain;
import com.github.fge.jsonschema.core.keyword.syntax.SyntaxProcessor;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.library.Library;
import com.github.fge.jsonschema.core.load.RefResolver;
import com.github.fge.jsonschema.core.tree.SchemaTree;
import com.github.fge.jsonschema.core.util.ValueHolder;
import com.github.fge.jsonschema.processors.data.ValidatorList;
import com.github.fge.jsonschema.processors.data.SchemaContext;
import com.github.fge.jsonschema.core.processing.Processor;

public final class ValidationChain implements Processor<SchemaContext, ValidatorList>
{
    private final Processor<ValueHolder<SchemaTree>, ValueHolder<SchemaTree>> resolver;
    private final Processor<SchemaContext, ValidatorList> builder;
    
    public ValidationChain(final RefResolver refResolver, final Library library, final ValidationConfiguration cfg) {
        final SyntaxProcessor syntaxProcessor = new SyntaxProcessor(cfg.getSyntaxMessages(), library.getSyntaxCheckers());
        final ProcessorChain<ValueHolder<SchemaTree>, ValueHolder<SchemaTree>> chain1 = ProcessorChain.startWith((Processor<ValueHolder<SchemaTree>, MessageProvider>)refResolver).chainWith((Processor<MessageProvider, ValueHolder<SchemaTree>>)syntaxProcessor);
        this.resolver = new CachingProcessor<ValueHolder<SchemaTree>, ValueHolder<SchemaTree>>(chain1.getProcessor(), SchemaHolderEquivalence.INSTANCE);
        final SchemaDigester digester = new SchemaDigester(library);
        final ValidatorBuilder keywordBuilder = new ValidatorBuilder(library);
        ProcessorChain<SchemaContext, ValidatorList> chain2 = ProcessorChain.startWith((Processor<SchemaContext, MessageProvider>)digester).chainWith((Processor<MessageProvider, ValidatorList>)keywordBuilder);
        if (cfg.getUseFormat()) {
            final FormatProcessor format = new FormatProcessor(library, cfg);
            chain2 = chain2.chainWith((Processor<ValidatorList, ValidatorList>)format);
        }
        this.builder = new CachingProcessor<SchemaContext, ValidatorList>(chain2.getProcessor(), SchemaContextEquivalence.getInstance());
    }
    
    @Override
    public ValidatorList process(final ProcessingReport report, final SchemaContext input) throws ProcessingException {
        final ValueHolder<SchemaTree> in = ValueHolder.hold("schema", input.getSchema());
        final ProcessingReport r = new ListProcessingReport(report);
        final ValueHolder<SchemaTree> out = this.resolver.process(r, in);
        report.mergeWith(r);
        if (!r.isSuccess()) {
            return null;
        }
        final SchemaContext output = new SchemaContext(out.getValue(), input.getInstanceType());
        return this.builder.process(report, output);
    }
    
    @Override
    public String toString() {
        return this.resolver + " -> " + this.builder;
    }
    
    @ParametersAreNonnullByDefault
    private static final class SchemaHolderEquivalence extends Equivalence<ValueHolder<SchemaTree>>
    {
        private static final Equivalence<ValueHolder<SchemaTree>> INSTANCE;
        
        @Override
        protected boolean doEquivalent(final ValueHolder<SchemaTree> a, final ValueHolder<SchemaTree> b) {
            return a.getValue().equals(b.getValue());
        }
        
        @Override
        protected int doHash(final ValueHolder<SchemaTree> t) {
            return t.getValue().hashCode();
        }
        
        static {
            INSTANCE = new SchemaHolderEquivalence();
        }
    }
}
