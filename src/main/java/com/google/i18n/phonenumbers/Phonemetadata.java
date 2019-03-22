// 
// Decompiled by Procyon v0.5.30
// 

package com.google.i18n.phonenumbers;

import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import java.io.Externalizable;

public final class Phonemetadata
{
    public static class NumberFormat implements Externalizable
    {
        private static final long serialVersionUID = 1L;
        private boolean hasPattern;
        private String pattern_;
        private boolean hasFormat;
        private String format_;
        private List<String> leadingDigitsPattern_;
        private boolean hasNationalPrefixFormattingRule;
        private String nationalPrefixFormattingRule_;
        private boolean hasNationalPrefixOptionalWhenFormatting;
        private boolean nationalPrefixOptionalWhenFormatting_;
        private boolean hasDomesticCarrierCodeFormattingRule;
        private String domesticCarrierCodeFormattingRule_;
        
        public NumberFormat() {
            this.pattern_ = "";
            this.format_ = "";
            this.leadingDigitsPattern_ = new ArrayList<String>();
            this.nationalPrefixFormattingRule_ = "";
            this.nationalPrefixOptionalWhenFormatting_ = false;
            this.domesticCarrierCodeFormattingRule_ = "";
        }
        
        public static Builder newBuilder() {
            return new Builder();
        }
        
        public boolean hasPattern() {
            return this.hasPattern;
        }
        
        public String getPattern() {
            return this.pattern_;
        }
        
        public NumberFormat setPattern(final String value) {
            this.hasPattern = true;
            this.pattern_ = value;
            return this;
        }
        
        public boolean hasFormat() {
            return this.hasFormat;
        }
        
        public String getFormat() {
            return this.format_;
        }
        
        public NumberFormat setFormat(final String value) {
            this.hasFormat = true;
            this.format_ = value;
            return this;
        }
        
        public List<String> leadingDigitPatterns() {
            return this.leadingDigitsPattern_;
        }
        
        public int leadingDigitsPatternSize() {
            return this.leadingDigitsPattern_.size();
        }
        
        public String getLeadingDigitsPattern(final int index) {
            return this.leadingDigitsPattern_.get(index);
        }
        
        public NumberFormat addLeadingDigitsPattern(final String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.leadingDigitsPattern_.add(value);
            return this;
        }
        
        public boolean hasNationalPrefixFormattingRule() {
            return this.hasNationalPrefixFormattingRule;
        }
        
        public String getNationalPrefixFormattingRule() {
            return this.nationalPrefixFormattingRule_;
        }
        
        public NumberFormat setNationalPrefixFormattingRule(final String value) {
            this.hasNationalPrefixFormattingRule = true;
            this.nationalPrefixFormattingRule_ = value;
            return this;
        }
        
        public NumberFormat clearNationalPrefixFormattingRule() {
            this.hasNationalPrefixFormattingRule = false;
            this.nationalPrefixFormattingRule_ = "";
            return this;
        }
        
        public boolean hasNationalPrefixOptionalWhenFormatting() {
            return this.hasNationalPrefixOptionalWhenFormatting;
        }
        
        public boolean isNationalPrefixOptionalWhenFormatting() {
            return this.nationalPrefixOptionalWhenFormatting_;
        }
        
        public NumberFormat setNationalPrefixOptionalWhenFormatting(final boolean value) {
            this.hasNationalPrefixOptionalWhenFormatting = true;
            this.nationalPrefixOptionalWhenFormatting_ = value;
            return this;
        }
        
        public boolean hasDomesticCarrierCodeFormattingRule() {
            return this.hasDomesticCarrierCodeFormattingRule;
        }
        
        public String getDomesticCarrierCodeFormattingRule() {
            return this.domesticCarrierCodeFormattingRule_;
        }
        
        public NumberFormat setDomesticCarrierCodeFormattingRule(final String value) {
            this.hasDomesticCarrierCodeFormattingRule = true;
            this.domesticCarrierCodeFormattingRule_ = value;
            return this;
        }
        
        public NumberFormat mergeFrom(final NumberFormat other) {
            if (other.hasPattern()) {
                this.setPattern(other.getPattern());
            }
            if (other.hasFormat()) {
                this.setFormat(other.getFormat());
            }
            for (int leadingDigitsPatternSize = other.leadingDigitsPatternSize(), i = 0; i < leadingDigitsPatternSize; ++i) {
                this.addLeadingDigitsPattern(other.getLeadingDigitsPattern(i));
            }
            if (other.hasNationalPrefixFormattingRule()) {
                this.setNationalPrefixFormattingRule(other.getNationalPrefixFormattingRule());
            }
            if (other.hasDomesticCarrierCodeFormattingRule()) {
                this.setDomesticCarrierCodeFormattingRule(other.getDomesticCarrierCodeFormattingRule());
            }
            this.setNationalPrefixOptionalWhenFormatting(other.isNationalPrefixOptionalWhenFormatting());
            return this;
        }
        
