package models;

import java.util.Objects;

public class SearchCriteria {
    private final SearchType type;
    private final String value;
    private final GenreType genre;
    private final Integer year;
    private final Float minRating;

    // let the search type is genre, value should be action, comedy, etc.
    public SearchCriteria(SearchType type, String value) {
        this.type = type;
        this.value = value;
        this.genre = null;
        this.year = null;
        this.minRating = null;
    }

    // Multi filter constructor
    public SearchCriteria(GenreType genre, Integer year, Float minRating) {
        this.type = null;
        this.value = null;
        this.genre = genre;
        this.year = year;
        this.minRating = minRating;
    }

    // Returns true if this SearchCriteria was created using the multi-filter constructor
    public boolean isMultiFilter() {
        return type == null;
    }

    public SearchType getType() {
        return type;
    }

    public Float getMinRating() {
        return minRating;
    }

    public Integer getYear() {
        return year;
    }

    public GenreType getGenre() {
        return genre;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (isMultiFilter()) {
            return String.format("MULTI[genre=%s, year=%d, minRating=%.1f]",
                    genre, year, minRating);
        }
        return String.format("%s:%s", type, value);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        SearchCriteria that = (SearchCriteria) obj;

        if(isMultiFilter() && that.isMultiFilter()) return false;

        if(isMultiFilter()) {
            return Objects.equals(genre, that.genre) &&
                   Objects.equals(year, that.year) &&
                   Objects.equals(minRating, that.minRating);
        }

        return type == that.type && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        if(isMultiFilter()) {
            return Objects.hash(genre, year, minRating);
        }
        return Objects.hash(type, value);
    }
}
