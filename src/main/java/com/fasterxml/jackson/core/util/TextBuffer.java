// 
// Decompiled by Procyon v0.5.30
// 

package com.fasterxml.jackson.core.util;

import java.util.Arrays;
import com.fasterxml.jackson.core.io.NumberInput;
import java.math.BigDecimal;
import java.util.ArrayList;

public final class TextBuffer
{
    static final char[] NO_CHARS;
    static final int MIN_SEGMENT_LEN = 1000;
    static final int MAX_SEGMENT_LEN = 262144;
    private final BufferRecycler _allocator;
    private char[] _inputBuffer;
    private int _inputStart;
    private int _inputLen;
    private ArrayList<char[]> _segments;
    private boolean _hasSegments;
    private int _segmentSize;
    private char[] _currentSegment;
    private int _currentSize;
    private String _resultString;
    private char[] _resultArray;
    
    public TextBuffer(final BufferRecycler allocator) {
        this._hasSegments = false;
        this._allocator = allocator;
    }
    
    public void releaseBuffers() {
        if (this._allocator == null) {
            this.resetWithEmpty();
        }
        else if (this._currentSegment != null) {
            this.resetWithEmpty();
            final char[] buf = this._currentSegment;
            this._currentSegment = null;
            this._allocator.releaseCharBuffer(2, buf);
        }
    }
    