        public void writeExternal(final ObjectOutput objectOutput) throws IOException {
            objectOutput.writeUTF(this.pattern_);
            objectOutput.writeUTF(this.format_);
            final int leadingDigitsPatternSize = this.leadingDigitsPatternSize();
            objectOutput.writeInt(leadingDigitsPatternSize);
            for (int i = 0; i < leadingDigitsPatternSize; ++i) {
                objectOutput.writeUTF(this.leadingDigitsPattern_.get(i));
            }
            objectOutput.writeBoolean(this.hasNationalPrefixFormattingRule);
            if (this.hasNationalPrefixFormattingRule) {
                objectOutput.writeUTF(this.nationalPrefixFormattingRule_);
            }
            objectOutput.writeBoolean(this.hasDomesticCarrierCodeFormattingRule);
            if (this.hasDomesticCarrierCodeFormattingRule) {
                objectOutput.writeUTF(this.domesticCarrierCodeFormattingRule_);
            }
            objectOutput.writeBoolean(this.nationalPrefixOptionalWhenFormatting_);
        }
        
        public void readExternal(final ObjectInput objectInput) throws IOException {
            this.setPattern(objectInput.readUTF());
            this.setFormat(objectInput.readUTF());
            for (int leadingDigitsPatternSize = objectInput.readInt(), i = 0; i < leadingDigitsPatternSize; ++i) {
                this.leadingDigitsPattern_.add(objectInput.readUTF());
            }
            if (objectInput.readBoolean()) {
                this.setNationalPrefixFormattingRule(objectInput.readUTF());
            }
            if (objectInput.readBoolean()) {
                this.setDomesticCarrierCodeFormattingRule(objectInput.readUTF());
            }
            this.setNationalPrefixOptionalWhenFormatting(objectInput.readBoolean());
        }
        
        public static final class Builder extends NumberFormat
        {
            public NumberFormat build() {
                return this;
            }
        }
    }
    
    public static class PhoneNumberDesc implements Externalizable
    {
        private static final long serialVersionUID = 1L;
        private boolean hasNationalNumberPattern;
        private String nationalNumberPattern_;
        private boolean hasPossibleNumberPattern;
        private String possibleNumberPattern_;
        private boolean hasExampleNumber;
        private String exampleNumber_;
        
        public PhoneNumberDesc() {
            this.nationalNumberPattern_ = "";
            this.possibleNumberPattern_ = "";
            this.exampleNumber_ = "";
        }
        
        public static Builder newBuilder() {
            return new Builder();
        }
        
        public boolean hasNationalNumberPattern() {
            return this.hasNationalNumberPattern;
        }
        
        public String getNationalNumberPattern() {
            return this.nationalNumberPattern_;
        }
        
        public PhoneNumberDesc setNationalNumberPattern(final String value) {
            this.hasNationalNumberPattern = true;
            this.nationalNumberPattern_ = value;
            return this;
        }
        
        public boolean hasPossibleNumberPattern() {
            return this.hasPossibleNumberPattern;
        }
        
        public String getPossibleNumberPattern() {
            return this.possibleNumberPattern_;
        }
        
        public PhoneNumberDesc setPossibleNumberPattern(final String value) {
            this.hasPossibleNumberPattern = true;
            this.possibleNumberPattern_ = value;
            return this;
        }
        
        public boolean hasExampleNumber() {
            return this.hasExampleNumber;
        }
        
        public String getExampleNumber() {
            return this.exampleNumber_;
        }
        
        public PhoneNumberDesc setExampleNumber(final String value) {
            this.hasExampleNumber = true;
            this.exampleNumber_ = value;
            return this;
        }
        
        public PhoneNumberDesc mergeFrom(final PhoneNumberDesc other) {
            if (other.hasNationalNumberPattern()) {
                this.setNationalNumberPattern(other.getNationalNumberPattern());
            }
            if (other.hasPossibleNumberPattern()) {
                this.setPossibleNumberPattern(other.getPossibleNumberPattern());
            }
            if (other.hasExampleNumber()) {
                this.setExampleNumber(other.getExampleNumber());
            }
            return this;
        }
        
