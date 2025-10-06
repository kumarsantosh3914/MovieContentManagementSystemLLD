package caches;

import models.Movie;
import models.SearchCriteria;

import java.util.ArrayList;
import java.util.List;

public class L2CacheManager {
    private static final int MAX_ENTRIES = 20;
    private final Cache<SearchCriteria, List<Movie>> cache;
    private int hits = 0;

    public L2CacheManager() {
        this.cache = new LFUCache<>(MAX_ENTRIES);
    }

    public void put(SearchCriteria criteria, List<Movie> movies) {
        cache.put(criteria, movies);
    }

    public List<Movie> get(SearchCriteria criteria) {
        List<Movie> result = cache.get(criteria);
        if(result != null) {
            hits++;
            return new ArrayList<>(result);
        }

        return null;
    }

    public  void clear() {
        cache.clear();
    }

    public int getHits() {
        return hits;
    }

    public void resetHits() {
        hits = 0;
    }
}
