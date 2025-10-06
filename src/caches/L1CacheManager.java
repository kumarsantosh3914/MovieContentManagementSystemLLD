package caches;

import models.Movie;
import models.SearchCriteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class L1CacheManager {
    private static final int MAX_ENTRIES_PER_USER = 5;
    private final Map<Integer, Cache<SearchCriteria, List<Movie>>> userCaches;
    private int hits = 0;

    public L1CacheManager() {
        // this is not thread safe for simplicity, if needed can be improved with ConcurrentHashMap
        this.userCaches = new HashMap<>();
    }

    public void put(int userId, SearchCriteria criteria, List<Movie> movies) {
        Cache<SearchCriteria, List<Movie>> userCache = userCaches.computeIfAbsent(userId, k -> new LRUCache<>(MAX_ENTRIES_PER_USER));
        userCache.put(criteria, movies);
    }

    public List<Movie> get(int userId, SearchCriteria criteria) {
        Cache<SearchCriteria, List<Movie>> userCache = userCaches.get(userId);
        if (userCache != null) {
            List<Movie> result = userCache.get(criteria);
            if (result != null) {
                hits++;
                return new ArrayList<>(result);
            }
        }

        return null;
    }

    /**
     * Clears all user caches.
     * This method first clears each individual user's cache,
     * then removes all user cache entries from the userCaches map.
     */
    public void clear() {
        userCaches.values().forEach(Cache::clear);
        userCaches.clear();
    }

    /**
     * Clears the cache for a specific user.
     * Removes all cached search results for the given userId.
     * If the user cache exists, it is cleared and removed from the userCaches map.
     */
    public void clearUserCache(int userId) {
        Cache<SearchCriteria, List<Movie>> userCache = userCaches.get(userId);
        if(userCache != null) {
            userCache.clear();
            userCaches.remove(userId);
        }
    }

    public int getHits() {
        return hits;
    }

    public void resetHits() {
        hits = 0;
    }
}
