// 
// Decompiled by Procyon v0.5.30
// 

package com.esotericsoftware.kryo.util;

public class IdentityObjectIntMap<K>
{
    private static final int PRIME1 = -1105259343;
    private static final int PRIME2 = -1262997959;
    private static final int PRIME3 = -825114047;
    public int size;
    K[] keyTable;
    int[] valueTable;
    int capacity;
    int stashSize;
    private float loadFactor;
    private int hashShift;
    private int mask;
    private int threshold;
    private int stashCapacity;
    private int pushIterations;
    
    public IdentityObjectIntMap() {
        this(32, 0.8f);
    }
    
    public IdentityObjectIntMap(final int initialCapacity) {
        this(initialCapacity, 0.8f);
    }
    
    public IdentityObjectIntMap(final int initialCapacity, final float loadFactor) {
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
        this.keyTable = new Object[this.capacity + this.stashCapacity];
        this.valueTable = new int[this.keyTable.length];
    }
    
    public void put(final K key, final int value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null.");
        }
        final K[] keyTable = (K[])this.keyTable;
        final int hashCode = System.identityHashCode(key);
        final int index1 = hashCode & this.mask;
        final K key2 = keyTable[index1];
        if (key == key2) {
            this.valueTable[index1] = value;
            return;
        }
        final int index2 = this.hash2(hashCode);
        final K key3 = keyTable[index2];
        if (key == key3) {
            this.valueTable[index2] = value;
            return;
        }
        final int index3 = this.hash3(hashCode);
        final K key4 = keyTable[index3];
        if (key == key4) {
            this.valueTable[index3] = value;
            return;
        }
        for (int i = this.capacity, n = i + this.stashSize; i < n; ++i) {
            if (keyTable[i] == key) {
                this.valueTable[i] = value;
                return;
            }
        }
        if (key2 == null) {
            keyTable[index1] = key;
            this.valueTable[index1] = value;
            if (this.size++ >= this.threshold) {
                this.resize(this.capacity << 1);
            }
            return;
        }
        if (key3 == null) {
            keyTable[index2] = key;
            this.valueTable[index2] = value;
            if (this.size++ >= this.threshold) {
                this.resize(this.capacity << 1);
            }
            return;
        }
        if (key4 == null) {
            keyTable[index3] = key;
            this.valueTable[index3] = value;
            if (this.size++ >= this.threshold) {
                this.resize(this.capacity << 1);
            }
            return;
        }
        this.push(key, value, index1, key2, index2, key3, index3, key4);
    }
    
    private void putResize(final K key, final int value) {
        final int hashCode = System.identityHashCode(key);
        final int index1 = hashCode & this.mask;
        final K key2 = (K)this.keyTable[index1];
        if (key2 == null) {
            this.keyTable[index1] = key;
            this.valueTable[index1] = value;
            if (this.size++ >= this.threshold) {
                this.resize(this.capacity << 1);
            }
            return;
        }
        final int index2 = this.hash2(hashCode);
        final K key3 = (K)this.keyTable[index2];
        if (key3 == null) {
            this.keyTable[index2] = key;
            this.valueTable[index2] = value;
            if (this.size++ >= this.threshold) {
                this.resize(this.capacity << 1);
            }
            return;
        }
        final int index3 = this.hash3(hashCode);
        final K key4 = (K)this.keyTable[index3];
        if (key4 == null) {
            this.keyTable[index3] = key;
            this.valueTable[index3] = value;
            if (this.size++ >= this.threshold) {
                this.resize(this.capacity << 1);
            }
            return;
        }
        this.push(key, value, index1, key2, index2, key3, index3, key4);
    }
    
    private void push(K insertKey, int insertValue, int index1, K key1, int index2, K key2, int index3, K key3) {
        final K[] keyTable = (K[])this.keyTable;
        final int[] valueTable = this.valueTable;
        final int mask = this.mask;
        int i = 0;
        final int pushIterations = this.pushIterations;
        while (true) {
            K evictedKey = null;
            int evictedValue = 0;
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
            final int hashCode = System.identityHashCode(evictedKey);
            index1 = (hashCode & mask);
            key1 = keyTable[index1];
            if (key1 == null) {
                keyTable[index1] = evictedKey;
                valueTable[index1] = evictedValue;
                if (this.size++ >= this.threshold) {
                    this.resize(this.capacity << 1);
                }
                return;
            }
            index2 = this.hash2(hashCode);
            key2 = keyTable[index2];
            if (key2 == null) {
                keyTable[index2] = evictedKey;
                valueTable[index2] = evictedValue;
                if (this.size++ >= this.threshold) {
                    this.resize(this.capacity << 1);
                }
                return;
            }
            index3 = this.hash3(hashCode);
            key3 = keyTable[index3];
            if (key3 == null) {
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
    
    private void putStash(final K key, final int value) {
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
    
    public int get(final K key, final int defaultValue) {
        final int hashCode = System.identityHashCode(key);
        int index = hashCode & this.mask;
        if (key != this.keyTable[index]) {
            index = this.hash2(hashCode);
            if (key != this.keyTable[index]) {
                index = this.hash3(hashCode);
                if (key != this.keyTable[index]) {
                    return this.getStash(key, defaultValue);
                }
            }
        }
        return this.valueTable[index];
    }
    
    private int getStash(final K key, final int defaultValue) {
        final K[] keyTable = (K[])this.keyTable;
        for (int i = this.capacity, n = i + this.stashSize; i < n; ++i) {
            if (key == keyTable[i]) {
                return this.valueTable[i];
            }
        }
        return defaultValue;
    }
    
    public int getAndIncrement(final K key, final int defaultValue, final int increment) {
        final int hashCode = System.identityHashCode(key);
        int index = hashCode & this.mask;
        if (key != this.keyTable[index]) {
            index = this.hash2(hashCode);
            if (key != this.keyTable[index]) {
                index = this.hash3(hashCode);
                if (key != this.keyTable[index]) {
                    return this.getAndIncrementStash(key, defaultValue, increment);
                }
            }
        }
        final int value = this.valueTable[index];
        this.valueTable[index] = value + increment;
        return value;
    }
    
    private int getAndIncrementStash(final K key, final int defaultValue, final int increment) {
        final K[] keyTable = (K[])this.keyTable;
        for (int i = this.capacity, n = i + this.stashSize; i < n; ++i) {
            if (key == keyTable[i]) {
                final int value = this.valueTable[i];
                this.valueTable[i] = value + increment;
                return value;
            }
        }
        this.put(key, defaultValue + increment);
        return defaultValue;
    }
    
    public int remove(final K key, final int defaultValue) {
        final int hashCode = System.identityHashCode(key);
        int index = hashCode & this.mask;
        if (key == this.keyTable[index]) {
            this.keyTable[index] = null;
            final int oldValue = this.valueTable[index];
            --this.size;
            return oldValue;
        }
        index = this.hash2(hashCode);
        if (key == this.keyTable[index]) {
            this.keyTable[index] = null;
            final int oldValue = this.valueTable[index];
            --this.size;
            return oldValue;
        }
        index = this.hash3(hashCode);
        if (key == this.keyTable[index]) {
            this.keyTable[index] = null;
            final int oldValue = this.valueTable[index];
            --this.size;
            return oldValue;
        }
        return this.removeStash(key, defaultValue);
    }
    
    int removeStash(final K key, final int defaultValue) {
        final K[] keyTable = (K[])this.keyTable;
        for (int i = this.capacity, n = i + this.stashSize; i < n; ++i) {
            if (key == keyTable[i]) {
                final int oldValue = this.valueTable[i];
                this.removeStashIndex(i);
                --this.size;
                return oldValue;
            }
        }
        return defaultValue;
    }
    
    void removeStashIndex(final int index) {
        --this.stashSize;
        final int lastIndex = this.capacity + this.stashSize;
        if (index < lastIndex) {
            this.keyTable[index] = this.keyTable[lastIndex];
            this.valueTable[index] = this.valueTable[lastIndex];
        }
    }
    
    public void clear() {
        final K[] keyTable = (K[])this.keyTable;
        final int[] valueTable = this.valueTable;
        int i = this.capacity + this.stashSize;
        while (i-- > 0) {
            keyTable[i] = null;
        }
        this.size = 0;
        this.stashSize = 0;
    }
    
    public boolean containsValue(final int value) {
        final int[] valueTable = this.valueTable;
        int i = this.capacity + this.stashSize;
        while (i-- > 0) {
            if (valueTable[i] == value) {
                return true;
            }
        }
        return false;
    }
    
    public boolean containsKey(final K key) {
        final int hashCode = System.identityHashCode(key);
        int index = hashCode & this.mask;
        if (key != this.keyTable[index]) {
            index = this.hash2(hashCode);
            if (key != this.keyTable[index]) {
                index = this.hash3(hashCode);
                if (key != this.keyTable[index]) {
                    return this.containsKeyStash(key);
                }
            }
        }
        return true;
    }
    
    private boolean containsKeyStash(final K key) {
        final K[] keyTable = (K[])this.keyTable;
        for (int i = this.capacity, n = i + this.stashSize; i < n; ++i) {
            if (key == keyTable[i]) {
                return true;
            }
        }
        return false;
    }
    
    public K findKey(final int value) {
        final int[] valueTable = this.valueTable;
        int i = this.capacity + this.stashSize;
        while (i-- > 0) {
            if (valueTable[i] == value) {
                return (K)this.keyTable[i];
            }
        }
        return null;
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
        final K[] oldKeyTable = (K[])this.keyTable;
        final int[] oldValueTable = this.valueTable;
        this.keyTable = new Object[newSize + this.stashCapacity];
        this.valueTable = new int[newSize + this.stashCapacity];
        this.size = 0;
        this.stashSize = 0;
        for (int i = 0; i < oldEndIndex; ++i) {
            final K key = oldKeyTable[i];
            if (key != null) {
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
            return "{}";
        }
        final StringBuilder buffer = new StringBuilder(32);
        buffer.append('{');
        final K[] keyTable = (K[])this.keyTable;
        final int[] valueTable = this.valueTable;
        int i = keyTable.length;
        while (i-- > 0) {
            final K key = keyTable[i];
            if (key == null) {
                continue;
            }
            buffer.append(key);
            buffer.append('=');
            buffer.append(valueTable[i]);
            break;
        }
        while (i-- > 0) {
            final K key = keyTable[i];
            if (key == null) {
                continue;
            }
            buffer.append(", ");
            buffer.append(key);
            buffer.append('=');
            buffer.append(valueTable[i]);
        }
        buffer.append('}');
        return buffer.toString();
    }
}
