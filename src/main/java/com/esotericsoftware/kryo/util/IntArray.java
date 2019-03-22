// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.util;

import java.util.Arrays;

public class IntArray
{
    public int[] items;
    public int size;
    public boolean ordered;
    
    public IntArray() {
        this(true, 16);
    }
    
    public IntArray(final int capacity) {
        this(true, capacity);
    }
    
    public IntArray(final boolean ordered, final int capacity) {
        this.ordered = ordered;
        this.items = new int[capacity];
    }
    
    public IntArray(final IntArray array) {
        this.ordered = array.ordered;
        this.size = array.size;
        this.items = new int[this.size];
        System.arraycopy(array.items, 0, this.items, 0, this.size);
    }
    
    public IntArray(final int[] array) {
        this(true, array);
    }
    
    public IntArray(final boolean ordered, final int[] array) {
        this(ordered, array.length);
        this.size = array.length;
        System.arraycopy(array, 0, this.items, 0, this.size);
    }
    
    public void add(final int value) {
        int[] items = this.items;
        if (this.size == items.length) {
            items = this.resize(Math.max(8, (int)(this.size * 1.75f)));
        }
        items[this.size++] = value;
    }
    
    public void addAll(final IntArray array) {
        this.addAll(array, 0, array.size);
    }
    
    public void addAll(final IntArray array, final int offset, final int length) {
        if (offset + length > array.size) {
            throw new IllegalArgumentException("offset + length must be <= size: " + offset + " + " + length + " <= " + array.size);
        }
        this.addAll(array.items, offset, length);
    }
    
    public void addAll(final int[] array) {
        this.addAll(array, 0, array.length);
    }
    
    public void addAll(final int[] array, final int offset, final int length) {
        int[] items = this.items;
        final int sizeNeeded = this.size + length - offset;
        if (sizeNeeded >= items.length) {
            items = this.resize(Math.max(8, (int)(sizeNeeded * 1.75f)));
        }
        System.arraycopy(array, offset, items, this.size, length);
        this.size += length;
    }
    
    public int get(final int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        return this.items[index];
    }
    
    public void set(final int index, final int value) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        this.items[index] = value;
    }
    
    public void insert(final int index, final int value) {
        int[] items = this.items;
        if (this.size == items.length) {
            items = this.resize(Math.max(8, (int)(this.size * 1.75f)));
        }
        if (this.ordered) {
            System.arraycopy(items, index, items, index + 1, this.size - index);
        }
        else {
            items[this.size] = items[index];
        }
        ++this.size;
        items[index] = value;
    }
    
    public void swap(final int first, final int second) {
        if (first >= this.size) {
            throw new IndexOutOfBoundsException(String.valueOf(first));
        }
        if (second >= this.size) {
            throw new IndexOutOfBoundsException(String.valueOf(second));
        }
        final int[] items = this.items;
        final int firstValue = items[first];
        items[first] = items[second];
        items[second] = firstValue;
    }
    
    public boolean contains(final int value) {
        int i = this.size - 1;
        final int[] items = this.items;
        while (i >= 0) {
            if (items[i--] == value) {
                return true;
            }
        }
        return false;
    }
    
    public int indexOf(final int value) {
        final int[] items = this.items;
        for (int i = 0, n = this.size; i < n; ++i) {
            if (items[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean removeValue(final int value) {
        final int[] items = this.items;
        for (int i = 0, n = this.size; i < n; ++i) {
            if (items[i] == value) {
                this.removeIndex(i);
                return true;
            }
        }
        return false;
    }
    
    public int removeIndex(final int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        final int[] items = this.items;
        final int value = items[index];
        --this.size;
        if (this.ordered) {
            System.arraycopy(items, index + 1, items, index, this.size - index);
        }
        else {
            items[index] = items[this.size];
        }
        return value;
    }
    
    public int pop() {
        final int[] items = this.items;
        final int size = this.size - 1;
        this.size = size;
        return items[size];
    }
    
    public int peek() {
        return this.items[this.size - 1];
    }
    
    public void clear() {
        this.size = 0;
    }
    
    public void shrink() {
        this.resize(this.size);
    }
    
    public int[] ensureCapacity(final int additionalCapacity) {
        final int sizeNeeded = this.size + additionalCapacity;
        if (sizeNeeded >= this.items.length) {
            this.resize(Math.max(8, sizeNeeded));
        }
        return this.items;
    }
    
    protected int[] resize(final int newSize) {
        final int[] newItems = new int[newSize];
        final int[] items = this.items;
        System.arraycopy(items, 0, newItems, 0, Math.min(items.length, newItems.length));
        return this.items = newItems;
    }
    
    public void sort() {
        Arrays.sort(this.items, 0, this.size);
    }
    
    public void reverse() {
        int i = 0;
        final int lastIndex = this.size - 1;
        for (int n = this.size / 2; i < n; ++i) {
            final int ii = lastIndex - i;
            final int temp = this.items[i];
            this.items[i] = this.items[ii];
            this.items[ii] = temp;
        }
    }
    
    public void truncate(final int newSize) {
        if (this.size > newSize) {
            this.size = newSize;
        }
    }
    
    public int[] toArray() {
        final int[] array = new int[this.size];
        System.arraycopy(this.items, 0, array, 0, this.size);
        return array;
    }
    
    public String toString() {
        if (this.size == 0) {
            return "[]";
        }
        final int[] items = this.items;
        final StringBuilder buffer = new StringBuilder(32);
        buffer.append('[');
        buffer.append(items[0]);
        for (int i = 1; i < this.size; ++i) {
            buffer.append(", ");
            buffer.append(items[i]);
        }
        buffer.append(']');
        return buffer.toString();
    }
    
    public String toString(final String separator) {
        if (this.size == 0) {
            return "";
        }
        final int[] items = this.items;
        final StringBuilder buffer = new StringBuilder(32);
        buffer.append(items[0]);
        for (int i = 1; i < this.size; ++i) {
            buffer.append(separator);
            buffer.append(items[i]);
        }
        return buffer.toString();
    }
}
