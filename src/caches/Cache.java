package caches;

import java.util.Map;

public interface Cache<K, V> {
    void put(K Key, V value);
    V get(K key);
    void clear();
    int size();
    Map<K, V> getAllEntries();
}
