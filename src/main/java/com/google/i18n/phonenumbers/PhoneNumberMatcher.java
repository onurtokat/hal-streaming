// 
// Decompiled by Procyon v0.5.30
// 

package com.google.i18n.phonenumbers;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Iterator;

final class PhoneNumberMatcher implements Iterator<PhoneNumberMatch>
{
    private static final Pattern PATTERN;
    private static final Pattern PUB_PAGES;
    private static final Pattern SLASH_SEPARATED_DATES;
    private static final Pattern TIME_STAMPS;
    private static final Pattern TIME_STAMPS_SUFFIX;
    private static final Pattern MATCHING_BRACKETS;
    private static final Pattern[] INNER_MATCHES;
    private static final Pattern LEAD_CLASS;
    private final PhoneNumberUtil phoneUtil;
    private final CharSequence text;
    private final String preferredRegion;
    private final PhoneNumberUtil.Leniency leniency;
    private long maxTries;
    private State state;
    private PhoneNumberMatch lastMatch;
    private int searchIndex;
    
    private static String limit(final int lower, final int upper) {
        if (lower < 0 || upper <= 0 || upper < lower) {
            throw new IllegalArgumentException();
        }
        return new StringBuilder(25).append("{").append(lower).append(",").append(upper).append("}").toString();
    }
    
    PhoneNumberMatcher(final PhoneNumberUtil util, final CharSequence text, final String country, final PhoneNumberUtil.Leniency leniency, final long maxTries) {
        this.state = State.NOT_READY;
        this.lastMatch = null;
        this.searchIndex = 0;
        if (util == null || leniency == null) {
            throw new NullPointerException();
        }
        if (maxTries < 0L) {
            throw new IllegalArgumentException();
        }
        this.phoneUtil = util;
        this.text = ((text != null) ? text : "");
        this.preferredRegion = country;
        this.leniency = leniency;
        this.maxTries = maxTries;
    }
    
    private PhoneNumberMatch find(int index) {
        int start;
        CharSequence candidate;
        for (Matcher matcher = PhoneNumberMatcher.PATTERN.matcher(this.text); this.maxTries > 0L && matcher.find(index); index = start + candidate.length(), --this.maxTries) {
            start = matcher.start();
            candidate = this.text.subSequence(start, matcher.end());
            candidate = trimAfterFirstMatch(PhoneNumberUtil.SECOND_NUMBER_START_PATTERN, candidate);
            final PhoneNumberMatch match = this.extractMatch(candidate, start);
            if (match != null) {
                return match;
            }
        }
        return null;
    }
    
    private static CharSequence trimAfterFirstMatch(final Pattern pattern, CharSequence candidate) {
        final Matcher trailingCharsMatcher = pattern.matcher(candidate);
        if (trailingCharsMatcher.find()) {
            candidate = candidate.subSequence(0, trailingCharsMatcher.start());
        }
        return candidate;
    }
    
    static boolean isLatinLetter(final char letter) {
        if (!Character.isLetter(letter) && Character.getType(letter) != 6) {
            return false;
        }
        final Character.UnicodeBlock block = Character.UnicodeBlock.of(letter);
        return block.equals(Character.UnicodeBlock.BASIC_LATIN) || block.equals(Character.UnicodeBlock.LATIN_1_SUPPLEMENT) || block.equals(Character.UnicodeBlock.LATIN_EXTENDED_A) || block.equals(Character.UnicodeBlock.LATIN_EXTENDED_ADDITIONAL) || block.equals(Character.UnicodeBlock.LATIN_EXTENDED_B) || block.equals(Character.UnicodeBlock.COMBINING_DIACRITICAL_MARKS);
    }
    
    private static boolean isInvalidPunctuationSymbol(final char character) {
        return character == '%' || Character.getType(character) == 26;
    }
    
    private PhoneNumberMatch extractMatch(final CharSequence candidate, final int offset) {
        if (PhoneNumberMatcher.SLASH_SEPARATED_DATES.matcher(candidate).find()) {
            return null;
        }
        if (PhoneNumberMatcher.TIME_STAMPS.matcher(candidate).find()) {
            final String followingText = this.text.toString().substring(offset + candidate.length());
            if (PhoneNumberMatcher.TIME_STAMPS_SUFFIX.matcher(followingText).lookingAt()) {
                return null;
            }
        }
        final String rawString = candidate.toString();
        final PhoneNumberMatch match = this.parseAndVerify(rawString, offset);
        if (match != null) {
            return match;
        }
        return this.extractInnerMatch(rawString, offset);
    }
    