        public boolean exactlySameAs(final PhoneNumberDesc other) {
            return this.nationalNumberPattern_.equals(other.nationalNumberPattern_) && this.possibleNumberPattern_.equals(other.possibleNumberPattern_) && this.exampleNumber_.equals(other.exampleNumber_);
        }
        
        public void writeExternal(final ObjectOutput objectOutput) throws IOException {
            objectOutput.writeBoolean(this.hasNationalNumberPattern);
            if (this.hasNationalNumberPattern) {
                objectOutput.writeUTF(this.nationalNumberPattern_);
            }
            objectOutput.writeBoolean(this.hasPossibleNumberPattern);
            if (this.hasPossibleNumberPattern) {
                objectOutput.writeUTF(this.possibleNumberPattern_);
            }
            objectOutput.writeBoolean(this.hasExampleNumber);
            if (this.hasExampleNumber) {
                objectOutput.writeUTF(this.exampleNumber_);
            }
        }
        
        public void readExternal(final ObjectInput objectInput) throws IOException {
            if (objectInput.readBoolean()) {
                this.setNationalNumberPattern(objectInput.readUTF());
            }
            if (objectInput.readBoolean()) {
                this.setPossibleNumberPattern(objectInput.readUTF());
            }
            if (objectInput.readBoolean()) {
                this.setExampleNumber(objectInput.readUTF());
            }
        }
        
        public static final class Builder extends PhoneNumberDesc
        {
            public PhoneNumberDesc build() {
                return this;
            }
        }
    }
    
    public static class PhoneMetadata implements Externalizable
    {
        private static final long serialVersionUID = 1L;
        private boolean hasGeneralDesc;
        private PhoneNumberDesc generalDesc_;
        private boolean hasFixedLine;
        private PhoneNumberDesc fixedLine_;
        private boolean hasMobile;
        private PhoneNumberDesc mobile_;
        private boolean hasTollFree;
        private PhoneNumberDesc tollFree_;
        private boolean hasPremiumRate;
        private PhoneNumberDesc premiumRate_;
        private boolean hasSharedCost;
        private PhoneNumberDesc sharedCost_;
        private boolean hasPersonalNumber;
        private PhoneNumberDesc personalNumber_;
        private boolean hasVoip;
        private PhoneNumberDesc voip_;
        private boolean hasPager;
        private PhoneNumberDesc pager_;
        private boolean hasUan;
        private PhoneNumberDesc uan_;
        private boolean hasEmergency;
        private PhoneNumberDesc emergency_;
        private boolean hasVoicemail;
        private PhoneNumberDesc voicemail_;
        private boolean hasShortCode;
        private PhoneNumberDesc shortCode_;
        private boolean hasStandardRate;
        private PhoneNumberDesc standardRate_;
        private boolean hasCarrierSpecific;
        private PhoneNumberDesc carrierSpecific_;
        private boolean hasNoInternationalDialling;
        private PhoneNumberDesc noInternationalDialling_;
        private boolean hasId;
        private String id_;
        private boolean hasCountryCode;
        private int countryCode_;
        private boolean hasInternationalPrefix;
        private String internationalPrefix_;
        private boolean hasPreferredInternationalPrefix;
        private String preferredInternationalPrefix_;
        private boolean hasNationalPrefix;
        private String nationalPrefix_;
        private boolean hasPreferredExtnPrefix;
        private String preferredExtnPrefix_;
        private boolean hasNationalPrefixForParsing;
        private String nationalPrefixForParsing_;
        private boolean hasNationalPrefixTransformRule;
        private String nationalPrefixTransformRule_;
        private boolean hasSameMobileAndFixedLinePattern;
        private boolean sameMobileAndFixedLinePattern_;
        private List<NumberFormat> numberFormat_;
        private List<NumberFormat> intlNumberFormat_;
        private boolean hasMainCountryForCode;
        private boolean mainCountryForCode_;
        private boolean hasLeadingDigits;
        private String leadingDigits_;
        private boolean hasLeadingZeroPossible;
        private boolean leadingZeroPossible_;
        private boolean hasMobileNumberPortableRegion;
        private boolean mobileNumberPortableRegion_;
        
