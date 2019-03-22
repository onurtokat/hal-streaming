// 
// Decompiled by Procyon v0.5.30
// 

package com.google.i18n.phonenumbers;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.io.ObjectInput;
import java.io.InputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.logging.Logger;

public class PhoneNumberUtil
{
    static final MetadataLoader DEFAULT_METADATA_LOADER;
    private static final Logger logger;
    static final int REGEX_FLAGS = 66;
    private static final int MIN_LENGTH_FOR_NSN = 2;
    static final int MAX_LENGTH_FOR_NSN = 17;
    static final int MAX_LENGTH_COUNTRY_CODE = 3;
    private static final int MAX_INPUT_STRING_LENGTH = 250;
    private static final String META_DATA_FILE_PREFIX = "/com/google/i18n/phonenumbers/data/PhoneNumberMetadataProto";
    private static final String UNKNOWN_REGION = "ZZ";
    private static final int NANPA_COUNTRY_CODE = 1;
    private static final String COLOMBIA_MOBILE_TO_FIXED_LINE_PREFIX = "3";
    private static final Map<Integer, String> MOBILE_TOKEN_MAPPINGS;
    static final char PLUS_SIGN = '+';
    private static final char STAR_SIGN = '*';
    private static final String RFC3966_EXTN_PREFIX = ";ext=";
    private static final String RFC3966_PREFIX = "tel:";
    private static final String RFC3966_PHONE_CONTEXT = ";phone-context=";
    private static final String RFC3966_ISDN_SUBADDRESS = ";isub=";
    private static final Map<Character, Character> DIALLABLE_CHAR_MAPPINGS;
    private static final Map<Character, Character> ALPHA_MAPPINGS;
    private static final Map<Character, Character> ALPHA_PHONE_MAPPINGS;
    private static final Map<Character, Character> ALL_PLUS_NUMBER_GROUPING_SYMBOLS;
    private static final Pattern UNIQUE_INTERNATIONAL_PREFIX;
    static final String VALID_PUNCTUATION = "-x\u2010-\u2015\u2212\u30fc\uff0d-\uff0f  \u00ad\u200b\u2060\u3000()\uff08\uff09\uff3b\uff3d.\\[\\]/~\u2053\u223c\uff5e";
    private static final String DIGITS = "\\p{Nd}";
    private static final String VALID_ALPHA;
    static final String PLUS_CHARS = "+\uff0b";
    static final Pattern PLUS_CHARS_PATTERN;
    private static final Pattern SEPARATOR_PATTERN;
    private static final Pattern CAPTURING_DIGIT_PATTERN;
    private static final String VALID_START_CHAR = "[+\uff0b\\p{Nd}]";
    private static final Pattern VALID_START_CHAR_PATTERN;
    private static final String SECOND_NUMBER_START = "[\\\\/] *x";
    static final Pattern SECOND_NUMBER_START_PATTERN;
    private static final String UNWANTED_END_CHARS = "[[\\P{N}&&\\P{L}]&&[^#]]+$";
    static final Pattern UNWANTED_END_CHAR_PATTERN;
    private static final Pattern VALID_ALPHA_PHONE_PATTERN;
    private static final String VALID_PHONE_NUMBER;
    private static final String DEFAULT_EXTN_PREFIX = " ext. ";
    private static final String CAPTURING_EXTN_DIGITS = "(\\p{Nd}{1,7})";
    private static final String EXTN_PATTERNS_FOR_PARSING;
    static final String EXTN_PATTERNS_FOR_MATCHING;
    private static final Pattern EXTN_PATTERN;
    private static final Pattern VALID_PHONE_NUMBER_PATTERN;
    static final Pattern NON_DIGITS_PATTERN;
    private static final Pattern FIRST_GROUP_PATTERN;
    private static final Pattern NP_PATTERN;
    private static final Pattern FG_PATTERN;
    private static final Pattern CC_PATTERN;
    private static final Pattern FIRST_GROUP_ONLY_PREFIX_PATTERN;
    private static PhoneNumberUtil instance;
    public static final String REGION_CODE_FOR_NON_GEO_ENTITY = "001";
    private final Map<Integer, List<String>> countryCallingCodeToRegionCodeMap;
    private final Set<String> nanpaRegions;
    private final Map<String, Phonemetadata.PhoneMetadata> regionToMetadataMap;
    private final Map<Integer, Phonemetadata.PhoneMetadata> countryCodeToNonGeographicalMetadataMap;
    private final RegexCache regexCache;
    private final Set<String> supportedRegions;
    private final Set<Integer> countryCodesForNonGeographicalRegion;
    private final String currentFilePrefix;
    private final MetadataLoader metadataLoader;
    
    private static String createExtnPattern(final String singleExtnSymbols) {
        final String value = String.valueOf(String.valueOf(";ext=(\\p{Nd}{1,7})|[  \\t,]*(?:e?xt(?:ensi(?:o\u0301?|\u00f3))?n?|\uff45?\uff58\uff54\uff4e?|["));
        final String value2 = String.valueOf(String.valueOf(singleExtnSymbols));
        final String value3 = String.valueOf(String.valueOf("(\\p{Nd}{1,7})"));
        final String value4 = String.valueOf(String.valueOf("\\p{Nd}"));
        return new StringBuilder(48 + value.length() + value2.length() + value3.length() + value4.length()).append(value).append(value2).append("]|int|anexo|\uff49\uff4e\uff54)").append("[:\\.\uff0e]?[  \\t,-]*").append(value3).append("#?|").append("[- ]+(").append(value4).append("{1,5})#").toString();
    }
    
    PhoneNumberUtil(final String filePrefix, final MetadataLoader metadataLoader, final Map<Integer, List<String>> countryCallingCodeToRegionCodeMap) {
        this.nanpaRegions = new HashSet<String>(35);
        this.regionToMetadataMap = Collections.synchronizedMap(new HashMap<String, Phonemetadata.PhoneMetadata>());
        this.countryCodeToNonGeographicalMetadataMap = Collections.synchronizedMap(new HashMap<Integer, Phonemetadata.PhoneMetadata>());
        this.regexCache = new RegexCache(100);
        this.supportedRegions = new HashSet<String>(320);
        this.countryCodesForNonGeographicalRegion = new HashSet<Integer>();
        this.currentFilePrefix = filePrefix;
        this.metadataLoader = metadataLoader;
        this.countryCallingCodeToRegionCodeMap = countryCallingCodeToRegionCodeMap;
        for (final Map.Entry<Integer, List<String>> entry : countryCallingCodeToRegionCodeMap.entrySet()) {
            final List<String> regionCodes = entry.getValue();
            if (regionCodes.size() == 1 && "001".equals(regionCodes.get(0))) {
                this.countryCodesForNonGeographicalRegion.add(entry.getKey());
            }
            else {
                this.supportedRegions.addAll(regionCodes);
            }
        }
        if (this.supportedRegions.remove("001")) {
            PhoneNumberUtil.logger.log(Level.WARNING, "invalid metadata (country calling code was mapped to the non-geo entity as well as specific region(s))");
        }
        this.nanpaRegions.addAll(countryCallingCodeToRegionCodeMap.get(1));
    }
    
