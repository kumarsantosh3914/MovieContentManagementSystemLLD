package caches;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;

public class LFUCache<K, V> implements Cache<K, V> {
    private final int capacity;
    private final Map<K, V> cache;
    private final Map<K, Integer> frequencies;
    private final TreeMap<Integer, LinkedHashSet<K>> frequencyMap;
    private int minFrequency;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.frequencies = new HashMap<>();
        this.frequencyMap = new TreeMap<>();
        this.minFrequency = 0;
    }

    @Override
    public void put(K key, V value) {
        if(capacity <= 0) return;

        if(cache.containsKey(key)) {
            cache.put(key, value);
            updateFrequency(key);
            return;
        }

        if(cache.size() >= capacity) {
            evictLFU();
        }

        cache.put(key, value);
        frequencies.put(key, 1);
        frequencyMap.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
        minFrequency = 1;
    }

    @Override
    public V get(K key) {
        if(!cache.containsKey(key)) {
            return null;
        }

        updateFrequency(key);
        return cache.get(key);
    }

    @Override
    public void clear() {
        cache.clear();
        frequencies.clear();
        frequencyMap.clear();
        minFrequency = 0;
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public Map<K, V> getAllEntries() {
        return new HashMap<>(cache);
    }

    // Updates the frequency count of a key and reorganizes the frequency map accordingly.
    private void updateFrequency(K key) {
        int freq = frequencies.get(key);
        frequencies.put(key, freq + 1);

        LinkedHashSet<K> keys = frequencyMap.get(freq);
        keys.remove(key);

        if(keys.isEmpty()) {
            frequencyMap.remove(freq);
            if(minFrequency == freq) {
                minFrequency++;
            }
        }

        frequencyMap.computeIfAbsent(freq + 1, k -> new LinkedHashSet<>()).add(key);
    }

    // Evicts the least frequently used (LFU) key from the cache.
    private void evictLFU() {
        Map.Entry<Integer, LinkedHashSet<K>> entry = frequencyMap.firstEntry();

        if(entry != null) {
            LinkedHashSet<K> keys = entry.getValue();
            K keyToRemove = keys.iterator().next();
            keys.remove(keyToRemove);

            if(keys.isEmpty()) {
                frequencyMap.remove(entry.getKey());
            }
            cache.remove(keyToRemove);
            frequencies.remove(keyToRemove);
        }
    }
}