        public PhoneMetadata() {
            this.generalDesc_ = null;
            this.fixedLine_ = null;
            this.mobile_ = null;
            this.tollFree_ = null;
            this.premiumRate_ = null;
            this.sharedCost_ = null;
            this.personalNumber_ = null;
            this.voip_ = null;
            this.pager_ = null;
            this.uan_ = null;
            this.emergency_ = null;
            this.voicemail_ = null;
            this.shortCode_ = null;
            this.standardRate_ = null;
            this.carrierSpecific_ = null;
            this.noInternationalDialling_ = null;
            this.id_ = "";
            this.countryCode_ = 0;
            this.internationalPrefix_ = "";
            this.preferredInternationalPrefix_ = "";
            this.nationalPrefix_ = "";
            this.preferredExtnPrefix_ = "";
            this.nationalPrefixForParsing_ = "";
            this.nationalPrefixTransformRule_ = "";
            this.sameMobileAndFixedLinePattern_ = false;
            this.numberFormat_ = new ArrayList<NumberFormat>();
            this.intlNumberFormat_ = new ArrayList<NumberFormat>();
            this.mainCountryForCode_ = false;
            this.leadingDigits_ = "";
            this.leadingZeroPossible_ = false;
            this.mobileNumberPortableRegion_ = false;
        }
        
        public static Builder newBuilder() {
            return new Builder();
        }
        
        public boolean hasGeneralDesc() {
            return this.hasGeneralDesc;
        }
        
        public PhoneNumberDesc getGeneralDesc() {
            return this.generalDesc_;
        }
        
        public PhoneMetadata setGeneralDesc(final PhoneNumberDesc value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.hasGeneralDesc = true;
            this.generalDesc_ = value;
            return this;
        }
        
        public boolean hasFixedLine() {
            return this.hasFixedLine;
        }
        
        public PhoneNumberDesc getFixedLine() {
            return this.fixedLine_;
        }
        
        public PhoneMetadata setFixedLine(final PhoneNumberDesc value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.hasFixedLine = true;
            this.fixedLine_ = value;
            return this;
        }
        
        public boolean hasMobile() {
            return this.hasMobile;
        }
        
        public PhoneNumberDesc getMobile() {
            return this.mobile_;
        }
        
        public PhoneMetadata setMobile(final PhoneNumberDesc value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.hasMobile = true;
            this.mobile_ = value;
            return this;
        }
        
        public boolean hasTollFree() {
            return this.hasTollFree;
        }
        
        public PhoneNumberDesc getTollFree() {
            return this.tollFree_;
        }
        
        public PhoneMetadata setTollFree(final PhoneNumberDesc value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.hasTollFree = true;
            this.tollFree_ = value;
            return this;
        }
        
        public boolean hasPremiumRate() {
            return this.hasPremiumRate;
        }
        
        public PhoneNumberDesc getPremiumRate() {
            return this.premiumRate_;
        }
        
        public PhoneMetadata setPremiumRate(final PhoneNumberDesc value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.hasPremiumRate = true;
            this.premiumRate_ = value;
            return this;
        }
        
        public boolean hasSharedCost() {
            return this.hasSharedCost;
        }
        
        public PhoneNumberDesc getSharedCost() {
            return this.sharedCost_;
        }
        
        public PhoneMetadata setSharedCost(final PhoneNumberDesc value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.hasSharedCost = true;
            this.sharedCost_ = value;
            return this;
        }
        
        public boolean hasPersonalNumber() {
            return this.hasPersonalNumber;
        }
        
        public PhoneNumberDesc getPersonalNumber() {
            return this.personalNumber_;
        }
        
        public PhoneMetadata setPersonalNumber(final PhoneNumberDesc value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.hasPersonalNumber = true;
            this.personalNumber_ = value;
            return this;
        }
        
        public boolean hasVoip() {
            return this.hasVoip;
        }
        
        public PhoneNumberDesc getVoip() {
            return this.voip_;
        }
        
        public PhoneMetadata setVoip(final PhoneNumberDesc value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.hasVoip = true;
            this.voip_ = value;
            return this;
        }
        
        public boolean hasPager() {
            return this.hasPager;
        }
        
        public PhoneNumberDesc getPager() {
            return this.pager_;
        }
        
        public PhoneMetadata setPager(final PhoneNumberDesc value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.hasPager = true;
            this.pager_ = value;
            return this;
        }
        
        public boolean hasUan() {
            return this.hasUan;
        }
        
        public PhoneNumberDesc getUan() {
            return this.uan_;
        }
        
        public PhoneMetadata setUan(final PhoneNumberDesc value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.hasUan = true;
            this.uan_ = value;
            return this;
        }
        
        public boolean hasEmergency() {
            return this.hasEmergency;
        }
        
        public PhoneNumberDesc getEmergency() {
            return this.emergency_;
        }
        
        public PhoneMetadata setEmergency(final PhoneNumberDesc value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.hasEmergency = true;
            this.emergency_ = value;
            return this;
        }
        
        public boolean hasVoicemail() {
            return this.hasVoicemail;
        }
        
        public PhoneNumberDesc getVoicemail() {
            return this.voicemail_;
        }
        
