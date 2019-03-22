// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.main.cli;

import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import java.util.Set;
import java.util.Collection;
import java.util.LinkedHashSet;
import joptsimple.OptionDescriptor;
import java.util.Map;
import com.google.common.collect.Lists;
import com.google.common.base.Joiner;
import java.util.List;
import joptsimple.HelpFormatter;

final class CustomHelpFormatter implements HelpFormatter
{
    private static final List<String> HELP_PREAMBLE;
    private static final List<String> HELP_POST;
    private static final String LINE_SEPARATOR;
    private static final Joiner JOINER;
    static final Joiner OPTIONS_JOINER;
    private final List<String> lines;
    
    CustomHelpFormatter() {
        this.lines = (List<String>)Lists.newArrayList();
    }
    
    @Override
    public String format(final Map<String, ? extends OptionDescriptor> options) {
        final Set<OptionDescriptor> opts = new LinkedHashSet<OptionDescriptor>(options.values());
        this.lines.addAll(CustomHelpFormatter.HELP_PREAMBLE);
        final int helpIndex = this.lines.size();
        for (final OptionDescriptor descriptor : opts) {
            if (descriptor.representsNonOptions()) {
                continue;
            }
            final Collection<String> names = (Collection<String>)descriptor.options();
            final StringBuilder sb = new StringBuilder().append("    ").append(optionsToString(names));
            if (descriptor.requiresArgument()) {
                sb.append(" uri");
            }
            sb.append(": ").append(descriptor.description());
            if (names.contains("help")) {
                this.lines.add(helpIndex, sb.toString());
            }
            else {
                this.lines.add(sb.toString());
            }
        }
        this.lines.addAll(CustomHelpFormatter.HELP_POST);
        return CustomHelpFormatter.JOINER.join(this.lines) + CustomHelpFormatter.LINE_SEPARATOR;
    }
    
    private static String optionsToString(final Collection<String> names) {
        final List<String> list = (List<String>)Lists.newArrayList();
        for (final String name : names) {
            list.add(((name.length() == 1) ? "-" : "--") + name);
        }
        return CustomHelpFormatter.OPTIONS_JOINER.join(list);
    }
    
    static {
        HELP_PREAMBLE = ImmutableList.of("Syntax:", "    java -jar jsonschema.jar [options] schema file [file...]", "    java -jar jsonschema.jar --syntax [options] schema [schema...]", "", "Options: ");
        HELP_POST = ImmutableList.builder().add("").add("Exit codes:").add("    0: validation successful;").add("    1: exception occurred (appears on stderr)").add("    2: command line syntax error (missing argument, etc)").add("  100: one or more file(s) failed validation").add("  101: one or more schema(s) is/are invalid").add("").add("Note: by default, the URI of schemas you use in validation mode").add("(that is, when you don't use --syntax) is considered to be the").add("current working directory plus the filename. If your schemas").add("all have a common URI prefix in a top level \"id\", you can fake").add("that the current directory is that prefix using --fakeroot.").build();
        LINE_SEPARATOR = System.getProperty("line.separator", "\n");
        JOINER = Joiner.on(CustomHelpFormatter.LINE_SEPARATOR);
        OPTIONS_JOINER = Joiner.on(", ");
    }
}
