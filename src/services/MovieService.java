package services;

import models.GenreType;
import models.Movie;
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

//    public List<Movie> search(SearchStrategy strategy) {
//        return strategy.search(movies.values());
//    }

    public List<Movie> searchByGenre(GenreType genre) {
        return movies.values().stream()
                .filter(movie -> GenreUtils.isGenreMatch(movie.getGenre(), genre))
                .collect(Collectors.toList());
    }

    public List<Movie> searchByYear(int year) {
        return movies.values().stream()
                .filter(m -> m.getReleaseYear() == year)
                .collect(Collectors.toList());
    }

    public List<Movie> searchByTitle(String title) {
        return movies.values().stream()
                .filter(m -> m.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Movie> searchByRating(float minRating) {
        return movies.values().stream()
                .filter(m -> m.getRating() >= minRating)
                .collect(Collectors.toList());
    }

    public List<Movie> searchMultiFilter(GenreType genre, Integer year, Float minRating) {
        return movies.values().stream()
                .filter(m -> genre == null || GenreUtils.isGenreMatch(m.getGenre(), genre))
                .filter(m -> year == null || m.getReleaseYear().equals(year))
                .filter(m -> minRating == null || m.getRating() >= minRating)
                .collect(Collectors.toList());
    }
}
