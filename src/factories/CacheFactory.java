package factories;

import caches.Cache;
import caches.LFUCache;
import caches.LRUCache;

public class CacheFactory {
    public CacheType cacheType;

    public static <K, V>Cache<K, V> createCache(CacheType type, int capacity) {
        switch (type) {
            case LRU:
                return new LRUCache<>(capacity);
            case LFU:
                return new LFUCache<>(capacity);
            default:
                throw new IllegalArgumentException("Unsupported cache type: " + type);
        }
    }
}
