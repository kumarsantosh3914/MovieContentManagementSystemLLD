package models;

import java.util.Objects;

public class Movie {
    private final Integer movieId;
    private final String title;
    private final String genre;
    private final Integer releaseYear;
    private float rating;


    public Movie(Integer movieId, String title, String genre, Integer releaseYear, float rating) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }

    // getters
    public Integer getMovieId() {
        return movieId;
    }

    public float getRating() {
        return rating;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    // setters
    public void setRating(float rating) {
        this.rating = rating;
    }

    public void printMovie() {
        System.out.printf("Movie ID: %d, Title: %s, Genre: %s, Release Year: %d, Rating: %.1f\n",
                          movieId, title, genre, releaseYear, rating);
    }

    // Override equals and hashCode for proper comparison in collections
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Movie movie = (Movie) obj;
        return movieId.equals(movie.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId);
    }
}
