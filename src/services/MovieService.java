package services;

import factories.SearchStrategyFactory;
import models.GenreType;
import models.Movie;
import models.SearchCriteria;
import strategies.SearchStrategy;
import utils.GenreUtils;

import java.util.*;
import java.util.stream.Collectors;

public class MovieService {
    private final Map<Integer, Movie> movies;

    public MovieService() {
        // this is not a thread-safe implementation for simplicity, but can be improved with ConcurrentHashMap if needed
        this.movies = new HashMap<>();
    }

    public void addMovie(Movie movie) {
        if(movies.containsKey(movie.getMovieId())) {
            throw new IllegalArgumentException("Movie already exists");
        }

        movies.put(movie.getMovieId(), movie);
    }

    public Movie getMovie(int id) {
        return movies.get(id);
    }

    public List<Movie> getMovies() {
        return new ArrayList<>(movies.values());
    }

    public List<Movie> search(SearchCriteria criteria) {
        SearchStrategy strategy = SearchStrategyFactory.getSearchStrategy(criteria);
        return strategy.search(movies.values(), criteria);
    }
}
