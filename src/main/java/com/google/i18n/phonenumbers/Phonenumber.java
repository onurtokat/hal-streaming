// 
// Decompiled by Procyon v0.5.30
// 

package com.google.i18n.phonenumbers;

import java.io.Serializable;

public final class Phonenumber
{
    public static class PhoneNumber implements Serializable
    {
        private static final long serialVersionUID = 1L;
        private boolean hasCountryCode;
        private int countryCode_;
        private boolean hasNationalNumber;
        private long nationalNumber_;
        private boolean hasExtension;
        private String extension_;
        private boolean hasItalianLeadingZero;
        private boolean italianLeadingZero_;
        private boolean hasNumberOfLeadingZeros;
        private int numberOfLeadingZeros_;
        private boolean hasRawInput;
        private String rawInput_;
        private boolean hasCountryCodeSource;
        private CountryCodeSource countryCodeSource_;
        private boolean hasPreferredDomesticCarrierCode;
        private String preferredDomesticCarrierCode_;
        
        public PhoneNumber() {
            this.countryCode_ = 0;
            this.nationalNumber_ = 0L;
            this.extension_ = "";
            this.italianLeadingZero_ = false;
            this.numberOfLeadingZeros_ = 1;
            this.rawInput_ = "";
            this.preferredDomesticCarrierCode_ = "";
            this.countryCodeSource_ = CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN;
        }
        
        public boolean hasCountryCode() {
            return this.hasCountryCode;
        }
        
        public int getCountryCode() {
            return this.countryCode_;
        }
        
        public PhoneNumber setCountryCode(final int value) {
            this.hasCountryCode = true;
            this.countryCode_ = value;
            return this;
        }
        
        public PhoneNumber clearCountryCode() {
            this.hasCountryCode = false;
            this.countryCode_ = 0;
            return this;
        }
        
        public boolean hasNationalNumber() {
            return this.hasNationalNumber;
        }
        
        public long getNationalNumber() {
            return this.nationalNumber_;
        }
        
        public PhoneNumber setNationalNumber(final long value) {
            this.hasNationalNumber = true;
            this.nationalNumber_ = value;
            return this;
        }
        
        public PhoneNumber clearNationalNumber() {
            this.hasNationalNumber = false;
            this.nationalNumber_ = 0L;
            return this;
        }
        
        public boolean hasExtension() {
            return this.hasExtension;
        }
        
        public String getExtension() {
            return this.extension_;
        }
        
        public PhoneNumber setExtension(final String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.hasExtension = true;
            this.extension_ = value;
            return this;
        }
        
        public PhoneNumber clearExtension() {
            this.hasExtension = false;
            this.extension_ = "";
            return this;
        }
        
        public boolean hasItalianLeadingZero() {
            return this.hasItalianLeadingZero;
        }
        
        public boolean isItalianLeadingZero() {
            return this.italianLeadingZero_;
        }
        
        public PhoneNumber setItalianLeadingZero(final boolean value) {
            this.hasItalianLeadingZero = true;
            this.italianLeadingZero_ = value;
            return this;
        }
        
        public PhoneNumber clearItalianLeadingZero() {
            this.hasItalianLeadingZero = false;
            this.italianLeadingZero_ = false;
            return this;
        }
        
        public boolean hasNumberOfLeadingZeros() {
            return this.hasNumberOfLeadingZeros;
        }
        
        public int getNumberOfLeadingZeros() {
            return this.numberOfLeadingZeros_;
        }
        
        public PhoneNumber setNumberOfLeadingZeros(final int value) {
            this.hasNumberOfLeadingZeros = true;
            this.numberOfLeadingZeros_ = value;
            return this;
        }
        
        public PhoneNumber clearNumberOfLeadingZeros() {
            this.hasNumberOfLeadingZeros = false;
            this.numberOfLeadingZeros_ = 1;
            return this;
        }
        
        public boolean hasRawInput() {
            return this.hasRawInput;
        }
        
        public String getRawInput() {
            return this.rawInput_;
        }
        
        public PhoneNumber setRawInput(final String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.hasRawInput = true;
            this.rawInput_ = value;
            return this;
        }
        
        public PhoneNumber clearRawInput() {
            this.hasRawInput = false;
            this.rawInput_ = "";
            return this;
        }
        
        public boolean hasCountryCodeSource() {
            return this.hasCountryCodeSource;
        }
        
        public CountryCodeSource getCountryCodeSource() {
            return this.countryCodeSource_;
        }
        
        public PhoneNumber setCountryCodeSource(final CountryCodeSource value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.hasCountryCodeSource = true;
            this.countryCodeSource_ = value;
            return this;
        }
        
        public PhoneNumber clearCountryCodeSource() {
            this.hasCountryCodeSource = false;
            this.countryCodeSource_ = CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN;
            return this;
        }
        
        public boolean hasPreferredDomesticCarrierCode() {
            return this.hasPreferredDomesticCarrierCode;
        }
        
