// 
// Decompiled by Procyon v0.5.30
// 

package com.google.i18n.phonenumbers;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AsYouTypeFormatter
{
    private String currentOutput;
    private StringBuilder formattingTemplate;
    private String currentFormattingPattern;
    private StringBuilder accruedInput;
    private StringBuilder accruedInputWithoutFormatting;
    private boolean ableToFormat;
    private boolean inputHasFormatting;
    private boolean isCompleteNumber;
    private boolean isExpectingCountryCallingCode;
    private final PhoneNumberUtil phoneUtil;
    private String defaultCountry;
    private static final char SEPARATOR_BEFORE_NATIONAL_NUMBER = ' ';
    private static final Phonemetadata.PhoneMetadata EMPTY_METADATA;
    private Phonemetadata.PhoneMetadata defaultMetadata;
    private Phonemetadata.PhoneMetadata currentMetadata;
    private static final Pattern CHARACTER_CLASS_PATTERN;
    private static final Pattern STANDALONE_DIGIT_PATTERN;
    private static final Pattern ELIGIBLE_FORMAT_PATTERN;
    private static final Pattern NATIONAL_PREFIX_SEPARATORS_PATTERN;
    private static final int MIN_LEADING_DIGITS_LENGTH = 3;
    private static final String DIGIT_PLACEHOLDER = "\u2008";
    private static final Pattern DIGIT_PATTERN;
    private int lastMatchPosition;
    private int originalPosition;
    private int positionToRemember;
    private StringBuilder prefixBeforeNationalNumber;
    private boolean shouldAddSpaceAfterNationalPrefix;
    private String extractedNationalPrefix;
    private StringBuilder nationalNumber;
    private List<Phonemetadata.NumberFormat> possibleFormats;
    private RegexCache regexCache;
    
    AsYouTypeFormatter(final String regionCode) {
        this.currentOutput = "";
        this.formattingTemplate = new StringBuilder();
        this.currentFormattingPattern = "";
        this.accruedInput = new StringBuilder();
        this.accruedInputWithoutFormatting = new StringBuilder();
        this.ableToFormat = true;
        this.inputHasFormatting = false;
        this.isCompleteNumber = false;
        this.isExpectingCountryCallingCode = false;
        this.phoneUtil = PhoneNumberUtil.getInstance();
        this.lastMatchPosition = 0;
        this.originalPosition = 0;
        this.positionToRemember = 0;
        this.prefixBeforeNationalNumber = new StringBuilder();
        this.shouldAddSpaceAfterNationalPrefix = false;
        this.extractedNationalPrefix = "";
        this.nationalNumber = new StringBuilder();
        this.possibleFormats = new ArrayList<Phonemetadata.NumberFormat>();
        this.regexCache = new RegexCache(64);
        this.defaultCountry = regionCode;
        this.currentMetadata = this.getMetadataForRegion(this.defaultCountry);
        this.defaultMetadata = this.currentMetadata;
    }
    
    private Phonemetadata.PhoneMetadata getMetadataForRegion(final String regionCode) {
        final int countryCallingCode = this.phoneUtil.getCountryCodeForRegion(regionCode);
        final String mainCountry = this.phoneUtil.getRegionCodeForCountryCode(countryCallingCode);
        final Phonemetadata.PhoneMetadata metadata = this.phoneUtil.getMetadataForRegion(mainCountry);
        if (metadata != null) {
            return metadata;
        }
        return AsYouTypeFormatter.EMPTY_METADATA;
    }
    
    private boolean maybeCreateNewTemplate() {
        final Iterator<Phonemetadata.NumberFormat> it = this.possibleFormats.iterator();
        while (it.hasNext()) {
            final Phonemetadata.NumberFormat numberFormat = it.next();
            final String pattern = numberFormat.getPattern();
            if (this.currentFormattingPattern.equals(pattern)) {
                return false;
            }
            if (this.createFormattingTemplate(numberFormat)) {
                this.currentFormattingPattern = pattern;
                this.shouldAddSpaceAfterNationalPrefix = AsYouTypeFormatter.NATIONAL_PREFIX_SEPARATORS_PATTERN.matcher(numberFormat.getNationalPrefixFormattingRule()).find();
                this.lastMatchPosition = 0;
                return true;
            }
            it.remove();
        }
        return this.ableToFormat = false;
    }
    
    private void getAvailableFormats(final String leadingDigits) {
        final List<Phonemetadata.NumberFormat> formatList = (this.isCompleteNumber && this.currentMetadata.intlNumberFormatSize() > 0) ? this.currentMetadata.intlNumberFormats() : this.currentMetadata.numberFormats();
        final boolean nationalPrefixIsUsedByCountry = this.currentMetadata.hasNationalPrefix();
        for (final Phonemetadata.NumberFormat format : formatList) {
            if ((!nationalPrefixIsUsedByCountry || this.isCompleteNumber || format.isNationalPrefixOptionalWhenFormatting() || PhoneNumberUtil.formattingRuleHasFirstGroupOnly(format.getNationalPrefixFormattingRule())) && this.isFormatEligible(format.getFormat())) {
                this.possibleFormats.add(format);
            }
        }
        this.narrowDownPossibleFormats(leadingDigits);
    }
    
    private boolean isFormatEligible(final String format) {
        return AsYouTypeFormatter.ELIGIBLE_FORMAT_PATTERN.matcher(format).matches();
    }
    
    private void narrowDownPossibleFormats(final String leadingDigits) {
        final int indexOfLeadingDigitsPattern = leadingDigits.length() - 3;
        final Iterator<Phonemetadata.NumberFormat> it = this.possibleFormats.iterator();
        while (it.hasNext()) {
            final Phonemetadata.NumberFormat format = it.next();
            if (format.leadingDigitsPatternSize() == 0) {
                continue;
            }
            final int lastLeadingDigitsPattern = Math.min(indexOfLeadingDigitsPattern, format.leadingDigitsPatternSize() - 1);
            final Pattern leadingDigitsPattern = this.regexCache.getPatternForRegex(format.getLeadingDigitsPattern(lastLeadingDigitsPattern));
            final Matcher m = leadingDigitsPattern.matcher(leadingDigits);
            if (m.lookingAt()) {
                continue;
            }
            it.remove();
        }
    }
    
    private boolean createFormattingTemplate(final Phonemetadata.NumberFormat format) {
        String numberPattern = format.getPattern();
        if (numberPattern.indexOf(124) != -1) {
            return false;
        }
        numberPattern = AsYouTypeFormatter.CHARACTER_CLASS_PATTERN.matcher(numberPattern).replaceAll("\\\\d");
        numberPattern = AsYouTypeFormatter.STANDALONE_DIGIT_PATTERN.matcher(numberPattern).replaceAll("\\\\d");
        this.formattingTemplate.setLength(0);
        final String tempTemplate = this.getFormattingTemplate(numberPattern, format.getFormat());
        if (tempTemplate.length() > 0) {
            this.formattingTemplate.append(tempTemplate);
            return true;
        }
        return false;
    }
    
    private String getFormattingTemplate(final String numberPattern, final String numberFormat) {
        final String longestPhoneNumber = "999999999999999";
        final Matcher m = this.regexCache.getPatternForRegex(numberPattern).matcher(longestPhoneNumber);
        m.find();
        final String aPhoneNumber = m.group();
        if (aPhoneNumber.length() < this.nationalNumber.length()) {
            return "";
        }
        String template = aPhoneNumber.replaceAll(numberPattern, numberFormat);
        template = template.replaceAll("9", "\u2008");
        return template;
    }
    
    public void clear() {
        this.currentOutput = "";
        this.accruedInput.setLength(0);
        this.accruedInputWithoutFormatting.setLength(0);
        this.formattingTemplate.setLength(0);
        this.lastMatchPosition = 0;
        this.currentFormattingPattern = "";
        this.prefixBeforeNationalNumber.setLength(0);
        this.extractedNationalPrefix = "";
        this.nationalNumber.setLength(0);
        this.ableToFormat = true;
        this.inputHasFormatting = false;
        this.positionToRemember = 0;
        this.originalPosition = 0;
        this.isCompleteNumber = false;
        this.isExpectingCountryCallingCode = false;
        this.possibleFormats.clear();
        this.shouldAddSpaceAfterNationalPrefix = false;
        if (!this.currentMetadata.equals(this.defaultMetadata)) {
            this.currentMetadata = this.getMetadataForRegion(this.defaultCountry);
        }
    }
    
    public String inputDigit(final char nextChar) {
        return this.currentOutput = this.inputDigitWithOptionToRememberPosition(nextChar, false);
    }
    
    public String inputDigitAndRememberPosition(final char nextChar) {
        return this.currentOutput = this.inputDigitWithOptionToRememberPosition(nextChar, true);
    }
    
    private String inputDigitWithOptionToRememberPosition(char nextChar, final boolean rememberPosition) {
        this.accruedInput.append(nextChar);
        if (rememberPosition) {
            this.originalPosition = this.accruedInput.length();
        }
        if (!this.isDigitOrLeadingPlusSign(nextChar)) {
            this.ableToFormat = false;
            this.inputHasFormatting = true;
        }
        else {
            nextChar = this.normalizeAndAccrueDigitsAndPlusSign(nextChar, rememberPosition);
        }
        if (!this.ableToFormat) {
            if (this.inputHasFormatting) {
                return this.accruedInput.toString();
            }
            if (this.attemptToExtractIdd()) {
                if (this.attemptToExtractCountryCallingCode()) {
                    return this.attemptToChoosePatternWithPrefixExtracted();
                }
            }
            else if (this.ableToExtractLongerNdd()) {
                this.prefixBeforeNationalNumber.append(' ');
                return this.attemptToChoosePatternWithPrefixExtracted();
            }
            return this.accruedInput.toString();
        }
        else {
            switch (this.accruedInputWithoutFormatting.length()) {
                case 0:
                case 1:
                case 2: {
                    return this.accruedInput.toString();
                }
                case 3: {
                    if (this.attemptToExtractIdd()) {
                        this.isExpectingCountryCallingCode = true;
                        break;
                    }
                    this.extractedNationalPrefix = this.removeNationalPrefixFromNationalNumber();
                    return this.attemptToChooseFormattingPattern();
                }
            }
            if (this.isExpectingCountryCallingCode) {
                if (this.attemptToExtractCountryCallingCode()) {
                    this.isExpectingCountryCallingCode = false;
                }
                final String value = String.valueOf(String.valueOf(this.prefixBeforeNationalNumber));
                final String value2 = String.valueOf(String.valueOf(this.nationalNumber.toString()));
                return new StringBuilder(0 + value.length() + value2.length()).append(value).append(value2).toString();
            }
            if (this.possibleFormats.size() <= 0) {
                return this.attemptToChooseFormattingPattern();
            }
            final String tempNationalNumber = this.inputDigitHelper(nextChar);
            final String formattedNumber = this.attemptToFormatAccruedDigits();
            if (formattedNumber.length() > 0) {
                return formattedNumber;
            }
            this.narrowDownPossibleFormats(this.nationalNumber.toString());
            if (this.maybeCreateNewTemplate()) {
                return this.inputAccruedNationalNumber();
            }
            return this.ableToFormat ? this.appendNationalNumber(tempNationalNumber) : this.accruedInput.toString();
        }
    }
    
    private String attemptToChoosePatternWithPrefixExtracted() {
        this.ableToFormat = true;
        this.isExpectingCountryCallingCode = false;
        this.possibleFormats.clear();
        return this.attemptToChooseFormattingPattern();
    }
    
    String getExtractedNationalPrefix() {
        return this.extractedNationalPrefix;
    }
    
    private boolean ableToExtractLongerNdd() {
        if (this.extractedNationalPrefix.length() > 0) {
            this.nationalNumber.insert(0, this.extractedNationalPrefix);
            final int indexOfPreviousNdd = this.prefixBeforeNationalNumber.lastIndexOf(this.extractedNationalPrefix);
            this.prefixBeforeNationalNumber.setLength(indexOfPreviousNdd);
        }
        return !this.extractedNationalPrefix.equals(this.removeNationalPrefixFromNationalNumber());
    }
    
    private boolean isDigitOrLeadingPlusSign(final char nextChar) {
        return Character.isDigit(nextChar) || (this.accruedInput.length() == 1 && PhoneNumberUtil.PLUS_CHARS_PATTERN.matcher(Character.toString(nextChar)).matches());
    }
    
    String attemptToFormatAccruedDigits() {
        for (final Phonemetadata.NumberFormat numberFormat : this.possibleFormats) {
            final Matcher m = this.regexCache.getPatternForRegex(numberFormat.getPattern()).matcher(this.nationalNumber);
            if (m.matches()) {
                this.shouldAddSpaceAfterNationalPrefix = AsYouTypeFormatter.NATIONAL_PREFIX_SEPARATORS_PATTERN.matcher(numberFormat.getNationalPrefixFormattingRule()).find();
                final String formattedNumber = m.replaceAll(numberFormat.getFormat());
                return this.appendNationalNumber(formattedNumber);
            }
        }
        return "";
    }
    
    public int getRememberedPosition() {
        if (!this.ableToFormat) {
            return this.originalPosition;
        }
        int accruedInputIndex;
        int currentOutputIndex;
        for (accruedInputIndex = 0, currentOutputIndex = 0; accruedInputIndex < this.positionToRemember && currentOutputIndex < this.currentOutput.length(); ++currentOutputIndex) {
            if (this.accruedInputWithoutFormatting.charAt(accruedInputIndex) == this.currentOutput.charAt(currentOutputIndex)) {
                ++accruedInputIndex;
            }
        }
        return currentOutputIndex;
    }
    
    private String appendNationalNumber(final String nationalNumber) {
        final int prefixBeforeNationalNumberLength = this.prefixBeforeNationalNumber.length();
        if (this.shouldAddSpaceAfterNationalPrefix && prefixBeforeNationalNumberLength > 0 && this.prefixBeforeNationalNumber.charAt(prefixBeforeNationalNumberLength - 1) != ' ') {
            final String value = String.valueOf(String.valueOf(new String(this.prefixBeforeNationalNumber)));
            final char c = ' ';
            final String value2 = String.valueOf(String.valueOf(nationalNumber));
            return new StringBuilder(1 + value.length() + value2.length()).append(value).append(c).append(value2).toString();
        }
        final String value3 = String.valueOf(String.valueOf(this.prefixBeforeNationalNumber));
        final String value4 = String.valueOf(String.valueOf(nationalNumber));
        return new StringBuilder(0 + value3.length() + value4.length()).append(value3).append(value4).toString();
    }
    
    private String attemptToChooseFormattingPattern() {
        if (this.nationalNumber.length() < 3) {
            return this.appendNationalNumber(this.nationalNumber.toString());
        }
        this.getAvailableFormats(this.nationalNumber.toString());
        final String formattedNumber = this.attemptToFormatAccruedDigits();
        if (formattedNumber.length() > 0) {
            return formattedNumber;
        }
        return this.maybeCreateNewTemplate() ? this.inputAccruedNationalNumber() : this.accruedInput.toString();
    }
    
    private String inputAccruedNationalNumber() {
        final int lengthOfNationalNumber = this.nationalNumber.length();
        if (lengthOfNationalNumber > 0) {
            String tempNationalNumber = "";
            for (int i = 0; i < lengthOfNationalNumber; ++i) {
                tempNationalNumber = this.inputDigitHelper(this.nationalNumber.charAt(i));
            }
            return this.ableToFormat ? this.appendNationalNumber(tempNationalNumber) : this.accruedInput.toString();
        }
        return this.prefixBeforeNationalNumber.toString();
    }
    
    private boolean isNanpaNumberWithNationalPrefix() {
        return this.currentMetadata.getCountryCode() == 1 && this.nationalNumber.charAt(0) == '1' && this.nationalNumber.charAt(1) != '0' && this.nationalNumber.charAt(1) != '1';
    }
    
    private String removeNationalPrefixFromNationalNumber() {
        int startOfNationalNumber = 0;
        if (this.isNanpaNumberWithNationalPrefix()) {
            startOfNationalNumber = 1;
            this.prefixBeforeNationalNumber.append('1').append(' ');
            this.isCompleteNumber = true;
        }
        else if (this.currentMetadata.hasNationalPrefixForParsing()) {
            final Pattern nationalPrefixForParsing = this.regexCache.getPatternForRegex(this.currentMetadata.getNationalPrefixForParsing());
            final Matcher m = nationalPrefixForParsing.matcher(this.nationalNumber);
            if (m.lookingAt() && m.end() > 0) {
                this.isCompleteNumber = true;
                startOfNationalNumber = m.end();
                this.prefixBeforeNationalNumber.append(this.nationalNumber.substring(0, startOfNationalNumber));
            }
        }
        final String nationalPrefix = this.nationalNumber.substring(0, startOfNationalNumber);
        this.nationalNumber.delete(0, startOfNationalNumber);
        return nationalPrefix;
    }
    
    private boolean attemptToExtractIdd() {
        final RegexCache regexCache = this.regexCache;
        final String value = String.valueOf("\\+|");
        final String value2 = String.valueOf(this.currentMetadata.getInternationalPrefix());
        final Pattern internationalPrefix = regexCache.getPatternForRegex((value2.length() != 0) ? value.concat(value2) : new String(value));
        final Matcher iddMatcher = internationalPrefix.matcher(this.accruedInputWithoutFormatting);
        if (iddMatcher.lookingAt()) {
            this.isCompleteNumber = true;
            final int startOfCountryCallingCode = iddMatcher.end();
            this.nationalNumber.setLength(0);
            this.nationalNumber.append(this.accruedInputWithoutFormatting.substring(startOfCountryCallingCode));
            this.prefixBeforeNationalNumber.setLength(0);
            this.prefixBeforeNationalNumber.append(this.accruedInputWithoutFormatting.substring(0, startOfCountryCallingCode));
            if (this.accruedInputWithoutFormatting.charAt(0) != '+') {
                this.prefixBeforeNationalNumber.append(' ');
            }
            return true;
        }
        return false;
    }
    
    private boolean attemptToExtractCountryCallingCode() {
        if (this.nationalNumber.length() == 0) {
            return false;
        }
        final StringBuilder numberWithoutCountryCallingCode = new StringBuilder();
        final int countryCode = this.phoneUtil.extractCountryCode(this.nationalNumber, numberWithoutCountryCallingCode);
        if (countryCode == 0) {
            return false;
        }
        this.nationalNumber.setLength(0);
        this.nationalNumber.append((CharSequence)numberWithoutCountryCallingCode);
        final String newRegionCode = this.phoneUtil.getRegionCodeForCountryCode(countryCode);
        if ("001".equals(newRegionCode)) {
            this.currentMetadata = this.phoneUtil.getMetadataForNonGeographicalRegion(countryCode);
        }
        else if (!newRegionCode.equals(this.defaultCountry)) {
            this.currentMetadata = this.getMetadataForRegion(newRegionCode);
        }
        final String countryCodeString = Integer.toString(countryCode);
        this.prefixBeforeNationalNumber.append(countryCodeString).append(' ');
        this.extractedNationalPrefix = "";
        return true;
    }
    
    private char normalizeAndAccrueDigitsAndPlusSign(final char nextChar, final boolean rememberPosition) {
        char normalizedChar;
        if (nextChar == '+') {
            normalizedChar = nextChar;
            this.accruedInputWithoutFormatting.append(nextChar);
        }
        else {
            final int radix = 10;
            normalizedChar = Character.forDigit(Character.digit(nextChar, radix), radix);
            this.accruedInputWithoutFormatting.append(normalizedChar);
            this.nationalNumber.append(normalizedChar);
        }
        if (rememberPosition) {
            this.positionToRemember = this.accruedInputWithoutFormatting.length();
        }
        return normalizedChar;
    }
    
    private String inputDigitHelper(final char nextChar) {
        final Matcher digitMatcher = AsYouTypeFormatter.DIGIT_PATTERN.matcher(this.formattingTemplate);
        if (digitMatcher.find(this.lastMatchPosition)) {
            final String tempTemplate = digitMatcher.replaceFirst(Character.toString(nextChar));
            this.formattingTemplate.replace(0, tempTemplate.length(), tempTemplate);
            this.lastMatchPosition = digitMatcher.start();
            return this.formattingTemplate.substring(0, this.lastMatchPosition + 1);
        }
        if (this.possibleFormats.size() == 1) {
            this.ableToFormat = false;
        }
        this.currentFormattingPattern = "";
        return this.accruedInput.toString();
    }
    
    static {
        EMPTY_METADATA = new Phonemetadata.PhoneMetadata().setInternationalPrefix("NA");
        CHARACTER_CLASS_PATTERN = Pattern.compile("\\[([^\\[\\]])*\\]");
        STANDALONE_DIGIT_PATTERN = Pattern.compile("\\d(?=[^,}][^,}])");
        ELIGIBLE_FORMAT_PATTERN = Pattern.compile("[-x\u2010-\u2015\u2212\u30fc\uff0d-\uff0f  \u00ad\u200b\u2060\u3000()\uff08\uff09\uff3b\uff3d.\\[\\]/~\u2053\u223c\uff5e]*(\\$\\d[-x\u2010-\u2015\u2212\u30fc\uff0d-\uff0f  \u00ad\u200b\u2060\u3000()\uff08\uff09\uff3b\uff3d.\\[\\]/~\u2053\u223c\uff5e]*)+");
        NATIONAL_PREFIX_SEPARATORS_PATTERN = Pattern.compile("[- ]");
        DIGIT_PATTERN = Pattern.compile("\u2008");
    }
}
