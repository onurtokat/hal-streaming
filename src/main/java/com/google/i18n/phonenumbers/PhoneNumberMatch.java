// 
// Decompiled by Procyon v0.5.30
// 

package com.google.i18n.phonenumbers;

import java.util.Arrays;

public final class PhoneNumberMatch
{
    private final int start;
    private final String rawString;
    private final Phonenumber.PhoneNumber number;
    
    PhoneNumberMatch(final int start, final String rawString, final Phonenumber.PhoneNumber number) {
        if (start < 0) {
            throw new IllegalArgumentException("Start index must be >= 0.");
        }
        if (rawString == null || number == null) {
            throw new NullPointerException();
        }
        this.start = start;
        this.rawString = rawString;
        this.number = number;
    }
    
    public Phonenumber.PhoneNumber number() {
        return this.number;
    }
    
    public int start() {
        return this.start;
    }
    
    public int end() {
        return this.start + this.rawString.length();
    }
    
    public String rawString() {
        return this.rawString;
    }
    
    public int hashCode() {
        return Arrays.hashCode(new Object[] { this.start, this.rawString, this.number });
    }
    
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PhoneNumberMatch)) {
            return false;
        }
        final PhoneNumberMatch other = (PhoneNumberMatch)obj;
        return this.rawString.equals(other.rawString) && this.start == other.start && this.number.equals(other.number);
    }
    
    public String toString() {
        final int start = this.start();
        final int end = this.end();
        final String value = String.valueOf(String.valueOf(this.rawString));
        return new StringBuilder(43 + value.length()).append("PhoneNumberMatch [").append(start).append(",").append(end).append(") ").append(value).toString();
    }
}