        public PhoneMetadata setVoicemail(final PhoneNumberDesc value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.hasVoicemail = true;
            this.voicemail_ = value;
            return this;
        }
        
        public boolean hasShortCode() {
            return this.hasShortCode;
        }
        
        public PhoneNumberDesc getShortCode() {
            return this.shortCode_;
        }
        
        public PhoneMetadata setShortCode(final PhoneNumberDesc value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.hasShortCode = true;
            this.shortCode_ = value;
            return this;
        }
        
        public boolean hasStandardRate() {
            return this.hasStandardRate;
        }
        
        public PhoneNumberDesc getStandardRate() {
            return this.standardRate_;
        }
        
        public PhoneMetadata setStandardRate(final PhoneNumberDesc value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.hasStandardRate = true;
            this.standardRate_ = value;
            return this;
        }
        
        public boolean hasCarrierSpecific() {
            return this.hasCarrierSpecific;
        }
        
        public PhoneNumberDesc getCarrierSpecific() {
            return this.carrierSpecific_;
        }
        
        public PhoneMetadata setCarrierSpecific(final PhoneNumberDesc value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.hasCarrierSpecific = true;
            this.carrierSpecific_ = value;
            return this;
        }
        
        public boolean hasNoInternationalDialling() {
            return this.hasNoInternationalDialling;
        }
        
        public PhoneNumberDesc getNoInternationalDialling() {
            return this.noInternationalDialling_;
        }
        
        public PhoneMetadata setNoInternationalDialling(final PhoneNumberDesc value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.hasNoInternationalDialling = true;
            this.noInternationalDialling_ = value;
            return this;
        }
        
        public boolean hasId() {
            return this.hasId;
        }
        
        public String getId() {
            return this.id_;
        }
        
        public PhoneMetadata setId(final String value) {
            this.hasId = true;
            this.id_ = value;
            return this;
        }
        
        public boolean hasCountryCode() {
            return this.hasCountryCode;
        }
        
        public int getCountryCode() {
            return this.countryCode_;
        }
        
        public PhoneMetadata setCountryCode(final int value) {
            this.hasCountryCode = true;
            this.countryCode_ = value;
            return this;
        }
        
        public boolean hasInternationalPrefix() {
            return this.hasInternationalPrefix;
        }
        
        public String getInternationalPrefix() {
            return this.internationalPrefix_;
        }
        
        public PhoneMetadata setInternationalPrefix(final String value) {
            this.hasInternationalPrefix = true;
            this.internationalPrefix_ = value;
            return this;
        }
        
        public boolean hasPreferredInternationalPrefix() {
            return this.hasPreferredInternationalPrefix;
        }
        
        public String getPreferredInternationalPrefix() {
            return this.preferredInternationalPrefix_;
        }
        
        public PhoneMetadata setPreferredInternationalPrefix(final String value) {
            this.hasPreferredInternationalPrefix = true;
            this.preferredInternationalPrefix_ = value;
            return this;
        }
        
        public boolean hasNationalPrefix() {
            return this.hasNationalPrefix;
        }
        
        public String getNationalPrefix() {
            return this.nationalPrefix_;
        }
        
        public PhoneMetadata setNationalPrefix(final String value) {
            this.hasNationalPrefix = true;
            this.nationalPrefix_ = value;
            return this;
        }
        
        public boolean hasPreferredExtnPrefix() {
            return this.hasPreferredExtnPrefix;
        }
        
        public String getPreferredExtnPrefix() {
            return this.preferredExtnPrefix_;
        }
        
        public PhoneMetadata setPreferredExtnPrefix(final String value) {
            this.hasPreferredExtnPrefix = true;
            this.preferredExtnPrefix_ = value;
            return this;
        }
        
        public boolean hasNationalPrefixForParsing() {
            return this.hasNationalPrefixForParsing;
        }
        
        public String getNationalPrefixForParsing() {
            return this.nationalPrefixForParsing_;
        }
        
        public PhoneMetadata setNationalPrefixForParsing(final String value) {
            this.hasNationalPrefixForParsing = true;
            this.nationalPrefixForParsing_ = value;
            return this;
        }
        
        public boolean hasNationalPrefixTransformRule() {
            return this.hasNationalPrefixTransformRule;
        }
        
        public String getNationalPrefixTransformRule() {
            return this.nationalPrefixTransformRule_;
        }
        
        public PhoneMetadata setNationalPrefixTransformRule(final String value) {
            this.hasNationalPrefixTransformRule = true;
            this.nationalPrefixTransformRule_ = value;
            return this;
        }
        
        public boolean hasSameMobileAndFixedLinePattern() {
            return this.hasSameMobileAndFixedLinePattern;
        }
        