    public void resetWithEmpty() {
        this._inputStart = -1;
        this._currentSize = 0;
        this._inputLen = 0;
        this._inputBuffer = null;
        this._resultString = null;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        }
    }
    
    public void resetWithShared(final char[] buf, final int start, final int len) {
        this._resultString = null;
        this._resultArray = null;
        this._inputBuffer = buf;
        this._inputStart = start;
        this._inputLen = len;
        if (this._hasSegments) {
            this.clearSegments();
        }
    }
    
    public void resetWithCopy(final char[] buf, final int start, final int len) {
        this._inputBuffer = null;
        this._inputStart = -1;
        this._inputLen = 0;
        this._resultString = null;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        }
        else if (this._currentSegment == null) {
            this._currentSegment = this.buf(len);
        }
        final boolean b = false;
        this._segmentSize = (b ? 1 : 0);
        this._currentSize = (b ? 1 : 0);
        this.append(buf, start, len);
    }
    
    public void resetWithString(final String value) {
        this._inputBuffer = null;
        this._inputStart = -1;
        this._inputLen = 0;
        this._resultString = value;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        }
        this._currentSize = 0;
    }
    
    private char[] buf(final int needed) {
        if (this._allocator != null) {
            return this._allocator.allocCharBuffer(2, needed);
        }
        return new char[Math.max(needed, 1000)];
    }
    
    private void clearSegments() {
        this._hasSegments = false;
        this._segments.clear();
        final boolean b = false;
        this._segmentSize = (b ? 1 : 0);
        this._currentSize = (b ? 1 : 0);
    }
    
    public int size() {
        if (this._inputStart >= 0) {
            return this._inputLen;
        }
        if (this._resultArray != null) {
            return this._resultArray.length;
        }
        if (this._resultString != null) {
            return this._resultString.length();
        }
        return this._segmentSize + this._currentSize;
    }
    
    public int getTextOffset() {
        return (this._inputStart >= 0) ? this._inputStart : 0;
    }
    
    public boolean hasTextAsCharacters() {
        return this._inputStart >= 0 || this._resultArray != null || this._resultString == null;
    }
    
    public char[] getTextBuffer() {
        if (this._inputStart >= 0) {
            return this._inputBuffer;
        }
        if (this._resultArray != null) {
            return this._resultArray;
        }
        if (this._resultString != null) {
            return this._resultArray = this._resultString.toCharArray();
        }
        if (!this._hasSegments) {
            return this._currentSegment;
        }
        return this.contentsAsArray();
    }
    
    public String contentsAsString() {
        if (this._resultString == null) {
            if (this._resultArray != null) {
                this._resultString = new String(this._resultArray);
            }
            else if (this._inputStart >= 0) {
                if (this._inputLen < 1) {
                    return this._resultString = "";
                }
                this._resultString = new String(this._inputBuffer, this._inputStart, this._inputLen);
            }
            else {
                final int segLen = this._segmentSize;
                final int currLen = this._currentSize;
                if (segLen == 0) {
                    this._resultString = ((currLen == 0) ? "" : new String(this._currentSegment, 0, currLen));
                }
                else {
                    final StringBuilder sb = new StringBuilder(segLen + currLen);
                    if (this._segments != null) {
                        for (int i = 0, len = this._segments.size(); i < len; ++i) {
                            final char[] curr = this._segments.get(i);
                            sb.append(curr, 0, curr.length);
                        }
                    }
                    sb.append(this._currentSegment, 0, this._currentSize);
                    this._resultString = sb.toString();
                }
            }
        }
        return this._resultString;
    }
    
    public char[] contentsAsArray() {
        char[] result = this._resultArray;
        if (result == null) {
            result = (this._resultArray = this.resultArray());
        }
        return result;
    }
    
    public BigDecimal contentsAsDecimal() throws NumberFormatException {
        if (this._resultArray != null) {
            return NumberInput.parseBigDecimal(this._resultArray);
        }
        if (this._inputStart >= 0 && this._inputBuffer != null) {
            return NumberInput.parseBigDecimal(this._inputBuffer, this._inputStart, this._inputLen);
        }
        if (this._segmentSize == 0 && this._currentSegment != null) {
            return NumberInput.parseBigDecimal(this._currentSegment, 0, this._currentSize);
        }
        return NumberInput.parseBigDecimal(this.contentsAsArray());
    }
    
    public double contentsAsDouble() throws NumberFormatException {
        return NumberInput.parseDouble(this.contentsAsString());
    }
    
    public void ensureNotShared() {
        if (this._inputStart >= 0) {
            this.unshare(16);
        }
    }
    
    public void append(final char c) {
        if (this._inputStart >= 0) {
            this.unshare(16);
        }
        this._resultString = null;
        this._resultArray = null;
        char[] curr = this._currentSegment;
        if (this._currentSize >= curr.length) {
            this.expand(1);
            curr = this._currentSegment;
        }
        curr[this._currentSize++] = c;
    }
    
    public void append(final char[] c, int start, int len) {
        if (this._inputStart >= 0) {
            this.unshare(len);
        }
        this._resultString = null;
        this._resultArray = null;
        final char[] curr = this._currentSegment;
        final int max = curr.length - this._currentSize;
        if (max >= len) {
            System.arraycopy(c, start, curr, this._currentSize, len);
            this._currentSize += len;
            return;
        }
        if (max > 0) {
            System.arraycopy(c, start, curr, this._currentSize, max);
            start += max;
            len -= max;
        }
        do {
            this.expand(len);
            final int amount = Math.min(this._currentSegment.length, len);
            System.arraycopy(c, start, this._currentSegment, 0, amount);
            this._currentSize += amount;
            start += amount;
            len -= amount;
        } while (len > 0);
    }
    
    public void append(final String str, int offset, int len) {
        if (this._inputStart >= 0) {
            this.unshare(len);
        }
        this._resultString = null;
        this._resultArray = null;
        final char[] curr = this._currentSegment;
        final int max = curr.length - this._currentSize;
        if (max >= len) {
            str.getChars(offset, offset + len, curr, this._currentSize);
            this._currentSize += len;
            return;
        }
        if (max > 0) {
            str.getChars(offset, offset + max, curr, this._currentSize);
            len -= max;
            offset += max;
        }
        do {
            this.expand(len);
            final int amount = Math.min(this._currentSegment.length, len);
            str.getChars(offset, offset + amount, this._currentSegment, 0);
            this._currentSize += amount;
            offset += amount;
            len -= amount;
        } while (len > 0);
    }
    
    public char[] getCurrentSegment() {
        if (this._inputStart >= 0) {
            this.unshare(1);
        }
        else {
            final char[] curr = this._currentSegment;
            if (curr == null) {
                this._currentSegment = this.buf(0);
            }
            else if (this._currentSize >= curr.length) {
                this.expand(1);
            }
        }
        return this._currentSegment;
    }
    
    public char[] emptyAndGetCurrentSegment() {
        this._inputStart = -1;
        this._currentSize = 0;
        this._inputLen = 0;
        this._inputBuffer = null;
        this._resultString = null;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        }
        char[] curr = this._currentSegment;
        if (curr == null) {
            curr = (this._currentSegment = this.buf(0));
        }
        return curr;
    }
    
    public int getCurrentSegmentSize() {
        return this._currentSize;
    }
    
    public void setCurrentLength(final int len) {
        this._currentSize = len;
    }
    
    public char[] finishCurrentSegment() {
        if (this._segments == null) {
            this._segments = new ArrayList<char[]>();
        }
        this._hasSegments = true;
        this._segments.add(this._currentSegment);
        final int oldLen = this._currentSegment.length;
        this._segmentSize += oldLen;
        this._currentSize = 0;
        int newLen = oldLen + (oldLen >> 1);
        if (newLen < 1000) {
            newLen = 1000;
        }
        else if (newLen > 262144) {
            newLen = 262144;
        }
        final char[] curr = this.carr(newLen);
        return this._currentSegment = curr;
    }
    
    public char[] expandCurrentSegment() {
        final char[] curr = this._currentSegment;
        final int len = curr.length;
        int newLen = len + (len >> 1);
        if (newLen > 262144) {
            newLen = len + (len >> 2);
        }
        return this._currentSegment = Arrays.copyOf(curr, newLen);
    }
    
    public char[] expandCurrentSegment(final int minSize) {
        char[] curr = this._currentSegment;
        if (curr.length >= minSize) {
            return curr;
        }
        curr = (this._currentSegment = Arrays.copyOf(curr, minSize));
        return curr;
    }
    
    @Override
    public String toString() {
        return this.contentsAsString();
    }
    
    private void unshare(final int needExtra) {
        final int sharedLen = this._inputLen;
        this._inputLen = 0;
        final char[] inputBuf = this._inputBuffer;
        this._inputBuffer = null;
        final int start = this._inputStart;
        this._inputStart = -1;
        final int needed = sharedLen + needExtra;
        if (this._currentSegment == null || needed > this._currentSegment.length) {
            this._currentSegment = this.buf(needed);
        }
        if (sharedLen > 0) {
            System.arraycopy(inputBuf, start, this._currentSegment, 0, sharedLen);
        }
        this._segmentSize = 0;
        this._currentSize = sharedLen;
    }
    
    private void expand(final int minNewSegmentSize) {
        if (this._segments == null) {
            this._segments = new ArrayList<char[]>();
        }
        final char[] curr = this._currentSegment;
        this._hasSegments = true;
        this._segments.add(curr);
        this._segmentSize += curr.length;
        this._currentSize = 0;
        final int oldLen = curr.length;
        int newLen = oldLen + (oldLen >> 1);
        if (newLen < 1000) {
            newLen = 1000;
        }
        else if (newLen > 262144) {
            newLen = 262144;
        }
        this._currentSegment = this.carr(newLen);
    }
    
    private char[] resultArray() {
        if (this._resultString != null) {
            return this._resultString.toCharArray();
        }
        if (this._inputStart >= 0) {
            final int len = this._inputLen;
            if (len < 1) {
                return TextBuffer.NO_CHARS;
            }
            final int start = this._inputStart;
            if (start == 0) {
                return Arrays.copyOf(this._inputBuffer, len);
            }
            return Arrays.copyOfRange(this._inputBuffer, start, start + len);
        }
        else {
            final int size = this.size();
            if (size < 1) {
                return TextBuffer.NO_CHARS;
            }
            int offset = 0;
            final char[] result = this.carr(size);
            if (this._segments != null) {
                for (int i = 0, len2 = this._segments.size(); i < len2; ++i) {
                    final char[] curr = this._segments.get(i);
                    final int currLen = curr.length;
                    System.arraycopy(curr, 0, result, offset, currLen);
                    offset += currLen;
                }
            }
            System.arraycopy(this._currentSegment, 0, result, offset, this._currentSize);
            return result;
        }
    }
    
    private char[] carr(final int len) {
        return new char[len];
    }
    
    static {
        NO_CHARS = new char[0];
    }
}
