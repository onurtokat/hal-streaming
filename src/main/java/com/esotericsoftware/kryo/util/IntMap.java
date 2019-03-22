// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.util;

public class IntMap<V>
{
    private static final int PRIME1 = -1105259343;
    private static final int PRIME2 = -1262997959;
    private static final int PRIME3 = -825114047;
    private static final int EMPTY = 0;
    public int size;
    int[] keyTable;
    V[] valueTable;
    int capacity;
    int stashSize;
    V zeroValue;
    boolean hasZeroValue;
    private float loadFactor;
    private int hashShift;
    private int mask;
    private int threshold;
    private int stashCapacity;
    private int pushIterations;
    
    public IntMap() {
        this(32, 0.8f);
    }
    
    public IntMap(final int initialCapacity) {
        this(initialCapacity, 0.8f);
    }
    
    public IntMap(final int initialCapacity, final float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("initialCapacity must be >= 0: " + initialCapacity);
        }
        if (this.capacity > 1073741824) {
            throw new IllegalArgumentException("initialCapacity is too large: " + initialCapacity);
        }
        this.capacity = ObjectMap.nextPowerOfTwo(initialCapacity);
        if (loadFactor <= 0.0f) {
            throw new IllegalArgumentException("loadFactor must be > 0: " + loadFactor);
        }
        this.loadFactor = loadFactor;
        this.threshold = (int)(this.capacity * loadFactor);
        this.mask = this.capacity - 1;
        this.hashShift = 31 - Integer.numberOfTrailingZeros(this.capacity);
        this.stashCapacity = Math.max(3, (int)Math.ceil(Math.log(this.capacity)) * 2);
        this.pushIterations = Math.max(Math.min(this.capacity, 8), (int)Math.sqrt(this.capacity) / 8);
        this.keyTable = new int[this.capacity + this.stashCapacity];
        this.valueTable = new Object[this.keyTable.length];
    }
    
    public V put(final int key, final V value) {
        if (key == 0) {
            final V oldValue = this.zeroValue;
            this.zeroValue = value;
            this.hasZeroValue = true;
            ++this.size;
            return oldValue;
        }
        final int[] keyTable = this.keyTable;
        final int index1 = key & this.mask;
        final int key2 = keyTable[index1];
        if (key2 == key) {
            final V oldValue2 = (V)this.valueTable[index1];
            this.valueTable[index1] = value;
            return oldValue2;
        }
        final int index2 = this.hash2(key);
        final int key3 = keyTable[index2];
        if (key3 == key) {
            final V oldValue3 = (V)this.valueTable[index2];
            this.valueTable[index2] = value;
            return oldValue3;
        }
        final int index3 = this.hash3(key);
        final int key4 = keyTable[index3];
        if (key4 == key) {
            final V oldValue4 = (V)this.valueTable[index3];
            this.valueTable[index3] = value;
            return oldValue4;
        }
        for (int i = this.capacity, n = i + this.stashSize; i < n; ++i) {
            if (key == keyTable[i]) {
                final V oldValue5 = (V)this.valueTable[i];
                this.valueTable[i] = value;
                return oldValue5;
            }
        }
        if (key2 == 0) {
            keyTable[index1] = key;
            this.valueTable[index1] = value;
            if (this.size++ >= this.threshold) {
                this.resize(this.capacity << 1);
            }
            return null;
        }
        if (key3 == 0) {
            keyTable[index2] = key;
            this.valueTable[index2] = value;
            if (this.size++ >= this.threshold) {
                this.resize(this.capacity << 1);
            }
            return null;
        }
        if (key4 == 0) {
            keyTable[index3] = key;
            this.valueTable[index3] = value;
            if (this.size++ >= this.threshold) {
                this.resize(this.capacity << 1);
            }
            return null;
        }
        this.push(key, value, index1, key2, index2, key3, index3, key4);
        return null;
    }
    
    private void putResize(final int key, final V value) {
        if (key == 0) {
            this.zeroValue = value;
            this.hasZeroValue = true;
            return;
        }
        final int index1 = key & this.mask;
        final int key2 = this.keyTable[index1];
        if (key2 == 0) {
            this.keyTable[index1] = key;
            this.valueTable[index1] = value;
            if (this.size++ >= this.threshold) {
                this.resize(this.capacity << 1);
            }
            return;
        }
        final int index2 = this.hash2(key);
        final int key3 = this.keyTable[index2];
        if (key3 == 0) {
            this.keyTable[index2] = key;
            this.valueTable[index2] = value;
            if (this.size++ >= this.threshold) {
                this.resize(this.capacity << 1);
            }
            return;
        }
        final int index3 = this.hash3(key);
        final int key4 = this.keyTable[index3];
        if (key4 == 0) {
            this.keyTable[index3] = key;
            this.valueTable[index3] = value;
            if (this.size++ >= this.threshold) {
                this.resize(this.capacity << 1);
            }
            return;
        }
        this.push(key, value, index1, key2, index2, key3, index3, key4);
    }
    
    private void push(int insertKey, V insertValue, int index1, int key1, int index2, int key2, int index3, int key3) {
        final int[] keyTable = this.keyTable;
        final V[] valueTable = (V[])this.valueTable;
        final int mask = this.mask;
        int i = 0;
        final int pushIterations = this.pushIterations;
        while (true) {
            int evictedKey = 0;
            V evictedValue = null;
            switch (ObjectMap.random.nextInt(3)) {
                case 0: {
                    evictedKey = key1;
                    evictedValue = valueTable[index1];
                    keyTable[index1] = insertKey;
                    valueTable[index1] = insertValue;
                    break;
                }
                case 1: {
                    evictedKey = key2;
                    evictedValue = valueTable[index2];
                    keyTable[index2] = insertKey;
                    valueTable[index2] = insertValue;
                    break;
                }
                default: {
                    evictedKey = key3;
                    evictedValue = valueTable[index3];
                    keyTable[index3] = insertKey;
                    valueTable[index3] = insertValue;
                    break;
                }
            }
            index1 = (evictedKey & mask);
            key1 = keyTable[index1];
            if (key1 == 0) {
                keyTable[index1] = evictedKey;
                valueTable[index1] = evictedValue;
                if (this.size++ >= this.threshold) {
                    this.resize(this.capacity << 1);
                }
                return;
            }
            index2 = this.hash2(evictedKey);
            key2 = keyTable[index2];
            if (key2 == 0) {
                keyTable[index2] = evictedKey;
                valueTable[index2] = evictedValue;
                if (this.size++ >= this.threshold) {
                    this.resize(this.capacity << 1);
                }
                return;
            }
            index3 = this.hash3(evictedKey);
            key3 = keyTable[index3];
            if (key3 == 0) {
                keyTable[index3] = evictedKey;
                valueTable[index3] = evictedValue;
                if (this.size++ >= this.threshold) {
                    this.resize(this.capacity << 1);
                }
                return;
            }
            if (++i == pushIterations) {
                this.putStash(evictedKey, evictedValue);
                return;
            }
            insertKey = evictedKey;
            insertValue = evictedValue;
        }
    }
    
    private void putStash(final int key, final V value) {
        if (this.stashSize == this.stashCapacity) {
            this.resize(this.capacity << 1);
            this.put(key, value);
            return;
        }
        final int index = this.capacity + this.stashSize;
        this.keyTable[index] = key;
        this.valueTable[index] = value;
        ++this.stashSize;
        ++this.size;
    }
    
    public V get(final int key) {
        if (key == 0) {
            return this.zeroValue;
        }
        int index = key & this.mask;
        if (this.keyTable[index] != key) {
            index = this.hash2(key);
            if (this.keyTable[index] != key) {
                index = this.hash3(key);
                if (this.keyTable[index] != key) {
                    return this.getStash(key, null);
                }
            }
        }
        return (V)this.valueTable[index];
    }
    
    public V get(final int key, final V defaultValue) {
        if (key == 0) {
            return this.zeroValue;
        }
        int index = key & this.mask;
        if (this.keyTable[index] != key) {
            index = this.hash2(key);
            if (this.keyTable[index] != key) {
                index = this.hash3(key);
                if (this.keyTable[index] != key) {
                    return this.getStash(key, defaultValue);
                }
            }
        }
        return (V)this.valueTable[index];
    }
    
    private V getStash(final int key, final V defaultValue) {
        final int[] keyTable = this.keyTable;
        for (int i = this.capacity, n = i + this.stashSize; i < n; ++i) {
            if (keyTable[i] == key) {
                return (V)this.valueTable[i];
            }
        }
        return defaultValue;
    }
    
    public V remove(final int key) {
        if (key == 0) {
            if (!this.hasZeroValue) {
                return null;
            }
            final V oldValue = this.zeroValue;
            this.zeroValue = null;
            this.hasZeroValue = false;
            --this.size;
            return oldValue;
        }
        else {
            int index = key & this.mask;
            if (this.keyTable[index] == key) {
                this.keyTable[index] = 0;
                final V oldValue2 = (V)this.valueTable[index];
                this.valueTable[index] = null;
                --this.size;
                return oldValue2;
            }
            index = this.hash2(key);
            if (this.keyTable[index] == key) {
                this.keyTable[index] = 0;
                final V oldValue2 = (V)this.valueTable[index];
                this.valueTable[index] = null;
                --this.size;
                return oldValue2;
            }
            index = this.hash3(key);
            if (this.keyTable[index] == key) {
                this.keyTable[index] = 0;
                final V oldValue2 = (V)this.valueTable[index];
                this.valueTable[index] = null;
                --this.size;
                return oldValue2;
            }
            return this.removeStash(key);
        }
    }
    
    V removeStash(final int key) {
        final int[] keyTable = this.keyTable;
        for (int i = this.capacity, n = i + this.stashSize; i < n; ++i) {
            if (keyTable[i] == key) {
                final V oldValue = (V)this.valueTable[i];
                this.removeStashIndex(i);
                --this.size;
                return oldValue;
            }
        }
        return null;
    }
    
    void removeStashIndex(final int index) {
        --this.stashSize;
        final int lastIndex = this.capacity + this.stashSize;
        if (index < lastIndex) {
            this.keyTable[index] = this.keyTable[lastIndex];
            this.valueTable[index] = this.valueTable[lastIndex];
            this.valueTable[lastIndex] = null;
        }
        else {
            this.valueTable[index] = null;
        }
    }
    
    public void clear() {
        final int[] keyTable = this.keyTable;
        final V[] valueTable = (V[])this.valueTable;
        int i = this.capacity + this.stashSize;
        while (i-- > 0) {
            keyTable[i] = 0;
            valueTable[i] = null;
        }
        this.size = 0;
        this.stashSize = 0;
        this.zeroValue = null;
        this.hasZeroValue = false;
    }
    
    public boolean containsValue(final Object value, final boolean identity) {
        final V[] valueTable = (V[])this.valueTable;
        if (value == null) {
            if (this.hasZeroValue && this.zeroValue == null) {
                return true;
            }
            final int[] keyTable = this.keyTable;
            int i = this.capacity + this.stashSize;
            while (i-- > 0) {
                if (keyTable[i] != 0 && valueTable[i] == null) {
                    return true;
                }
            }
        }
        else if (identity) {
            if (value == this.zeroValue) {
                return true;
            }
            int j = this.capacity + this.stashSize;
            while (j-- > 0) {
                if (valueTable[j] == value) {
                    return true;
                }
            }
        }
        else {
            if (this.hasZeroValue && value.equals(this.zeroValue)) {
                return true;
            }
            int j = this.capacity + this.stashSize;
            while (j-- > 0) {
                if (value.equals(valueTable[j])) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean containsKey(final int key) {
        if (key == 0) {
            return this.hasZeroValue;
        }
        int index = key & this.mask;
        if (this.keyTable[index] != key) {
            index = this.hash2(key);
            if (this.keyTable[index] != key) {
                index = this.hash3(key);
                if (this.keyTable[index] != key) {
                    return this.containsKeyStash(key);
                }
            }
        }
        return true;
    }
    
    private boolean containsKeyStash(final int key) {
        final int[] keyTable = this.keyTable;
        for (int i = this.capacity, n = i + this.stashSize; i < n; ++i) {
            if (keyTable[i] == key) {
                return true;
            }
        }
        return false;
    }
    
    public int findKey(final Object value, final boolean identity, final int notFound) {
        final V[] valueTable = (V[])this.valueTable;
        if (value == null) {
            if (this.hasZeroValue && this.zeroValue == null) {
                return 0;
            }
            final int[] keyTable = this.keyTable;
            int i = this.capacity + this.stashSize;
            while (i-- > 0) {
                if (keyTable[i] != 0 && valueTable[i] == null) {
                    return keyTable[i];
                }
            }
        }
        else if (identity) {
            if (value == this.zeroValue) {
                return 0;
            }
            int j = this.capacity + this.stashSize;
            while (j-- > 0) {
                if (valueTable[j] == value) {
                    return this.keyTable[j];
                }
            }
        }
        else {
            if (this.hasZeroValue && value.equals(this.zeroValue)) {
                return 0;
            }
            int j = this.capacity + this.stashSize;
            while (j-- > 0) {
                if (value.equals(valueTable[j])) {
                    return this.keyTable[j];
                }
            }
        }
        return notFound;
    }
    
    public void ensureCapacity(final int additionalCapacity) {
        final int sizeNeeded = this.size + additionalCapacity;
        if (sizeNeeded >= this.threshold) {
            this.resize(ObjectMap.nextPowerOfTwo((int)(sizeNeeded / this.loadFactor)));
        }
    }
    
    private void resize(final int newSize) {
        final int oldEndIndex = this.capacity + this.stashSize;
        this.capacity = newSize;
        this.threshold = (int)(newSize * this.loadFactor);
        this.mask = newSize - 1;
        this.hashShift = 31 - Integer.numberOfTrailingZeros(newSize);
        this.stashCapacity = Math.max(3, (int)Math.ceil(Math.log(newSize)) * 2);
        this.pushIterations = Math.max(Math.min(newSize, 8), (int)Math.sqrt(newSize) / 8);
        final int[] oldKeyTable = this.keyTable;
        final V[] oldValueTable = (V[])this.valueTable;
        this.keyTable = new int[newSize + this.stashCapacity];
        this.valueTable = new Object[newSize + this.stashCapacity];
        this.size = (this.hasZeroValue ? 1 : 0);
        this.stashSize = 0;
        for (int i = 0; i < oldEndIndex; ++i) {
            final int key = oldKeyTable[i];
            if (key != 0) {
                this.putResize(key, oldValueTable[i]);
            }
        }
    }
    
    private int hash2(int h) {
        h *= -1262997959;
        return (h ^ h >>> this.hashShift) & this.mask;
    }
    
    private int hash3(int h) {
        h *= -825114047;
        return (h ^ h >>> this.hashShift) & this.mask;
    }
    
    public String toString() {
        if (this.size == 0) {
            return "[]";
        }
        final StringBuilder buffer = new StringBuilder(32);
        buffer.append('[');
        final int[] keyTable = this.keyTable;
        final V[] valueTable = (V[])this.valueTable;
        int i = keyTable.length;
        while (i-- > 0) {
            final int key = keyTable[i];
            if (key == 0) {
                continue;
            }
            buffer.append(key);
            buffer.append('=');
            buffer.append(valueTable[i]);
            break;
        }
        while (i-- > 0) {
            final int key = keyTable[i];
            if (key == 0) {
                continue;
            }
            buffer.append(", ");
            buffer.append(key);
            buffer.append('=');
            buffer.append(valueTable[i]);
        }
        buffer.append(']');
        return buffer.toString();
    }
}
