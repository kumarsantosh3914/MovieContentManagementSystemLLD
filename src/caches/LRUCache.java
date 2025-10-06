package caches;

import java.util.LinkedHashMap;

public class LRUCache<K, V> implements  Cache<K, V> {
    private final int capacity;
    private final LinkedHashMap<K, V> cache;

    // Constructs an LRU cache with the specified capacity.
    // Uses a LinkedHashMap with access order to automatically remove the eldest
    // entry when the cache exceeds its capacity.
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<K, V>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
                return size() > LRUCache.this.capacity;
            }
        };
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public java.util.Map<K, V> getAllEntries() {
        return new LinkedHashMap<>(cache);
    }
}
