// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.databind.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Date;
import java.util.TimeZone;

public class ISO8601Utils
{
    private static final String GMT_ID = "GMT";
    private static final TimeZone TIMEZONE_GMT;
    
    public static TimeZone timeZoneGMT() {
        return ISO8601Utils.TIMEZONE_GMT;
    }
    
    public static String format(final Date date) {
        return format(date, false, ISO8601Utils.TIMEZONE_GMT);
    }
    
    public static String format(final Date date, final boolean millis) {
        return format(date, millis, ISO8601Utils.TIMEZONE_GMT);
    }
    
    public static String format(final Date date, final boolean millis, final TimeZone tz) {
        final Calendar calendar = new GregorianCalendar(tz, Locale.US);
        calendar.setTime(date);
        int capacity = "yyyy-MM-ddThh:mm:ss".length();
        capacity += (millis ? ".sss".length() : 0);
        capacity += ((tz.getRawOffset() == 0) ? "Z".length() : "+hh:mm".length());
        final StringBuilder formatted = new StringBuilder(capacity);
        padInt(formatted, calendar.get(1), "yyyy".length());
        formatted.append('-');
        padInt(formatted, calendar.get(2) + 1, "MM".length());
        formatted.append('-');
        padInt(formatted, calendar.get(5), "dd".length());
        formatted.append('T');
        padInt(formatted, calendar.get(11), "hh".length());
        formatted.append(':');
        padInt(formatted, calendar.get(12), "mm".length());
        formatted.append(':');
        padInt(formatted, calendar.get(13), "ss".length());
        if (millis) {
            formatted.append('.');
            padInt(formatted, calendar.get(14), "sss".length());
        }
        final int offset = tz.getOffset(calendar.getTimeInMillis());
        if (offset != 0) {
            final int hours = Math.abs(offset / 60000 / 60);
            final int minutes = Math.abs(offset / 60000 % 60);
            formatted.append((offset < 0) ? '-' : '+');
            padInt(formatted, hours, "hh".length());
            formatted.append(':');
            padInt(formatted, minutes, "mm".length());
        }
        else {
            formatted.append('Z');
        }
        return formatted.toString();
    }
    
    public static Date parse(final String date, final ParsePosition pos) throws ParseException {
        Exception fail = null;
        try {
            final int \u0131ndex;
            int offset = \u0131ndex = pos.getIndex();
            offset += 4;
            final int year = parseInt(date, \u0131ndex, offset);
            checkOffset(date, offset, '-');
            final int beginIndex = ++offset;
            offset += 2;
            final int month = parseInt(date, beginIndex, offset);
            checkOffset(date, offset, '-');
            final int beginIndex2 = ++offset;
            offset += 2;
            final int day = parseInt(date, beginIndex2, offset);
            checkOffset(date, offset, 'T');
            final int beginIndex3 = ++offset;
            offset += 2;
            final int hour = parseInt(date, beginIndex3, offset);
            checkOffset(date, offset, ':');
            final int beginIndex4 = ++offset;
            offset += 2;
            final int minutes = parseInt(date, beginIndex4, offset);
            checkOffset(date, offset, ':');
            final int beginIndex5 = ++offset;
            offset += 2;
            final int seconds = parseInt(date, beginIndex5, offset);
            int milliseconds = 0;
            if (date.charAt(offset) == '.') {
                checkOffset(date, offset, '.');
                final int beginIndex6 = ++offset;
                offset += 3;
                milliseconds = parseInt(date, beginIndex6, offset);
            }
            final char timezoneIndicator = date.charAt(offset);
            String timezoneId;
            if (timezoneIndicator == '+' || timezoneIndicator == '-') {
                final String timezoneOffset = date.substring(offset);
                timezoneId = "GMT" + timezoneOffset;
                offset += timezoneOffset.length();
            }
            else {
                if (timezoneIndicator != 'Z') {
                    throw new IndexOutOfBoundsException("Invalid time zone indicator " + timezoneIndicator);
                }
                timezoneId = "GMT";
                ++offset;
            }
            final TimeZone timezone = TimeZone.getTimeZone(timezoneId);
            if (!timezone.getID().equals(timezoneId)) {
                throw new IndexOutOfBoundsException();
            }
            final Calendar calendar = new GregorianCalendar(timezone);
            calendar.setLenient(false);
            calendar.set(1, year);
            calendar.set(2, month - 1);
            calendar.set(5, day);
            calendar.set(11, hour);
            calendar.set(12, minutes);
            calendar.set(13, seconds);
            calendar.set(14, milliseconds);
            pos.setIndex(offset);
            return calendar.getTime();
        }
        catch (IndexOutOfBoundsException e) {
            fail = e;
        }
        catch (NumberFormatException e2) {
            fail = e2;
        }
        catch (IllegalArgumentException e3) {
            fail = e3;
        }
        final String input = (date == null) ? null : ('\"' + date + "'");
        throw new ParseException("Failed to parse date [" + input + "]: " + fail.getMessage(), pos.getIndex());
    }
    
    private static void checkOffset(final String value, final int offset, final char expected) throws ParseException {
        final char found = value.charAt(offset);
        if (found != expected) {
            throw new ParseException("Expected '" + expected + "' character but found '" + found + "'", offset);
        }
    }
    
    private static int parseInt(final String value, final int beginIndex, final int endIndex) throws NumberFormatException {
        if (beginIndex < 0 || endIndex > value.length() || beginIndex > endIndex) {
            throw new NumberFormatException(value);
        }
        int i = beginIndex;
        int result = 0;
        if (i < endIndex) {
            final int digit = Character.digit(value.charAt(i++), 10);
            if (digit < 0) {
                throw new NumberFormatException("Invalid number: " + value);
            }
            result = -digit;
        }
        while (i < endIndex) {
            final int digit = Character.digit(value.charAt(i++), 10);
            if (digit < 0) {
                throw new NumberFormatException("Invalid number: " + value);
            }
            result *= 10;
            result -= digit;
        }
        return -result;
    }
    
    private static void padInt(final StringBuilder buffer, final int value, final int length) {
        final String strValue = Integer.toString(value);
        for (int i = length - strValue.length(); i > 0; --i) {
            buffer.append('0');
        }
        buffer.append(strValue);
    }
    
    static {
        TIMEZONE_GMT = TimeZone.getTimeZone("GMT");
    }
}