        public boolean isSameMobileAndFixedLinePattern() {
            return this.sameMobileAndFixedLinePattern_;
        }
        
        public PhoneMetadata setSameMobileAndFixedLinePattern(final boolean value) {
            this.hasSameMobileAndFixedLinePattern = true;
            this.sameMobileAndFixedLinePattern_ = value;
            return this;
        }
        
        public List<NumberFormat> numberFormats() {
            return this.numberFormat_;
        }
        
        public int numberFormatSize() {
            return this.numberFormat_.size();
        }
        
        public NumberFormat getNumberFormat(final int index) {
            return this.numberFormat_.get(index);
        }
        
        public PhoneMetadata addNumberFormat(final NumberFormat value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.numberFormat_.add(value);
            return this;
        }
        
        public List<NumberFormat> intlNumberFormats() {
            return this.intlNumberFormat_;
        }
        
        public int intlNumberFormatSize() {
            return this.intlNumberFormat_.size();
        }
        
        public NumberFormat getIntlNumberFormat(final int index) {
            return this.intlNumberFormat_.get(index);
        }
        
        public PhoneMetadata addIntlNumberFormat(final NumberFormat value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.intlNumberFormat_.add(value);
            return this;
        }
        
        public PhoneMetadata clearIntlNumberFormat() {
            this.intlNumberFormat_.clear();
            return this;
        }
        
        public boolean hasMainCountryForCode() {
            return this.hasMainCountryForCode;
        }
        
        public boolean isMainCountryForCode() {
            return this.mainCountryForCode_;
        }
        
        public boolean getMainCountryForCode() {
            return this.mainCountryForCode_;
        }
        
        public PhoneMetadata setMainCountryForCode(final boolean value) {
            this.hasMainCountryForCode = true;
            this.mainCountryForCode_ = value;
            return this;
        }
        
        public boolean hasLeadingDigits() {
            return this.hasLeadingDigits;
        }
        
        public String getLeadingDigits() {
            return this.leadingDigits_;
        }
        
        public PhoneMetadata setLeadingDigits(final String value) {
            this.hasLeadingDigits = true;
            this.leadingDigits_ = value;
            return this;
        }
        
        public boolean hasLeadingZeroPossible() {
            return this.hasLeadingZeroPossible;
        }
        
        public boolean isLeadingZeroPossible() {
            return this.leadingZeroPossible_;
        }
        
        public PhoneMetadata setLeadingZeroPossible(final boolean value) {
            this.hasLeadingZeroPossible = true;
            this.leadingZeroPossible_ = value;
            return this;
        }
        
        public boolean hasMobileNumberPortableRegion() {
            return this.hasMobileNumberPortableRegion;
        }
        
        public boolean isMobileNumberPortableRegion() {
            return this.mobileNumberPortableRegion_;
        }
        
        public PhoneMetadata setMobileNumberPortableRegion(final boolean value) {
            this.hasMobileNumberPortableRegion = true;
            this.mobileNumberPortableRegion_ = value;
            return this;
        }
        