        public String getPreferredDomesticCarrierCode() {
            return this.preferredDomesticCarrierCode_;
        }
        
        public PhoneNumber setPreferredDomesticCarrierCode(final String value) {
            if (value == null) {
                throw new NullPointerException();
            }
            this.hasPreferredDomesticCarrierCode = true;
            this.preferredDomesticCarrierCode_ = value;
            return this;
        }
        
        public PhoneNumber clearPreferredDomesticCarrierCode() {
            this.hasPreferredDomesticCarrierCode = false;
            this.preferredDomesticCarrierCode_ = "";
            return this;
        }
        
        public final PhoneNumber clear() {
            this.clearCountryCode();
            this.clearNationalNumber();
            this.clearExtension();
            this.clearItalianLeadingZero();
            this.clearNumberOfLeadingZeros();
            this.clearRawInput();
            this.clearCountryCodeSource();
            this.clearPreferredDomesticCarrierCode();
            return this;
        }
        
        public PhoneNumber mergeFrom(final PhoneNumber other) {
            if (other.hasCountryCode()) {
                this.setCountryCode(other.getCountryCode());
            }
            if (other.hasNationalNumber()) {
                this.setNationalNumber(other.getNationalNumber());
            }
            if (other.hasExtension()) {
                this.setExtension(other.getExtension());
            }
            if (other.hasItalianLeadingZero()) {
                this.setItalianLeadingZero(other.isItalianLeadingZero());
            }
            if (other.hasNumberOfLeadingZeros()) {
                this.setNumberOfLeadingZeros(other.getNumberOfLeadingZeros());
            }
            if (other.hasRawInput()) {
                this.setRawInput(other.getRawInput());
            }
            if (other.hasCountryCodeSource()) {
                this.setCountryCodeSource(other.getCountryCodeSource());
            }
            if (other.hasPreferredDomesticCarrierCode()) {
                this.setPreferredDomesticCarrierCode(other.getPreferredDomesticCarrierCode());
            }
            return this;
        }
        
        public boolean exactlySameAs(final PhoneNumber other) {
            return other != null && (this == other || (this.countryCode_ == other.countryCode_ && this.nationalNumber_ == other.nationalNumber_ && this.extension_.equals(other.extension_) && this.italianLeadingZero_ == other.italianLeadingZero_ && this.numberOfLeadingZeros_ == other.numberOfLeadingZeros_ && this.rawInput_.equals(other.rawInput_) && this.countryCodeSource_ == other.countryCodeSource_ && this.preferredDomesticCarrierCode_.equals(other.preferredDomesticCarrierCode_) && this.hasPreferredDomesticCarrierCode() == other.hasPreferredDomesticCarrierCode()));
        }
        
        public boolean equals(final Object that) {
            return that instanceof PhoneNumber && this.exactlySameAs((PhoneNumber)that);
        }
        
        public int hashCode() {
            int hash = 41;
            hash = 53 * hash + this.getCountryCode();
            hash = 53 * hash + Long.valueOf(this.getNationalNumber()).hashCode();
            hash = 53 * hash + this.getExtension().hashCode();
            hash = 53 * hash + (this.isItalianLeadingZero() ? 1231 : 1237);
            hash = 53 * hash + this.getNumberOfLeadingZeros();
            hash = 53 * hash + this.getRawInput().hashCode();
            hash = 53 * hash + this.getCountryCodeSource().hashCode();
            hash = 53 * hash + this.getPreferredDomesticCarrierCode().hashCode();
            hash = 53 * hash + (this.hasPreferredDomesticCarrierCode() ? 1231 : 1237);
            return hash;
        }
        
        public String toString() {
            final StringBuilder outputString = new StringBuilder();
            outputString.append("Country Code: ").append(this.countryCode_);
            outputString.append(" National Number: ").append(this.nationalNumber_);
            if (this.hasItalianLeadingZero() && this.isItalianLeadingZero()) {
                outputString.append(" Leading Zero(s): true");
            }
            if (this.hasNumberOfLeadingZeros()) {
                outputString.append(" Number of leading zeros: ").append(this.numberOfLeadingZeros_);
            }
            if (this.hasExtension()) {
                outputString.append(" Extension: ").append(this.extension_);
            }
            if (this.hasCountryCodeSource()) {
                outputString.append(" Country Code Source: ").append(this.countryCodeSource_);
            }
            if (this.hasPreferredDomesticCarrierCode()) {
                outputString.append(" Preferred Domestic Carrier Code: ").append(this.preferredDomesticCarrierCode_);
            }
            return outputString.toString();
        }
        
        public enum CountryCodeSource
        {
            FROM_NUMBER_WITH_PLUS_SIGN, 
            FROM_NUMBER_WITH_IDD, 
            FROM_NUMBER_WITHOUT_PLUS_SIGN, 
            FROM_DEFAULT_COUNTRY;
        }
    }
}
