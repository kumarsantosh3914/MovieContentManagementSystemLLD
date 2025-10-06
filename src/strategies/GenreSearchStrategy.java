package strategies;

import models.GenreType;
import models.Movie;
import models.SearchCriteria;
import utils.GenreUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GenreSearchStrategy implements SearchStrategy {
    private final GenreType genre;

    public GenreSearchStrategy(GenreType genre) {
        this.genre = genre;
    }

    @Override
    public List<Movie> search(Collection<Movie> movies) {
        return movies.stream()
                .filter(m -> GenreUtils.isGenreMatch(m.getGenre(), genre))
                .collect(Collectors.toList());
    }
}
