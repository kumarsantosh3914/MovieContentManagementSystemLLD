package services;

import caches.L1CacheManager;
import caches.L2CacheManager;
import models.*;
import utils.GenreUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchService {
    private final MovieService movieService;
    private final UserService userService;
    private final L1CacheManager l1Cache;
    private final L2CacheManager l2Cache;
    private int primaryStoreHits = 0;
    private int totalSearchesRequest = 0;

    public SearchService(MovieService movieService, UserService userService, L1CacheManager l1Cache, L2CacheManager l2Cache) {
        this.movieService = movieService;
        this.userService  = userService;
        this.l1Cache = l1Cache;
        this.l2Cache = l2Cache;
    }

    public SearchResult search(int userId, SearchCriteria criteria) {
        if(!userService.userExists(userId)) {
            throw new IllegalArgumentException("User does not exist");
        }

        totalSearchesRequest++;

        // Check L1 Cache
        List<Movie> l1Result = l1Cache.get(userId, criteria);
        if(l1Result != null) {
            return new SearchResult(l1Result, CacheLevel.L1);
        }

        // Check L2 Cache
        List<Movie> l2Result = l2Cache.get(criteria);
        if(l2Result != null) {
            // Update L1 cache on L2 hit
            l1Cache.put(userId, criteria, l2Result);
            return new SearchResult(l2Result, CacheLevel.L2);
        }

        // Search in primary store
        List<Movie> primaryResult = searchPrimaryStore(criteria);
        primaryStoreHits++;

        // Update both caches
        l1Cache.put(userId, criteria, primaryResult);
        l2Cache.put(criteria, primaryResult);

        return new SearchResult(primaryResult, CacheLevel.PRIMARY_STORE);
    }

    private List<Movie> searchPrimaryStore(SearchCriteria criteria) {
        if(criteria.isMultiFilter()) {
            return movieService.searchMultiFilter(
                    criteria.getGenre(),
                    criteria.getYear(),
                    criteria.getMinRating()
            );
        }

        switch (criteria.getType()) {
            case GENRE:
                return movieService.searchByGenre(GenreUtils.parseGenreType(criteria.getValue()));
            case YEAR:
                return movieService.searchByYear(Integer.parseInt(criteria.getValue()));
            case RATING:
                return movieService.searchByRating(Float.parseFloat(criteria.getValue()));
            case TITLE:
                return movieService.searchByTitle(criteria.getValue());
            default:
                return new ArrayList<>();
        }
    }

    public void clearCache(String level) {
        switch (level.toUpperCase()) {
            case "L1":
                l1Cache.clear();
                break;
            case "L2":
                l2Cache.clear();
                break;
            case "ALL":
                l1Cache.clear();
                l2Cache.clear();
                break;
            default:
                throw new IllegalArgumentException("Invalid cache level. Use L1, L2, or ALL.");
        }
    }

    public void printCacheStats() {
        System.out.println("Total Searches: " + totalSearchesRequest);
        System.out.println("Primary Store Hits: " + primaryStoreHits);
        System.out.println("L1 Cache Hits: " + l1Cache.getHits());
        System.out.println("L2 Cache Hits: " + l2Cache.getHits());
    }

    public void resetCacheStats() {
        totalSearchesRequest = 0;
        primaryStoreHits = 0;
        l1Cache.resetHits();
        l2Cache.resetHits();
    }

}
