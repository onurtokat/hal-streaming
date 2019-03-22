// 
// Decompiled by Procyon v0.5.30
// 

package com.github.fge.jsonschema.main.cli;

import com.github.fge.jackson.JacksonUtils;
import com.github.fge.jsonschema.core.util.URIUtils;
import com.github.fge.jsonschema.main.JsonSchema;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.load.uri.URITranslatorConfigurationBuilder;
import com.github.fge.jsonschema.core.load.configuration.LoadingConfiguration;
import com.github.fge.jsonschema.core.load.uri.URITranslatorConfiguration;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import joptsimple.OptionSet;
import java.io.File;
import com.google.common.collect.Lists;
import joptsimple.OptionException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Arrays;
import joptsimple.OptionParser;
import com.github.fge.jsonschema.processors.syntax.SyntaxValidator;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import joptsimple.HelpFormatter;

public final class Main
{
    private static final HelpFormatter HELP;
    private static final ObjectMapper MAPPER;
    private final JsonSchemaFactory factory;
    private final SyntaxValidator syntaxValidator;
    
    public static void main(final String... args) throws IOException, ProcessingException {
        final OptionParser parser = new OptionParser();
        parser.accepts("help", "show this help").forHelp();
        parser.acceptsAll((Collection)Arrays.asList("s", "brief"), "only show validation status (OK/NOT OK)");
        parser.acceptsAll((Collection)Arrays.asList("q", "quiet"), "no output; exit with the relevant return code (see below)");
        parser.accepts("syntax", "check the syntax of schema(s) given as argument(s)");
        parser.accepts("fakeroot", "pretend that the current directory is absolute URI \"uri\"").withRequiredArg();
        parser.formatHelpWith(Main.HELP);
        Reporter reporter = Reporters.DEFAULT;
        String fakeRoot = null;
        OptionSet optionSet;
        try {
            optionSet = parser.parse(args);
        }
        catch (OptionException e) {
            System.err.println("unrecognized option(s): " + CustomHelpFormatter.OPTIONS_JOINER.join(e.options()));
            parser.printHelpOn(System.err);
            System.exit(RetCode.CMD_ERROR.get());
            throw new IllegalStateException("WTF??");
        }
        if (optionSet.has("help")) {
            parser.printHelpOn(System.out);
            System.exit(RetCode.ALL_OK.get());
        }
        if (optionSet.has("s") && optionSet.has("q")) {
            System.err.println("cannot specify both \"--brief\" and \"--quiet\"");
            parser.printHelpOn(System.err);
            System.exit(RetCode.CMD_ERROR.get());
        }
        if (optionSet.has("fakeroot")) {
            fakeRoot = (String)optionSet.valueOf("fakeroot");
        }
        final boolean isSyntax = optionSet.has("syntax");
        final int requiredArgs = isSyntax ? 1 : 2;
        final List<String> arguments = (List<String>)optionSet.nonOptionArguments();
        if (arguments.size() < requiredArgs) {
            System.err.println("missing arguments");
            parser.printHelpOn(System.err);
            System.exit(RetCode.CMD_ERROR.get());
        }
        final List<File> files = (List<File>)Lists.newArrayList();
        for (final String target : arguments) {
            files.add(new File(target).getCanonicalFile());
        }
        if (optionSet.has("brief")) {
            reporter = Reporters.BRIEF;
        }
        else if (optionSet.has("quiet")) {
            System.out.close();
            System.err.close();
            reporter = Reporters.QUIET;
        }
        new Main(fakeRoot).proceed(reporter, files, isSyntax);
    }
    
    Main(final String fakeRoot) throws IOException {
        final URITranslatorConfigurationBuilder builder = URITranslatorConfiguration.newBuilder().setNamespace(getCwd());
        if (fakeRoot != null) {
            builder.addPathRedirect(fakeRoot, getCwd());
        }
        final LoadingConfiguration cfg = LoadingConfiguration.newBuilder().setURITranslatorConfiguration(builder.freeze()).freeze();
        this.factory = JsonSchemaFactory.newBuilder().setLoadingConfiguration(cfg).freeze();
        this.syntaxValidator = this.factory.getSyntaxValidator();
    }
    
    private void proceed(final Reporter reporter, final List<File> files, final boolean isSyntax) throws IOException, ProcessingException {
        final RetCode retCode = isSyntax ? this.doSyntax(reporter, files) : this.doValidation(reporter, files);
        System.exit(retCode.get());
    }
    
    private RetCode doSyntax(final Reporter reporter, final List<File> files) throws IOException {
        RetCode ret = RetCode.ALL_OK;
        for (final File file : files) {
            final String fileName = file.toString();
            final JsonNode node = Main.MAPPER.readTree(file);
            final RetCode retcode = reporter.validateSchema(this.syntaxValidator, fileName, node);
            if (retcode != RetCode.ALL_OK) {
                ret = retcode;
            }
        }
        return ret;
    }
    
    private RetCode doValidation(final Reporter reporter, final List<File> files) throws IOException, ProcessingException {
        final File schemaFile = files.remove(0);
        final String uri = schemaFile.toURI().normalize().toString();
        JsonNode node = Main.MAPPER.readTree(schemaFile);
        if (!this.syntaxValidator.schemaIsValid(node)) {
            System.err.println("Schema is invalid! Aborting...");
            return RetCode.SCHEMA_SYNTAX_ERROR;
        }
        final JsonSchema schema = this.factory.getJsonSchema(uri);
        RetCode ret = RetCode.ALL_OK;
        for (final File file : files) {
            node = Main.MAPPER.readTree(file);
            final RetCode retcode = reporter.validateInstance(schema, file.toString(), node);
            if (retcode != RetCode.ALL_OK) {
                ret = retcode;
            }
        }
        return ret;
    }
    
    private static String getCwd() throws IOException {
        final File cwd = new File(System.getProperty("user.dir", ".")).getCanonicalFile();
        return URIUtils.normalizeURI(cwd.toURI()).toString();
    }
    
    static {
        HELP = new CustomHelpFormatter();
        MAPPER = JacksonUtils.newMapper();
    }
}