    private PhoneNumberMatch extractInnerMatch(final String candidate, final int offset) {
        for (final Pattern possibleInnerMatch : PhoneNumberMatcher.INNER_MATCHES) {
            final int rangeStart = 0;
            final Matcher groupMatcher = possibleInnerMatch.matcher(candidate);
            boolean isFirstMatch = true;
            while (groupMatcher.find() && this.maxTries > 0L) {
                if (isFirstMatch) {
                    final CharSequence group = trimAfterFirstMatch(PhoneNumberUtil.UNWANTED_END_CHAR_PATTERN, candidate.substring(0, groupMatcher.start()));
                    final PhoneNumberMatch match = this.parseAndVerify(group.toString(), offset);
                    if (match != null) {
                        return match;
                    }
                    --this.maxTries;
                    isFirstMatch = false;
                }
                final CharSequence group = trimAfterFirstMatch(PhoneNumberUtil.UNWANTED_END_CHAR_PATTERN, groupMatcher.group(1));
                final PhoneNumberMatch match = this.parseAndVerify(group.toString(), offset + groupMatcher.start(1));
                if (match != null) {
                    return match;
                }
                --this.maxTries;
            }
        }
        return null;
    }
    
    private PhoneNumberMatch parseAndVerify(final String candidate, final int offset) {
        try {
            if (!PhoneNumberMatcher.MATCHING_BRACKETS.matcher(candidate).matches() || PhoneNumberMatcher.PUB_PAGES.matcher(candidate).find()) {
                return null;
            }
            if (this.leniency.compareTo(PhoneNumberUtil.Leniency.VALID) >= 0) {
                if (offset > 0 && !PhoneNumberMatcher.LEAD_CLASS.matcher(candidate).lookingAt()) {
                    final char previousChar = this.text.charAt(offset - 1);
                    if (isInvalidPunctuationSymbol(previousChar) || isLatinLetter(previousChar)) {
                        return null;
                    }
                }
                final int lastCharIndex = offset + candidate.length();
                if (lastCharIndex < this.text.length()) {
                    final char nextChar = this.text.charAt(lastCharIndex);
                    if (isInvalidPunctuationSymbol(nextChar) || isLatinLetter(nextChar)) {
                        return null;
                    }
                }
            }
            final Phonenumber.PhoneNumber number = this.phoneUtil.parseAndKeepRawInput(candidate, this.preferredRegion);
            if (this.phoneUtil.getRegionCodeForCountryCode(number.getCountryCode()).equals("IL") && this.phoneUtil.getNationalSignificantNumber(number).length() == 4 && (offset == 0 || (offset > 0 && this.text.charAt(offset - 1) != '*'))) {
                return null;
            }
            if (this.leniency.verify(number, candidate, this.phoneUtil)) {
                number.clearCountryCodeSource();
                number.clearRawInput();
                number.clearPreferredDomesticCarrierCode();
                return new PhoneNumberMatch(offset, candidate, number);
            }
        }
        catch (NumberParseException ex) {}
        return null;
    }
    
    static boolean allNumberGroupsRemainGrouped(final PhoneNumberUtil util, final Phonenumber.PhoneNumber number, final StringBuilder normalizedCandidate, final String[] formattedNumberGroups) {
        int fromIndex = 0;
        if (number.getCountryCodeSource() != Phonenumber.PhoneNumber.CountryCodeSource.FROM_DEFAULT_COUNTRY) {
            final String countryCode = Integer.toString(number.getCountryCode());
            fromIndex = normalizedCandidate.indexOf(countryCode) + countryCode.length();
        }
        for (int i = 0; i < formattedNumberGroups.length; ++i) {
            fromIndex = normalizedCandidate.indexOf(formattedNumberGroups[i], fromIndex);
            if (fromIndex < 0) {
                return false;
            }
            fromIndex += formattedNumberGroups[i].length();
            if (i == 0 && fromIndex < normalizedCandidate.length()) {
                final String region = util.getRegionCodeForCountryCode(number.getCountryCode());
                if (util.getNddPrefixForRegion(region, true) != null && Character.isDigit(normalizedCandidate.charAt(fromIndex))) {
                    final String nationalSignificantNumber = util.getNationalSignificantNumber(number);
                    return normalizedCandidate.substring(fromIndex - formattedNumberGroups[i].length()).startsWith(nationalSignificantNumber);
                }
            }
        }
        return normalizedCandidate.substring(fromIndex).contains(number.getExtension());
    }
    
