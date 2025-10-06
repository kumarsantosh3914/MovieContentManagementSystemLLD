package models;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    private final List<Movie> movies;
    private final CacheLevel foundIn;

    public SearchResult(List<Movie> movies, CacheLevel foundIn) {
        this.movies = new ArrayList<>(movies);
        this.foundIn = foundIn;
    }

    public List<Movie> getMovies() {
        return new ArrayList<>(movies);
    }

    public CacheLevel getFoundIn() {
        return foundIn;
    }
}