    void loadMetadataFromFile(final String filePrefix, final String regionCode, final int countryCallingCode, final MetadataLoader metadataLoader) {
        final boolean isNonGeoRegion = "001".equals(regionCode);
        final String value = String.valueOf(String.valueOf(filePrefix));
        final String value2 = String.valueOf(String.valueOf(isNonGeoRegion ? String.valueOf(countryCallingCode) : regionCode));
        final String fileName = new StringBuilder(1 + value.length() + value2.length()).append(value).append("_").append(value2).toString();
        final InputStream source = metadataLoader.loadMetadata(fileName);
        if (source == null) {
            final Logger logger = PhoneNumberUtil.logger;
            final Level severe = Level.SEVERE;
            final String s = "missing metadata: ";
            final String value3 = String.valueOf(fileName);
            logger.log(severe, (value3.length() != 0) ? s.concat(value3) : new String(s));
            final String s2 = "missing metadata: ";
            final String value4 = String.valueOf(fileName);
            throw new IllegalStateException((value4.length() != 0) ? s2.concat(value4) : new String(s2));
        }
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(source);
            final Phonemetadata.PhoneMetadataCollection metadataCollection = loadMetadataAndCloseInput(in);
            final List<Phonemetadata.PhoneMetadata> metadataList = metadataCollection.getMetadataList();
            if (metadataList.isEmpty()) {
                final Logger logger2 = PhoneNumberUtil.logger;
                final Level severe2 = Level.SEVERE;
                final String s3 = "empty metadata: ";
                final String value5 = String.valueOf(fileName);
                logger2.log(severe2, (value5.length() != 0) ? s3.concat(value5) : new String(s3));
                final String s4 = "empty metadata: ";
                final String value6 = String.valueOf(fileName);
                throw new IllegalStateException((value6.length() != 0) ? s4.concat(value6) : new String(s4));
            }
            if (metadataList.size() > 1) {
                final Logger logger3 = PhoneNumberUtil.logger;
                final Level warn\u0131ng = Level.WARNING;
                final String s5 = "invalid metadata (too many entries): ";
                final String value7 = String.valueOf(fileName);
                logger3.log(warn\u0131ng, (value7.length() != 0) ? s5.concat(value7) : new String(s5));
            }
            final Phonemetadata.PhoneMetadata metadata = metadataList.get(0);
            if (isNonGeoRegion) {
                this.countryCodeToNonGeographicalMetadataMap.put(countryCallingCode, metadata);
            }
            else {
                this.regionToMetadataMap.put(regionCode, metadata);
            }
        }
        catch (IOException e) {
            final Logger logger4 = PhoneNumberUtil.logger;
            final Level severe3 = Level.SEVERE;
            final String s6 = "cannot load/parse metadata: ";
            final String value8 = String.valueOf(fileName);
            logger4.log(severe3, (value8.length() != 0) ? s6.concat(value8) : new String(s6), e);
            final String s7 = "cannot load/parse metadata: ";
            final String value9 = String.valueOf(fileName);
            throw new RuntimeException((value9.length() != 0) ? s7.concat(value9) : new String(s7), e);
        }
    }
    
    private static Phonemetadata.PhoneMetadataCollection loadMetadataAndCloseInput(final ObjectInputStream source) {
        final Phonemetadata.PhoneMetadataCollection metadataCollection = new Phonemetadata.PhoneMetadataCollection();
        try {
            metadataCollection.readExternal(source);
        }
        catch (IOException e) {
            PhoneNumberUtil.logger.log(Level.WARNING, "error reading input (ignored)", e);
            try {
                source.close();
            }
            catch (IOException e) {
                PhoneNumberUtil.logger.log(Level.WARNING, "error closing input stream (ignored)", e);
            }
            finally {
                return metadataCollection;
            }
        }
        finally {
            try {
                source.close();
            }
            catch (IOException e2) {
                PhoneNumberUtil.logger.log(Level.WARNING, "error closing input stream (ignored)", e2);
                return metadataCollection;
            }
            finally {
                return metadataCollection;
            }
        }
    }
    
    static String extractPossibleNumber(String number) {
        final Matcher m = PhoneNumberUtil.VALID_START_CHAR_PATTERN.matcher(number);
        if (m.find()) {
            number = number.substring(m.start());
            final Matcher trailingCharsMatcher = PhoneNumberUtil.UNWANTED_END_CHAR_PATTERN.matcher(number);
            if (trailingCharsMatcher.find()) {
                number = number.substring(0, trailingCharsMatcher.start());
                final Logger logger = PhoneNumberUtil.logger;
                final Level f\u0131ner = Level.FINER;
                final String s = "Stripped trailing characters: ";
                final String value = String.valueOf(number);
                logger.log(f\u0131ner, (value.length() != 0) ? s.concat(value) : new String(s));
            }
            final Matcher secondNumber = PhoneNumberUtil.SECOND_NUMBER_START_PATTERN.matcher(number);
            if (secondNumber.find()) {
                number = number.substring(0, secondNumber.start());
            }
            return number;
        }
        return "";
    }
    
    static boolean isViablePhoneNumber(final String number) {
        if (number.length() < 2) {
            return false;
        }
        final Matcher m = PhoneNumberUtil.VALID_PHONE_NUMBER_PATTERN.matcher(number);
        return m.matches();
    }
    
    static String normalize(final String number) {
        final Matcher m = PhoneNumberUtil.VALID_ALPHA_PHONE_PATTERN.matcher(number);
        if (m.matches()) {
            return normalizeHelper(number, PhoneNumberUtil.ALPHA_PHONE_MAPPINGS, true);
        }
        return normalizeDigitsOnly(number);
    }
    
    static void normalize(final StringBuilder number) {
        final String normalizedNumber = normalize(number.toString());
        number.replace(0, number.length(), normalizedNumber);
    }
    
    public static String normalizeDigitsOnly(final String number) {
        return normalizeDigits(number, false).toString();
    }
    
    static StringBuilder normalizeDigits(final String number, final boolean keepNonDigits) {
        final StringBuilder normalizedDigits = new StringBuilder(number.length());
        for (final char c : number.toCharArray()) {
            final int digit = Character.digit(c, 10);
            if (digit != -1) {
                normalizedDigits.append(digit);
            }
            else if (keepNonDigits) {
                normalizedDigits.append(c);
            }
        }
        return normalizedDigits;
    }
    
    static String normalizeDiallableCharsOnly(final String number) {
        return normalizeHelper(number, PhoneNumberUtil.DIALLABLE_CHAR_MAPPINGS, true);
    }
    
    public static String convertAlphaCharactersInNumber(final String number) {
        return normalizeHelper(number, PhoneNumberUtil.ALPHA_PHONE_MAPPINGS, false);
    }
    
    public int getLengthOfGeographicalAreaCode(final Phonenumber.PhoneNumber number) {
        final Phonemetadata.PhoneMetadata metadata = this.getMetadataForRegion(this.getRegionCodeForNumber(number));
        if (metadata == null) {
            return 0;
        }
        if (!metadata.hasNationalPrefix() && !number.isItalianLeadingZero()) {
            return 0;
        }
        if (!this.isNumberGeographical(number)) {
            return 0;
        }
        return this.getLengthOfNationalDestinationCode(number);
    }
    
    public int getLengthOfNationalDestinationCode(final Phonenumber.PhoneNumber number) {
        Phonenumber.PhoneNumber copiedProto;
        if (number.hasExtension()) {
            copiedProto = new Phonenumber.PhoneNumber();
            copiedProto.mergeFrom(number);
            copiedProto.clearExtension();
        }
        else {
            copiedProto = number;
        }
        final String nationalSignificantNumber = this.format(copiedProto, PhoneNumberFormat.INTERNATIONAL);
        final String[] numberGroups = PhoneNumberUtil.NON_DIGITS_PATTERN.split(nationalSignificantNumber);
        if (numberGroups.length <= 3) {
            return 0;
        }
        if (this.getNumberType(number) == PhoneNumberType.MOBILE) {
            final String mobileToken = getCountryMobileToken(number.getCountryCode());
            if (!mobileToken.equals("")) {
                return numberGroups[2].length() + numberGroups[3].length();
            }
        }
        return numberGroups[2].length();
    }
    
    public static String getCountryMobileToken(final int countryCallingCode) {
        if (PhoneNumberUtil.MOBILE_TOKEN_MAPPINGS.containsKey(countryCallingCode)) {
            return PhoneNumberUtil.MOBILE_TOKEN_MAPPINGS.get(countryCallingCode);
        }
        return "";
    }
    
    private static String normalizeHelper(final String number, final Map<Character, Character> normalizationReplacements, final boolean removeNonMatches) {
        final StringBuilder normalizedNumber = new StringBuilder(number.length());
        for (int i = 0; i < number.length(); ++i) {
            final char character = number.charAt(i);
            final Character newDigit = normalizationReplacements.get(Character.toUpperCase(character));
            if (newDigit != null) {
                normalizedNumber.append(newDigit);
            }
            else if (!removeNonMatches) {
                normalizedNumber.append(character);
            }
        }
        return normalizedNumber.toString();
    }
    
    static synchronized void setInstance(final PhoneNumberUtil util) {
        PhoneNumberUtil.instance = util;
    }
    
    public Set<String> getSupportedRegions() {
        return Collections.unmodifiableSet((Set<? extends String>)this.supportedRegions);
    }
    
    public Set<Integer> getSupportedGlobalNetworkCallingCodes() {
        return Collections.unmodifiableSet((Set<? extends Integer>)this.countryCodesForNonGeographicalRegion);
    }
    
    public static synchronized PhoneNumberUtil getInstance() {
        if (PhoneNumberUtil.instance == null) {
            setInstance(createInstance(PhoneNumberUtil.DEFAULT_METADATA_LOADER));
        }
        return PhoneNumberUtil.instance;
    }
    
    public static PhoneNumberUtil createInstance(final MetadataLoader metadataLoader) {
        if (metadataLoader == null) {
            throw new IllegalArgumentException("metadataLoader could not be null.");
        }
        return new PhoneNumberUtil("/com/google/i18n/phonenumbers/data/PhoneNumberMetadataProto", metadataLoader, CountryCodeToRegionCodeMap.getCountryCodeToRegionCodeMap());
    }
    
    static boolean formattingRuleHasFirstGroupOnly(final String nationalPrefixFormattingRule) {
        return nationalPrefixFormattingRule.length() == 0 || PhoneNumberUtil.FIRST_GROUP_ONLY_PREFIX_PATTERN.matcher(nationalPrefixFormattingRule).matches();
    }
    
    boolean isNumberGeographical(final Phonenumber.PhoneNumber phoneNumber) {
        final PhoneNumberType numberType = this.getNumberType(phoneNumber);
        return numberType == PhoneNumberType.FIXED_LINE || numberType == PhoneNumberType.FIXED_LINE_OR_MOBILE;
    }
    
    private boolean isValidRegionCode(final String regionCode) {
        return regionCode != null && this.supportedRegions.contains(regionCode);
    }
    
    private boolean hasValidCountryCallingCode(final int countryCallingCode) {
        return this.countryCallingCodeToRegionCodeMap.containsKey(countryCallingCode);
    }
    
    public String format(final Phonenumber.PhoneNumber number, final PhoneNumberFormat numberFormat) {
        if (number.getNationalNumber() == 0L && number.hasRawInput()) {
            final String rawInput = number.getRawInput();
            if (rawInput.length() > 0) {
                return rawInput;
            }
        }
        final StringBuilder formattedNumber = new StringBuilder(20);
        this.format(number, numberFormat, formattedNumber);
        return formattedNumber.toString();
    }
    
    public void format(final Phonenumber.PhoneNumber number, final PhoneNumberFormat numberFormat, final StringBuilder formattedNumber) {
        formattedNumber.setLength(0);
        final int countryCallingCode = number.getCountryCode();
        final String nationalSignificantNumber = this.getNationalSignificantNumber(number);
        if (numberFormat == PhoneNumberFormat.E164) {
            formattedNumber.append(nationalSignificantNumber);
            this.prefixNumberWithCountryCallingCode(countryCallingCode, PhoneNumberFormat.E164, formattedNumber);
            return;
        }
        if (!this.hasValidCountryCallingCode(countryCallingCode)) {
            formattedNumber.append(nationalSignificantNumber);
            return;
        }
        final String regionCode = this.getRegionCodeForCountryCode(countryCallingCode);
        final Phonemetadata.PhoneMetadata metadata = this.getMetadataForRegionOrCallingCode(countryCallingCode, regionCode);
        formattedNumber.append(this.formatNsn(nationalSignificantNumber, metadata, numberFormat));
        this.maybeAppendFormattedExtension(number, metadata, numberFormat, formattedNumber);
        this.prefixNumberWithCountryCallingCode(countryCallingCode, numberFormat, formattedNumber);
    }
    
    public String formatByPattern(final Phonenumber.PhoneNumber number, final PhoneNumberFormat numberFormat, final List<Phonemetadata.NumberFormat> userDefinedFormats) {
        final int countryCallingCode = number.getCountryCode();
        final String nationalSignificantNumber = this.getNationalSignificantNumber(number);
        if (!this.hasValidCountryCallingCode(countryCallingCode)) {
            return nationalSignificantNumber;
        }
        final String regionCode = this.getRegionCodeForCountryCode(countryCallingCode);
        final Phonemetadata.PhoneMetadata metadata = this.getMetadataForRegionOrCallingCode(countryCallingCode, regionCode);
        final StringBuilder formattedNumber = new StringBuilder(20);
        final Phonemetadata.NumberFormat formattingPattern = this.chooseFormattingPatternForNumber(userDefinedFormats, nationalSignificantNumber);
        if (formattingPattern == null) {
            formattedNumber.append(nationalSignificantNumber);
        }
        else {
            final Phonemetadata.NumberFormat numFormatCopy = new Phonemetadata.NumberFormat();
            numFormatCopy.mergeFrom(formattingPattern);
            String nationalPrefixFormattingRule = formattingPattern.getNationalPrefixFormattingRule();
            if (nationalPrefixFormattingRule.length() > 0) {
                final String nationalPrefix = metadata.getNationalPrefix();
                if (nationalPrefix.length() > 0) {
                    nationalPrefixFormattingRule = PhoneNumberUtil.NP_PATTERN.matcher(nationalPrefixFormattingRule).replaceFirst(nationalPrefix);
                    nationalPrefixFormattingRule = PhoneNumberUtil.FG_PATTERN.matcher(nationalPrefixFormattingRule).replaceFirst("\\$1");
                    numFormatCopy.setNationalPrefixFormattingRule(nationalPrefixFormattingRule);
                }
                else {
                    numFormatCopy.clearNationalPrefixFormattingRule();
                }
            }
            formattedNumber.append(this.formatNsnUsingPattern(nationalSignificantNumber, numFormatCopy, numberFormat));
        }
        this.maybeAppendFormattedExtension(number, metadata, numberFormat, formattedNumber);
        this.prefixNumberWithCountryCallingCode(countryCallingCode, numberFormat, formattedNumber);
        return formattedNumber.toString();
    }
    
    public String formatNationalNumberWithCarrierCode(final Phonenumber.PhoneNumber number, final String carrierCode) {
        final int countryCallingCode = number.getCountryCode();
        final String nationalSignificantNumber = this.getNationalSignificantNumber(number);
        if (!this.hasValidCountryCallingCode(countryCallingCode)) {
            return nationalSignificantNumber;
        }
        final String regionCode = this.getRegionCodeForCountryCode(countryCallingCode);
        final Phonemetadata.PhoneMetadata metadata = this.getMetadataForRegionOrCallingCode(countryCallingCode, regionCode);
        final StringBuilder formattedNumber = new StringBuilder(20);
        formattedNumber.append(this.formatNsn(nationalSignificantNumber, metadata, PhoneNumberFormat.NATIONAL, carrierCode));
        this.maybeAppendFormattedExtension(number, metadata, PhoneNumberFormat.NATIONAL, formattedNumber);
        this.prefixNumberWithCountryCallingCode(countryCallingCode, PhoneNumberFormat.NATIONAL, formattedNumber);
        return formattedNumber.toString();
    }
    
    private Phonemetadata.PhoneMetadata getMetadataForRegionOrCallingCode(final int countryCallingCode, final String regionCode) {
        return "001".equals(regionCode) ? this.getMetadataForNonGeographicalRegion(countryCallingCode) : this.getMetadataForRegion(regionCode);
    }
    
    public String formatNationalNumberWithPreferredCarrierCode(final Phonenumber.PhoneNumber number, final String fallbackCarrierCode) {
        return this.formatNationalNumberWithCarrierCode(number, number.hasPreferredDomesticCarrierCode() ? number.getPreferredDomesticCarrierCode() : fallbackCarrierCode);
    }
    
    public String formatNumberForMobileDialing(final Phonenumber.PhoneNumber number, final String regionCallingFrom, final boolean withFormatting) {
        final int countryCallingCode = number.getCountryCode();
        if (!this.hasValidCountryCallingCode(countryCallingCode)) {
            return number.hasRawInput() ? number.getRawInput() : "";
        }
        String formattedNumber = "";
        final Phonenumber.PhoneNumber numberNoExt = new Phonenumber.PhoneNumber().mergeFrom(number).clearExtension();
        final String regionCode = this.getRegionCodeForCountryCode(countryCallingCode);
        final PhoneNumberType numberType = this.getNumberType(numberNoExt);
        final boolean isValidNumber = numberType != PhoneNumberType.UNKNOWN;
        if (regionCallingFrom.equals(regionCode)) {
            final boolean isFixedLineOrMobile = numberType == PhoneNumberType.FIXED_LINE || numberType == PhoneNumberType.MOBILE || numberType == PhoneNumberType.FIXED_LINE_OR_MOBILE;
            if (regionCode.equals("CO") && numberType == PhoneNumberType.FIXED_LINE) {
                formattedNumber = this.formatNationalNumberWithCarrierCode(numberNoExt, "3");
            }
            else if (regionCode.equals("BR") && isFixedLineOrMobile) {
                formattedNumber = (numberNoExt.hasPreferredDomesticCarrierCode() ? (formattedNumber = this.formatNationalNumberWithPreferredCarrierCode(numberNoExt, "")) : "");
            }
            else if (isValidNumber && regionCode.equals("HU")) {
                final String value = String.valueOf(String.valueOf(this.getNddPrefixForRegion(regionCode, true)));
                final String value2 = String.valueOf(String.valueOf(this.format(numberNoExt, PhoneNumberFormat.NATIONAL)));
                formattedNumber = new StringBuilder(1 + value.length() + value2.length()).append(value).append(" ").append(value2).toString();
            }
            else if (countryCallingCode == 1) {
                final Phonemetadata.PhoneMetadata regionMetadata = this.getMetadataForRegion(regionCallingFrom);
                if (this.canBeInternationallyDialled(numberNoExt) && !this.isShorterThanPossibleNormalNumber(regionMetadata, this.getNationalSignificantNumber(numberNoExt))) {
                    formattedNumber = this.format(numberNoExt, PhoneNumberFormat.INTERNATIONAL);
                }
                else {
                    formattedNumber = this.format(numberNoExt, PhoneNumberFormat.NATIONAL);
                }
            }
            else if ((regionCode.equals("001") || ((regionCode.equals("MX") || regionCode.equals("CL")) && isFixedLineOrMobile)) && this.canBeInternationallyDialled(numberNoExt)) {
                formattedNumber = this.format(numberNoExt, PhoneNumberFormat.INTERNATIONAL);
            }
            else {
                formattedNumber = this.format(numberNoExt, PhoneNumberFormat.NATIONAL);
            }
        }
        else if (isValidNumber && this.canBeInternationallyDialled(numberNoExt)) {
            return withFormatting ? this.format(numberNoExt, PhoneNumberFormat.INTERNATIONAL) : this.format(numberNoExt, PhoneNumberFormat.E164);
        }
        return withFormatting ? formattedNumber : normalizeDiallableCharsOnly(formattedNumber);
    }
    
    public String formatOutOfCountryCallingNumber(final Phonenumber.PhoneNumber number, final String regionCallingFrom) {
        if (!this.isValidRegionCode(regionCallingFrom)) {
            final Logger logger = PhoneNumberUtil.logger;
            final Level warn\u0131ng = Level.WARNING;
            final String value = String.valueOf(String.valueOf(regionCallingFrom));
            logger.log(warn\u0131ng, new StringBuilder(79 + value.length()).append("Trying to format number from invalid region ").append(value).append(". International formatting applied.").toString());
            return this.format(number, PhoneNumberFormat.INTERNATIONAL);
        }
        final int countryCallingCode = number.getCountryCode();
        final String nationalSignificantNumber = this.getNationalSignificantNumber(number);
        if (!this.hasValidCountryCallingCode(countryCallingCode)) {
            return nationalSignificantNumber;
        }
        if (countryCallingCode == 1) {
            if (this.isNANPACountry(regionCallingFrom)) {
                final int n = countryCallingCode;
                final String value2 = String.valueOf(String.valueOf(this.format(number, PhoneNumberFormat.NATIONAL)));
                return new StringBuilder(12 + value2.length()).append(n).append(" ").append(value2).toString();
            }
        }
        else if (countryCallingCode == this.getCountryCodeForValidRegion(regionCallingFrom)) {
            return this.format(number, PhoneNumberFormat.NATIONAL);
        }
        final Phonemetadata.PhoneMetadata metadataForRegionCallingFrom = this.getMetadataForRegion(regionCallingFrom);
        final String internationalPrefix = metadataForRegionCallingFrom.getInternationalPrefix();
        String internationalPrefixForFormatting = "";
        if (PhoneNumberUtil.UNIQUE_INTERNATIONAL_PREFIX.matcher(internationalPrefix).matches()) {
            internationalPrefixForFormatting = internationalPrefix;
        }
        else if (metadataForRegionCallingFrom.hasPreferredInternationalPrefix()) {
            internationalPrefixForFormatting = metadataForRegionCallingFrom.getPreferredInternationalPrefix();
        }
        final String regionCode = this.getRegionCodeForCountryCode(countryCallingCode);
        final Phonemetadata.PhoneMetadata metadataForRegion = this.getMetadataForRegionOrCallingCode(countryCallingCode, regionCode);
        final String formattedNationalNumber = this.formatNsn(nationalSignificantNumber, metadataForRegion, PhoneNumberFormat.INTERNATIONAL);
        final StringBuilder formattedNumber = new StringBuilder(formattedNationalNumber);
        this.maybeAppendFormattedExtension(number, metadataForRegion, PhoneNumberFormat.INTERNATIONAL, formattedNumber);
        if (internationalPrefixForFormatting.length() > 0) {
            formattedNumber.insert(0, " ").insert(0, countryCallingCode).insert(0, " ").insert(0, internationalPrefixForFormatting);
        }
        else {
            this.prefixNumberWithCountryCallingCode(countryCallingCode, PhoneNumberFormat.INTERNATIONAL, formattedNumber);
        }
        return formattedNumber.toString();
    }
    
    public String formatInOriginalFormat(final Phonenumber.PhoneNumber number, final String regionCallingFrom) {
        if (number.hasRawInput() && (this.hasUnexpectedItalianLeadingZero(number) || !this.hasFormattingPatternForNumber(number))) {
            return number.getRawInput();
        }
        if (!number.hasCountryCodeSource()) {
            return this.format(number, PhoneNumberFormat.NATIONAL);
        }
        String formattedNumber = null;
        switch (number.getCountryCodeSource()) {
            case FROM_NUMBER_WITH_PLUS_SIGN: {
                formattedNumber = this.format(number, PhoneNumberFormat.INTERNATIONAL);
                break;
            }
            case FROM_NUMBER_WITH_IDD: {
                formattedNumber = this.formatOutOfCountryCallingNumber(number, regionCallingFrom);
                break;
            }
            case FROM_NUMBER_WITHOUT_PLUS_SIGN: {
                formattedNumber = this.format(number, PhoneNumberFormat.INTERNATIONAL).substring(1);
                break;
            }
            default: {
                final String regionCode = this.getRegionCodeForCountryCode(number.getCountryCode());
                final String nationalPrefix = this.getNddPrefixForRegion(regionCode, true);
                final String nationalFormat = this.format(number, PhoneNumberFormat.NATIONAL);
                if (nationalPrefix == null || nationalPrefix.length() == 0) {
                    formattedNumber = nationalFormat;
                    break;
                }
                if (this.rawInputContainsNationalPrefix(number.getRawInput(), nationalPrefix, regionCode)) {
                    formattedNumber = nationalFormat;
                    break;
                }
                final Phonemetadata.PhoneMetadata metadata = this.getMetadataForRegion(regionCode);
                final String nationalNumber = this.getNationalSignificantNumber(number);
                final Phonemetadata.NumberFormat formatRule = this.chooseFormattingPatternForNumber(metadata.numberFormats(), nationalNumber);
                if (formatRule == null) {
                    formattedNumber = nationalFormat;
                    break;
                }
                String candidateNationalPrefixRule = formatRule.getNationalPrefixFormattingRule();
                final int indexOfFirstGroup = candidateNationalPrefixRule.indexOf("$1");
                if (indexOfFirstGroup <= 0) {
                    formattedNumber = nationalFormat;
                    break;
                }
                candidateNationalPrefixRule = candidateNationalPrefixRule.substring(0, indexOfFirstGroup);
                candidateNationalPrefixRule = normalizeDigitsOnly(candidateNationalPrefixRule);
                if (candidateNationalPrefixRule.length() == 0) {
                    formattedNumber = nationalFormat;
                    break;
                }
                final Phonemetadata.NumberFormat numFormatCopy = new Phonemetadata.NumberFormat();
                numFormatCopy.mergeFrom(formatRule);
                numFormatCopy.clearNationalPrefixFormattingRule();
                final List<Phonemetadata.NumberFormat> numberFormats = new ArrayList<Phonemetadata.NumberFormat>(1);
                numberFormats.add(numFormatCopy);
                formattedNumber = this.formatByPattern(number, PhoneNumberFormat.NATIONAL, numberFormats);
                break;
            }
        }
        final String rawInput = number.getRawInput();
        if (formattedNumber != null && rawInput.length() > 0) {
            final String normalizedFormattedNumber = normalizeDiallableCharsOnly(formattedNumber);
            final String normalizedRawInput = normalizeDiallableCharsOnly(rawInput);
            if (!normalizedFormattedNumber.equals(normalizedRawInput)) {
                formattedNumber = rawInput;
            }
        }
        return formattedNumber;
    }
    
    private boolean rawInputContainsNationalPrefix(final String rawInput, final String nationalPrefix, final String regionCode) {
        final String normalizedNationalNumber = normalizeDigitsOnly(rawInput);
        if (normalizedNationalNumber.startsWith(nationalPrefix)) {
            try {
                return this.isValidNumber(this.parse(normalizedNationalNumber.substring(nationalPrefix.length()), regionCode));
            }
            catch (NumberParseException e) {
                return false;
            }
        }
        return false;
    }
    
    private boolean hasUnexpectedItalianLeadingZero(final Phonenumber.PhoneNumber number) {
        return number.isItalianLeadingZero() && !this.isLeadingZeroPossible(number.getCountryCode());
    }
    
    private boolean hasFormattingPatternForNumber(final Phonenumber.PhoneNumber number) {
        final int countryCallingCode = number.getCountryCode();
        final String phoneNumberRegion = this.getRegionCodeForCountryCode(countryCallingCode);
        final Phonemetadata.PhoneMetadata metadata = this.getMetadataForRegionOrCallingCode(countryCallingCode, phoneNumberRegion);
        if (metadata == null) {
            return false;
        }
        final String nationalNumber = this.getNationalSignificantNumber(number);
        final Phonemetadata.NumberFormat formatRule = this.chooseFormattingPatternForNumber(metadata.numberFormats(), nationalNumber);
        return formatRule != null;
    }
    
    public String formatOutOfCountryKeepingAlphaChars(final Phonenumber.PhoneNumber number, final String regionCallingFrom) {
        String rawInput = number.getRawInput();
        if (rawInput.length() == 0) {
            return this.formatOutOfCountryCallingNumber(number, regionCallingFrom);
        }
        final int countryCode = number.getCountryCode();
        if (!this.hasValidCountryCallingCode(countryCode)) {
            return rawInput;
        }
        rawInput = normalizeHelper(rawInput, PhoneNumberUtil.ALL_PLUS_NUMBER_GROUPING_SYMBOLS, true);
        final String nationalNumber = this.getNationalSignificantNumber(number);
        if (nationalNumber.length() > 3) {
            final int firstNationalNumberDigit = rawInput.indexOf(nationalNumber.substring(0, 3));
            if (firstNationalNumberDigit != -1) {
                rawInput = rawInput.substring(firstNationalNumberDigit);
            }
        }
        final Phonemetadata.PhoneMetadata metadataForRegionCallingFrom = this.getMetadataForRegion(regionCallingFrom);
        if (countryCode == 1) {
            if (this.isNANPACountry(regionCallingFrom)) {
                final int n = countryCode;
                final String value = String.valueOf(String.valueOf(rawInput));
                return new StringBuilder(12 + value.length()).append(n).append(" ").append(value).toString();
            }
        }
        else if (metadataForRegionCallingFrom != null && countryCode == this.getCountryCodeForValidRegion(regionCallingFrom)) {
            final Phonemetadata.NumberFormat formattingPattern = this.chooseFormattingPatternForNumber(metadataForRegionCallingFrom.numberFormats(), nationalNumber);
            if (formattingPattern == null) {
                return rawInput;
            }
            final Phonemetadata.NumberFormat newFormat = new Phonemetadata.NumberFormat();
            newFormat.mergeFrom(formattingPattern);
            newFormat.setPattern("(\\d+)(.*)");
            newFormat.setFormat("$1$2");
            return this.formatNsnUsingPattern(rawInput, newFormat, PhoneNumberFormat.NATIONAL);
        }
        String internationalPrefixForFormatting = "";
        if (metadataForRegionCallingFrom != null) {
            final String internationalPrefix = metadataForRegionCallingFrom.getInternationalPrefix();
            internationalPrefixForFormatting = (PhoneNumberUtil.UNIQUE_INTERNATIONAL_PREFIX.matcher(internationalPrefix).matches() ? internationalPrefix : metadataForRegionCallingFrom.getPreferredInternationalPrefix());
        }
        final StringBuilder formattedNumber = new StringBuilder(rawInput);
        final String regionCode = this.getRegionCodeForCountryCode(countryCode);
        final Phonemetadata.PhoneMetadata metadataForRegion = this.getMetadataForRegionOrCallingCode(countryCode, regionCode);
        this.maybeAppendFormattedExtension(number, metadataForRegion, PhoneNumberFormat.INTERNATIONAL, formattedNumber);
        if (internationalPrefixForFormatting.length() > 0) {
            formattedNumber.insert(0, " ").insert(0, countryCode).insert(0, " ").insert(0, internationalPrefixForFormatting);
        }
        else {
            final Logger logger = PhoneNumberUtil.logger;
            final Level warn\u0131ng = Level.WARNING;
            final String value2 = String.valueOf(String.valueOf(regionCallingFrom));
            logger.log(warn\u0131ng, new StringBuilder(79 + value2.length()).append("Trying to format number from invalid region ").append(value2).append(". International formatting applied.").toString());
            this.prefixNumberWithCountryCallingCode(countryCode, PhoneNumberFormat.INTERNATIONAL, formattedNumber);
        }
        return formattedNumber.toString();
    }
    
    public String getNationalSignificantNumber(final Phonenumber.PhoneNumber number) {
        final StringBuilder nationalNumber = new StringBuilder();
        if (number.isItalianLeadingZero()) {
            final char[] zeros = new char[number.getNumberOfLeadingZeros()];
            Arrays.fill(zeros, '0');
            nationalNumber.append(new String(zeros));
        }
        nationalNumber.append(number.getNationalNumber());
        return nationalNumber.toString();
    }
    
    private void prefixNumberWithCountryCallingCode(final int countryCallingCode, final PhoneNumberFormat numberFormat, final StringBuilder formattedNumber) {
        switch (numberFormat) {
            case E164: {
                formattedNumber.insert(0, countryCallingCode).insert(0, '+');
            }
            case INTERNATIONAL: {
                formattedNumber.insert(0, " ").insert(0, countryCallingCode).insert(0, '+');
            }
            case RFC3966: {
                formattedNumber.insert(0, "-").insert(0, countryCallingCode).insert(0, '+').insert(0, "tel:");
            }
            default: {}
        }
    }
    
    private String formatNsn(final String number, final Phonemetadata.PhoneMetadata metadata, final PhoneNumberFormat numberFormat) {
        return this.formatNsn(number, metadata, numberFormat, null);
    }
    
    private String formatNsn(final String number, final Phonemetadata.PhoneMetadata metadata, final PhoneNumberFormat numberFormat, final String carrierCode) {
        final List<Phonemetadata.NumberFormat> intlNumberFormats = metadata.intlNumberFormats();
        final List<Phonemetadata.NumberFormat> availableFormats = (intlNumberFormats.size() == 0 || numberFormat == PhoneNumberFormat.NATIONAL) ? metadata.numberFormats() : metadata.intlNumberFormats();
        final Phonemetadata.NumberFormat formattingPattern = this.chooseFormattingPatternForNumber(availableFormats, number);
        return (formattingPattern == null) ? number : this.formatNsnUsingPattern(number, formattingPattern, numberFormat, carrierCode);
    }
    
    Phonemetadata.NumberFormat chooseFormattingPatternForNumber(final List<Phonemetadata.NumberFormat> availableFormats, final String nationalNumber) {
        for (final Phonemetadata.NumberFormat numFormat : availableFormats) {
            final int size = numFormat.leadingDigitsPatternSize();
            if (size == 0 || this.regexCache.getPatternForRegex(numFormat.getLeadingDigitsPattern(size - 1)).matcher(nationalNumber).lookingAt()) {
                final Matcher m = this.regexCache.getPatternForRegex(numFormat.getPattern()).matcher(nationalNumber);
                if (m.matches()) {
                    return numFormat;
                }
                continue;
            }
        }
        return null;
    }
    
    String formatNsnUsingPattern(final String nationalNumber, final Phonemetadata.NumberFormat formattingPattern, final PhoneNumberFormat numberFormat) {
        return this.formatNsnUsingPattern(nationalNumber, formattingPattern, numberFormat, null);
    }
    
    private String formatNsnUsingPattern(final String nationalNumber, final Phonemetadata.NumberFormat formattingPattern, final PhoneNumberFormat numberFormat, final String carrierCode) {
        String numberFormatRule = formattingPattern.getFormat();
        final Matcher m = this.regexCache.getPatternForRegex(formattingPattern.getPattern()).matcher(nationalNumber);
        String formattedNationalNumber = "";
        if (numberFormat == PhoneNumberFormat.NATIONAL && carrierCode != null && carrierCode.length() > 0 && formattingPattern.getDomesticCarrierCodeFormattingRule().length() > 0) {
            String carrierCodeFormattingRule = formattingPattern.getDomesticCarrierCodeFormattingRule();
            carrierCodeFormattingRule = PhoneNumberUtil.CC_PATTERN.matcher(carrierCodeFormattingRule).replaceFirst(carrierCode);
            numberFormatRule = PhoneNumberUtil.FIRST_GROUP_PATTERN.matcher(numberFormatRule).replaceFirst(carrierCodeFormattingRule);
            formattedNationalNumber = m.replaceAll(numberFormatRule);
        }
        else {
            final String nationalPrefixFormattingRule = formattingPattern.getNationalPrefixFormattingRule();
            if (numberFormat == PhoneNumberFormat.NATIONAL && nationalPrefixFormattingRule != null && nationalPrefixFormattingRule.length() > 0) {
                final Matcher firstGroupMatcher = PhoneNumberUtil.FIRST_GROUP_PATTERN.matcher(numberFormatRule);
                formattedNationalNumber = m.replaceAll(firstGroupMatcher.replaceFirst(nationalPrefixFormattingRule));
            }
            else {
                formattedNationalNumber = m.replaceAll(numberFormatRule);
            }
        }
        if (numberFormat == PhoneNumberFormat.RFC3966) {
            final Matcher matcher = PhoneNumberUtil.SEPARATOR_PATTERN.matcher(formattedNationalNumber);
            if (matcher.lookingAt()) {
                formattedNationalNumber = matcher.replaceFirst("");
            }
            formattedNationalNumber = matcher.reset(formattedNationalNumber).replaceAll("-");
        }
        return formattedNationalNumber;
    }
    
    public Phonenumber.PhoneNumber getExampleNumber(final String regionCode) {
        return this.getExampleNumberForType(regionCode, PhoneNumberType.FIXED_LINE);
    }
    
    public Phonenumber.PhoneNumber getExampleNumberForType(final String regionCode, final PhoneNumberType type) {
        if (!this.isValidRegionCode(regionCode)) {
            final Logger logger = PhoneNumberUtil.logger;
            final Level warn\u0131ng = Level.WARNING;
            final String s = "Invalid or unknown region code provided: ";
            final String value = String.valueOf(regionCode);
            logger.log(warn\u0131ng, (value.length() != 0) ? s.concat(value) : new String(s));
            return null;
        }
        final Phonemetadata.PhoneNumberDesc desc = this.getNumberDescByType(this.getMetadataForRegion(regionCode), type);
        try {
            if (desc.hasExampleNumber()) {
                return this.parse(desc.getExampleNumber(), regionCode);
            }
        }
        catch (NumberParseException e) {
            PhoneNumberUtil.logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }
    
    public Phonenumber.PhoneNumber getExampleNumberForNonGeoEntity(final int countryCallingCode) {
        final Phonemetadata.PhoneMetadata metadata = this.getMetadataForNonGeographicalRegion(countryCallingCode);
        if (metadata != null) {
            final Phonemetadata.PhoneNumberDesc desc = metadata.getGeneralDesc();
            try {
                if (desc.hasExampleNumber()) {
                    final String value = String.valueOf(String.valueOf(desc.getExampleNumber()));
                    return this.parse(new StringBuilder(12 + value.length()).append("+").append(countryCallingCode).append(value).toString(), "ZZ");
                }
            }
            catch (NumberParseException e) {
                PhoneNumberUtil.logger.log(Level.SEVERE, e.toString());
            }
        }
        else {
            PhoneNumberUtil.logger.log(Level.WARNING, new StringBuilder(61).append("Invalid or unknown country calling code provided: ").append(countryCallingCode).toString());
        }
        return null;
    }
    
    private void maybeAppendFormattedExtension(final Phonenumber.PhoneNumber number, final Phonemetadata.PhoneMetadata metadata, final PhoneNumberFormat numberFormat, final StringBuilder formattedNumber) {
        if (number.hasExtension() && number.getExtension().length() > 0) {
            if (numberFormat == PhoneNumberFormat.RFC3966) {
                formattedNumber.append(";ext=").append(number.getExtension());
            }
            else if (metadata.hasPreferredExtnPrefix()) {
                formattedNumber.append(metadata.getPreferredExtnPrefix()).append(number.getExtension());
            }
            else {
                formattedNumber.append(" ext. ").append(number.getExtension());
            }
        }
    }
    
    Phonemetadata.PhoneNumberDesc getNumberDescByType(final Phonemetadata.PhoneMetadata metadata, final PhoneNumberType type) {
        switch (type) {
            case PREMIUM_RATE: {
                return metadata.getPremiumRate();
            }
            case TOLL_FREE: {
                return metadata.getTollFree();
            }
            case MOBILE: {
                return metadata.getMobile();
            }
            case FIXED_LINE:
            case FIXED_LINE_OR_MOBILE: {
                return metadata.getFixedLine();
            }
            case SHARED_COST: {
                return metadata.getSharedCost();
            }
            case VOIP: {
                return metadata.getVoip();
            }
            case PERSONAL_NUMBER: {
                return metadata.getPersonalNumber();
            }
            case PAGER: {
                return metadata.getPager();
            }
            case UAN: {
                return metadata.getUan();
            }
            case VOICEMAIL: {
                return metadata.getVoicemail();
            }
            default: {
                return metadata.getGeneralDesc();
            }
        }
    }
    
    public PhoneNumberType getNumberType(final Phonenumber.PhoneNumber number) {
        final String regionCode = this.getRegionCodeForNumber(number);
        final Phonemetadata.PhoneMetadata metadata = this.getMetadataForRegionOrCallingCode(number.getCountryCode(), regionCode);
        if (metadata == null) {
            return PhoneNumberType.UNKNOWN;
        }
        final String nationalSignificantNumber = this.getNationalSignificantNumber(number);
        return this.getNumberTypeHelper(nationalSignificantNumber, metadata);
    }
    
    private PhoneNumberType getNumberTypeHelper(final String nationalNumber, final Phonemetadata.PhoneMetadata metadata) {
        final Phonemetadata.PhoneNumberDesc generalNumberDesc = metadata.getGeneralDesc();
        if (!generalNumberDesc.hasNationalNumberPattern() || !this.isNumberMatchingDesc(nationalNumber, generalNumberDesc)) {
            return PhoneNumberType.UNKNOWN;
        }
        if (this.isNumberMatchingDesc(nationalNumber, metadata.getPremiumRate())) {
            return PhoneNumberType.PREMIUM_RATE;
        }
        if (this.isNumberMatchingDesc(nationalNumber, metadata.getTollFree())) {
            return PhoneNumberType.TOLL_FREE;
        }
        if (this.isNumberMatchingDesc(nationalNumber, metadata.getSharedCost())) {
            return PhoneNumberType.SHARED_COST;
        }
        if (this.isNumberMatchingDesc(nationalNumber, metadata.getVoip())) {
            return PhoneNumberType.VOIP;
        }
        if (this.isNumberMatchingDesc(nationalNumber, metadata.getPersonalNumber())) {
            return PhoneNumberType.PERSONAL_NUMBER;
        }
        if (this.isNumberMatchingDesc(nationalNumber, metadata.getPager())) {
            return PhoneNumberType.PAGER;
        }
        if (this.isNumberMatchingDesc(nationalNumber, metadata.getUan())) {
            return PhoneNumberType.UAN;
        }
        if (this.isNumberMatchingDesc(nationalNumber, metadata.getVoicemail())) {
            return PhoneNumberType.VOICEMAIL;
        }
        final boolean isFixedLine = this.isNumberMatchingDesc(nationalNumber, metadata.getFixedLine());
        if (isFixedLine) {
            if (metadata.isSameMobileAndFixedLinePattern()) {
                return PhoneNumberType.FIXED_LINE_OR_MOBILE;
            }
            if (this.isNumberMatchingDesc(nationalNumber, metadata.getMobile())) {
                return PhoneNumberType.FIXED_LINE_OR_MOBILE;
            }
            return PhoneNumberType.FIXED_LINE;
        }
        else {
            if (!metadata.isSameMobileAndFixedLinePattern() && this.isNumberMatchingDesc(nationalNumber, metadata.getMobile())) {
                return PhoneNumberType.MOBILE;
            }
            return PhoneNumberType.UNKNOWN;
        }
    }
    
    Phonemetadata.PhoneMetadata getMetadataForRegion(final String regionCode) {
        if (!this.isValidRegionCode(regionCode)) {
            return null;
        }
        synchronized (this.regionToMetadataMap) {
            if (!this.regionToMetadataMap.containsKey(regionCode)) {
                this.loadMetadataFromFile(this.currentFilePrefix, regionCode, 0, this.metadataLoader);
            }
        }
        return this.regionToMetadataMap.get(regionCode);
    }
    
    Phonemetadata.PhoneMetadata getMetadataForNonGeographicalRegion(final int countryCallingCode) {
        synchronized (this.countryCodeToNonGeographicalMetadataMap) {
            if (!this.countryCallingCodeToRegionCodeMap.containsKey(countryCallingCode)) {
                return null;
            }
            if (!this.countryCodeToNonGeographicalMetadataMap.containsKey(countryCallingCode)) {
                this.loadMetadataFromFile(this.currentFilePrefix, "001", countryCallingCode, this.metadataLoader);
            }
        }
        return this.countryCodeToNonGeographicalMetadataMap.get(countryCallingCode);
    }
    
    boolean isNumberPossibleForDesc(final String nationalNumber, final Phonemetadata.PhoneNumberDesc numberDesc) {
        final Matcher possibleNumberPatternMatcher = this.regexCache.getPatternForRegex(numberDesc.getPossibleNumberPattern()).matcher(nationalNumber);
        return possibleNumberPatternMatcher.matches();
    }
    
    boolean isNumberMatchingDesc(final String nationalNumber, final Phonemetadata.PhoneNumberDesc numberDesc) {
        final Matcher nationalNumberPatternMatcher = this.regexCache.getPatternForRegex(numberDesc.getNationalNumberPattern()).matcher(nationalNumber);
        return this.isNumberPossibleForDesc(nationalNumber, numberDesc) && nationalNumberPatternMatcher.matches();
    }
    
    public boolean isValidNumber(final Phonenumber.PhoneNumber number) {
        final String regionCode = this.getRegionCodeForNumber(number);
        return this.isValidNumberForRegion(number, regionCode);
    }
    
    public boolean isValidNumberForRegion(final Phonenumber.PhoneNumber number, final String regionCode) {
        final int countryCode = number.getCountryCode();
        final Phonemetadata.PhoneMetadata metadata = this.getMetadataForRegionOrCallingCode(countryCode, regionCode);
        if (metadata == null || (!"001".equals(regionCode) && countryCode != this.getCountryCodeForValidRegion(regionCode))) {
            return false;
        }
        final Phonemetadata.PhoneNumberDesc generalNumDesc = metadata.getGeneralDesc();
        final String nationalSignificantNumber = this.getNationalSignificantNumber(number);
        if (!generalNumDesc.hasNationalNumberPattern()) {
            final int numberLength = nationalSignificantNumber.length();
            return numberLength > 2 && numberLength <= 17;
        }
        return this.getNumberTypeHelper(nationalSignificantNumber, metadata) != PhoneNumberType.UNKNOWN;
    }
    
    public String getRegionCodeForNumber(final Phonenumber.PhoneNumber number) {
        final int countryCode = number.getCountryCode();
        final List<String> regions = this.countryCallingCodeToRegionCodeMap.get(countryCode);
        if (regions == null) {
            final String numberString = this.getNationalSignificantNumber(number);
            final Logger logger = PhoneNumberUtil.logger;
            final Level warn\u0131ng = Level.WARNING;
            final int n = countryCode;
            final String value = String.valueOf(String.valueOf(numberString));
            logger.log(warn\u0131ng, new StringBuilder(54 + value.length()).append("Missing/invalid country_code (").append(n).append(") for number ").append(value).toString());
            return null;
        }
        if (regions.size() == 1) {
            return regions.get(0);
        }
        return this.getRegionCodeForNumberFromRegionList(number, regions);
    }
    
    private String getRegionCodeForNumberFromRegionList(final Phonenumber.PhoneNumber number, final List<String> regionCodes) {
        final String nationalNumber = this.getNationalSignificantNumber(number);
        for (final String regionCode : regionCodes) {
            final Phonemetadata.PhoneMetadata metadata = this.getMetadataForRegion(regionCode);
            if (metadata.hasLeadingDigits()) {
                if (this.regexCache.getPatternForRegex(metadata.getLeadingDigits()).matcher(nationalNumber).lookingAt()) {
                    return regionCode;
                }
                continue;
            }
            else {
                if (this.getNumberTypeHelper(nationalNumber, metadata) != PhoneNumberType.UNKNOWN) {
                    return regionCode;
                }
                continue;
            }
        }
        return null;
    }
    
    public String getRegionCodeForCountryCode(final int countryCallingCode) {
        final List<String> regionCodes = this.countryCallingCodeToRegionCodeMap.get(countryCallingCode);
        return (regionCodes == null) ? "ZZ" : regionCodes.get(0);
    }
    
    public List<String> getRegionCodesForCountryCode(final int countryCallingCode) {
        final List<String> regionCodes = this.countryCallingCodeToRegionCodeMap.get(countryCallingCode);
        return Collections.unmodifiableList((List<? extends String>)((regionCodes == null) ? new ArrayList<String>(0) : regionCodes));
    }
    
    public int getCountryCodeForRegion(final String regionCode) {
        if (!this.isValidRegionCode(regionCode)) {
            final Logger logger = PhoneNumberUtil.logger;
            final Level warn\u0131ng = Level.WARNING;
            final String value = String.valueOf(String.valueOf((regionCode == null) ? "null" : regionCode));
            logger.log(warn\u0131ng, new StringBuilder(43 + value.length()).append("Invalid or missing region code (").append(value).append(") provided.").toString());
            return 0;
        }
        return this.getCountryCodeForValidRegion(regionCode);
    }
    
    private int getCountryCodeForValidRegion(final String regionCode) {
        final Phonemetadata.PhoneMetadata metadata = this.getMetadataForRegion(regionCode);
        if (metadata == null) {
            final String s = "Invalid region code: ";
            final String value = String.valueOf(regionCode);
            throw new IllegalArgumentException((value.length() != 0) ? s.concat(value) : new String(s));
        }
        return metadata.getCountryCode();
    }
    
    public String getNddPrefixForRegion(final String regionCode, final boolean stripNonDigits) {
        final Phonemetadata.PhoneMetadata metadata = this.getMetadataForRegion(regionCode);
        if (metadata == null) {
            final Logger logger = PhoneNumberUtil.logger;
            final Level warn\u0131ng = Level.WARNING;
            final String value = String.valueOf(String.valueOf((regionCode == null) ? "null" : regionCode));
            logger.log(warn\u0131ng, new StringBuilder(43 + value.length()).append("Invalid or missing region code (").append(value).append(") provided.").toString());
            return null;
        }
        String nationalPrefix = metadata.getNationalPrefix();
        if (nationalPrefix.length() == 0) {
            return null;
        }
        if (stripNonDigits) {
            nationalPrefix = nationalPrefix.replace("~", "");
        }
        return nationalPrefix;
    }
    
    public boolean isNANPACountry(final String regionCode) {
        return this.nanpaRegions.contains(regionCode);
    }
    
    boolean isLeadingZeroPossible(final int countryCallingCode) {
        final Phonemetadata.PhoneMetadata mainMetadataForCallingCode = this.getMetadataForRegionOrCallingCode(countryCallingCode, this.getRegionCodeForCountryCode(countryCallingCode));
        return mainMetadataForCallingCode != null && mainMetadataForCallingCode.isLeadingZeroPossible();
    }
    
    public boolean isAlphaNumber(final String number) {
        if (!isViablePhoneNumber(number)) {
            return false;
        }
        final StringBuilder strippedNumber = new StringBuilder(number);
        this.maybeStripExtension(strippedNumber);
        return PhoneNumberUtil.VALID_ALPHA_PHONE_PATTERN.matcher(strippedNumber).matches();
    }
    
    public boolean isPossibleNumber(final Phonenumber.PhoneNumber number) {
        return this.isPossibleNumberWithReason(number) == ValidationResult.IS_POSSIBLE;
    }
    
    private ValidationResult testNumberLengthAgainstPattern(final Pattern numberPattern, final String number) {
        final Matcher numberMatcher = numberPattern.matcher(number);
        if (numberMatcher.matches()) {
            return ValidationResult.IS_POSSIBLE;
        }
        if (numberMatcher.lookingAt()) {
            return ValidationResult.TOO_LONG;
        }
        return ValidationResult.TOO_SHORT;
    }
    
    private boolean isShorterThanPossibleNormalNumber(final Phonemetadata.PhoneMetadata regionMetadata, final String number) {
        final Pattern possibleNumberPattern = this.regexCache.getPatternForRegex(regionMetadata.getGeneralDesc().getPossibleNumberPattern());
        return this.testNumberLengthAgainstPattern(possibleNumberPattern, number) == ValidationResult.TOO_SHORT;
    }
    
    public ValidationResult isPossibleNumberWithReason(final Phonenumber.PhoneNumber number) {
        final String nationalNumber = this.getNationalSignificantNumber(number);
        final int countryCode = number.getCountryCode();
        if (!this.hasValidCountryCallingCode(countryCode)) {
            return ValidationResult.INVALID_COUNTRY_CODE;
        }
        final String regionCode = this.getRegionCodeForCountryCode(countryCode);
        final Phonemetadata.PhoneMetadata metadata = this.getMetadataForRegionOrCallingCode(countryCode, regionCode);
        final Phonemetadata.PhoneNumberDesc generalNumDesc = metadata.getGeneralDesc();
        if (generalNumDesc.hasNationalNumberPattern()) {
            final Pattern possibleNumberPattern = this.regexCache.getPatternForRegex(generalNumDesc.getPossibleNumberPattern());
            return this.testNumberLengthAgainstPattern(possibleNumberPattern, nationalNumber);
        }
        PhoneNumberUtil.logger.log(Level.FINER, "Checking if number is possible with incomplete metadata.");
        final int numberLength = nationalNumber.length();
        if (numberLength < 2) {
            return ValidationResult.TOO_SHORT;
        }
        if (numberLength > 17) {
            return ValidationResult.TOO_LONG;
        }
        return ValidationResult.IS_POSSIBLE;
    }
    
    public boolean isPossibleNumber(final String number, final String regionDialingFrom) {
        try {
            return this.isPossibleNumber(this.parse(number, regionDialingFrom));
        }
        catch (NumberParseException e) {
            return false;
        }
    }
    
    public boolean truncateTooLongNumber(final Phonenumber.PhoneNumber number) {
        if (this.isValidNumber(number)) {
            return true;
        }
        final Phonenumber.PhoneNumber numberCopy = new Phonenumber.PhoneNumber();
        numberCopy.mergeFrom(number);
        long nationalNumber = number.getNationalNumber();
        do {
            nationalNumber /= 10L;
            numberCopy.setNationalNumber(nationalNumber);
            if (this.isPossibleNumberWithReason(numberCopy) == ValidationResult.TOO_SHORT || nationalNumber == 0L) {
                return false;
            }
        } while (!this.isValidNumber(numberCopy));
        number.setNationalNumber(nationalNumber);
        return true;
    }
    
    public AsYouTypeFormatter getAsYouTypeFormatter(final String regionCode) {
        return new AsYouTypeFormatter(regionCode);
    }
    
    int extractCountryCode(final StringBuilder fullNumber, final StringBuilder nationalNumber) {
        if (fullNumber.length() == 0 || fullNumber.charAt(0) == '0') {
            return 0;
        }
        for (int numberLength = fullNumber.length(), i = 1; i <= 3 && i <= numberLength; ++i) {
            final int potentialCountryCode = Integer.parseInt(fullNumber.substring(0, i));
            if (this.countryCallingCodeToRegionCodeMap.containsKey(potentialCountryCode)) {
                nationalNumber.append(fullNumber.substring(i));
                return potentialCountryCode;
            }
        }
        return 0;
    }
    
    int maybeExtractCountryCode(final String number, final Phonemetadata.PhoneMetadata defaultRegionMetadata, final StringBuilder nationalNumber, final boolean keepRawInput, final Phonenumber.PhoneNumber phoneNumber) throws NumberParseException {
        if (number.length() == 0) {
            return 0;
        }
        final StringBuilder fullNumber = new StringBuilder(number);
        String possibleCountryIddPrefix = "NonMatch";
        if (defaultRegionMetadata != null) {
            possibleCountryIddPrefix = defaultRegionMetadata.getInternationalPrefix();
        }
        final Phonenumber.PhoneNumber.CountryCodeSource countryCodeSource = this.maybeStripInternationalPrefixAndNormalize(fullNumber, possibleCountryIddPrefix);
        if (keepRawInput) {
            phoneNumber.setCountryCodeSource(countryCodeSource);
        }
        if (countryCodeSource == Phonenumber.PhoneNumber.CountryCodeSource.FROM_DEFAULT_COUNTRY) {
            if (defaultRegionMetadata != null) {
                final int defaultCountryCode = defaultRegionMetadata.getCountryCode();
                final String defaultCountryCodeString = String.valueOf(defaultCountryCode);
                final String normalizedNumber = fullNumber.toString();
                if (normalizedNumber.startsWith(defaultCountryCodeString)) {
                    final StringBuilder potentialNationalNumber = new StringBuilder(normalizedNumber.substring(defaultCountryCodeString.length()));
                    final Phonemetadata.PhoneNumberDesc generalDesc = defaultRegionMetadata.getGeneralDesc();
                    final Pattern validNumberPattern = this.regexCache.getPatternForRegex(generalDesc.getNationalNumberPattern());
                    this.maybeStripNationalPrefixAndCarrierCode(potentialNationalNumber, defaultRegionMetadata, null);
                    final Pattern possibleNumberPattern = this.regexCache.getPatternForRegex(generalDesc.getPossibleNumberPattern());
                    if ((!validNumberPattern.matcher(fullNumber).matches() && validNumberPattern.matcher(potentialNationalNumber).matches()) || this.testNumberLengthAgainstPattern(possibleNumberPattern, fullNumber.toString()) == ValidationResult.TOO_LONG) {
                        nationalNumber.append((CharSequence)potentialNationalNumber);
                        if (keepRawInput) {
                            phoneNumber.setCountryCodeSource(Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITHOUT_PLUS_SIGN);
                        }
                        phoneNumber.setCountryCode(defaultCountryCode);
                        return defaultCountryCode;
                    }
                }
            }
            phoneNumber.setCountryCode(0);
            return 0;
        }
        if (fullNumber.length() <= 2) {
            throw new NumberParseException(NumberParseException.ErrorType.TOO_SHORT_AFTER_IDD, "Phone number had an IDD, but after this was not long enough to be a viable phone number.");
        }
        final int potentialCountryCode = this.extractCountryCode(fullNumber, nationalNumber);
        if (potentialCountryCode != 0) {
            phoneNumber.setCountryCode(potentialCountryCode);
            return potentialCountryCode;
        }
        throw new NumberParseException(NumberParseException.ErrorType.INVALID_COUNTRY_CODE, "Country calling code supplied was not recognised.");
    }
    
    private boolean parsePrefixAsIdd(final Pattern iddPattern, final StringBuilder number) {
        final Matcher m = iddPattern.matcher(number);
        if (m.lookingAt()) {
            final int matchEnd = m.end();
            final Matcher digitMatcher = PhoneNumberUtil.CAPTURING_DIGIT_PATTERN.matcher(number.substring(matchEnd));
            if (digitMatcher.find()) {
                final String normalizedGroup = normalizeDigitsOnly(digitMatcher.group(1));
                if (normalizedGroup.equals("0")) {
                    return false;
                }
            }
            number.delete(0, matchEnd);
            return true;
        }
        return false;
    }
    
    Phonenumber.PhoneNumber.CountryCodeSource maybeStripInternationalPrefixAndNormalize(final StringBuilder number, final String possibleIddPrefix) {
        if (number.length() == 0) {
            return Phonenumber.PhoneNumber.CountryCodeSource.FROM_DEFAULT_COUNTRY;
        }
        final Matcher m = PhoneNumberUtil.PLUS_CHARS_PATTERN.matcher(number);
        if (m.lookingAt()) {
            number.delete(0, m.end());
            normalize(number);
            return Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN;
        }
        final Pattern iddPattern = this.regexCache.getPatternForRegex(possibleIddPrefix);
        normalize(number);
        return this.parsePrefixAsIdd(iddPattern, number) ? Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_IDD : Phonenumber.PhoneNumber.CountryCodeSource.FROM_DEFAULT_COUNTRY;
    }
    
    boolean maybeStripNationalPrefixAndCarrierCode(final StringBuilder number, final Phonemetadata.PhoneMetadata metadata, final StringBuilder carrierCode) {
        final int numberLength = number.length();
        final String possibleNationalPrefix = metadata.getNationalPrefixForParsing();
        if (numberLength == 0 || possibleNationalPrefix.length() == 0) {
            return false;
        }
        final Matcher prefixMatcher = this.regexCache.getPatternForRegex(possibleNationalPrefix).matcher(number);
        if (!prefixMatcher.lookingAt()) {
            return false;
        }
        final Pattern nationalNumberRule = this.regexCache.getPatternForRegex(metadata.getGeneralDesc().getNationalNumberPattern());
        final boolean isViableOriginalNumber = nationalNumberRule.matcher(number).matches();
        final int numOfGroups = prefixMatcher.groupCount();
        final String transformRule = metadata.getNationalPrefixTransformRule();
        if (transformRule == null || transformRule.length() == 0 || prefixMatcher.group(numOfGroups) == null) {
            if (isViableOriginalNumber && !nationalNumberRule.matcher(number.substring(prefixMatcher.end())).matches()) {
                return false;
            }
            if (carrierCode != null && numOfGroups > 0 && prefixMatcher.group(numOfGroups) != null) {
                carrierCode.append(prefixMatcher.group(1));
            }
            number.delete(0, prefixMatcher.end());
            return true;
        }
        else {
            final StringBuilder transformedNumber = new StringBuilder(number);
            transformedNumber.replace(0, numberLength, prefixMatcher.replaceFirst(transformRule));
            if (isViableOriginalNumber && !nationalNumberRule.matcher(transformedNumber.toString()).matches()) {
                return false;
            }
            if (carrierCode != null && numOfGroups > 1) {
                carrierCode.append(prefixMatcher.group(1));
            }
            number.replace(0, number.length(), transformedNumber.toString());
            return true;
        }
    }
    
    String maybeStripExtension(final StringBuilder number) {
        final Matcher m = PhoneNumberUtil.EXTN_PATTERN.matcher(number);
        if (m.find() && isViablePhoneNumber(number.substring(0, m.start()))) {
            for (int i = 1, length = m.groupCount(); i <= length; ++i) {
                if (m.group(i) != null) {
                    final String extension = m.group(i);
                    number.delete(m.start(), number.length());
                    return extension;
                }
            }
        }
        return "";
    }
    
    private boolean checkRegionForParsing(final String numberToParse, final String defaultRegion) {
        return this.isValidRegionCode(defaultRegion) || (numberToParse != null && numberToParse.length() != 0 && PhoneNumberUtil.PLUS_CHARS_PATTERN.matcher(numberToParse).lookingAt());
    }
    
    public Phonenumber.PhoneNumber parse(final String numberToParse, final String defaultRegion) throws NumberParseException {
        final Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber();
        this.parse(numberToParse, defaultRegion, phoneNumber);
        return phoneNumber;
    }
    
    public void parse(final String numberToParse, final String defaultRegion, final Phonenumber.PhoneNumber phoneNumber) throws NumberParseException {
        this.parseHelper(numberToParse, defaultRegion, false, true, phoneNumber);
    }
    
    public Phonenumber.PhoneNumber parseAndKeepRawInput(final String numberToParse, final String defaultRegion) throws NumberParseException {
        final Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber();
        this.parseAndKeepRawInput(numberToParse, defaultRegion, phoneNumber);
        return phoneNumber;
    }
    
    public void parseAndKeepRawInput(final String numberToParse, final String defaultRegion, final Phonenumber.PhoneNumber phoneNumber) throws NumberParseException {
        this.parseHelper(numberToParse, defaultRegion, true, true, phoneNumber);
    }
    
    public Iterable<PhoneNumberMatch> findNumbers(final CharSequence text, final String defaultRegion) {
        return this.findNumbers(text, defaultRegion, Leniency.VALID, Long.MAX_VALUE);
    }
    
    public Iterable<PhoneNumberMatch> findNumbers(final CharSequence text, final String defaultRegion, final Leniency leniency, final long maxTries) {
        return new Iterable<PhoneNumberMatch>() {
            public Iterator<PhoneNumberMatch> iterator() {
                return new PhoneNumberMatcher(PhoneNumberUtil.this, text, defaultRegion, leniency, maxTries);
            }
        };
    }
    
    static void setItalianLeadingZerosForPhoneNumber(final String nationalNumber, final Phonenumber.PhoneNumber phoneNumber) {
        if (nationalNumber.length() > 1 && nationalNumber.charAt(0) == '0') {
            phoneNumber.setItalianLeadingZero(true);
            int numberOfLeadingZeros;
            for (numberOfLeadingZeros = 1; numberOfLeadingZeros < nationalNumber.length() - 1 && nationalNumber.charAt(numberOfLeadingZeros) == '0'; ++numberOfLeadingZeros) {}
            if (numberOfLeadingZeros != 1) {
                phoneNumber.setNumberOfLeadingZeros(numberOfLeadingZeros);
            }
        }
    }
    
    private void parseHelper(final String numberToParse, final String defaultRegion, final boolean keepRawInput, final boolean checkRegion, final Phonenumber.PhoneNumber phoneNumber) throws NumberParseException {
        if (numberToParse == null) {
            throw new NumberParseException(NumberParseException.ErrorType.NOT_A_NUMBER, "The phone number supplied was null.");
        }
        if (numberToParse.length() > 250) {
            throw new NumberParseException(NumberParseException.ErrorType.TOO_LONG, "The string supplied was too long to parse.");
        }
        final StringBuilder nationalNumber = new StringBuilder();
        this.buildNationalNumberForParsing(numberToParse, nationalNumber);
        if (!isViablePhoneNumber(nationalNumber.toString())) {
            throw new NumberParseException(NumberParseException.ErrorType.NOT_A_NUMBER, "The string supplied did not seem to be a phone number.");
        }
        if (checkRegion && !this.checkRegionForParsing(nationalNumber.toString(), defaultRegion)) {
            throw new NumberParseException(NumberParseException.ErrorType.INVALID_COUNTRY_CODE, "Missing or invalid default region.");
        }
        if (keepRawInput) {
            phoneNumber.setRawInput(numberToParse);
        }
        final String extension = this.maybeStripExtension(nationalNumber);
        if (extension.length() > 0) {
            phoneNumber.setExtension(extension);
        }
        Phonemetadata.PhoneMetadata regionMetadata = this.getMetadataForRegion(defaultRegion);
        StringBuilder normalizedNationalNumber = new StringBuilder();
        int countryCode = 0;
        try {
            countryCode = this.maybeExtractCountryCode(nationalNumber.toString(), regionMetadata, normalizedNationalNumber, keepRawInput, phoneNumber);
        }
        catch (NumberParseException e) {
            final Matcher matcher = PhoneNumberUtil.PLUS_CHARS_PATTERN.matcher(nationalNumber.toString());
            if (e.getErrorType() != NumberParseException.ErrorType.INVALID_COUNTRY_CODE || !matcher.lookingAt()) {
                throw new NumberParseException(e.getErrorType(), e.getMessage());
            }
            countryCode = this.maybeExtractCountryCode(nationalNumber.substring(matcher.end()), regionMetadata, normalizedNationalNumber, keepRawInput, phoneNumber);
            if (countryCode == 0) {
                throw new NumberParseException(NumberParseException.ErrorType.INVALID_COUNTRY_CODE, "Could not interpret numbers after plus-sign.");
            }
        }
        if (countryCode != 0) {
            final String phoneNumberRegion = this.getRegionCodeForCountryCode(countryCode);
            if (!phoneNumberRegion.equals(defaultRegion)) {
                regionMetadata = this.getMetadataForRegionOrCallingCode(countryCode, phoneNumberRegion);
            }
        }
        else {
            normalize(nationalNumber);
            normalizedNationalNumber.append((CharSequence)nationalNumber);
            if (defaultRegion != null) {
                countryCode = regionMetadata.getCountryCode();
                phoneNumber.setCountryCode(countryCode);
            }
            else if (keepRawInput) {
                phoneNumber.clearCountryCodeSource();
            }
        }
        if (normalizedNationalNumber.length() < 2) {
            throw new NumberParseException(NumberParseException.ErrorType.TOO_SHORT_NSN, "The string supplied is too short to be a phone number.");
        }
        if (regionMetadata != null) {
            final StringBuilder carrierCode = new StringBuilder();
            final StringBuilder potentialNationalNumber = new StringBuilder(normalizedNationalNumber);
            this.maybeStripNationalPrefixAndCarrierCode(potentialNationalNumber, regionMetadata, carrierCode);
            if (!this.isShorterThanPossibleNormalNumber(regionMetadata, potentialNationalNumber.toString())) {
                normalizedNationalNumber = potentialNationalNumber;
                if (keepRawInput) {
                    phoneNumber.setPreferredDomesticCarrierCode(carrierCode.toString());
                }
            }
        }
        final int lengthOfNationalNumber = normalizedNationalNumber.length();
        if (lengthOfNationalNumber < 2) {
            throw new NumberParseException(NumberParseException.ErrorType.TOO_SHORT_NSN, "The string supplied is too short to be a phone number.");
        }
        if (lengthOfNationalNumber > 17) {
            throw new NumberParseException(NumberParseException.ErrorType.TOO_LONG, "The string supplied is too long to be a phone number.");
        }
        setItalianLeadingZerosForPhoneNumber(normalizedNationalNumber.toString(), phoneNumber);
        phoneNumber.setNationalNumber(Long.parseLong(normalizedNationalNumber.toString()));
    }
    
    private void buildNationalNumberForParsing(final String numberToParse, final StringBuilder nationalNumber) {
        final int indexOfPhoneContext = numberToParse.indexOf(";phone-context=");
        if (indexOfPhoneContext > 0) {
            final int phoneContextStart = indexOfPhoneContext + ";phone-context=".length();
            if (numberToParse.charAt(phoneContextStart) == '+') {
                final int phoneContextEnd = numberToParse.indexOf(59, phoneContextStart);
                if (phoneContextEnd > 0) {
                    nationalNumber.append(numberToParse.substring(phoneContextStart, phoneContextEnd));
                }
                else {
                    nationalNumber.append(numberToParse.substring(phoneContextStart));
                }
            }
            final int indexOfRfc3966Prefix = numberToParse.indexOf("tel:");
            final int indexOfNationalNumber = (indexOfRfc3966Prefix >= 0) ? (indexOfRfc3966Prefix + "tel:".length()) : 0;
            nationalNumber.append(numberToParse.substring(indexOfNationalNumber, indexOfPhoneContext));
        }
        else {
            nationalNumber.append(extractPossibleNumber(numberToParse));
        }
        final int indexOfIsdn = nationalNumber.indexOf(";isub=");
        if (indexOfIsdn > 0) {
            nationalNumber.delete(indexOfIsdn, nationalNumber.length());
        }
    }
    
    public MatchType isNumberMatch(final Phonenumber.PhoneNumber firstNumberIn, final Phonenumber.PhoneNumber secondNumberIn) {
        final Phonenumber.PhoneNumber firstNumber = new Phonenumber.PhoneNumber();
        firstNumber.mergeFrom(firstNumberIn);
        final Phonenumber.PhoneNumber secondNumber = new Phonenumber.PhoneNumber();
        secondNumber.mergeFrom(secondNumberIn);
        firstNumber.clearRawInput();
        firstNumber.clearCountryCodeSource();
        firstNumber.clearPreferredDomesticCarrierCode();
        secondNumber.clearRawInput();
        secondNumber.clearCountryCodeSource();
        secondNumber.clearPreferredDomesticCarrierCode();
        if (firstNumber.hasExtension() && firstNumber.getExtension().length() == 0) {
            firstNumber.clearExtension();
        }
        if (secondNumber.hasExtension() && secondNumber.getExtension().length() == 0) {
            secondNumber.clearExtension();
        }
        if (firstNumber.hasExtension() && secondNumber.hasExtension() && !firstNumber.getExtension().equals(secondNumber.getExtension())) {
            return MatchType.NO_MATCH;
        }
        final int firstNumberCountryCode = firstNumber.getCountryCode();
        final int secondNumberCountryCode = secondNumber.getCountryCode();
        if (firstNumberCountryCode != 0 && secondNumberCountryCode != 0) {
            if (firstNumber.exactlySameAs(secondNumber)) {
                return MatchType.EXACT_MATCH;
            }
            if (firstNumberCountryCode == secondNumberCountryCode && this.isNationalNumberSuffixOfTheOther(firstNumber, secondNumber)) {
                return MatchType.SHORT_NSN_MATCH;
            }
            return MatchType.NO_MATCH;
        }
        else {
            firstNumber.setCountryCode(secondNumberCountryCode);
            if (firstNumber.exactlySameAs(secondNumber)) {
                return MatchType.NSN_MATCH;
            }
            if (this.isNationalNumberSuffixOfTheOther(firstNumber, secondNumber)) {
                return MatchType.SHORT_NSN_MATCH;
            }
            return MatchType.NO_MATCH;
        }
    }
    
    private boolean isNationalNumberSuffixOfTheOther(final Phonenumber.PhoneNumber firstNumber, final Phonenumber.PhoneNumber secondNumber) {
        final String firstNumberNationalNumber = String.valueOf(firstNumber.getNationalNumber());
        final String secondNumberNationalNumber = String.valueOf(secondNumber.getNationalNumber());
        return firstNumberNationalNumber.endsWith(secondNumberNationalNumber) || secondNumberNationalNumber.endsWith(firstNumberNationalNumber);
    }
    
    public MatchType isNumberMatch(final String firstNumber, final String secondNumber) {
        try {
            final Phonenumber.PhoneNumber firstNumberAsProto = this.parse(firstNumber, "ZZ");
            return this.isNumberMatch(firstNumberAsProto, secondNumber);
        }
        catch (NumberParseException e) {
            if (e.getErrorType() == NumberParseException.ErrorType.INVALID_COUNTRY_CODE) {
                try {
                    final Phonenumber.PhoneNumber secondNumberAsProto = this.parse(secondNumber, "ZZ");
                    return this.isNumberMatch(secondNumberAsProto, firstNumber);
                }
                catch (NumberParseException e2) {
                    if (e2.getErrorType() == NumberParseException.ErrorType.INVALID_COUNTRY_CODE) {
                        try {
                            final Phonenumber.PhoneNumber firstNumberProto = new Phonenumber.PhoneNumber();
                            final Phonenumber.PhoneNumber secondNumberProto = new Phonenumber.PhoneNumber();
                            this.parseHelper(firstNumber, null, false, false, firstNumberProto);
                            this.parseHelper(secondNumber, null, false, false, secondNumberProto);
                            return this.isNumberMatch(firstNumberProto, secondNumberProto);
                        }
                        catch (NumberParseException ex) {}
                    }
                }
            }
            return MatchType.NOT_A_NUMBER;
        }
    }
    
    public MatchType isNumberMatch(final Phonenumber.PhoneNumber firstNumber, final String secondNumber) {
        try {
            final Phonenumber.PhoneNumber secondNumberAsProto = this.parse(secondNumber, "ZZ");
            return this.isNumberMatch(firstNumber, secondNumberAsProto);
        }
        catch (NumberParseException e) {
            if (e.getErrorType() == NumberParseException.ErrorType.INVALID_COUNTRY_CODE) {
                final String firstNumberRegion = this.getRegionCodeForCountryCode(firstNumber.getCountryCode());
                try {
                    if (firstNumberRegion.equals("ZZ")) {
                        final Phonenumber.PhoneNumber secondNumberProto = new Phonenumber.PhoneNumber();
                        this.parseHelper(secondNumber, null, false, false, secondNumberProto);
                        return this.isNumberMatch(firstNumber, secondNumberProto);
                    }
                    final Phonenumber.PhoneNumber secondNumberWithFirstNumberRegion = this.parse(secondNumber, firstNumberRegion);
                    final MatchType match = this.isNumberMatch(firstNumber, secondNumberWithFirstNumberRegion);
                    if (match == MatchType.EXACT_MATCH) {
                        return MatchType.NSN_MATCH;
                    }
                    return match;
                }
                catch (NumberParseException ex) {}
            }
            return MatchType.NOT_A_NUMBER;
        }
    }
    
    boolean canBeInternationallyDialled(final Phonenumber.PhoneNumber number) {
        final Phonemetadata.PhoneMetadata metadata = this.getMetadataForRegion(this.getRegionCodeForNumber(number));
        if (metadata == null) {
            return true;
        }
        final String nationalSignificantNumber = this.getNationalSignificantNumber(number);
        return !this.isNumberMatchingDesc(nationalSignificantNumber, metadata.getNoInternationalDialling());
    }
    
    public boolean isMobileNumberPortableRegion(final String regionCode) {
        final Phonemetadata.PhoneMetadata metadata = this.getMetadataForRegion(regionCode);
        if (metadata == null) {
            final Logger logger = PhoneNumberUtil.logger;
            final Level warn\u0131ng = Level.WARNING;
            final String s = "Invalid or unknown region code provided: ";
            final String value = String.valueOf(regionCode);
            logger.log(warn\u0131ng, (value.length() != 0) ? s.concat(value) : new String(s));
            return false;
        }
        return metadata.isMobileNumberPortableRegion();
    }
    
    static {
        DEFAULT_METADATA_LOADER = new MetadataLoader() {
            public InputStream loadMetadata(final String metadataFileName) {
                return PhoneNumberUtil.class.getResourceAsStream(metadataFileName);
            }
        };
        logger = Logger.getLogger(PhoneNumberUtil.class.getName());
        final HashMap<Integer, String> mobileTokenMap = new HashMap<Integer, String>();
        mobileTokenMap.put(52, "1");
        mobileTokenMap.put(54, "9");
        MOBILE_TOKEN_MAPPINGS = Collections.unmodifiableMap((Map<? extends Integer, ? extends String>)mobileTokenMap);
        final HashMap<Character, Character> asciiDigitMappings = new HashMap<Character, Character>();
        asciiDigitMappings.put('0', '0');
        asciiDigitMappings.put('1', '1');
        asciiDigitMappings.put('2', '2');
        asciiDigitMappings.put('3', '3');
        asciiDigitMappings.put('4', '4');
        asciiDigitMappings.put('5', '5');
        asciiDigitMappings.put('6', '6');
        asciiDigitMappings.put('7', '7');
        asciiDigitMappings.put('8', '8');
        asciiDigitMappings.put('9', '9');
        final HashMap<Character, Character> alphaMap = new HashMap<Character, Character>(40);
        alphaMap.put('A', '2');
        alphaMap.put('B', '2');
        alphaMap.put('C', '2');
        alphaMap.put('D', '3');
        alphaMap.put('E', '3');
        alphaMap.put('F', '3');
        alphaMap.put('G', '4');
        alphaMap.put('H', '4');
        alphaMap.put('I', '4');
        alphaMap.put('J', '5');
        alphaMap.put('K', '5');
        alphaMap.put('L', '5');
        alphaMap.put('M', '6');
        alphaMap.put('N', '6');
        alphaMap.put('O', '6');
        alphaMap.put('P', '7');
        alphaMap.put('Q', '7');
        alphaMap.put('R', '7');
        alphaMap.put('S', '7');
        alphaMap.put('T', '8');
        alphaMap.put('U', '8');
        alphaMap.put('V', '8');
        alphaMap.put('W', '9');
        alphaMap.put('X', '9');
        alphaMap.put('Y', '9');
        alphaMap.put('Z', '9');
        ALPHA_MAPPINGS = Collections.unmodifiableMap((Map<? extends Character, ? extends Character>)alphaMap);
        final HashMap<Character, Character> combinedMap = new HashMap<Character, Character>(100);
        combinedMap.putAll(PhoneNumberUtil.ALPHA_MAPPINGS);
        combinedMap.putAll(asciiDigitMappings);
        ALPHA_PHONE_MAPPINGS = Collections.unmodifiableMap((Map<? extends Character, ? extends Character>)combinedMap);
        final HashMap<Character, Character> diallableCharMap = new HashMap<Character, Character>();
        diallableCharMap.putAll(asciiDigitMappings);
        diallableCharMap.put('+', '+');
        diallableCharMap.put('*', '*');
        DIALLABLE_CHAR_MAPPINGS = Collections.unmodifiableMap((Map<? extends Character, ? extends Character>)diallableCharMap);
        final HashMap<Character, Character> allPlusNumberGroupings = new HashMap<Character, Character>();
        for (final char c : PhoneNumberUtil.ALPHA_MAPPINGS.keySet()) {
            allPlusNumberGroupings.put(Character.toLowerCase(c), c);
            allPlusNumberGroupings.put(c, c);
        }
        allPlusNumberGroupings.putAll(asciiDigitMappings);
        allPlusNumberGroupings.put('-', '-');
        allPlusNumberGroupings.put('\uff0d', '-');
        allPlusNumberGroupings.put('\u2010', '-');
        allPlusNumberGroupings.put('\u2011', '-');
        allPlusNumberGroupings.put('\u2012', '-');
        allPlusNumberGroupings.put('\u2013', '-');
        allPlusNumberGroupings.put('\u2014', '-');
        allPlusNumberGroupings.put('\u2015', '-');
        allPlusNumberGroupings.put('\u2212', '-');
        allPlusNumberGroupings.put('/', '/');
        allPlusNumberGroupings.put('\uff0f', '/');
        allPlusNumberGroupings.put(' ', ' ');
        allPlusNumberGroupings.put('\u3000', ' ');
        allPlusNumberGroupings.put('\u2060', ' ');
        allPlusNumberGroupings.put('.', '.');
        allPlusNumberGroupings.put('\uff0e', '.');
        ALL_PLUS_NUMBER_GROUPING_SYMBOLS = Collections.unmodifiableMap((Map<? extends Character, ? extends Character>)allPlusNumberGroupings);
        UNIQUE_INTERNATIONAL_PREFIX = Pattern.compile("[\\d]+(?:[~\u2053\u223c\uff5e][\\d]+)?");
        final String value = String.valueOf(Arrays.toString(PhoneNumberUtil.ALPHA_MAPPINGS.keySet().toArray()).replaceAll("[, \\[\\]]", ""));
        final String value2 = String.valueOf(Arrays.toString(PhoneNumberUtil.ALPHA_MAPPINGS.keySet().toArray()).toLowerCase().replaceAll("[, \\[\\]]", ""));
        VALID_ALPHA = ((value2.length() != 0) ? value.concat(value2) : new String(value));
        PLUS_CHARS_PATTERN = Pattern.compile("[+\uff0b]+");
        SEPARATOR_PATTERN = Pattern.compile("[-x\u2010-\u2015\u2212\u30fc\uff0d-\uff0f  \u00ad\u200b\u2060\u3000()\uff08\uff09\uff3b\uff3d.\\[\\]/~\u2053\u223c\uff5e]+");
        CAPTURING_DIGIT_PATTERN = Pattern.compile("(\\p{Nd})");
        VALID_START_CHAR_PATTERN = Pattern.compile("[+\uff0b\\p{Nd}]");
        SECOND_NUMBER_START_PATTERN = Pattern.compile("[\\\\/] *x");
        UNWANTED_END_CHAR_PATTERN = Pattern.compile("[[\\P{N}&&\\P{L}]&&[^#]]+$");
        VALID_ALPHA_PHONE_PATTERN = Pattern.compile("(?:.*?[A-Za-z]){3}.*");
        final String value3 = String.valueOf(String.valueOf("\\p{Nd}{2}|[+\uff0b]*+(?:[-x\u2010-\u2015\u2212\u30fc\uff0d-\uff0f  \u00ad\u200b\u2060\u3000()\uff08\uff09\uff3b\uff3d.\\[\\]/~\u2053\u223c\uff5e*]*\\p{Nd}){3,}[-x\u2010-\u2015\u2212\u30fc\uff0d-\uff0f  \u00ad\u200b\u2060\u3000()\uff08\uff09\uff3b\uff3d.\\[\\]/~\u2053\u223c\uff5e*"));
        final String value4 = String.valueOf(String.valueOf(PhoneNumberUtil.VALID_ALPHA));
        final String value5 = String.valueOf(String.valueOf("\\p{Nd}"));
        VALID_PHONE_NUMBER = new StringBuilder(2 + value3.length() + value4.length() + value5.length()).append(value3).append(value4).append(value5).append("]*").toString();
        final String singleExtnSymbolsForMatching = "x\uff58#\uff03~\uff5e";
        final String s = ",";
        final String value6 = String.valueOf(singleExtnSymbolsForMatching);
        final String singleExtnSymbolsForParsing = (value6.length() != 0) ? s.concat(value6) : new String(s);
        EXTN_PATTERNS_FOR_PARSING = createExtnPattern(singleExtnSymbolsForParsing);
        EXTN_PATTERNS_FOR_MATCHING = createExtnPattern(singleExtnSymbolsForMatching);
        final String value7 = String.valueOf(String.valueOf(PhoneNumberUtil.EXTN_PATTERNS_FOR_PARSING));
        EXTN_PATTERN = Pattern.compile(new StringBuilder(5 + value7.length()).append("(?:").append(value7).append(")$").toString(), 66);
        final String value8 = String.valueOf(String.valueOf(PhoneNumberUtil.VALID_PHONE_NUMBER));
        final String value9 = String.valueOf(String.valueOf(PhoneNumberUtil.EXTN_PATTERNS_FOR_PARSING));
        VALID_PHONE_NUMBER_PATTERN = Pattern.compile(new StringBuilder(5 + value8.length() + value9.length()).append(value8).append("(?:").append(value9).append(")?").toString(), 66);
        NON_DIGITS_PATTERN = Pattern.compile("(\\D+)");
        FIRST_GROUP_PATTERN = Pattern.compile("(\\$\\d)");
        NP_PATTERN = Pattern.compile("\\$NP");
        FG_PATTERN = Pattern.compile("\\$FG");
        CC_PATTERN = Pattern.compile("\\$CC");
        FIRST_GROUP_ONLY_PREFIX_PATTERN = Pattern.compile("\\(?\\$1\\)?");
        PhoneNumberUtil.instance = null;
    }
    
    public enum PhoneNumberFormat
    {
        E164, 
        INTERNATIONAL, 
        NATIONAL, 
        RFC3966;
    }
    
    public enum PhoneNumberType
    {
        FIXED_LINE, 
        MOBILE, 
        FIXED_LINE_OR_MOBILE, 
        TOLL_FREE, 
        PREMIUM_RATE, 
        SHARED_COST, 
        VOIP, 
        PERSONAL_NUMBER, 
        PAGER, 
        UAN, 
        VOICEMAIL, 
        UNKNOWN;
    }
    
    public enum MatchType
    {
        NOT_A_NUMBER, 
        NO_MATCH, 
        SHORT_NSN_MATCH, 
        NSN_MATCH, 
        EXACT_MATCH;
    }
    
    public enum ValidationResult
    {
        IS_POSSIBLE, 
        INVALID_COUNTRY_CODE, 
        TOO_SHORT, 
        TOO_LONG;
    }
    
    public enum Leniency
    {
        POSSIBLE {
            boolean verify(final Phonenumber.PhoneNumber number, final String candidate, final PhoneNumberUtil util) {
                return util.isPossibleNumber(number);
            }
        }, 
        VALID {
            boolean verify(final Phonenumber.PhoneNumber number, final String candidate, final PhoneNumberUtil util) {
                return util.isValidNumber(number) && PhoneNumberMatcher.containsOnlyValidXChars(number, candidate, util) && PhoneNumberMatcher.isNationalPrefixPresentIfRequired(number, util);
            }
        }, 
        STRICT_GROUPING {
            boolean verify(final Phonenumber.PhoneNumber number, final String candidate, final PhoneNumberUtil util) {
                return util.isValidNumber(number) && PhoneNumberMatcher.containsOnlyValidXChars(number, candidate, util) && !PhoneNumberMatcher.containsMoreThanOneSlashInNationalNumber(number, candidate) && PhoneNumberMatcher.isNationalPrefixPresentIfRequired(number, util) && PhoneNumberMatcher.checkNumberGroupingIsValid(number, candidate, util, new PhoneNumberMatcher.NumberGroupingChecker() {
                    public boolean checkGroups(final PhoneNumberUtil util, final Phonenumber.PhoneNumber number, final StringBuilder normalizedCandidate, final String[] expectedNumberGroups) {
                        return PhoneNumberMatcher.allNumberGroupsRemainGrouped(util, number, normalizedCandidate, expectedNumberGroups);
                    }
                });
            }
        }, 
        EXACT_GROUPING {
            boolean verify(final Phonenumber.PhoneNumber number, final String candidate, final PhoneNumberUtil util) {
                return util.isValidNumber(number) && PhoneNumberMatcher.containsOnlyValidXChars(number, candidate, util) && !PhoneNumberMatcher.containsMoreThanOneSlashInNationalNumber(number, candidate) && PhoneNumberMatcher.isNationalPrefixPresentIfRequired(number, util) && PhoneNumberMatcher.checkNumberGroupingIsValid(number, candidate, util, new PhoneNumberMatcher.NumberGroupingChecker() {
                    public boolean checkGroups(final PhoneNumberUtil util, final Phonenumber.PhoneNumber number, final StringBuilder normalizedCandidate, final String[] expectedNumberGroups) {
                        return PhoneNumberMatcher.allNumberGroupsAreExactlyPresent(util, number, normalizedCandidate, expectedNumberGroups);
                    }
                });
            }
        };
        
        abstract boolean verify(final Phonenumber.PhoneNumber p0, final String p1, final PhoneNumberUtil p2);
    }
}