        public void writeExternal(final ObjectOutput objectOutput) throws IOException {
            objectOutput.writeBoolean(this.hasGeneralDesc);
            if (this.hasGeneralDesc) {
                this.generalDesc_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasFixedLine);
            if (this.hasFixedLine) {
                this.fixedLine_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasMobile);
            if (this.hasMobile) {
                this.mobile_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasTollFree);
            if (this.hasTollFree) {
                this.tollFree_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasPremiumRate);
            if (this.hasPremiumRate) {
                this.premiumRate_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasSharedCost);
            if (this.hasSharedCost) {
                this.sharedCost_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasPersonalNumber);
            if (this.hasPersonalNumber) {
                this.personalNumber_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasVoip);
            if (this.hasVoip) {
                this.voip_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasPager);
            if (this.hasPager) {
                this.pager_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasUan);
            if (this.hasUan) {
                this.uan_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasEmergency);
            if (this.hasEmergency) {
                this.emergency_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasVoicemail);
            if (this.hasVoicemail) {
                this.voicemail_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasShortCode);
            if (this.hasShortCode) {
                this.shortCode_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasStandardRate);
            if (this.hasStandardRate) {
                this.standardRate_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasCarrierSpecific);
            if (this.hasCarrierSpecific) {
                this.carrierSpecific_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasNoInternationalDialling);
            if (this.hasNoInternationalDialling) {
                this.noInternationalDialling_.writeExternal(objectOutput);
            }
            objectOutput.writeUTF(this.id_);
            objectOutput.writeInt(this.countryCode_);
            objectOutput.writeUTF(this.internationalPrefix_);
            objectOutput.writeBoolean(this.hasPreferredInternationalPrefix);
            if (this.hasPreferredInternationalPrefix) {
                objectOutput.writeUTF(this.preferredInternationalPrefix_);
            }
            objectOutput.writeBoolean(this.hasNationalPrefix);
            if (this.hasNationalPrefix) {
                objectOutput.writeUTF(this.nationalPrefix_);
            }
            objectOutput.writeBoolean(this.hasPreferredExtnPrefix);
            if (this.hasPreferredExtnPrefix) {
                objectOutput.writeUTF(this.preferredExtnPrefix_);
            }
            objectOutput.writeBoolean(this.hasNationalPrefixForParsing);
            if (this.hasNationalPrefixForParsing) {
                objectOutput.writeUTF(this.nationalPrefixForParsing_);
            }
            objectOutput.writeBoolean(this.hasNationalPrefixTransformRule);
            if (this.hasNationalPrefixTransformRule) {
                objectOutput.writeUTF(this.nationalPrefixTransformRule_);
            }
            objectOutput.writeBoolean(this.sameMobileAndFixedLinePattern_);
            final int numberFormatSize = this.numberFormatSize();
            objectOutput.writeInt(numberFormatSize);
            for (int i = 0; i < numberFormatSize; ++i) {
                this.numberFormat_.get(i).writeExternal(objectOutput);
            }
            final int intlNumberFormatSize = this.intlNumberFormatSize();
            objectOutput.writeInt(intlNumberFormatSize);
            for (int j = 0; j < intlNumberFormatSize; ++j) {
                this.intlNumberFormat_.get(j).writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.mainCountryForCode_);
            objectOutput.writeBoolean(this.hasLeadingDigits);
            if (this.hasLeadingDigits) {
                objectOutput.writeUTF(this.leadingDigits_);
            }
            objectOutput.writeBoolean(this.leadingZeroPossible_);
            objectOutput.writeBoolean(this.mobileNumberPortableRegion_);
        }
        