    static boolean allNumberGroupsAreExactlyPresent(final PhoneNumberUtil util, final Phonenumber.PhoneNumber number, final StringBuilder normalizedCandidate, final String[] formattedNumberGroups) {
        final String[] candidateGroups = PhoneNumberUtil.NON_DIGITS_PATTERN.split(normalizedCandidate.toString());
        int candidateNumberGroupIndex = number.hasExtension() ? (candidateGroups.length - 2) : (candidateGroups.length - 1);
        if (candidateGroups.length == 1 || candidateGroups[candidateNumberGroupIndex].contains(util.getNationalSignificantNumber(number))) {
            return true;
        }
        for (int formattedNumberGroupIndex = formattedNumberGroups.length - 1; formattedNumberGroupIndex > 0 && candidateNumberGroupIndex >= 0; --formattedNumberGroupIndex, --candidateNumberGroupIndex) {
            if (!candidateGroups[candidateNumberGroupIndex].equals(formattedNumberGroups[formattedNumberGroupIndex])) {
                return false;
            }
        }
        return candidateNumberGroupIndex >= 0 && candidateGroups[candidateNumberGroupIndex].endsWith(formattedNumberGroups[0]);
    }
    
    private static String[] getNationalNumberGroups(final PhoneNumberUtil util, final Phonenumber.PhoneNumber number, final Phonemetadata.NumberFormat formattingPattern) {
        if (formattingPattern == null) {
            final String rfc3966Format = util.format(number, PhoneNumberUtil.PhoneNumberFormat.RFC3966);
            int endIndex = rfc3966Format.indexOf(59);
            if (endIndex < 0) {
                endIndex = rfc3966Format.length();
            }
            final int startIndex = rfc3966Format.indexOf(45) + 1;
            return rfc3966Format.substring(startIndex, endIndex).split("-");
        }
        final String nationalSignificantNumber = util.getNationalSignificantNumber(number);
        return util.formatNsnUsingPattern(nationalSignificantNumber, formattingPattern, PhoneNumberUtil.PhoneNumberFormat.RFC3966).split("-");
    }
    