        public void readExternal(final ObjectInput objectInput) throws IOException {
            boolean hasDesc = objectInput.readBoolean();
            if (hasDesc) {
                final PhoneNumberDesc desc = new PhoneNumberDesc();
                desc.readExternal(objectInput);
                this.setGeneralDesc(desc);
            }
            hasDesc = objectInput.readBoolean();
            if (hasDesc) {
                final PhoneNumberDesc desc = new PhoneNumberDesc();
                desc.readExternal(objectInput);
                this.setFixedLine(desc);
            }
            hasDesc = objectInput.readBoolean();
            if (hasDesc) {
                final PhoneNumberDesc desc = new PhoneNumberDesc();
                desc.readExternal(objectInput);
                this.setMobile(desc);
            }
            hasDesc = objectInput.readBoolean();
            if (hasDesc) {
                final PhoneNumberDesc desc = new PhoneNumberDesc();
                desc.readExternal(objectInput);
                this.setTollFree(desc);
            }
            hasDesc = objectInput.readBoolean();
            if (hasDesc) {
                final PhoneNumberDesc desc = new PhoneNumberDesc();
                desc.readExternal(objectInput);
                this.setPremiumRate(desc);
            }
            hasDesc = objectInput.readBoolean();
            if (hasDesc) {
                final PhoneNumberDesc desc = new PhoneNumberDesc();
                desc.readExternal(objectInput);
                this.setSharedCost(desc);
            }
            hasDesc = objectInput.readBoolean();
            if (hasDesc) {
                final PhoneNumberDesc desc = new PhoneNumberDesc();
                desc.readExternal(objectInput);
                this.setPersonalNumber(desc);
            }
            hasDesc = objectInput.readBoolean();
            if (hasDesc) {
                final PhoneNumberDesc desc = new PhoneNumberDesc();
                desc.readExternal(objectInput);
                this.setVoip(desc);
            }
            hasDesc = objectInput.readBoolean();
            if (hasDesc) {
                final PhoneNumberDesc desc = new PhoneNumberDesc();
                desc.readExternal(objectInput);
                this.setPager(desc);
            }
            hasDesc = objectInput.readBoolean();
            if (hasDesc) {
                final PhoneNumberDesc desc = new PhoneNumberDesc();
                desc.readExternal(objectInput);
                this.setUan(desc);
            }
            hasDesc = objectInput.readBoolean();
            if (hasDesc) {
                final PhoneNumberDesc desc = new PhoneNumberDesc();
                desc.readExternal(objectInput);
                this.setEmergency(desc);
            }
            hasDesc = objectInput.readBoolean();
            if (hasDesc) {
                final PhoneNumberDesc desc = new PhoneNumberDesc();
                desc.readExternal(objectInput);
                this.setVoicemail(desc);
            }
            hasDesc = objectInput.readBoolean();
            if (hasDesc) {
                final PhoneNumberDesc desc = new PhoneNumberDesc();
                desc.readExternal(objectInput);
                this.setShortCode(desc);
            }
            hasDesc = objectInput.readBoolean();
            if (hasDesc) {
                final PhoneNumberDesc desc = new PhoneNumberDesc();
                desc.readExternal(objectInput);
                this.setStandardRate(desc);
            }
            hasDesc = objectInput.readBoolean();
            if (hasDesc) {
                final PhoneNumberDesc desc = new PhoneNumberDesc();
                desc.readExternal(objectInput);
                this.setCarrierSpecific(desc);
            }
            hasDesc = objectInput.readBoolean();
            if (hasDesc) {
                final PhoneNumberDesc desc = new PhoneNumberDesc();
                desc.readExternal(objectInput);
                this.setNoInternationalDialling(desc);
            }
            this.setId(objectInput.readUTF());
            this.setCountryCode(objectInput.readInt());
            this.setInternationalPrefix(objectInput.readUTF());
            boolean hasString = objectInput.readBoolean();
            if (hasString) {
                this.setPreferredInternationalPrefix(objectInput.readUTF());
            }
            hasString = objectInput.readBoolean();
            if (hasString) {
                this.setNationalPrefix(objectInput.readUTF());
            }
            hasString = objectInput.readBoolean();
            if (hasString) {
                this.setPreferredExtnPrefix(objectInput.readUTF());
            }
            hasString = objectInput.readBoolean();
            if (hasString) {
                this.setNationalPrefixForParsing(objectInput.readUTF());
            }
            hasString = objectInput.readBoolean();
            if (hasString) {
                this.setNationalPrefixTransformRule(objectInput.readUTF());
            }
            this.setSameMobileAndFixedLinePattern(objectInput.readBoolean());
            for (int nationalFormatSize = objectInput.readInt(), i = 0; i < nationalFormatSize; ++i) {
                final NumberFormat numFormat = new NumberFormat();
                numFormat.readExternal(objectInput);
                this.numberFormat_.add(numFormat);
            }
            for (int intlNumberFormatSize = objectInput.readInt(), j = 0; j < intlNumberFormatSize; ++j) {
                final NumberFormat numFormat2 = new NumberFormat();
                numFormat2.readExternal(objectInput);
                this.intlNumberFormat_.add(numFormat2);
            }
            this.setMainCountryForCode(objectInput.readBoolean());
            hasString = objectInput.readBoolean();
            if (hasString) {
                this.setLeadingDigits(objectInput.readUTF());
            }
            this.setLeadingZeroPossible(objectInput.readBoolean());
            this.setMobileNumberPortableRegion(objectInput.readBoolean());
        }
        
        public static final class Builder extends PhoneMetadata
        {
            public PhoneMetadata build() {
                return this;
            }
        }
    }
    
    public static class PhoneMetadataCollection implements Externalizable
    {
        private static final long serialVersionUID = 1L;
        private List<PhoneMetadata> metadata_;
        
        public PhoneMetadataCollection() {
            this.metadata_ = new ArrayList<PhoneMetadata>();
        }
        
        public static Builder newBuilder() {
            return new Builder();
        }
        
        public List<PhoneMetadata> getMetadataList() {
            return this.metadata_;
        }
        
        public int getMetadataCount() {
            return this.metadata_.size();
        }
        
        public PhoneMetadataCollection addMetadata(final PhoneMetadata value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.metadata_.add(value);
            return this;
        }
        
        public void writeExternal(final ObjectOutput objectOutput) throws IOException {
            final int size = this.getMetadataCount();
            objectOutput.writeInt(size);
            for (int i = 0; i < size; ++i) {
                this.metadata_.get(i).writeExternal(objectOutput);
            }
        }
        
        public void readExternal(final ObjectInput objectInput) throws IOException {
            for (int size = objectInput.readInt(), i = 0; i < size; ++i) {
                final PhoneMetadata metadata = new PhoneMetadata();
                metadata.readExternal(objectInput);
                this.metadata_.add(metadata);
            }
        }
        
        public PhoneMetadataCollection clear() {
            this.metadata_.clear();
            return this;
        }
        
        public static final class Builder extends PhoneMetadataCollection
        {
            public PhoneMetadataCollection build() {
                return this;
            }
        }
    }
}