    static boolean checkNumberGroupingIsValid(final Phonenumber.PhoneNumber number, final String candidate, final PhoneNumberUtil util, final NumberGroupingChecker checker) {
        final StringBuilder normalizedCandidate = PhoneNumberUtil.normalizeDigits(candidate, true);
        String[] formattedNumberGroups = getNationalNumberGroups(util, number, null);
        if (checker.checkGroups(util, number, normalizedCandidate, formattedNumberGroups)) {
            return true;
        }
        final Phonemetadata.PhoneMetadata alternateFormats = MetadataManager.getAlternateFormatsForCountry(number.getCountryCode());
        if (alternateFormats != null) {
            for (final Phonemetadata.NumberFormat alternateFormat : alternateFormats.numberFormats()) {
                formattedNumberGroups = getNationalNumberGroups(util, number, alternateFormat);
                if (checker.checkGroups(util, number, normalizedCandidate, formattedNumberGroups)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    static boolean containsMoreThanOneSlashInNationalNumber(final Phonenumber.PhoneNumber number, final String candidate) {
        final int firstSlashInBodyIndex = candidate.indexOf(47);
        if (firstSlashInBodyIndex < 0) {
            return false;
        }
        final int secondSlashInBodyIndex = candidate.indexOf(47, firstSlashInBodyIndex + 1);
        if (secondSlashInBodyIndex < 0) {
            return false;
        }
        final boolean candidateHasCountryCode = number.getCountryCodeSource() == Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN || number.getCountryCodeSource() == Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITHOUT_PLUS_SIGN;
        return !candidateHasCountryCode || !PhoneNumberUtil.normalizeDigitsOnly(candidate.substring(0, firstSlashInBodyIndex)).equals(Integer.toString(number.getCountryCode())) || candidate.substring(secondSlashInBodyIndex + 1).contains("/");
    }
    
    static boolean containsOnlyValidXChars(final Phonenumber.PhoneNumber number, final String candidate, final PhoneNumberUtil util) {
        for (int index = 0; index < candidate.length() - 1; ++index) {
            final char charAtIndex = candidate.charAt(index);
            if (charAtIndex == 'x' || charAtIndex == 'X') {
                final char charAtNextIndex = candidate.charAt(index + 1);
                if (charAtNextIndex == 'x' || charAtNextIndex == 'X') {
                    ++index;
                    if (util.isNumberMatch(number, candidate.substring(index)) != PhoneNumberUtil.MatchType.NSN_MATCH) {
                        return false;
                    }
                }
                else if (!PhoneNumberUtil.normalizeDigitsOnly(candidate.substring(index)).equals(number.getExtension())) {
                    return false;
                }
            }
        }
        return true;
    }
    
    static boolean isNationalPrefixPresentIfRequired(final Phonenumber.PhoneNumber number, final PhoneNumberUtil util) {
        if (number.getCountryCodeSource() != Phonenumber.PhoneNumber.CountryCodeSource.FROM_DEFAULT_COUNTRY) {
            return true;
        }
        final String phoneNumberRegion = util.getRegionCodeForCountryCode(number.getCountryCode());
        final Phonemetadata.PhoneMetadata metadata = util.getMetadataForRegion(phoneNumberRegion);
        if (metadata == null) {
            return true;
        }
        final String nationalNumber = util.getNationalSignificantNumber(number);
        final Phonemetadata.NumberFormat formatRule = util.chooseFormattingPatternForNumber(metadata.numberFormats(), nationalNumber);
        if (formatRule == null || formatRule.getNationalPrefixFormattingRule().length() <= 0) {
            return true;
        }
        if (formatRule.isNationalPrefixOptionalWhenFormatting()) {
            return true;
        }
        if (PhoneNumberUtil.formattingRuleHasFirstGroupOnly(formatRule.getNationalPrefixFormattingRule())) {
            return true;
        }
        final String rawInputCopy = PhoneNumberUtil.normalizeDigitsOnly(number.getRawInput());
        final StringBuilder rawInput = new StringBuilder(rawInputCopy);
        return util.maybeStripNationalPrefixAndCarrierCode(rawInput, metadata, null);
    }
    
    public boolean hasNext() {
        if (this.state == State.NOT_READY) {
            this.lastMatch = this.find(this.searchIndex);
            if (this.lastMatch == null) {
                this.state = State.DONE;
            }
            else {
                this.searchIndex = this.lastMatch.end();
                this.state = State.READY;
            }
        }
        return this.state == State.READY;
    }
    
    public PhoneNumberMatch next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        final PhoneNumberMatch result = this.lastMatch;
        this.lastMatch = null;
        this.state = State.NOT_READY;
        return result;
    }
    
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
    static {
        PUB_PAGES = Pattern.compile("\\d{1,5}-+\\d{1,5}\\s{0,4}\\(\\d{1,4}");
        SLASH_SEPARATED_DATES = Pattern.compile("(?:(?:[0-3]?\\d/[01]?\\d)|(?:[01]?\\d/[0-3]?\\d))/(?:[12]\\d)?\\d{2}");
        TIME_STAMPS = Pattern.compile("[12]\\d{3}[-/]?[01]\\d[-/]?[0-3]\\d +[0-2]\\d$");
        TIME_STAMPS_SUFFIX = Pattern.compile(":[0-5]\\d");
        INNER_MATCHES = new Pattern[] { Pattern.compile("/+(.*)"), Pattern.compile("(\\([^(]*)"), Pattern.compile("(?:\\p{Z}-|-\\p{Z})\\p{Z}*(.+)"), Pattern.compile("[\u2012-\u2015\uff0d]\\p{Z}*(.+)"), Pattern.compile("\\.+\\p{Z}*([^.]+)"), Pattern.compile("\\p{Z}+(\\P{Z}+)") };
        final String openingParens = "(\\[\uff08\uff3b";
        final String closingParens = ")\\]\uff09\uff3d";
        final String value = String.valueOf(String.valueOf(openingParens));
        final String value2 = String.valueOf(String.valueOf(closingParens));
        final String nonParens = new StringBuilder(3 + value.length() + value2.length()).append("[^").append(value).append(value2).append("]").toString();
        final String bracketPairLimit = limit(0, 3);
        final String value3 = String.valueOf(String.valueOf(openingParens));
        final String value4 = String.valueOf(String.valueOf(nonParens));
        final String value5 = String.valueOf(String.valueOf(closingParens));
        final String value6 = String.valueOf(String.valueOf(nonParens));
        final String value7 = String.valueOf(String.valueOf(openingParens));
        final String value8 = String.valueOf(String.valueOf(nonParens));
        final String value9 = String.valueOf(String.valueOf(closingParens));
        final String value10 = String.valueOf(String.valueOf(bracketPairLimit));
        final String value11 = String.valueOf(String.valueOf(nonParens));
        MATCHING_BRACKETS = Pattern.compile(new StringBuilder(26 + value3.length() + value4.length() + value5.length() + value6.length() + value7.length() + value8.length() + value9.length() + value10.length() + value11.length()).append("(?:[").append(value3).append("])?").append("(?:").append(value4).append("+").append("[").append(value5).append("])?").append(value6).append("+").append("(?:[").append(value7).append("]").append(value8).append("+[").append(value9).append("])").append(value10).append(value11).append("*").toString());
        final String leadLimit = limit(0, 2);
        final String punctuationLimit = limit(0, 4);
        final int digitBlockLimit = 20;
        final String blockLimit = limit(0, digitBlockLimit);
        final String value12 = String.valueOf("[-x\u2010-\u2015\u2212\u30fc\uff0d-\uff0f  \u00ad\u200b\u2060\u3000()\uff08\uff09\uff3b\uff3d.\\[\\]/~\u2053\u223c\uff5e]");
        final String value13 = String.valueOf(punctuationLimit);
        final String punctuation = (value13.length() != 0) ? value12.concat(value13) : new String(value12);
        final String s = "\\p{Nd}";
        final String value14 = String.valueOf(limit(1, digitBlockLimit));
        final String digitSequence = (value14.length() != 0) ? s.concat(value14) : new String(s);
        final String value15 = String.valueOf(openingParens);
        final String value16 = String.valueOf("+\uff0b");
        final String leadClassChars = (value16.length() != 0) ? value15.concat(value16) : new String(value15);
        final String value17 = String.valueOf(String.valueOf(leadClassChars));
        final String leadClass = new StringBuilder(2 + value17.length()).append("[").append(value17).append("]").toString();
        LEAD_CLASS = Pattern.compile(leadClass);
        final String value18 = String.valueOf(String.valueOf(leadClass));
        final String value19 = String.valueOf(String.valueOf(punctuation));
        final String value20 = String.valueOf(String.valueOf(leadLimit));
        final String value21 = String.valueOf(String.valueOf(digitSequence));
        final String value22 = String.valueOf(String.valueOf(punctuation));
        final String value23 = String.valueOf(String.valueOf(digitSequence));
        final String value24 = String.valueOf(String.valueOf(blockLimit));
        final String value25 = String.valueOf(String.valueOf(PhoneNumberUtil.EXTN_PATTERNS_FOR_MATCHING));
        PATTERN = Pattern.compile(new StringBuilder(13 + value18.length() + value19.length() + value20.length() + value21.length() + value22.length() + value23.length() + value24.length() + value25.length()).append("(?:").append(value18).append(value19).append(")").append(value20).append(value21).append("(?:").append(value22).append(value23).append(")").append(value24).append("(?:").append(value25).append(")?").toString(), 66);
    }
    
    private enum State
    {
        NOT_READY, 
        READY, 
        DONE;
    }
    
    interface NumberGroupingChecker
    {
        boolean checkGroups(final PhoneNumberUtil p0, final Phonenumber.PhoneNumber p1, final StringBuilder p2, final String[] p3);
    }
}
